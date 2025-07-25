/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import { GQLFormDescriptionEditor } from './FormDescriptionEditorEventFragment.types';

export type Kind =
  | 'Textfield'
  | 'TextArea'
  | 'Checkbox'
  | 'Radio'
  | 'Slider'
  | 'Select'
  | 'MultiSelect'
  | 'Button'
  | 'Label'
  | 'Link'
  | 'List'
  | 'BarChart'
  | 'PieChart'
  | 'FlexboxContainer'
  | 'RichText'
  | 'FormElementFor'
  | 'FormElementIf'
  | 'SplitButton'
  | 'DateTime'
  | 'Tree';

export type WidgetDescriptor = {
  name: string;
  label?: string | null;
  icon: JSX.Element;
};

export type FormDescriptionEditorRepresentationState = {
  formDescriptionEditor: GQLFormDescriptionEditor | null;
};
