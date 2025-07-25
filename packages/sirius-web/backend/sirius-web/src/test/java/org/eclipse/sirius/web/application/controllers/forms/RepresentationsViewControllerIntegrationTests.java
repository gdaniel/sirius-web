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
package org.eclipse.sirius.web.application.controllers.forms;

import static org.eclipse.sirius.components.forms.tests.FormEventPayloadConsumer.assertRefreshedFormThat;
import static org.eclipse.sirius.components.forms.tests.assertions.FormAssertions.assertThat;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.eclipse.sirius.components.forms.TreeWidget;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.representations.dto.RepresentationsEventInput;
import org.eclipse.sirius.web.application.views.representations.services.RepresentationsFormDescriptionProvider;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.RepresentationsEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import reactor.test.StepVerifier;

/**
 * Integration tests of the representations view.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepresentationsViewControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private RepresentationsEventSubscriptionRunner representationsEventSubscriptionRunner;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a semantic object associated with representations, when we subscribe to its representations events, then the form sent contains the list widget listing the associated representations")
    public void givenSemanticObjectAssociatedWithRepresentationsWhenWeSubscribeToItsRepresentationsEventsThenTheFormSentContainsTheListWidgetListingTheAssociatedRepresentations() {
        var representationId = representationIdBuilder.buildRepresentationViewRepresentationId(List.of(TestIdentifiers.EPACKAGE_OBJECT.toString()));
        var input = new RepresentationsEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString(), representationId);
        var flux = this.representationsEventSubscriptionRunner.run(input);

        Consumer<Object> initialformContentConsumer = assertRefreshedFormThat(form -> {
            var formNavigator = new FormNavigator(form);
            var listWidget = formNavigator.page(RepresentationsFormDescriptionProvider.PAGE_LABEL)
                    .group(RepresentationsFormDescriptionProvider.GROUP_LABEL)
                    .findWidget("Representations", org.eclipse.sirius.components.forms.List.class);
            assertThat(listWidget).hasListItemWithLabel("Portal");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialformContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a portal containing another portal, when we subscribe to its representations events, then the form sent contains the tree widget containing at least the portal and the other portal as a child")
    public void givenPortalContainingAnotherPortalWhenWeSubscribeToItsRepresentationsEventsThenTheFormSentContainsTheTreeWidgetContainingAtLeastThePortalAndTheOtherPortalAsAChild() {
        var representationId = representationIdBuilder.buildRepresentationViewRepresentationId(List.of(TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString()));
        var input = new RepresentationsEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString(), representationId);
        var flux = this.representationsEventSubscriptionRunner.run(input);

        Consumer<Object> initialformContentConsumer = assertRefreshedFormThat(form -> {
            var formNavigator = new FormNavigator(form);
            var treeWidget = formNavigator.page(RepresentationsFormDescriptionProvider.PAGE_LABEL)
                    .group(RepresentationsFormDescriptionProvider.GROUP_LABEL)
                    .findWidget("Portal contents", TreeWidget.class);
            assertThat(treeWidget).hasTreeItemWithLabel("Portal");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialformContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
