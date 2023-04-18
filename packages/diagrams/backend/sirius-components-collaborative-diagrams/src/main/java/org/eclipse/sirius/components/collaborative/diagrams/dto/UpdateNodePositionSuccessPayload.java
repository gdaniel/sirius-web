/*******************************************************************************
 * Copyright (c) 2021, 2023 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.diagrams.Diagram;

/**
 * The payload of the "Update Node Position" mutation returned on success.
 *
 * @author fbarbin
 */
public record UpdateNodePositionSuccessPayload(UUID id, Diagram diagram) implements IPayload {
    public UpdateNodePositionSuccessPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(diagram);
    }
}
