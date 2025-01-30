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
package org.eclipse.sirius.web.application.studio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IOmniboxCommandProvider;
import org.eclipse.sirius.components.collaborative.dto.GetOmniboxCommandsInput;
import org.eclipse.sirius.components.collaborative.dto.OmniboxCommand;
import org.eclipse.sirius.web.application.studio.services.api.IStudioCapableEditingContextPredicate;
import org.springframework.stereotype.Service;

/**
 * Provides the publish studio command in the omnibox.
 *
 * @author gdaniel
 */
@Service
public class PublishStudioCommandProvider implements IOmniboxCommandProvider {

    public static final String PUBLISH_STUDIO_COMMAND_ID = "publishStudio";

    private static final String PUBLISH_STUDIO_COMMAND = "Publish Studio";

    private final IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate;

    public PublishStudioCommandProvider(IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate) {
        this.studioCapableEditingContextPredicate = Objects.requireNonNull(studioCapableEditingContextPredicate);
    }

    @Override
    public List<OmniboxCommand> getCommands(GetOmniboxCommandsInput input) {
        List<OmniboxCommand> result = new ArrayList<>();
        if (PUBLISH_STUDIO_COMMAND.toLowerCase().contains(input.query().toLowerCase())
                && input.contextEntries().stream().anyMatch(entry -> entry.kind().equals("EditingContext") && this.studioCapableEditingContextPredicate.test(entry.id()))) {
            result.add(new OmniboxCommand(PUBLISH_STUDIO_COMMAND_ID, PUBLISH_STUDIO_COMMAND, "Command", List.of("/omnibox/publish.svg"), "Publish all the domains and representation descriptions as individual libraries"));
        }
        return result;
    }

}
