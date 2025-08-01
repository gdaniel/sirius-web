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
package org.eclipse.sirius.web.application.project.services.api;

import java.util.Optional;

import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;

/**
 * Handle node style appearance during diagram import.
 *
 * @author frouene
 */
public interface IDiagramImporterNodeStyleAppearanceChangeHandler {

    boolean canHandle(INodeStyle nodeStyle);

    Optional<IAppearanceChange> handle(String nodeId, INodeStyle nodeStyle, String customizedStyleProperty);
}
