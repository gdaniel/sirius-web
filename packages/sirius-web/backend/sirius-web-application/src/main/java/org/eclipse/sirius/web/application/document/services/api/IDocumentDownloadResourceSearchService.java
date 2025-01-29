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
package org.eclipse.sirius.web.application.document.services.api;

import java.util.Optional;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * Used to provide search service to find custom resource.
 *
 * @author frouene
 */
public interface IDocumentDownloadResourceSearchService {

    Optional<Resource> findResource(String editingContextId, String documentId);
}
