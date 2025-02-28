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
package org.eclipse.sirius.web.application.editingcontext.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextDependencyLoader;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextMigrationParticipantPredicate;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceLoader;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.springframework.stereotype.Service;

/**
 * Loads dependencies into the editing context.
 *
 * @author gdaniel
 */
@Service
public class EditingContextDependencyLoader implements IEditingContextDependencyLoader {

    private final ISemanticDataSearchService semanticDataSearchService;

    private final IResourceLoader resourceLoader;

    private final List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates;

    public EditingContextDependencyLoader(ISemanticDataSearchService semanticDataSearchService, IResourceLoader resourceLoader, List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates) {
        this.semanticDataSearchService = Objects.requireNonNull(semanticDataSearchService);
        this.resourceLoader = Objects.requireNonNull(resourceLoader);
        this.migrationParticipantPredicates = Objects.requireNonNull(migrationParticipantPredicates);
    }

    @Override
    public void loadDependencies(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            Set<SemanticData> dependenciesSemanticData = new UUIDParser().parse(siriusWebEditingContext.getId())
                    .map(this.semanticDataSearchService::findAllTransitiveSemanticDataById)
                    .orElse(Set.of());
            for (SemanticData semanticData : dependenciesSemanticData) {
                semanticData.getDocuments().forEach(document -> {
                    URI dependencyResourceURI = URI.createURI(DEPENDENCY_SCHEME + ":///" + document.getId().toString());
                    if (siriusWebEditingContext.getDomain().getResourceSet().getResource(dependencyResourceURI, false) == null) {
                        Optional<Resource> resource = this.resourceLoader.toResource(siriusWebEditingContext.getDomain().getResourceSet(), document.getId().toString(), document.getName(), document.getContent(),
                                this.migrationParticipantPredicates.stream().anyMatch(predicate -> predicate.test(editingContext.getId())));
                        resource.ifPresent(r -> {
                            siriusWebEditingContext.getDomain().getResourceSet().getURIConverter().getURIMap().put(r.getURI(), dependencyResourceURI);
                            r.setURI(dependencyResourceURI);
                        });
                    }
                });
            }
        }
    }
}
