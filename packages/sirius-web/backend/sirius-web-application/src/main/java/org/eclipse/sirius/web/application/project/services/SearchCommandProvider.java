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
package org.eclipse.sirius.web.application.project.services;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.collaborative.api.IOmniboxCommandProvider;
import org.eclipse.sirius.components.collaborative.dto.GetOmniboxCommandsInput;
import org.eclipse.sirius.components.collaborative.dto.OmniboxCommand;
import org.springframework.stereotype.Service;

/**
 * Provides the search command in the omnibox.
 *
 * @author gdaniel
 */
@Service
public class SearchCommandProvider implements IOmniboxCommandProvider {

    public static final String SEARCH_COMMAND_ID = "searchContent";

    private static final String SEARCH_COMMAND = "Search";

    @Override
    public List<OmniboxCommand> getCommands(GetOmniboxCommandsInput input) {
        List<OmniboxCommand> result = new ArrayList<>();
        if (SEARCH_COMMAND.toLowerCase().contains(input.query().toLowerCase())) {
            result.add(new OmniboxCommand(SEARCH_COMMAND_ID, SEARCH_COMMAND, "Command", List.of("/omnibox/search.svg"), "Search an element in the project"));
        }
        return result;
    }

}
