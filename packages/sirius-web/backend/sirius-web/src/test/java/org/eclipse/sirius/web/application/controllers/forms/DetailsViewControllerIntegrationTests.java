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

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.forms.tests.FormEventPayloadConsumer.assertRefreshedFormThat;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.Group;
import org.eclipse.sirius.components.forms.Page;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.details.dto.DetailsEventInput;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.DetailsEventSubscriptionRunner;
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
 * Integration tests of the details view.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DetailsViewControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private DetailsEventSubscriptionRunner detailsEventSubscriptionRunner;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a semantic object, when we subscribe to its properties events, then the form is sent")
    public void givenSemanticObjectWhenWeSubscribeToItsPropertiesEventsThenTheFormIsSent() {
        var detailsRepresentationId = representationIdBuilder.buildDetailsRepresentationId(List.of(PapayaIdentifiers.SIRIUS_WEB_DOMAIN_OBJECT.toString()));
        var input = new DetailsEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), detailsRepresentationId);
        var flux = this.detailsEventSubscriptionRunner.run(input);

        Predicate<Object> formContentMatcher = object -> Optional.of(object)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .isPresent();

        StepVerifier.create(flux)
                .expectNextMatches(formContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a read only object, when we subscribe to its details view, then the widget of the form are read only")
    public void givenReadOnlyObjectWhenWeSubscribreToItsDetailsViewThenTheWidgetOfTheFormAreReadOnly() {
        var detailsRepresentationId = representationIdBuilder.buildDetailsRepresentationId(List.of(PapayaIdentifiers.PAPAYA_LIBRARY_OBJECT_SIRIUS_WEB_TESTS_DATA.toString()));
        var input = new DetailsEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), detailsRepresentationId);
        var flux = this.detailsEventSubscriptionRunner.run(input);

        Consumer<Object> formContentMatcher = assertRefreshedFormThat(form -> {
            var allWidgets = form.getPages().stream()
                    .map(Page::getGroups)
                    .flatMap(Collection::stream)
                    .map(Group::getWidgets)
                    .flatMap(Collection::stream);
            assertThat(allWidgets).allMatch(AbstractWidget::isReadOnly);
        });

        StepVerifier.create(flux)
                .consumeNextWith(formContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}
