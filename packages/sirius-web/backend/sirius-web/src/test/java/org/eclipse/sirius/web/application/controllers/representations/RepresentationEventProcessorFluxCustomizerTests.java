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
package org.eclipse.sirius.web.application.controllers.representations;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.details.dto.DetailsEventInput;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.services.representations.TestPayload;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.DetailsEventSubscriptionRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

/**
 * Used to test the representation event processor flux customizer.
 *
 * @author sbegaudeau
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=representationeventprocessorfluxcustomizer" })
public class RepresentationEventProcessorFluxCustomizerTests extends AbstractIntegrationTests {

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
    @DisplayName("Given a form representation, when we customize its output events, then additional payloads are received")
    public void givenFormRepresentationWhenWeCustomizeItsOuputEventsThenAdditionalPayloadsAreReceived() {
        var detailsRepresentationId = representationIdBuilder.buildDetailsRepresentationId(List.of(TestIdentifiers.EPACKAGE_OBJECT.toString()));
        var input = new DetailsEventInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_EDITING_CONTEXT_ID.toString(), detailsRepresentationId);
        var flux = this.detailsEventSubscriptionRunner.run(input);

        Predicate<Object> formContentMatcher = object -> Optional.of(object)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .isPresent();

        Predicate<Object> testPayloadMatcher = object -> Optional.of(object)
                .filter(TestPayload.class::isInstance)
                .isPresent();

        StepVerifier.create(flux)
                .expectNextMatches(formContentMatcher)
                .expectNextMatches(testPayloadMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
