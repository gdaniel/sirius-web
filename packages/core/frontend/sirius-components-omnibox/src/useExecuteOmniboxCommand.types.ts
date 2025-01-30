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

import { GQLMessage } from '@eclipse-sirius/sirius-components-core';

export interface UseExecuteOmniboxCommandValue {
  executeOmniboxCommand: (editingContextId: string, omniboxCommandId: string) => void;
  loading: boolean;
  commandData: GQLExecuteOmniboxCommandPayload | null;
}

export interface GQLExecuteOmniboxCommandMutationVariables {
  input: GQLExecuteOmniboxMutationInput;
}

export interface GQLExecuteOmniboxMutationInput {
  id: string;
  editingContextId: string;
  omniboxCommandId: string;
}

export interface GQLExecuteOmniboxCommandMutationData {
  executeCommand: GQLExecuteOmniboxCommandPayload;
}

export interface GQLExecuteOmniboxCommandPayload {
  __typename: string;
}

export interface GQLSuccessPayload extends GQLExecuteOmniboxCommandPayload {
  messages: GQLMessage[];
}

export interface GQLErrorPayload extends GQLExecuteOmniboxCommandPayload {
  messages: GQLMessage[];
}
