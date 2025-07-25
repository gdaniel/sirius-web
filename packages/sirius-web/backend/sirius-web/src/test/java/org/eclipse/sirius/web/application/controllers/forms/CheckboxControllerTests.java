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

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.EditCheckboxInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.Checkbox;
import org.eclipse.sirius.components.forms.tests.graphql.EditCheckboxMutationRunner;
import org.eclipse.sirius.components.forms.tests.navigation.FormNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.services.forms.FormWithCheckboxDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedFormSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of the checkbox widget.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"sirius.web.test.enabled=studio"})
public class CheckboxControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedFormSubscription givenCreatedFormSubscription;

    @Autowired
    private FormWithCheckboxDescriptionProvider formWithCheckboxDescriptionProvider;

    @Autowired
    private EditCheckboxMutationRunner editCheckboxMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToCheckboxForm() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(),
                this.formWithCheckboxDescriptionProvider.getRepresentationDescriptionId(),
                StudioIdentifiers.DOMAIN_OBJECT.toString(),
                "FormWithCheckbox"
        );
        return this.givenCreatedFormSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a checkbox widget, when it is displayed, then it is properly initialized")
    public void givenCheckboxWidgetWhenItIsDisplayedThenItIsProperlyInitialized() {
        var flux = this.givenSubscriptionToCheckboxForm();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var checkbox = groupNavigator.findWidget("Name", Checkbox.class);

            assertThat(checkbox)
                    .hasLabel("Name")
                    .hasValue(true)
                    .hasHelp("Does the object have a name?")
                    .isNotReadOnly();
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a checkbox widget, when it is edited, then its value is updated")
    public void givenCheckboxWidgetWhenItIsEditedThenItsValueIsUpdated() {
        var flux = this.givenSubscriptionToCheckboxForm();

        var formId = new AtomicReference<String>();
        var checkboxId = new AtomicReference<String>();

        Consumer<Object> initialFormContentConsumer = assertRefreshedFormThat(form -> {
            formId.set(form.getId());

            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var checkbox = groupNavigator.findWidget("Name", Checkbox.class);

            checkboxId.set(checkbox.getId());
        });

        Runnable editCheckbox = () -> {
            var input = new EditCheckboxInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.toString(), formId.get(), checkboxId.get(), false);
            var result = this.editCheckboxMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editCheckbox.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedFormContentConsumer = assertRefreshedFormThat(form -> {
            var groupNavigator = new FormNavigator(form).page("Page").group("Group");
            var checkbox = groupNavigator.findWidget("Name", Checkbox.class);

            assertThat(checkbox)
                    .hasLabel("Name")
                    .hasValue(false)
                    .hasHelp("Does the object have a name?")
                    .isNotReadOnly();
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialFormContentConsumer)
                .then(editCheckbox)
                .consumeNextWith(updatedFormContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
