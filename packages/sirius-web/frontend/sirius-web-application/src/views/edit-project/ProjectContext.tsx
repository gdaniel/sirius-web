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

import React from 'react';
import { ProjectContextValue } from './ProjectContext.types';

const value: ProjectContextValue = {
  project: {
    id: '',
    name: '',
    natures: [],
    capabilities: {
      canDownload: false,
      canRename: false,
      canDelete: false,
      canEdit: false,
    },
    currentEditingContext: {
      id: '',
    },
  },
};

export const ProjectContext = React.createContext<ProjectContextValue>(value);
