/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import { gql, useMutation } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import {
  GQLErrorPayload,
  GQLExecuteOmniboxCommandMutationData,
  GQLExecuteOmniboxCommandMutationVariables,
  GQLExecuteOmniboxCommandPayload,
  GQLSuccessPayload,
  UseExecuteOmniboxCommandValue,
} from './useExecuteOmniboxCommand.types';

const executeOmniboxCommandMutation = gql`
  mutation executeOmniboxCommand($input: ExecuteCommandInput!) {
    executeCommand(input: $input) {
      __typename
      ... on SuccessPayload {
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

const isErrorPayload = (payload: GQLExecuteOmniboxCommandPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isSuccessPayload = (payload: GQLExecuteOmniboxCommandPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const useExecuteOmniboxCommand = (): UseExecuteOmniboxCommandValue => {
  const { addErrorMessage, addMessages } = useMultiToast();
  const [performExecuteOmniboxCommand, { loading, data }] = useMutation<
    GQLExecuteOmniboxCommandMutationData,
    GQLExecuteOmniboxCommandMutationVariables
  >(executeOmniboxCommandMutation, {
    onCompleted: (data) => {
      const { executeCommand } = data;
      if (isErrorPayload(executeCommand)) {
        addMessages(executeCommand.messages);
      }
      if (isSuccessPayload(executeCommand)) {
        addMessages(executeCommand.messages);
      }
    },
    onError: () => {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    },
  });

  const executeOmniboxCommand = (editingContextId: string, omniboxCommandId: string) => {
    const variables: GQLExecuteOmniboxCommandMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        omniboxCommandId,
      },
    };
    performExecuteOmniboxCommand({ variables });
  };

  return {
    executeOmniboxCommand,
    loading,
    commandData: data?.executeCommand ?? null,
  };
};
