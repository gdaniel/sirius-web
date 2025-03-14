/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

export interface EditSelectCellValue {
  editSelectCell: (newValue: string) => void;
  loading: boolean;
}

export interface GQLEditSelectCellMutationData {
  editSelectCell: GQLEditSelectCellPayload;
}

export interface GQLEditSelectCellMutationVariables {
  input: GQLEditSelectCellInput;
}

export interface GQLEditSelectCellInput {
  id: string;
  editingContextId: string;
  representationId: string;
  tableId: string;
  cellId: string;
  newValue: string;
}

export type GQLEditSelectCellPayload = GQLErrorPayload | GQLSuccessPayload;
