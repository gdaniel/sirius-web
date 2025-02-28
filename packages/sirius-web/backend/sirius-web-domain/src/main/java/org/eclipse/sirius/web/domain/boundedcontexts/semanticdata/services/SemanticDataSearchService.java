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
package org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticDataDependency;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.repositories.ISemanticDataRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to retrieve semantic data.
 *
 * @author sbegaudeau
 */
@Service
public class SemanticDataSearchService implements ISemanticDataSearchService {

    private final ISemanticDataRepository semanticDataRepository;

    public SemanticDataSearchService(ISemanticDataRepository semanticDataRepository) {
        this.semanticDataRepository = Objects.requireNonNull(semanticDataRepository);
    }

    @Override
    public List<SemanticData> findAllByDomains(List<String> domainUris) {
        return this.semanticDataRepository.findAllByDomains(domainUris);
    }

    @Override
    public Optional<SemanticData> findById(UUID id) {
        return this.semanticDataRepository.findById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return this.semanticDataRepository.existsById(id);
    }

    @Override
    public Set<SemanticData> findAllTransitiveSemanticDataById(UUID id) {
        Set<SemanticData> collectedSemanticData = new LinkedHashSet<>();
        Optional<SemanticData> semanticData = this.findById(id);
        if (semanticData.isPresent()) {
            this.collectTransitiveSemanticData(semanticData.get(), collectedSemanticData);
        }
        return collectedSemanticData;
    }

    private void collectTransitiveSemanticData(SemanticData semanticData, Set<SemanticData> collectedSemanticData) {
        semanticData.getDependencies().stream()
            .map(SemanticDataDependency::dependencySemanticDataId)
            .map(AggregateReference::getId)
            .map(this::findById)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .forEach(dependencySemanticData -> {
                if (!collectedSemanticData.contains(dependencySemanticData)) {
                    collectedSemanticData.add(dependencySemanticData);
                    this.collectTransitiveSemanticData(dependencySemanticData, collectedSemanticData);
                }
            });
    }
}
