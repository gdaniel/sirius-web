/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.DialogDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription;
import org.springframework.stereotype.Service;

/**
 * descriptionID for DiagramDescription & DiagramElementDescription.
 *
 * @author mcharfadi
 */
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DiagramIdProvider implements IDiagramIdProvider {

    private final IIdentityService identityService;

    public DiagramIdProvider(IIdentityService identityService) {
        this.identityService = Objects.requireNonNull(identityService);
    }

    @Override
    public String getId(DiagramDescription diagramDescription) {
        String sourceId = this.getSourceIdFromElementDescription(diagramDescription);
        String sourceElementId = this.identityService.getId(diagramDescription);
        return DIAGRAM_DESCRIPTION_KIND + "&" + SOURCE_KIND + "=" + VIEW_SOURCE_KIND + "&" + SOURCE_ID + "=" + sourceId + "&" + SOURCE_ELEMENT_ID + "=" + sourceElementId;
    }

    @Override
    public String getId(DiagramElementDescription diagramElementDescription) {
        String sourceId = this.getSourceIdFromElementDescription(diagramElementDescription);
        String sourceElementId = this.identityService.getId(diagramElementDescription);
        if (diagramElementDescription instanceof NodeDescription) {
            return NODE_DESCRIPTION_KIND + "?" + SOURCE_KIND + "=" + VIEW_SOURCE_KIND + "&" + SOURCE_ID + "=" + sourceId + "&" + SOURCE_ELEMENT_ID + "=" + sourceElementId;
        } else {
            return EDGE_DESCRIPTION_KIND + "?" + SOURCE_KIND + "=" + VIEW_SOURCE_KIND + "&" + SOURCE_ID + "=" + sourceId + "&" + SOURCE_ELEMENT_ID + "=" + sourceElementId;
        }
    }

    @Override
    public String getId(DialogDescription dialogDescription) {
        if (dialogDescription != null) {
            String sourceId = this.getSourceIdFromElementDescription(dialogDescription);
            String sourceElementId = this.identityService.getId(dialogDescription);
            return this.getDialogDescriptionTypeName(dialogDescription) + "&" + SOURCE_KIND + "=" + VIEW_SOURCE_KIND + "&" + SOURCE_ID + "=" + sourceId + "&" + SOURCE_ELEMENT_ID + "="
                    + sourceElementId;
        }
        return null;
    }

    @Override
    public String getId(SelectionDialogTreeDescription treeDescription) {
        if (treeDescription != null) {
            String sourceId = this.getSourceIdFromElementDescription(treeDescription);
            String sourceElementId = this.identityService.getId(treeDescription);
            return SELECTION_DIALOG_TREE_DESCRIPTION_KIND + "?" + SOURCE_KIND + "=" + VIEW_SOURCE_KIND + "&" + SOURCE_ID + "=" + sourceId + "&" + SOURCE_ELEMENT_ID + "="
                    + sourceElementId;
        }
        return null;
    }

    private String getDialogDescriptionTypeName(DialogDescription dialogDescription) {
        String name = dialogDescription.eClass().getName();
        String typeName = "siriusComponents://";
        if (!name.isEmpty()) {
            char[] charArray = name.toCharArray();
            charArray[0] = Character.toLowerCase(charArray[0]);
            typeName += new String(charArray);
        }
        return typeName;
    }

    private String getSourceIdFromElementDescription(EObject elementDescription) {
        return elementDescription.eResource().getURI().toString().split("///")[1];
    }

}
