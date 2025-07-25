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

import { GQLErrorPayload, GQLSuccessPayload } from '@eclipse-sirius/sirius-components-core';

export interface UseResetNodeAppearanceValue {
  resetNodeStyleProperties: (
    editingContextId: string,
    representationId: string,
    nodeId: string,
    propertiesToReset: string[]
  ) => void;
}

export interface GQLResetNodeApparenceData {
  resetNodeAppearance: GQLResetNodeApparencePayload;
}

export type GQLResetNodeApparencePayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLResetNodeAppearanceInput {
  id: string;
  editingContextId: string;
  representationId: string;
  nodeId: string;
  propertiesToReset: string[];
}

export interface GQLResetNodeApparenceVariables {
  input: GQLResetNodeAppearanceInput;
}
