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
import { GQLSplitButton } from '../form/FormEventFragments.types';

export interface SplitButtonPropertySectionProps {
  editingContextId: string;
  formId: string;
  widget: GQLSplitButton;
  readOnly: boolean;
}

export interface SplitButtonState {
  open: boolean;
  selectedIndex: number;
}
