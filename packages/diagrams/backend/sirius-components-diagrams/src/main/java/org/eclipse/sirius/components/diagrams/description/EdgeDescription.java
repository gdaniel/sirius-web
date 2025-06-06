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
package org.eclipse.sirius.components.diagrams.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description of the edge.
 *
 * @author sbegaudeau
 */
@Immutable
public final class EdgeDescription implements IDiagramElementDescription {

    /**
     * The name of the variables which points to the representation element at the source/origin of a particular edge.
     */
    public static final String EDGE_SOURCE = "edgeSource";

    /**
     * The name of the variables which points to the representation element at the destination/target of a particular
     * edge.
     */
    public static final String EDGE_TARGET = "edgeTarget";

    /**
     * The name of the variables which points to the semantic element at the source/origin of a particular edge.
     */
    public static final String SEMANTIC_EDGE_SOURCE = "semanticEdgeSource";

    /**
     * The name of the variables which points to the semantic element at the destination/target of a particular edge.
     */
    public static final String SEMANTIC_EDGE_TARGET = "semanticEdgeTarget";

    public static final String GRAPHICAL_EDGE_SOURCE = "graphicalEdgeSource";

    public static final String GRAPHICAL_EDGE_TARGET = "graphicalEdgeTarget";

    private String id;

    private SynchronizationPolicy synchronizationPolicy;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Function<VariableManager, String> targetObjectKindProvider;

    private Function<VariableManager, String> targetObjectLabelProvider;

    private Function<VariableManager, List<?>> semanticElementsProvider;

    private Predicate<VariableManager> shouldRenderPredicate;

    private LabelDescription beginLabelDescription;

    private LabelDescription centerLabelDescription;

    private LabelDescription endLabelDescription;

    private List<IDiagramElementDescription> sourceDescriptions;

    private List<IDiagramElementDescription> targetDescriptions;

    private Function<VariableManager, List<Element>> sourceProvider;

    private Function<VariableManager, List<Element>> targetProvider;

    private Function<VariableManager, EdgeStyle> styleProvider;

    private Function<VariableManager, IStatus> deleteHandler;

    private IEdgeEditLabelHandler labelEditHandler;

    private EdgeDescription() {
        // Prevent instantiation
    }

    public static Builder newEdgeDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String getId() {
        return this.id;
    }

    public SynchronizationPolicy getSynchronizationPolicy() {
        return this.synchronizationPolicy;
    }

    public Function<VariableManager, String> getTargetObjectIdProvider() {
        return this.targetObjectIdProvider;
    }

    public Function<VariableManager, String> getTargetObjectKindProvider() {
        return this.targetObjectKindProvider;
    }

    public Function<VariableManager, String> getTargetObjectLabelProvider() {
        return this.targetObjectLabelProvider;
    }

    public Function<VariableManager, List<?>> getSemanticElementsProvider() {
        return this.semanticElementsProvider;
    }

    public Predicate<VariableManager> getShouldRenderPredicate() {
        return this.shouldRenderPredicate;
    }

    public LabelDescription getBeginLabelDescription() {
        return this.beginLabelDescription;
    }

    public LabelDescription getCenterLabelDescription() {
        return this.centerLabelDescription;
    }

    public LabelDescription getEndLabelDescription() {
        return this.endLabelDescription;
    }

    public List<IDiagramElementDescription> getSourceDescriptions() {
        return this.sourceDescriptions;
    }

    public List<IDiagramElementDescription> getTargetDescriptions() {
        return this.targetDescriptions;
    }

    public Function<VariableManager, List<Element>> getSourceProvider() {
        return this.sourceProvider;
    }

    public Function<VariableManager, List<Element>> getTargetProvider() {
        return this.targetProvider;
    }

    public Function<VariableManager, EdgeStyle> getStyleProvider() {
        return this.styleProvider;
    }

    public Function<VariableManager, IStatus> getDeleteHandler() {
        return this.deleteHandler;
    }

    public IEdgeEditLabelHandler getLabelEditHandler() {
        return this.labelEditHandler;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, sourceDescriptionCount: {2}, targetDescriptionCount: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.sourceDescriptions.size(), this.targetDescriptions.size());
    }

    /**
     * The builder of the edge description.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private SynchronizationPolicy synchronizationPolicy = SynchronizationPolicy.SYNCHRONIZED;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> targetObjectKindProvider;

        private Function<VariableManager, String> targetObjectLabelProvider;

        private Function<VariableManager, List<?>> semanticElementsProvider;

        private Predicate<VariableManager> shouldRenderPredicate = variableManager -> true;

        private LabelDescription beginLabelDescription;

        private LabelDescription centerLabelDescription;

        private LabelDescription endLabelDescription;

        private List<IDiagramElementDescription> sourceDescriptions;

        private List<IDiagramElementDescription> targetDescriptions;

        private Function<VariableManager, List<Element>> sourceProvider;

        private Function<VariableManager, List<Element>> targetProvider;

        private Function<VariableManager, EdgeStyle> styleProvider;

        private Function<VariableManager, IStatus> deleteHandler;

        private IEdgeEditLabelHandler labelEditHandler;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder synchronizationPolicy(SynchronizationPolicy synchronizationPolicy) {
            this.synchronizationPolicy = synchronizationPolicy;
            return this;
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
            return this;
        }

        public Builder targetObjectKindProvider(Function<VariableManager, String> targetObjectKindProvider) {
            this.targetObjectKindProvider = Objects.requireNonNull(targetObjectKindProvider);
            return this;
        }

        public Builder targetObjectLabelProvider(Function<VariableManager, String> targetObjectLabelProvider) {
            this.targetObjectLabelProvider = Objects.requireNonNull(targetObjectLabelProvider);
            return this;
        }

        public Builder semanticElementsProvider(Function<VariableManager, List<?>> semanticElementsProvider) {
            this.semanticElementsProvider = Objects.requireNonNull(semanticElementsProvider);
            return this;
        }

        public Builder shouldRenderPredicate(Predicate<VariableManager> shouldRenderPredicate) {
            this.shouldRenderPredicate = Objects.requireNonNull(shouldRenderPredicate);
            return this;
        }

        public Builder beginLabelDescription(LabelDescription beginLabelDescription) {
            this.beginLabelDescription = Objects.requireNonNull(beginLabelDescription);
            return this;
        }

        public Builder centerLabelDescription(LabelDescription centerLabelDescription) {
            this.centerLabelDescription = Objects.requireNonNull(centerLabelDescription);
            return this;
        }

        public Builder endLabelDescription(LabelDescription endLabelDescription) {
            this.endLabelDescription = Objects.requireNonNull(endLabelDescription);
            return this;
        }

        public Builder sourceDescriptions(List<IDiagramElementDescription> sourceDescriptions) {
            this.sourceDescriptions = Objects.requireNonNull(sourceDescriptions);
            return this;
        }

        public Builder targetDescriptions(List<IDiagramElementDescription> targetDescriptions) {
            this.targetDescriptions = Objects.requireNonNull(targetDescriptions);
            return this;
        }

        public Builder sourceProvider(Function<VariableManager, List<Element>> sourceProvider) {
            this.sourceProvider = Objects.requireNonNull(sourceProvider);
            return this;

        }

        public Builder targetProvider(Function<VariableManager, List<Element>> targetProvider) {
            this.targetProvider = Objects.requireNonNull(targetProvider);
            return this;
        }

        public Builder styleProvider(Function<VariableManager, EdgeStyle> styleProvider) {
            this.styleProvider = Objects.requireNonNull(styleProvider);
            return this;
        }

        public Builder deleteHandler(Function<VariableManager, IStatus> deleteHandler) {
            this.deleteHandler = Objects.requireNonNull(deleteHandler);
            return this;
        }

        public Builder labelEditHandler(IEdgeEditLabelHandler labelEditHandler) {
            this.labelEditHandler = Objects.requireNonNull(labelEditHandler);
            return this;
        }

        public EdgeDescription build() {
            EdgeDescription edgeDescription = new EdgeDescription();
            edgeDescription.id = Objects.requireNonNull(this.id);
            edgeDescription.synchronizationPolicy = this.synchronizationPolicy;
            edgeDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            edgeDescription.targetObjectKindProvider = Objects.requireNonNull(this.targetObjectKindProvider);
            edgeDescription.targetObjectLabelProvider = Objects.requireNonNull(this.targetObjectLabelProvider);
            edgeDescription.sourceDescriptions = Objects.requireNonNull(this.sourceDescriptions);
            edgeDescription.targetDescriptions = Objects.requireNonNull(this.targetDescriptions);
            edgeDescription.semanticElementsProvider = Objects.requireNonNull(this.semanticElementsProvider);
            edgeDescription.shouldRenderPredicate = Objects.requireNonNull(this.shouldRenderPredicate);
            edgeDescription.beginLabelDescription = this.beginLabelDescription;
            edgeDescription.centerLabelDescription = this.centerLabelDescription;
            edgeDescription.endLabelDescription = this.endLabelDescription;
            edgeDescription.sourceProvider = Objects.requireNonNull(this.sourceProvider);
            edgeDescription.targetProvider = Objects.requireNonNull(this.targetProvider);
            edgeDescription.styleProvider = Objects.requireNonNull(this.styleProvider);
            edgeDescription.deleteHandler = Objects.requireNonNull(this.deleteHandler);
            edgeDescription.labelEditHandler = this.labelEditHandler; // Optional on purpose
            return edgeDescription;
        }

    }
}
