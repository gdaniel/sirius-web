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
import { GQLManageVisibilityAction } from './useManageVisibilityActions.types';

export interface UseManageVisibilityInvokeActionValue {
  invokeAction: (diagramElementId: string, action: GQLManageVisibilityAction) => void;
}

export interface GQLManageVisibilityInvokeActionPayload {
  __typename: string;
}

export interface GQLSuccessPayload extends GQLManageVisibilityInvokeActionPayload {
  messages: GQLMessage[];
}

export interface GQLErrorPayload extends GQLManageVisibilityInvokeActionPayload {
  messages: GQLMessage[];
}

export interface GQLManageVisibilityInvokeActionData {
  invokeManageVisibilityAction: GQLManageVisibilityInvokeActionPayload;
}

export interface GQLManageVisibilityInvokeActionVariables {
  input: GQLManageVisibilityInvokeActionInput;
}

export interface GQLManageVisibilityInvokeActionInput {
  id: string;
  editingContextId: string;
  representationId: string;
  diagramElementId: string;
  actionId: string;
}
