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
package org.eclipse.sirius.web.papaya.representations.classdiagram.tools.interfacenode;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.INodeToolProvider;
import org.eclipse.sirius.components.view.diagram.NodeContainmentKind;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.ClassNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.InterfaceNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.RecordNodeDescriptionProvider;

/**
 * Used to create the import all interface implementations tool.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ImportAllInterfaceSubtypesNodeToolProvider implements INodeToolProvider {

    @Override
    public NodeTool create(IViewDiagramElementFinder cache) {
        var classNodeDescription = cache.getNodeDescription(ClassNodeDescriptionProvider.NAME).orElse(null);
        var interfaceNodeDescription = cache.getNodeDescription(InterfaceNodeDescriptionProvider.NAME).orElse(null);
        var recordNodeDescription = cache.getNodeDescription(RecordNodeDescriptionProvider.NAME).orElse(null);

        var ifClassCreateView = new ViewBuilders().newIf()
                .conditionExpression("aql:type.eClass() = papaya::Class")
                .children(
                        new DiagramBuilders().newCreateView()
                                .elementDescription(classNodeDescription)
                                .semanticElementExpression("aql:type")
                                .parentViewExpression("aql:null")
                                .containmentKind(NodeContainmentKind.CHILD_NODE)
                                .build()
                )
                .build();

        var ifInterfaceCreateView = new ViewBuilders().newIf()
                .conditionExpression("aql:type.eClass() = papaya::Interface")
                .children(
                        new DiagramBuilders().newCreateView()
                                .elementDescription(interfaceNodeDescription)
                                .semanticElementExpression("aql:type")
                                .parentViewExpression("aql:null")
                                .containmentKind(NodeContainmentKind.CHILD_NODE)
                                .build()
                )
                .build();

        var ifRecordCreateView = new ViewBuilders().newIf()
                .conditionExpression("aql:type.eClass() = papaya::Record")
                .children(
                        new DiagramBuilders().newCreateView()
                                .elementDescription(recordNodeDescription)
                                .semanticElementExpression("aql:type")
                                .parentViewExpression("aql:null")
                                .containmentKind(NodeContainmentKind.CHILD_NODE)
                                .build()
                )
                .build();

        return new DiagramBuilders().newNodeTool()
                .name("Import all subtypes")
                .iconURLsExpression("/icons/papaya/full/obj16/Interface.svg")
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:self")
                                .children(
                                        new ViewBuilders().newFor()
                                                .expression("aql:self.allSubtypes")
                                                .iteratorName("type")
                                                .children(
                                                        ifClassCreateView,
                                                        ifInterfaceCreateView,
                                                        ifRecordCreateView
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();
    }
}
