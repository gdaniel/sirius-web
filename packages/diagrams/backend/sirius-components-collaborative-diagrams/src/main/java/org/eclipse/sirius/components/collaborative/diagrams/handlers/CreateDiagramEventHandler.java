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
package org.eclipse.sirius.components.collaborative.diagrams.handlers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IRepresentationMetadataPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to create a new diagram representation.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Service
public class CreateDiagramEventHandler implements IEditingContextEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationMetadataPersistenceService representationMetadataPersistenceService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final IDiagramCreationService diagramCreationService;

    private final IObjectSearchService objectSearchService;

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    public CreateDiagramEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, IRepresentationMetadataPersistenceService representationMetadataPersistenceService, IRepresentationPersistenceService representationPersistenceService,
            IDiagramCreationService diagramCreationService, IObjectSearchService objectSearchService, ICollaborativeDiagramMessageService messageService, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.representationMetadataPersistenceService = Objects.requireNonNull(representationMetadataPersistenceService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        if (input instanceof CreateRepresentationInput createRepresentationInput) {
            return this.representationDescriptionSearchService.findById(editingContext, createRepresentationInput.representationDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .isPresent();
        }
        return false;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), CreateRepresentationInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        if (input instanceof CreateRepresentationInput createRepresentationInput) {
            Optional<DiagramDescription> optionalDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, createRepresentationInput.representationDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);
            Optional<Object> optionalObject = this.objectSearchService.getObject(editingContext, createRepresentationInput.objectId());

            if (optionalDiagramDescription.isPresent() && optionalObject.isPresent()) {
                DiagramDescription diagramDescription = optionalDiagramDescription.get();
                Object object = optionalObject.get();

                var variableManager = new VariableManager();
                variableManager.put(VariableManager.SELF, object);
                variableManager.put(DiagramDescription.LABEL, createRepresentationInput.representationName());
                String label = diagramDescription.getLabelProvider().apply(variableManager);
                List<String> iconURLs = diagramDescription.getIconURLsProvider().apply(variableManager);

                Diagram diagram = this.diagramCreationService.create(object, diagramDescription, editingContext);
                var representationMetadata = RepresentationMetadata.newRepresentationMetadata(diagram.getId())
                        .kind(diagram.getKind())
                        .label(label)
                        .descriptionId(diagram.getDescriptionId())
                        .iconURLs(iconURLs)
                        .build();

                this.representationMetadataPersistenceService.save(createRepresentationInput, editingContext, representationMetadata, diagram.getTargetObjectId());
                this.representationPersistenceService.save(createRepresentationInput, editingContext, diagram);

                payload = new CreateRepresentationSuccessPayload(input.id(), representationMetadata);
                changeDescription = new ChangeDescription(ChangeKind.REPRESENTATION_CREATION, editingContext.getId(), input);
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

}
