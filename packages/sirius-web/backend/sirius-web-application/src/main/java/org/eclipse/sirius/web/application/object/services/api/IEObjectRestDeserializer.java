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
package org.eclipse.sirius.web.application.object.services.api;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * Used to deserialize EObjects for the REST API.
 *
 * @author sbegaudeau
 */
public interface IEObjectRestDeserializer {
    void fromMap(IEditingContext editingContext, Map<String, Object> serializedEObject, EObject eObject, Map<EObject, Map<String, Object>> emptyNewObjects, Set<EObject> newObjectsAttached);
}
