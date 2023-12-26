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
package org.eclipse.sirius.components.collaborative.gantt.dto.input;

import java.util.UUID;

import org.eclipse.sirius.components.collaborative.gantt.api.IGanttInput;

/**
 * The class of the inputs for the "Delete Gantt task" mutation.
 *
 * @author lfasani
 */
public record DeleteGanttTaskInput(UUID id, String editingContextId, String representationId, String taskId) implements IGanttInput {
}
