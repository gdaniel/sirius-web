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
package org.eclipse.sirius.web.papaya.factories;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.papaya.Component;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.web.papaya.factories.services.api.IEObjectIndexer;
import org.eclipse.sirius.web.papaya.factories.services.api.IObjectFactory;

/**
 * Used to create the Reactive Streams project.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ReactiveStreamsProjectFactory implements IObjectFactory {
    @Override
    public void create(IEMFEditingContext editingContext) {
        var documentId = UUID.randomUUID();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
        var resourceMetadataAdapter = new ResourceMetadataAdapter("Reactive Streams");
        resource.eAdapters().add(resourceMetadataAdapter);
        editingContext.getDomain().getResourceSet().getResources().add(resource);

        var reactiveStreamsProject = this.reactiveStreamsProject();
        resource.getContents().add(reactiveStreamsProject);
    }

    private Project reactiveStreamsProject() {
        var reactiveStreams = PapayaFactory.eINSTANCE.createProject();
        reactiveStreams.setName("Reactive Streams Project");
        reactiveStreams.getElements().add(this.reactiveStreams());

        return reactiveStreams;
    }

    private Component reactiveStreams() {
        var reactiveStreams = PapayaFactory.eINSTANCE.createComponent();
        reactiveStreams.setName("reactive-streams");
        reactiveStreams.getPackages().add(new ReactiveStreamsFactory().orgReactiveStreams());

        return reactiveStreams;
    }

    @Override
    public void link(IEObjectIndexer eObjectIndexer) {
        var publisherInterface = eObjectIndexer.getInterface("org.reactivestreams.Publisher");
        var subscriberInterface = eObjectIndexer.getInterface("org.reactivestreams.Subscriber");
        var processorInterface = eObjectIndexer.getInterface("org.reactivestreams.Processor");

        processorInterface.getExtends().addAll(List.of(publisherInterface, subscriberInterface));
    }
}
