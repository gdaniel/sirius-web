/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo and others.
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
package org.eclipse.sirius.components.collaborative.diagrams.handlers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnGroupToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnGroupToolSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnGroupTool;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.representations.WorkbenchSelection;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

@Service
public class InvokeSingleClickOnGroupToolEventHandler implements IDiagramEventHandler {

    private final IObjectService objectService;

    private final IDiagramQueryService diagramQueryService;

    private final IToolService toolService;

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    public InvokeSingleClickOnGroupToolEventHandler(IObjectService objectService, IDiagramQueryService diagramQueryService, IToolService toolService,
            ICollaborativeDiagramMessageService messageService, MeterRegistry meterRegistry, IRepresentationDescriptionSearchService representationDescriptionSearchService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.toolService = Objects.requireNonNull(toolService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on

        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof InvokeSingleClickOnGroupToolInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        System.out.println("CALLED A GROUP TOOL");

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), InvokeSingleClickOnGroupToolInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);

        if (diagramInput instanceof InvokeSingleClickOnGroupToolInput input) {
            Diagram diagram = diagramContext.getDiagram();
            // @formatter:off
            var optionalTool = this.toolService.findToolById(editingContext, diagram, input.toolId())
                    .filter(SingleClickOnGroupTool.class::isInstance)
                    .map(SingleClickOnGroupTool.class::cast);
            // @formatter:on
            if (optionalTool.isPresent()) {
                IStatus status = this.executeTool(editingContext, diagramContext, input.diagramElementIds(), optionalTool.get(), input.startingPositionX(), input.startingPositionY(),
                        input.selectedObjectIds());
                if (status instanceof Success success) {
                    WorkbenchSelection newSelection = null;
                    Object newSelectionParameter = success.getParameters().get(Success.NEW_SELECTION);
                    if (newSelectionParameter instanceof WorkbenchSelection workbenchSelection) {
                        newSelection = workbenchSelection;
                    }
                    payload = new InvokeSingleClickOnGroupToolSuccessPayload(diagramInput.id(), newSelection, success.getMessages());
                    changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.representationId(), diagramInput);
                } else if (status instanceof Failure failure) {
                    payload = new ErrorPayload(diagramInput.id(), failure.getMessages());
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private IStatus executeTool(IEditingContext editingContext, IDiagramContext diagramContext, List<String> diagramElementIds, SingleClickOnGroupTool tool, double startingPositionX,
            double startingPositionY, List<String> selectedObjectIds) {
        IStatus result = new Failure("");
        Diagram diagram = diagramContext.getDiagram();


        List<IDiagramElement> selectedViews = diagramElementIds.stream()
            .map(diagramElementId -> {
                Optional<Node> node = this.diagramQueryService.findNodeById(diagram, diagramElementId);
                if (node.isEmpty()) {
                    Optional<Edge> edge = this.diagramQueryService.findEdgeById(diagram, diagramElementId);
                    return edge.orElse(null);
                }
                return node.orElse(null);
            })
            .toList();

        // [FRO] Is it correct to find the semantic elements here ? For the classical SingleClick tool this is kind of done when looking for self.
        List<Object> selectedElements = selectedViews.stream()
                .map(selectedView -> {
                    String id = null;
                    if (selectedView instanceof Node node) {
                        id = node.getTargetObjectId();
                    } else if (selectedView instanceof Edge edge) {
                        id = edge.getTargetObjectId();
                    }
                    return id;
                })
                .map(selectedElementId -> this.objectService.getObject(editingContext, selectedElementId).orElse(null))
                .toList();

        // [FRO] Set self to the diagram here, not sure it is correct
        Optional<Object> self = this.objectService.getObject(editingContext, diagram.getTargetObjectId());

        if (self.isPresent()) {
            VariableManager variableManager = this.populateVariableManager(editingContext, diagramContext, selectedViews, selectedElements, self);
            // [FRO] Not sure if this is needed for this tool
            //            String selectionDescriptionId = tool.getSelectionDescriptionId();
//            if (selectionDescriptionId != null && selectedObjectId != null) {
//                var selectionDescriptionOpt = this.representationDescriptionSearchService.findById(editingContext, selectionDescriptionId);
//                var selectedObjectOpt = this.objectService.getObject(editingContext, selectedObjectId);
//                if (selectionDescriptionOpt.isPresent() && selectedObjectOpt.isPresent()) {
//                    variableManager.put(SingleClickOnDiagramElementTool.SELECTED_OBJECT, selectedObjectOpt.get());
//                }
//            }
//            if (selectionDescriptionId == null || selectedObjectId != null) {
            result = tool.getHandler().apply(variableManager);
            // [FRO] Not sure what this position is used for
            Position newPosition = Position.at(startingPositionX, startingPositionY);

//                diagramContext.setDiagramEvent(new SinglePositionEvent(diagramElementId, newPosition));
//            }
        }
        return result;
    }

    private VariableManager populateVariableManager(IEditingContext editingContext, IDiagramContext diagramContext, List<IDiagramElement> selectedViews, List<Object> selectedElements, Optional<Object> self) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(IDiagramContext.DIAGRAM_CONTEXT, diagramContext);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
        variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));
        variableManager.put(VariableManager.SELF, self.get());
        // [FRO] Add constants in IDiagramElement ?
        variableManager.put("selectedViews", selectedViews);
        variableManager.put("selectedElements", selectedElements);
//        variableManager.put(Node.SELECTED_NODE, node.orElse(null));
//        variableManager.put(Edge.SELECTED_EDGE, edge.orElse(null));
        return variableManager;
    }
}
