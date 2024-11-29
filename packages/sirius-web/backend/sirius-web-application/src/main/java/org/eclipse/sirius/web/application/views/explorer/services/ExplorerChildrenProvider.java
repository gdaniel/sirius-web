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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerChildrenProvider;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerServices;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerTreeItemAlteredContentProvider;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IExplorerChildrenProvider} for the Sirius Web Explorer view.
 *
 * @author arichard
 */
@Service
public class ExplorerChildrenProvider implements IExplorerChildrenProvider {

    private final List<IExplorerTreeItemAlteredContentProvider> alteredContentProviders;

    private final IExplorerServices explorerServices;

    public ExplorerChildrenProvider(List<IExplorerTreeItemAlteredContentProvider> alteredContentProviders, IExplorerServices explorerServices) {
        this.alteredContentProviders = Objects.requireNonNull(alteredContentProviders);
        this.explorerServices = Objects.requireNonNull(explorerServices);
    }

    @Override
    public boolean hasChildren(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        Optional<IEditingContext> optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        return this.explorerServices.hasChildren(self, optionalEditingContext.orElse(null));
    }

    @Override
    public List<Object> getChildren(VariableManager variableManager) {
        List<Object> children = new ArrayList<>();
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        if (self != null) {
            children = this.getDefaultChildren(variableManager);
            List<String> activeFilterIds = this.getActiveFilterIds(variableManager);

            var providers = this.alteredContentProviders.stream()
                    .filter(provider -> provider.canHandle(self, activeFilterIds))
                    .toList();
            for (IExplorerTreeItemAlteredContentProvider provider : providers) {
                children = provider.apply(children, variableManager);
            }
        }
        return children;
    }

    private List<Object> getDefaultChildren(VariableManager variableManager) {
        List<String> expandedIds = new ArrayList<>();
        Object objects = variableManager.getVariables().get(TreeRenderer.EXPANDED);
        if (objects instanceof List<?> list) {
            expandedIds = list.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .toList();
        }
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        return this.explorerServices.getDefaultChildren(self, optionalEditingContext.orElse(null), expandedIds);
    }

    private List<String> getActiveFilterIds(VariableManager variableManager) {
        List<String> activeFilterIds;
        Object objects = variableManager.getVariables().get(TreeRenderer.ACTIVE_FILTER_IDS);
        if (objects instanceof List<?> list) {
            activeFilterIds = list.stream().filter(String.class::isInstance).map(String.class::cast).toList();
        } else {
            activeFilterIds = new ArrayList<>();
        }
        return activeFilterIds;
    }
}
