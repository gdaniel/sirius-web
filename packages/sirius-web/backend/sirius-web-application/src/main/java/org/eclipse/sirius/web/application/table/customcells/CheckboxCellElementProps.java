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
package org.eclipse.sirius.web.application.table.customcells;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.representations.IProps;

/**
 * Properties of the cell element.
 *
 * @author lfasani
 */
public record CheckboxCellElementProps(UUID id, String descriptionId, String targetObjectId, String targetObjectKind, UUID columnId, boolean value, String tooltipValue) implements IProps {

    public static final String TYPE = "CheckboxCell";

    public CheckboxCellElementProps {
        Objects.requireNonNull(id);
        Objects.requireNonNull(descriptionId);
        Objects.requireNonNull(targetObjectId);
        Objects.requireNonNull(targetObjectKind);
        Objects.requireNonNull(columnId);
        Objects.requireNonNull(tooltipValue);
    }
}
