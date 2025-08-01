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

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.trees.dto.InvokeSingleClickTreeItemContextMenuEntryInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.explorer.ExplorerEventInput;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.ExplorerDescriptionsQueryRunner;
import org.eclipse.sirius.web.tests.graphql.FetchTreeItemContextMenuEntryDataQueryRunner;
import org.eclipse.sirius.web.tests.graphql.SingleClickTreeItemContextMenuEntryMutationRunner;
import org.eclipse.sirius.web.tests.graphql.TreeItemContextMenuQueryRunner;
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
 * Integration tests of the tree item context menu controllers.
 *
 * @author Jerome Gout
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class TreeItemContextMenuControllerTests extends AbstractIntegrationTests {

    private static final String ROOT_ENTITY_ID = "c341bf91-d315-4264-9787-c51b121a6375";

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private TreeItemContextMenuQueryRunner treeItemContextMenuQueryRunner;

    @Autowired
    private FetchTreeItemContextMenuEntryDataQueryRunner treeItemFetchContextActionDataQueryRunner;

    @Autowired
    private SingleClickTreeItemContextMenuEntryMutationRunner singleClickTreeItemContexteMenuEntryMutationRunner;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @Autowired
    private ExplorerEventSubscriptionRunner explorerEventSubscriptionRunner;

    @Autowired
    private ExplorerDescriptionsQueryRunner explorerDescriptionsQueryRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a studio, when the context menu actions are requested on an Entity, then the correct actions are returned")
    public void givenAStudioWhenTheContextMenuActionsAreRequestedOnAnEntityThenTheCorrectActionsAreReturned() {

        // 1- retrieve the tree description id of the DSL Domain explorer example
        var explorerDescriptionId = new AtomicReference<String>();

        Map<String, Object> explorerVariables = Map.of(
                "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString()
        );
        var explorerResult = TreeItemContextMenuControllerTests.this.explorerDescriptionsQueryRunner.run(explorerVariables);
        List<String> explorerIds = JsonPath.read(explorerResult, "$.data.viewer.editingContext.explorerDescriptions[*].id");
        assertThat(explorerIds).isNotEmpty().hasSize(2);
        assertThat(explorerIds.get(0)).isEqualTo(ExplorerDescriptionProvider.DESCRIPTION_ID);
        assertThat(explorerIds.get(1)).startsWith("siriusComponents://representationDescription?kind=treeDescription");
        explorerDescriptionId.set(explorerIds.get(1));

        var explorerRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(explorerDescriptionId.get(), List.of(StudioIdentifiers.DOMAIN_DOCUMENT.toString(), StudioIdentifiers.DOMAIN_OBJECT.toString(), ROOT_ENTITY_ID), List.of());
        var input = new ExplorerEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(), explorerRepresentationId);
        var flux = this.explorerEventSubscriptionRunner.run(input);

        // 2- Retrieve the representation id (the id of DSL Domain explorer example tree)
        var treeId = new AtomicReference<String>();

        Consumer<Object> initialTreeContentConsumer = assertRefreshedTreeThat(tree -> treeId.set(tree.getId()));

        var helpId = new AtomicReference<String>();
        var toggleAbstractAction = new AtomicReference<String>();
        // 3- retrieve all context menu actions defined for an Entity tree item
        Runnable getContextMenuActions = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(),
                    "representationId", treeId.get(),
                    "treeItemId", ROOT_ENTITY_ID
            );
            var result = this.treeItemContextMenuQueryRunner.run(variables);

            Object document = Configuration.defaultConfiguration().jsonProvider().parse(result);
            List<String> actionLabels = JsonPath.read(document, "$.data.viewer.editingContext.representation.description.contextMenu[*].label");
            List<String> actionIds = JsonPath.read(document, "$.data.viewer.editingContext.representation.description.contextMenu[*].id");
            assertThat(actionLabels).isNotEmpty().hasSize(3);
            assertThat(actionIds).isNotEmpty().hasSize(3);
            // The ExpandAll action doesn't have a label, it only contains the ID of the frontend component to use.
            assertThat(actionLabels.get(0)).isEqualTo("");
            assertThat(actionIds.get(0)).isEqualTo("expandAll");
            assertThat(actionLabels.get(1)).isEqualTo("Help");
            assertThat(actionLabels.get(2)).isEqualTo("Toggle abstract");

            helpId.set(JsonPath.read(document, "$.data.viewer.editingContext.representation.description.contextMenu[1].id"));
            toggleAbstractAction.set(JsonPath.read(document, "$.data.viewer.editingContext.representation.description.contextMenu[2].id"));
        };

        // 4- invoke fetch action data query to retrieve the fetch action data
        Runnable getFetchActionData = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(),
                    "representationId", treeId.get(),
                    "treeItemId", ROOT_ENTITY_ID,
                    "menuEntryId", helpId.get()
                    );
            var result = this.treeItemFetchContextActionDataQueryRunner.run(variables);

            Object document = Configuration.defaultConfiguration().jsonProvider().parse(result);
            String urlToFetch = JsonPath.read(document, "$.data.viewer.editingContext.representation.description.fetchTreeItemContextMenuEntryData.urlToFetch");
            String fetchKind = JsonPath.read(document, "$.data.viewer.editingContext.representation.description.fetchTreeItemContextMenuEntryData.fetchKind");

            assertThat(urlToFetch).isEqualTo("https://eclipse.dev/sirius/sirius-web.html");
            assertThat(fetchKind).isEqualTo("OPEN");
        };

        // 5- invoke simple action mutation
        Runnable invokeToggleAbstractAction = () -> {
            var toggleAbstractActionParameters = new InvokeSingleClickTreeItemContextMenuEntryInput(
                    UUID.randomUUID(),
                    StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(),
                    treeId.get(),
                    ROOT_ENTITY_ID,
                    toggleAbstractAction.get()
                    );
            var result = this.singleClickTreeItemContexteMenuEntryMutationRunner.run(toggleAbstractActionParameters);
            String typename = JsonPath.read(result, "$.data.invokeSingleClickTreeItemContextMenuEntry.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        StepVerifier.create(flux)
            .consumeNextWith(initialTreeContentConsumer)
            .then(getContextMenuActions)
            .then(getFetchActionData)
            .then(invokeToggleAbstractAction)
            .thenCancel()
            .verify(Duration.ofSeconds(30));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project with dependencies, when the context menu actions are requested on an imported resource, then the update action is returned")
    public void givenProjectWithDependenciesWhenContextMenuActionsAreRequestedOnImportedResourceThenUpdateActionIsReturned() {
        var explorerRepresentationId = this.representationIdBuilder.buildExplorerRepresentationId(ExplorerDescriptionProvider.DESCRIPTION_ID, List.of(), List.of());
        var input = new ExplorerEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), explorerRepresentationId);
        var flux = this.explorerEventSubscriptionRunner.run(input);

        var treeId = new AtomicReference<String>();

        Consumer<Object> initialTreeContentConsumer = assertRefreshedTreeThat(tree -> treeId.set(tree.getId()));

        Runnable getContextMenuActions = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "representationId", treeId.get(),
                    "treeItemId", PapayaIdentifiers.PAPAYA_SIRIUS_WEB_TESTS_DATA_DOCUMENT.toString()
            );
            var result = this.treeItemContextMenuQueryRunner.run(variables);

            Object document = Configuration.defaultConfiguration().jsonProvider().parse(result);

            List<String> actionIds = JsonPath.read(document, "$.data.viewer.editingContext.representation.description.contextMenu[*].id");
            assertThat(actionIds).isNotEmpty();
            assertThat(actionIds).anyMatch(id -> Objects.equals(id, "updateLibrary"));
        };

        StepVerifier.create(flux)
            .consumeNextWith(initialTreeContentConsumer)
            .then(getContextMenuActions)
            .thenCancel()
            .verify(Duration.ofSeconds(5));
    }

}
