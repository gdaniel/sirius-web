/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { gql, useMutation, useQuery } from '@apollo/client';
import { IconOverlay, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import InputLabel from '@mui/material/InputLabel';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';
import { makeStyles } from 'tss-react/mui';
import { StateMachine } from 'xstate';
import {
  GQLCreateChildMutationData,
  GQLCreateChildPayload,
  GQLCreateChildSuccessPayload,
  GQLErrorPayload,
  GQLGetChildCreationDescriptionsQueryData,
  GQLGetChildCreationDescriptionsQueryVariables,
  NewObjectModalProps,
} from './NewObjectModal.types';
import {
  ChangeChildCreationDescriptionEvent,
  CreateChildEvent,
  FetchedChildCreationDescriptionsEvent,
  HandleResponseEvent,
  NewObjectModalContext,
  NewObjectModalEvent,
  NewObjectModalStateSchema,
  SchemaValue,
  newObjectModalMachine,
} from './NewObjectModalMachine';

const createChildMutation = gql`
  mutation createChild($input: CreateChildInput!) {
    createChild(input: $input) {
      __typename
      ... on CreateChildSuccessPayload {
        object {
          id
        }
        messages {
          body
          level
        }
      }
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const getChildCreationDescriptionsQuery = gql`
  query getChildCreationDescriptions($editingContextId: ID!, $kind: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        childCreationDescriptions(kind: $kind) {
          id
          label
          iconURL
        }
      }
    }
  }
`;

const useNewObjectModalStyles = makeStyles()((theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      marginBottom: theme.spacing(1),
    },
  },
  select: {
    '&': {
      display: 'flex',
      alignItems: 'center',
    },
  },
  iconRoot: {
    minWidth: theme.spacing(3),
  },
}));

const isErrorPayload = (payload: GQLCreateChildPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLCreateChildPayload): payload is GQLCreateChildSuccessPayload =>
  payload.__typename === 'CreateChildSuccessPayload';

export const NewObjectModal = ({ editingContextId, item, onObjectCreated, onClose }: NewObjectModalProps) => {
  const { classes } = useNewObjectModalStyles();
  const { addErrorMessage, addMessages } = useMultiToast();
  const [{ value, context }, dispatch] =
    useMachine<StateMachine<NewObjectModalContext, NewObjectModalStateSchema, NewObjectModalEvent>>(
      newObjectModalMachine
    );
  const { newObjectModal } = value as SchemaValue;
  const { selectedChildCreationDescriptionId, childCreationDescriptions, objectToSelect } = context;

  const {
    loading: childCreationDescriptionsLoading,
    data: childCreationDescriptionsData,
    error: childCreationDescriptionsError,
  } = useQuery<GQLGetChildCreationDescriptionsQueryData, GQLGetChildCreationDescriptionsQueryVariables>(
    getChildCreationDescriptionsQuery,
    { variables: { editingContextId, kind: item.kind } }
  );
  useEffect(() => {
    if (!childCreationDescriptionsLoading) {
      if (childCreationDescriptionsError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (childCreationDescriptionsData) {
        const fetchChildCreationDescriptionsEvent: FetchedChildCreationDescriptionsEvent = {
          type: 'HANDLE_FETCHED_CHILD_CREATION_DESCRIPTIONS',
          data: childCreationDescriptionsData,
        };
        dispatch(fetchChildCreationDescriptionsEvent);
      }
    }
  }, [childCreationDescriptionsLoading, childCreationDescriptionsData, childCreationDescriptionsError]);

  const onChildCreationDescriptionChange = (event) => {
    const value = event.target.value;
    const changeChildCreationDescriptionEvent: ChangeChildCreationDescriptionEvent = {
      type: 'CHANGE_CHILD_CREATION_DESCRIPTION',
      childCreationDescriptionId: value,
    };
    dispatch(changeChildCreationDescriptionEvent);
  };

  const [createChild, { loading: createChildLoading, data: createChildData, error: createChildError }] =
    useMutation<GQLCreateChildMutationData>(createChildMutation);
  useEffect(() => {
    if (!createChildLoading) {
      if (createChildError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (createChildData) {
        const handleResponseEvent: HandleResponseEvent = { type: 'HANDLE_RESPONSE', data: createChildData };
        dispatch(handleResponseEvent);

        const { createChild } = createChildData;
        if (isErrorPayload(createChild) || isSuccessPayload(createChild)) {
          const { messages } = createChild;
          addMessages(messages);
        }
      }
    }
  }, [createChildLoading, createChildData, createChildError]);

  const onCreateObject = () => {
    dispatch({ type: 'CREATE_CHILD' } as CreateChildEvent);
    const input = {
      id: crypto.randomUUID(),
      editingContextId,
      objectId: item.id,
      childCreationDescriptionId: selectedChildCreationDescriptionId,
    };
    createChild({ variables: { input } });
  };

  useEffect(() => {
    if (newObjectModal === 'success') {
      onObjectCreated({ entries: [objectToSelect] });
    }
  }, [newObjectModal, objectToSelect]);
  return (
    <>
      <Dialog
        open={true}
        onClose={onClose}
        aria-labelledby="dialog-title"
        maxWidth="xs"
        fullWidth
        data-testid={'new-object-modal'}>
        <DialogTitle id="dialog-title">Create a new object</DialogTitle>
        <DialogContent>
          <div className={classes.form}>
            <InputLabel id="newObjectModalChildCreationDescriptionLabel">Object type</InputLabel>
            <Select
              variant="standard"
              classes={{ select: classes.select }}
              value={selectedChildCreationDescriptionId}
              onChange={onChildCreationDescriptionChange}
              disabled={newObjectModal === 'loading' || newObjectModal === 'creatingChild'}
              labelId="newObjectModalChildCreationDescriptionLabel"
              fullWidth
              data-testid="childCreationDescription">
              {childCreationDescriptions.map((childCreationDescription) => (
                <MenuItem value={childCreationDescription.id} key={childCreationDescription.id}>
                  {childCreationDescription.iconURL.length > 0 && (
                    <ListItemIcon className={classes.iconRoot}>
                      <IconOverlay iconURL={childCreationDescription.iconURL} alt={childCreationDescription.label} />
                    </ListItemIcon>
                  )}
                  <ListItemText primary={childCreationDescription.label} />
                </MenuItem>
              ))}
            </Select>
          </div>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            disabled={newObjectModal !== 'valid'}
            data-testid="create-object"
            color="primary"
            onClick={onCreateObject}>
            Create
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};
