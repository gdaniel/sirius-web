/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
package org.eclipse.sirius.components.diagrams.components;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.elements.DiagramElementProps;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the diagram.
 *
 * @author sbegaudeau
 */
public class DiagramComponent implements IComponent {

    private final DiagramComponentProps props;

    public DiagramComponent(DiagramComponentProps props) {
        this.props = props;
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        DiagramDescription diagramDescription = this.props.getDiagramDescription();
        List<DiagramDescription> allDiagramDescriptions = this.props.getAllDiagramDescriptions();

        var optionalPreviousDiagram = this.props.getPreviousDiagram();
        variableManager.put(DiagramComponentProps.PREVIOUS_DIAGRAM, optionalPreviousDiagram.orElse(null));
        variableManager.put(IDiagramEvent.DIAGRAM_EVENTS, this.props.getDiagramEvents());

        String diagramId = optionalPreviousDiagram.map(Diagram::getId).orElseGet(() -> UUID.randomUUID().toString());
        String targetObjectId = diagramDescription.getTargetObjectIdProvider().apply(variableManager);

        DiagramRenderingCache cache = new DiagramRenderingCache();

        IDiagramElementRequestor diagramElementRequestor = new DiagramElementRequestor();
        INodeDescriptionRequestor nodeDescriptionRequestor = new NodeDescriptionRequestor(allDiagramDescriptions);

        var nodes = diagramDescription.getNodeDescriptions().stream()
                .map(nodeDescription -> {
                    var nodeVariableManager = variableManager.createChild();
                    var ancestors = new ArrayList<>();
                    variableManager.get(VariableManager.SELF, Object.class).ifPresent(ancestors::add);
                    nodeVariableManager.put(NodeDescription.ANCESTORS, ancestors);

                    var previousNodes = optionalPreviousDiagram.map(previousDiagram -> diagramElementRequestor.getRootNodes(previousDiagram, nodeDescription))
                            .orElse(List.of());
                    var previousNodesTargetIds = previousNodes.stream().map(node -> node.getTargetObjectId()).toList();
                    INodesRequestor nodesRequestor = new NodesRequestor(previousNodes);
                    var nodeComponentProps = NodeComponentProps.newNodeComponentProps()
                            .variableManager(nodeVariableManager)
                            .nodeDescription(nodeDescription)
                            .nodesRequestor(nodesRequestor)
                            .nodeDescriptionRequestor(nodeDescriptionRequestor)
                            .containmentKind(NodeContainmentKind.CHILD_NODE)
                            .cache(cache)
                            .viewCreationRequests(this.props.getViewCreationRequests())
                            .viewDeletionRequests(this.props.getViewDeletionRequests())
                            .parentElementId(diagramId)
                            .previousTargetObjectIds(previousNodesTargetIds)
                            .diagramEvents(this.props.getDiagramEvents())
                            .parentElementState(ViewModifier.Normal)
                            .operationValidator(this.props.getOperationValidator())
                            .nodeAppearanceHandlers(this.props.getNodeAppearanceHandlers())
                            .build();
                    return new Element(NodeComponent.class, nodeComponentProps);
                }).toList();

        var edges = diagramDescription.getEdgeDescriptions().stream()
                .map(edgeDescription -> {
                    var previousEdges = optionalPreviousDiagram.map(previousDiagram -> diagramElementRequestor.getEdges(previousDiagram, edgeDescription))
                            .orElse(List.of());
                    IEdgesRequestor edgesRequestor = new EdgesRequestor(previousEdges);
                    var edgeComponentProps = new EdgeComponentProps(variableManager, edgeDescription, edgesRequestor, cache, this.props.getOperationValidator(), this.props.getDiagramEvents());
                    return new Element(EdgeComponent.class, edgeComponentProps);
                })
                .toList();

        List<Element> children = new ArrayList<>();
        children.addAll(nodes);
        children.addAll(edges);

        DiagramElementProps diagramElementProps = DiagramElementProps.newDiagramElementProps(diagramId)
                .targetObjectId(targetObjectId)
                .descriptionId(diagramDescription.getId())
                .children(children)
                .build();
        return new Element(DiagramElementProps.TYPE, diagramElementProps);
    }

}
