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
package org.eclipse.sirius.components.tables;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * Textfield-based Cell concept of the table representation.
 *
 * @author arichard
 */
@Immutable
public final class TextfieldCell implements ICell {

    public static final String TYPE = "TEXTFIELD";

    private UUID id;

    private String descriptionId;

    private String targetObjectId;

    private String targetObjectKind;

    private UUID columnId;

    private String value;

    private String tooltipValue;

    private TextfieldCell() {
        // Prevent instantiation
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getDescriptionId() {
        return this.descriptionId;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    @Override
    public String getTargetObjectKind() {
        return this.targetObjectKind;
    }

    @Override
    public UUID getColumnId() {
        return this.columnId;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String getTooltipValue() {
        return this.tooltipValue;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, columnId: {2}, value: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId(), this.columnId, this.value);
    }

    public static Builder newTextfieldCell(UUID id) {
        return new Builder(id);
    }

    /**
     * The builder used to create a cell.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final UUID id;

        private String descriptionId;

        private String targetObjectId;

        private String targetObjectKind;

        private UUID columnId;

        private String value;

        private String tooltipValue;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder targetObjectKind(String targetObjectKind) {
            this.targetObjectKind = Objects.requireNonNull(targetObjectKind);
            return this;
        }

        public Builder columnId(UUID columnId) {
            this.columnId = Objects.requireNonNull(columnId);
            return this;
        }

        public Builder value(String value) {
            this.value = Objects.requireNonNull(value);
            return this;
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder tooltipValue(String tooltipValue) {
            this.tooltipValue = Objects.requireNonNull(tooltipValue);
            return this;
        }

        public TextfieldCell build() {
            TextfieldCell cell = new TextfieldCell();
            cell.id = Objects.requireNonNull(this.id);
            cell.descriptionId = Objects.requireNonNull(this.descriptionId);
            cell.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            cell.targetObjectKind = Objects.requireNonNull(this.targetObjectKind);
            cell.columnId = Objects.requireNonNull(this.columnId);
            cell.value = Objects.requireNonNull(this.value);
            cell.tooltipValue = Objects.requireNonNull(this.tooltipValue);
            return cell;
        }
    }
}
