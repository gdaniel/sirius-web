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
package org.eclipse.sirius.web.application.views.tree;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.trees.api.ITreeItemContextMenuEntryProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.ContributedTreeItemContextMenuEntry;
import org.eclipse.sirius.components.collaborative.trees.dto.ITreeItemContextMenuEntry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.web.application.studio.services.representations.DomainViewTreeDescriptionProvider;
import org.eclipse.sirius.web.application.views.explorer.ExplorerImageConstants;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * @author gdaniel
 */
// Doesn't work
@Service
public class DomainTreeItemContextMenuEntryProvider implements ITreeItemContextMenuEntryProvider {

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    public DomainTreeItemContextMenuEntryProvider(IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService) {
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        boolean result = false;
        if (tree.getId().startsWith(ExplorerDescriptionProvider.PREFIX)
                && tree.getDescriptionId().startsWith(IRepresentationDescriptionIdProvider.PREFIX)) {
            var optionalViewTreeDescription = this.viewRepresentationDescriptionSearchService.findById(editingContext, tree.getDescriptionId());
            if (optionalViewTreeDescription.isPresent()) {
                result = optionalViewTreeDescription.get().getName().equals(DomainViewTreeDescriptionProvider.DOMAIN_EXPLORER_DESCRIPTION_NAME);
            }
        }
        return result;
    }

    @Override
    public List<ITreeItemContextMenuEntry> getTreeItemContextMenuEntries(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        if (treeItem.isHasChildren()) {
            return List.of(new ContributedTreeItemContextMenuEntry("expand-all", "Expand all", List.of(ExplorerImageConstants.EXPAND_ALL_SVG)));
        } else {
            return List.of();
        }
    }

}
