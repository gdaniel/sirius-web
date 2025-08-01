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
package org.eclipse.sirius.web.application.controllers.trees;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.trees.tests.TreeEventPayloadConsumer.assertRefreshedTreeThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionInput;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionRunner;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionSuccessPayload;
import org.eclipse.sirius.components.trees.tests.graphql.TreePathQueryRunner;
import org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.explorer.ExplorerEventInput;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.services.PapayaViewInjector;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.explorer.ExplorerEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import reactor.test.StepVerifier;

/**
 * Integration tests of the computation of the tree path in the explorer.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExplorerTreePathControllerTests extends AbstractIntegrationTests {

    private static final String DEFAULT_TREE_ID = ExplorerDescriptionProvider.PREFIX + "?" + ExplorerDescriptionProvider.TREE_DESCRIPTION_ID_PARAMETER + "=" + ExplorerDescriptionProvider.DESCRIPTION_ID;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private ExplorerEventSubscriptionRunner treeEventSubscriptionRunner;

    @Autowired
    private ExecuteEditingContextFunctionRunner executeEditingContextFunctionRunner;

    @Autowired
    private PapayaViewInjector papayaViewInjector;

    @Autowired
    private TreePathQueryRunner treePathQueryRunner;

    @Autowired
    private IIdentityService identityService;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a studio, when we ask for the tree path of an object, then its path in the explorer is returned")
    public void givenStudioWhenWeAskForTheTreePathOfAnObjectThenItsPathInTheExplorerIsReturned() {
        var explorerRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, List.of(), List.of());
        var input = new ExplorerEventInput(UUID.randomUUID(), StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString(), explorerRepresentationId);
        var flux = this.treeEventSubscriptionRunner.run(input);

        var treeId = new AtomicReference<String>();
        var objectId = new AtomicReference<String>();

        Consumer<Object> initialTreeContentConsumer = assertRefreshedTreeThat(tree -> treeId.set(tree.getId()));

        Runnable createView = () -> {
            var createViewInput = new ExecuteEditingContextFunctionInput(UUID.randomUUID(), StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString(), this.papayaViewInjector);
            this.executeEditingContextFunctionRunner.execute(createViewInput).block();

            BiFunction<IEditingContext, IInput, IPayload> getObjectIdFunction = (editingContext, executeEditingContextFunctionInput) -> {
                var id = Optional.of(editingContext)
                        .filter(IEMFEditingContext.class::isInstance)
                        .map(IEMFEditingContext.class::cast)
                        .flatMap(emfEditingContext -> {
                            var iterator = emfEditingContext.getDomain().getResourceSet().getAllContents();
                            var stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
                            return stream.filter(RectangularNodeStyleDescription.class::isInstance)
                                    .findFirst();
                        })
                        .map(this.identityService::getId)
                        .orElse("");
                return new ExecuteEditingContextFunctionSuccessPayload(executeEditingContextFunctionInput.id(), id);
            };

            var getObjectIdInput = new ExecuteEditingContextFunctionInput(UUID.randomUUID(), StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString(), getObjectIdFunction, new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString(), createViewInput));
            var payload = this.executeEditingContextFunctionRunner.execute(getObjectIdInput).block();

            assertThat(payload).isInstanceOf(ExecuteEditingContextFunctionSuccessPayload.class);
            ExecuteEditingContextFunctionSuccessPayload successPayload = (ExecuteEditingContextFunctionSuccessPayload) payload;
            objectId.set(successPayload.result().toString());
        };

        Consumer<Object> updatedTreeContentConsumer = assertRefreshedTreeThat(tree -> assertThat(tree).isNotNull());

        Runnable getTreePath = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.EMPTY_STUDIO_EDITING_CONTEXT_ID.toString(),
                    "treeId", treeId.get(),
                    "selectionEntryIds", List.of(objectId.get())
            );
            var result = this.treePathQueryRunner.run(variables);
            List<String> treeItemIdsToExpand = JsonPath.read(result, "$.data.viewer.editingContext.treePath.treeItemIdsToExpand");
            assertThat(treeItemIdsToExpand).isNotEmpty();
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialTreeContentConsumer)
                .then(createView)
                .consumeNextWith(updatedTreeContentConsumer)
                .then(getTreePath)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
