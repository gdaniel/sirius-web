/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { useMutation } from '@apollo/client';
import { ServerContext, ServerContextValue, getCSSColor, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import Button from '@mui/material/Button';
import ButtonGroup from '@mui/material/ButtonGroup';
import ClickAwayListener from '@mui/material/ClickAwayListener';
import Grow from '@mui/material/Grow';
import MenuItem from '@mui/material/MenuItem';
import MenuList from '@mui/material/MenuList';
import Paper from '@mui/material/Paper';
import Popper from '@mui/material/Popper';
import gql from 'graphql-tag';
import { useContext, useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { GQLButton } from '../form/FormEventFragments.types';
import {
  ButtonStyleProps,
  GQLErrorPayload,
  GQLPushButtonInput,
  GQLPushButtonMutationData,
  GQLPushButtonMutationVariables,
  GQLPushButtonPayload,
  GQLSuccessPayload,
} from './ButtonPropertySection.types';
import { getTextDecorationLineValue } from './getTextDecorationLineValue';
import { LoadingIndicator } from './LoadingIndicator';
import { PropertySectionLabel } from './PropertySectionLabel';
import { SplitButtonPropertySectionProps, SplitButtonState } from './SplitButtonPropertySection.types';

const useStyle = makeStyles<ButtonStyleProps>()(
  (theme, { backgroundColor, foregroundColor, fontSize, italic, bold, underline, strikeThrough, iconOnly }) => ({
    style: {
      backgroundColor: backgroundColor ? getCSSColor(backgroundColor, theme) : theme.palette.primary.light,
      color: foregroundColor ? getCSSColor(foregroundColor, theme) : 'white',
      fontSize: fontSize ? fontSize : null,
      fontStyle: italic ? 'italic' : null,
      fontWeight: bold ? 'bold' : null,
      textDecorationLine: getTextDecorationLineValue(underline, strikeThrough),
      paddingTop: theme.spacing(0.5),
      paddingBottom: theme.spacing(0.5),
      '&:hover': {
        backgroundColor: backgroundColor ? getCSSColor(backgroundColor, theme) : theme.palette.primary.main,
        color: foregroundColor ? getCSSColor(foregroundColor, theme) : 'white',
        fontSize: fontSize ? fontSize : null,
        fontStyle: italic ? 'italic' : null,
        fontWeight: bold ? 'bold' : null,
        textDecorationLine: getTextDecorationLineValue(underline, strikeThrough),
      },
      '&.Mui-disabled': {
        color: theme.palette.action.disabled,
        backgroundColor: theme.palette.action.disabledBackground,
        opacity: 1,
      },
    },
    icon: {
      marginRight: iconOnly ? theme.spacing(0) : theme.spacing(2),
      height: theme.spacing(2),
      width: theme.spacing(2),
    },
  })
);

const useContainerStyle = makeStyles()((theme) => ({
  style: {
    display: 'flex',
    flexDirection: 'row',
    gap: theme.spacing(2),
  },
}));

export const pushButtonMutation = gql`
  mutation pushButton($input: PushButtonInput!) {
    pushButton(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLPushButtonPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isSuccessPayload = (payload: GQLPushButtonPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const SplitButtonPropertySection = ({
  editingContextId,
  formId,
  widget,
  readOnly,
}: SplitButtonPropertySectionProps) => {
  const { addErrorMessage, addMessages } = useMultiToast();
  const [state, setState] = useState<SplitButtonState>({
    open: false,
    selectedIndex: 0,
  });

  const buttonGroupRef = useRef<HTMLDivElement>(null);

  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  if (widget.actions.length == 0) {
    return null;
  }

  const firstEnabledAction = widget.actions.find((action) => !action.readOnly);

  useEffect(() => {
    if (!!firstEnabledAction) {
      setState((prevState) => ({ ...prevState, selectedIndex: widget.actions.indexOf(firstEnabledAction) }));
    }
  }, []);

  const classes = widget.actions.map((action) => {
    const props: ButtonStyleProps = {
      backgroundColor: action.style?.backgroundColor ?? null,
      foregroundColor: action.style?.foregroundColor ?? null,
      fontSize: action.style?.fontSize ?? null,
      italic: action.style?.italic ?? null,
      bold: action.style?.bold ?? null,
      underline: action.style?.underline ?? null,
      strikeThrough: action.style?.strikeThrough ?? null,
      iconOnly: action.buttonLabel ? false : true,
    };
    return useStyle(props).classes;
  });

  const { classes: containerClasses } = useContainerStyle();

  const [pushButton, { loading, data, error }] = useMutation<GQLPushButtonMutationData, GQLPushButtonMutationVariables>(
    pushButtonMutation
  );

  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (data) {
      const { pushButton } = data;
      if (isErrorPayload(pushButton) || isSuccessPayload(pushButton)) {
        addMessages(pushButton.messages);
      }
    }
  }, [error, data]);

  const handleClick = () => {
    const button: GQLButton = widget.actions[state.selectedIndex];
    const input: GQLPushButtonInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: formId,
      buttonId: button.id,
    };
    const variables: GQLPushButtonMutationVariables = { input };
    pushButton({ variables });
  };

  const handleMenuItemClick = (_event, index) => {
    setState((prevState) => ({ ...prevState, open: false, selectedIndex: index }));
  };

  const handleToggle = () => {
    setState((prevState) => ({ ...prevState, open: !prevState.open }));
  };

  const handleClose = (event) => {
    if (buttonGroupRef.current && buttonGroupRef.current.contains(event.target)) {
      return;
    }
    setState((prevState) => ({ ...prevState, open: false }));
  };

  return (
    <div className={containerClasses.style}>
      <PropertySectionLabel
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        data-testid={widget.label}
      />
      <ButtonGroup variant="contained" color="primary" ref={buttonGroupRef} aria-label="split button">
        <Button
          data-testid={widget.label}
          variant="contained"
          color="primary"
          onClick={handleClick}
          disabled={readOnly || widget.readOnly || widget.actions[state.selectedIndex].readOnly}
          classes={{ root: classes[state.selectedIndex].style }}>
          {widget.actions[state.selectedIndex].imageURL?.length > 0 ? (
            <img
              className={classes[state.selectedIndex].icon}
              alt={widget.actions[state.selectedIndex].label}
              src={httpOrigin + widget.actions[state.selectedIndex].imageURL}
            />
          ) : null}
          {widget.actions[state.selectedIndex].buttonLabel}
        </Button>
        <Button
          color="primary"
          size="small"
          aria-controls={state.open ? 'split-button-menu' : undefined}
          aria-expanded={state.open ? 'true' : undefined}
          aria-label="select button action"
          aria-haspopup="menu"
          role={'show-actions'}
          disabled={readOnly || widget.readOnly}
          onClick={handleToggle}
          classes={{ root: classes[state.selectedIndex].style }}>
          <ArrowDropDownIcon />
        </Button>
      </ButtonGroup>
      <LoadingIndicator loading={loading} />
      <Popper open={state.open} anchorEl={buttonGroupRef.current} transition placement="bottom">
        {({ TransitionProps, placement }) => (
          <Grow
            {...TransitionProps}
            style={{
              transformOrigin: placement === 'bottom' ? 'center top' : 'center bottom',
            }}>
            <Paper>
              <ClickAwayListener onClickAway={handleClose}>
                <MenuList id="split-button-menu">
                  {widget.actions.map((option, index) => (
                    <MenuItem
                      key={index}
                      selected={index === state.selectedIndex}
                      onClick={(event) => handleMenuItemClick(event, index)}
                      classes={{ root: classes[index].style }}
                      disabled={readOnly || widget.readOnly || widget.actions[index].readOnly}>
                      {option.imageURL?.length > 0 ? (
                        <img className={classes[index].icon} alt={option.label} src={httpOrigin + option.imageURL} />
                      ) : null}
                      {option.buttonLabel}
                    </MenuItem>
                  ))}
                </MenuList>
              </ClickAwayListener>
            </Paper>
          </Grow>
        )}
      </Popper>
    </div>
  );
};
