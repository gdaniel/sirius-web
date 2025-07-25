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

import org.eclipse.sirius.components.forms.Textfield;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.views.details.dto.DetailsEventInput;
import org.eclipse.sirius.web.data.StudioIdentifiers;
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
 * Integration tests of the label style details view.
 *
 * @author frouene
 */

@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LabelStyleDetailsViewControllerTests extends AbstractIntegrationTests {

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
    @DisplayName("Given an InsideLabelStyleDescription, when we subscribe to its properties events, then the form is sent")
    public void givenInsideLabelStyleDescriptionWhenWeSubscribeToItsPropertiesEventsThenTheFormIsSent() {
        var detailRepresentationId = representationIdBuilder.buildDetailsRepresentationId(List.of(StudioIdentifiers.HUMAN_INSIDE_LABEL_STYLE_OBJECT.toString()));
        var input = new DetailsEventInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(), detailRepresentationId);
        var flux = this.detailsEventSubscriptionRunner.run(input);

        Consumer<Object> formContentMatcher = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("InsideLabelStyle").group("Core Properties");

            var borderSizeTextField = groupNavigator.findWidget("Border Size", Textfield.class);
            assertThat(borderSizeTextField).isNotNull();

            var backgroundReferenceWidget = groupNavigator.findWidget("Background", ReferenceWidget.class);
            assertThat(backgroundReferenceWidget).isNotNull();

            var borderColorReferenceWidget = groupNavigator.findWidget("Border Color", ReferenceWidget.class);
            assertThat(borderColorReferenceWidget).isNotNull();
        });

        StepVerifier.create(flux)
                .consumeNextWith(formContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

}

