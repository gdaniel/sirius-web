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
package org.eclipse.sirius.components.core.graphql.datafetchers.mutation;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IOmniboxCommandHandler;
import org.eclipse.sirius.components.collaborative.dto.ExecuteCommandInput;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Mutation#executeCommand.
 *
 * @author gdaniel
 */
@MutationDataFetcher(type = "Mutation", field = "executeCommand")
public class MutationExecuteCommandDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<IPayload>> {

    private static final String INPUT_ARGUMENT = "input";

    private final Logger logger = LoggerFactory.getLogger(MutationExecuteCommandDataFetcher.class);

    private final ObjectMapper objectMapper;

    private final List<IOmniboxCommandHandler> omniboxCommandHandlers;

    public MutationExecuteCommandDataFetcher(ObjectMapper objectMapper, List<IOmniboxCommandHandler> omniboxCommandHandlers) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.omniboxCommandHandlers = Objects.requireNonNull(omniboxCommandHandlers);
    }

    @Override
    public CompletableFuture<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, ExecuteCommandInput.class);
        Optional<IOmniboxCommandHandler> optionalOmniboxCommandHandler = this.omniboxCommandHandlers.stream()
            .filter(handler -> handler.canHandle(input))
            .findFirst();

        if (optionalOmniboxCommandHandler.isPresent()) {
            IOmniboxCommandHandler omniboxCommandHandler = optionalOmniboxCommandHandler.get();
            return CompletableFuture.supplyAsync(() -> omniboxCommandHandler.handle(input));
        } else {
            this.logger.warn("No handler found for event: {}", input);
        }
        return CompletableFuture.completedFuture(new ErrorPayload(input.id(), List.of(new Message("Error", MessageLevel.ERROR))));
    }

}
