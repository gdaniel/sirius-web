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
package org.eclipse.sirius.web.application.library.services.api;

import java.util.List;

import org.eclipse.sirius.web.application.library.dto.PublishLibrariesInput;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.services.IResult;

/**
 * Handles the publication of libraries.
 *
 * @author gdaniel
 */
public interface ILibraryPublicationHandler {

    boolean canHandle(PublishLibrariesInput input);

    IResult<List<Library>> handle(PublishLibrariesInput input);

}
