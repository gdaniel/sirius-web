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
package org.eclipse.sirius.components.diagrams.graphql.datafetchers.diagram;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetGroupPaletteInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetGroupPaletteSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Palette;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * The data fetcher used to retrieve the tool sections from the diagram description.
 *
 * @author gdaniel
 */
@QueryDataFetcher(type = "DiagramDescription", field = "groupPalette")
public class DiagramDescriptionGroupPaletteDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<Palette>> {

    private static final String DIAGRAM_ELEMENT_IDS = "diagramElementIds";

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public DiagramDescriptionGroupPaletteDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<Palette> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        String representationId = Optional.ofNullable(localContext.get(LocalContextConstants.REPRESENTATION_ID)).map(Object::toString).orElse(null);
        List<String> diagramElementIds = environment.getArgument(DIAGRAM_ELEMENT_IDS);

        if (editingContextId != null && representationId != null) {
            GetGroupPaletteInput input = new GetGroupPaletteInput(UUID.randomUUID(), editingContextId, representationId, diagramElementIds);
            return this.editingContextEventProcessorRegistry.dispatchEvent(input.editingContextId(), input)
                    .filter(GetGroupPaletteSuccessPayload.class::isInstance)
                    .map(GetGroupPaletteSuccessPayload.class::cast)
                    .map(GetGroupPaletteSuccessPayload::palette)
                    .toFuture();
        }

        return Mono.<Palette>empty().toFuture();
    }

}
