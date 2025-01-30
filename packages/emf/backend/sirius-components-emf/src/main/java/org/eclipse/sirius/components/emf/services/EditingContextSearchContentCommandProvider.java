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
package org.eclipse.sirius.components.emf.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.api.IOmniboxCommandProvider;
import org.eclipse.sirius.components.collaborative.dto.GetOmniboxCommandsInput;
import org.eclipse.sirius.components.collaborative.dto.GetOmniboxCommandsPayload;
import org.eclipse.sirius.components.collaborative.dto.OmniboxCommand;
import org.eclipse.sirius.components.collaborative.dto.OmniboxContextEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Provides the search content commands in the omnibox.
 *
 * @author gdaniel
 */
@Service
public class EditingContextSearchContentCommandProvider implements IOmniboxCommandProvider {

    private final Logger logger = LoggerFactory.getLogger(EditingContextSearchContentCommandProvider.class);

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public EditingContextSearchContentCommandProvider(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public List<OmniboxCommand> getCommands(GetOmniboxCommandsInput input) {
        List<OmniboxCommand> result = List.of();
        Optional<String> optionalEditingContextId = input.contextEntries().stream()
                .filter(entry -> entry.kind().equals("Search"))
                .map(OmniboxContextEntry::id)
                .findFirst();
        if (optionalEditingContextId.isPresent()) {
            try {
                result = this.editingContextEventProcessorRegistry.dispatchEvent(optionalEditingContextId.get(), input)
                    .filter(GetOmniboxCommandsPayload.class::isInstance)
                    .map(GetOmniboxCommandsPayload.class::cast)
                    .map(GetOmniboxCommandsPayload::omniboxCommands)
                    .toFuture()
                    .get();
            } catch (ExecutionException | InterruptedException e) {
                this.logger.warn(e.getMessage(), e);
            }
        }
        return result;
    }

}
