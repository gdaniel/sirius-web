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
package org.eclipse.sirius.web.application.views.explorer.services.api;

/**
 * Used to provide the default behavior of the labels in the explorer.
 *
 * @author sbegaudeau
 */
public interface IDefaultExplorerLabelService {

    boolean isEditable(Object self);

    void editLabel(Object self, String newValue);
}
