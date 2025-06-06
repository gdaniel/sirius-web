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
package org.eclipse.sirius.components.collaborative.trees.architecture.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.sirius.components.collaborative.trees.dto.ExpandAllTreePathInput;
import org.eclipse.sirius.components.collaborative.trees.dto.ExpandAllTreePathSuccessPayload;
import org.eclipse.sirius.components.collaborative.trees.handlers.DefaultExpandAllTreePathHandler;
import org.eclipse.sirius.components.collaborative.trees.services.api.ITreeNavigationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.junit.jupiter.api.Test;

/**
 * Test for the DefaultExpandAllTreePathHandler.
 *
 * @author frouene
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DefaultExpandAllTreePathHandlerTests {

    @Test
    public void testMaximumDepthReached() {
        AtomicBoolean  isIndexVariableAvailableForGetChildren = new AtomicBoolean(true);
        AtomicBoolean  isAncestorVariableAvaibleForGetChildren = new AtomicBoolean(true);
        AtomicBoolean  isIndexVariableAvailableForHasChildren = new AtomicBoolean(true);
        AtomicBoolean  isAncestorVariableAvaibleForHasChildren = new AtomicBoolean(true);

        IRepresentationDescriptionSearchService representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String id) {
                TreeDescription diagramDescription = TreeDescription.newTreeDescription("descriptionId")
                        .canCreatePredicate(variableManager -> true)
                        .childrenProvider(this::getChildren)
                        .deletableProvider(variableManager -> false)
                        .deleteHandler(variableManager -> new Success())
                        .editableProvider(variableManager -> true)
                        .elementsProvider(variableManager -> List.of())
                        .hasChildrenProvider(this::hasChildren)
                        .treeItemIconURLsProvider(variableManager -> List.of())
                        .idProvider(variableManager -> "id")
                        .kindProvider(variableManager -> "kind")
                        .label("Fake tree")
                        .labelProvider(variableManager -> null)
                        .parentObjectProvider(variableManager -> null)
                        .renameHandler((v, name) -> new Success())
                        .selectableProvider(variableManager -> true)
                        .targetObjectIdProvider(variableManager -> null)
                        .treeItemIdProvider(variableManager -> "itemId")
                        .treeItemObjectProvider(variableManager -> new Object())
                        .treeItemLabelProvider(variableManager -> null)
                        .iconURLsProvider(variableManager -> List.of())
                        .build();

                return Optional.of(diagramDescription);
            }

            private List<Object> getChildren(VariableManager variableManager) {
                isIndexVariableAvailableForGetChildren.set(isIndexVariableAvailableForGetChildren.get() && variableManager.get(TreeRenderer.INDEX, Integer.class).isPresent());
                isAncestorVariableAvaibleForGetChildren.set(isAncestorVariableAvaibleForGetChildren.get() && variableManager.get(TreeRenderer.ANCESTOR_IDS, List.class).isPresent());
                return List.of(new Object());
            }
            private boolean hasChildren(VariableManager variableManager) {
                isIndexVariableAvailableForHasChildren.set(isIndexVariableAvailableForHasChildren.get() && variableManager.get(TreeRenderer.INDEX, Integer.class).isPresent());
                isAncestorVariableAvaibleForHasChildren.set(isAncestorVariableAvaibleForHasChildren.get() && variableManager.get(TreeRenderer.ANCESTOR_IDS, List.class).isPresent());
                return true;
            }
        };

        var handler = new DefaultExpandAllTreePathHandler(new ITreeNavigationService.NoOp(), representationDescriptionSearchService, new IURLParser.NoOp(), List.of());
        Tree tree = this.createTree();

        var input = new ExpandAllTreePathInput(UUID.randomUUID(), "editingContextId", "representationId", "treeItemId");

        var result = handler.handle(new IEditingContext.NoOp(), tree, input);
        assertThat(isIndexVariableAvailableForGetChildren.get()).as("Missing index in children provider").isTrue();
        assertThat(isAncestorVariableAvaibleForGetChildren.get()).as("Missing ancestor variable in children provider").isTrue();
        assertThat(isIndexVariableAvailableForHasChildren.get()).as("Missing index in has children provider").isTrue();
        assertThat(isAncestorVariableAvaibleForHasChildren.get()).as("Missing ancestor variable in has children provider").isTrue();
        assertThat(result).isNotNull().isInstanceOf(ExpandAllTreePathSuccessPayload.class);
        assertThat(((ExpandAllTreePathSuccessPayload) result).treePath().getMaxDepth()).isEqualTo(100);

    }

    @Test
    public void activeFilterIdsIsSet() {
        AtomicBoolean hasActiveFilterIds = new AtomicBoolean(false);

        IRepresentationDescriptionSearchService representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String id) {
                TreeDescription diagramDescription = TreeDescription.newTreeDescription("descriptionId")
                        .canCreatePredicate(variableManager -> true)
                        .childrenProvider(variableManager -> List.of())
                        .deletableProvider(variableManager -> false)
                        .deleteHandler(variableManager -> new Success())
                        .editableProvider(variableManager -> true)
                        .elementsProvider(variableManager -> List.of())
                        .hasChildrenProvider(variableManager -> {
                            // Keep the test simple by checking the variable in hasChildren instead of getChildren, so we don't have to mock children.
                            hasActiveFilterIds.set(variableManager.hasVariable(TreeRenderer.ACTIVE_FILTER_IDS));
                            return false;
                        })
                        .treeItemIconURLsProvider(variableManager -> List.of())
                        .idProvider(variableManager -> "id")
                        .kindProvider(variableManager -> "kind")
                        .label("Fake tree")
                        .labelProvider(variableManager -> null)
                        .parentObjectProvider(variableManager -> null)
                        .renameHandler((v, name) -> new Success())
                        .selectableProvider(variableManager -> true)
                        .targetObjectIdProvider(variableManager -> null)
                        .treeItemIdProvider(variableManager -> "itemId")
                        .treeItemObjectProvider(variableManager -> new Object())
                        .treeItemLabelProvider(variableManager -> null)
                        .iconURLsProvider(variableManager -> List.of())
                        .build();

                return Optional.of(diagramDescription);
            }
        };

        var handler = new DefaultExpandAllTreePathHandler(new ITreeNavigationService.NoOp(), representationDescriptionSearchService, new IURLParser.NoOp(), List.of());
        Tree tree = this.createTree();

        var input = new ExpandAllTreePathInput(UUID.randomUUID(), "editingContextId", "representationId", "treeItemId");

        handler.handle(new IEditingContext.NoOp(), tree, input);

        assertThat(hasActiveFilterIds).isTrue();
    }

    private Tree createTree() {
        return Tree.newTree("treeId")
                .targetObjectId("targetObjectId")
                .descriptionId("descriptionId")
                .children(List.of())
                .build();
    }
}
