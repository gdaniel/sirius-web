/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
package org.eclipse.sirius.components.diagrams;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Set;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * An edge.
 *
 * @author hmarchadour
 * @author sbegaudeau
 */
@Immutable
public final class Edge implements IDiagramElement {

    public static final String SELECTED_EDGE = "selectedEdge";

    public static final String PRE_SOURCE = "preSource";

    public static final String PRE_TARGET = "preTarget";

    public static final String PRE_SOURCE_VIEW = "preSourceView";

    public static final String PRE_TARGET_VIEW = "preTargetView";

    private String id;

    private String type;

    private String targetObjectId;

    private String targetObjectKind;

    private String targetObjectLabel;

    private String descriptionId;

    private Label beginLabel;

    private Label centerLabel;

    private Label endLabel;

    private String sourceId;

    private String targetId;

    private Set<ViewModifier> modifiers;

    private ViewModifier state;

    private EdgeStyle style;

    private boolean centerLabelEditable;

    private Edge() {
        // Prevent instantiation
    }

    public static Builder newEdge(String id) {
        return new Builder(id);
    }

    public static Builder newEdge(Edge edge) {
        return new Builder(edge);
    }

    @Override
    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    @Deprecated(forRemoval = true)
    public String getTargetObjectKind() {
        return this.targetObjectKind;
    }

    @Deprecated(forRemoval = true)
    public String getTargetObjectLabel() {
        return this.targetObjectLabel;
    }

    @Override
    public String getDescriptionId() {
        return this.descriptionId;
    }

    public Label getBeginLabel() {
        return this.beginLabel;
    }

    public Label getCenterLabel() {
        return this.centerLabel;
    }

    public Label getEndLabel() {
        return this.endLabel;
    }

    public String getSourceId() {
        return this.sourceId;
    }

    public String getTargetId() {
        return this.targetId;
    }

    public Set<ViewModifier> getModifiers() {
        return this.modifiers;
    }

    public ViewModifier getState() {
        return this.state;
    }

    public EdgeStyle getStyle() {
        return this.style;
    }

    public boolean isCenterLabelEditable() {
        return this.centerLabelEditable;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, targetObjectId: {2}, targetObjectKind: {3}, targetObjectLabel: {4}, descriptionId: {5}, sourceId: {6}, targetId: {7}, state: {8}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.targetObjectId, this.targetObjectKind, this.targetObjectLabel, this.descriptionId, this.sourceId,
                this.targetId, this.state.name());
    }

    /**
     * The builder used to create an edge.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String type;

        private String targetObjectId;

        private String targetObjectKind;

        private String targetObjectLabel;

        private String descriptionId;

        private Label beginLabel;

        private Label centerLabel;

        private Label endLabel;

        private String sourceId;

        private String targetId;

        private Set<ViewModifier> modifiers;

        private ViewModifier state;

        private EdgeStyle style;

        private boolean centerLabelEditable;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder(Edge edge) {
            this.id = edge.getId();
            this.type = edge.getType();
            this.targetObjectId = edge.getTargetObjectId();
            this.targetObjectKind = edge.getTargetObjectKind();
            this.targetObjectLabel = edge.getTargetObjectLabel();
            this.descriptionId = edge.getDescriptionId();
            this.beginLabel = edge.getBeginLabel();
            this.centerLabel = edge.getCenterLabel();
            this.endLabel = edge.getEndLabel();
            this.sourceId = edge.getSourceId();
            this.targetId = edge.getTargetId();
            this.modifiers = edge.getModifiers();
            this.state = edge.getState();
            this.style = edge.getStyle();
            this.centerLabelEditable = edge.isCenterLabelEditable();
        }

        public Builder type(String type) {
            this.type = Objects.requireNonNull(type);
            return this;
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder targetObjectKind(String targetObjectKind) {
            this.targetObjectKind = Objects.requireNonNull(targetObjectKind);
            return this;
        }

        public Builder targetObjectLabel(String targetObjectLabel) {
            this.targetObjectLabel = Objects.requireNonNull(targetObjectLabel);
            return this;
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder beginLabel(Label beginLabel) {
            this.beginLabel = beginLabel;
            return this;
        }

        public Builder centerLabel(Label centerLabel) {
            this.centerLabel = centerLabel;
            return this;
        }

        public Builder endLabel(Label endLabel) {
            this.endLabel = endLabel;
            return this;
        }

        public Builder sourceId(String sourceId) {
            this.sourceId = Objects.requireNonNull(sourceId);
            return this;
        }

        public Builder targetId(String targetId) {
            this.targetId = Objects.requireNonNull(targetId);
            return this;
        }

        public Builder modifiers(Set<ViewModifier> modifiers) {
            this.modifiers = Objects.requireNonNull(modifiers);
            return this;
        }

        public Builder state(ViewModifier state) {
            this.state = Objects.requireNonNull(state);
            return this;
        }

        public Builder style(EdgeStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public Builder centerLabelEditable(boolean centerLabelEditable) {
            this.centerLabelEditable = centerLabelEditable;
            return this;
        }

        public Edge build() {
            Edge edge = new Edge();
            edge.id = Objects.requireNonNull(this.id);
            edge.type = Objects.requireNonNull(this.type);
            edge.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            edge.targetObjectKind = Objects.requireNonNull(this.targetObjectKind);
            edge.targetObjectLabel = Objects.requireNonNull(this.targetObjectLabel);
            edge.descriptionId = Objects.requireNonNull(this.descriptionId);
            edge.beginLabel = this.beginLabel;
            edge.centerLabel = this.centerLabel;
            edge.endLabel = this.endLabel;
            edge.sourceId = Objects.requireNonNull(this.sourceId);
            edge.targetId = Objects.requireNonNull(this.targetId);
            edge.modifiers = Objects.requireNonNull(this.modifiers);
            edge.state = Objects.requireNonNull(this.state);
            edge.style = Objects.requireNonNull(this.style);
            edge.centerLabelEditable = this.centerLabelEditable;
            return edge;
        }
    }
}
