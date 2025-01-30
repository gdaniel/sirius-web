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
  GQLPublishLibrariesMutationData,
  GQLPublishLibrariesMutationVariables,
  GQLPublishLibrariesPayload,
  GQLPublishLibrariesSuccessPayload,
  UsePublishLibrariesValue,
} from './usePublishLibraries.types';

const publishLibrariesMutation = gql`
  mutation publishLibraries($input: PublishLibrariesInput!) {
    publishLibraries(input: $input) {
      __typename
      ... on PublishLibrariesSuccessPayload {
        libraries {
          id
        }
      }
      ... on ErrorPayload {
        message
        messages {
          level
          body
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLPublishLibrariesPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isSuccessPayload = (payload: GQLPublishLibrariesPayload): payload is GQLPublishLibrariesSuccessPayload =>
  payload.__typename === 'PublishLibrariesSuccessPayload';

export const usePublishLibraries = (): UsePublishLibrariesValue => {
  const { addErrorMessage, addMessages } = useMultiToast();
  const [performPublishLibraries, { loading, data }] = useMutation<
    GQLPublishLibrariesMutationData,
    GQLPublishLibrariesMutationVariables
  >(publishLibrariesMutation, {
    onCompleted: (data) => {
      const { publishLibraries } = data;
      if (isErrorPayload(publishLibraries)) {
        addMessages(publishLibraries.messages);
      }
      if (isSuccessPayload(publishLibraries)) {
        addMessages([{ body: publishLibraries.libraries.length + ' libraries published', level: 'success' }]);
      }
    },
    onError: () => {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    },
  });

  const publishLibraries = (projectId: string, version: string, description: string) => {
    const variables: GQLPublishLibrariesMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        projectId,
        version,
        description,
      },
    };
    performPublishLibraries({ variables });
  };

  return {
    publishLibraries,
    loading,
    commandData: data?.publishLibraries ?? null,
  };
};
