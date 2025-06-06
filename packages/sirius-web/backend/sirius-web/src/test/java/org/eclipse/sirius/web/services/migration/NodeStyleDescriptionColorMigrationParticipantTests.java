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
package org.eclipse.sirius.web.services.migration;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.emf.migration.api.MigrationData;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionInput;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionRunner;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionSuccessPayload;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.document.dto.UploadDocumentInput;
import org.eclipse.sirius.web.application.document.dto.UploadDocumentSuccessPayload;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.data.MigrationIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.UploadDocumentMutationRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of NodeStyleDescriptionColorMigrationParticipant.
 *
 * @author frouene
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NodeStyleDescriptionColorMigrationParticipantTests extends AbstractIntegrationTests {

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Autowired
    private IGivenCommittedTransaction givenCommittedTransaction;

    @Autowired
    private ExecuteEditingContextFunctionRunner executeEditingContextFunctionRunner;

    @Autowired
    private UploadDocumentMutationRunner uploadDocumentMutationRunner;

    @Autowired
    private List<IMigrationParticipant> migrationParticipants;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project with an old model, NodeStyleDescriptionColorMigrationParticipant migrates the model correctly")
    public void givenAnOldModelMigrationParticipantCanBeContributedToUpdateTheModel() {
        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_NODE_STYLE_DESCRIPTION_COLOR_STUDIO.toString());
        assertThat(optionalEditingContext).isPresent();
        this.testIsMigrationSuccessful(optionalEditingContext.get());
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an uploaded project with an old model, NodeStyleDescriptionColorMigrationParticipant migrates the model correctly")
    public void givenAnOldViewDiagramMigrationServiceIsExecutedProperly() {
        var content = """
                {
                  "json": { "version": "1.0", "encoding": "utf-8" },
                  "ns": {
                    "diagram": "http://www.eclipse.org/sirius-web/diagram",
                    "view": "http://www.eclipse.org/sirius-web/view"
                  },
                  "content": [
                    {
                      "id": "9674f8f7-ff1a-4061-bb32-a4a235a9c2ca",
                      "eClass": "view:View",
                      "data": {
                      "colorPalettes": [
                          {
                            "data": {
                              "colors": [
                                {
                                  "data": {
                                    "name": "color_empty",
                                    "value": ""
                                  },
                                  "eClass": "view:FixedColor",
                                  "id": "63184ddc-74c4-4888-bb65-418361689e2b"
                                }
                              ]
                            },
                            "eClass": "view:ColorPalette",
                            "id": "d315989f-826f-490d-b898-d94200b0caa2"
                          }
                        ],
                        "descriptions": [
                          {
                            "id": "22fb1f4d-109d-4e73-bff0-f7cd96fb5fbb",
                            "eClass": "diagram:DiagramDescription",
                            "data": {
                              "name": "NodeStyleDescription#color migration_upload",
                              "domainType": "flow::System",
                              "nodeDescriptions": [
                                {
                                  "id": "6949ddfe-f480-473b-bc8a-6f2bdde07e4d",
                                  "eClass": "diagram:NodeDescription",
                                  "data": {
                                    "name": "NodeWithoutImage migration",
                                    "domainType": "flow::CompositeProcessor",
                                    "childrenLayoutStrategy": {
                                      "id": "20651d93-2ee5-41cb-b2bd-1e75958c73cf",
                                      "eClass": "diagram:FreeFormLayoutStrategyDescription"
                                    },
                                    "style": {
                                      "id": "92fe9d3f-2c5b-41ab-81ea-8482c8cd57b9",
                                      "eClass": "diagram:RectangularNodeStyleDescription",
                                      "data": {
                                        "borderColor": "//@colorPalettes.0/@colors.0",
                                        "color": "//@colorPalettes.0/@colors.0"
                                      }
                                    }
                                  }
                                }
                              ]
                            }
                          }
                        ]
                      }
                    }
                  ]
                }
                """;
        this.uploadDocument(MigrationIdentifiers.MIGRATION_NODE_STYLE_DESCRIPTION_COLOR_STUDIO.toString(), content);
    }

    private void uploadDocument(String editingContextId, String content) {
        this.givenCommittedTransaction.commit();

        var file = new UploadFile("test_upload", new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
        var input = new UploadDocumentInput(UUID.randomUUID(), editingContextId, file);
        var result = this.uploadDocumentMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();

        String typename = JsonPath.read(result, "$.data.uploadDocument.__typename");
        assertThat(typename).isEqualTo(UploadDocumentSuccessPayload.class.getSimpleName());

        Predicate<IPayload> predicate = payload -> Optional.of(payload)
                .filter(ExecuteEditingContextFunctionSuccessPayload.class::isInstance)
                .map(ExecuteEditingContextFunctionSuccessPayload.class::cast)
                .map(ExecuteEditingContextFunctionSuccessPayload::result)
                .filter(Boolean.class::isInstance)
                .map(Boolean.class::cast)
                .orElse(false);

        var optionalLastMigrationData = this.migrationParticipants.stream()
                .sorted(Comparator.comparing(IMigrationParticipant::getVersion).reversed())
                .map(migrationParticipant -> new MigrationData(migrationParticipant.getClass().getSimpleName(), migrationParticipant.getVersion()))
                .findFirst();
        assertThat(optionalLastMigrationData).isPresent();
        var lastMigrationData = optionalLastMigrationData.get();

        BiFunction<IEditingContext, IInput, IPayload> function = (editingContext, executeEditingContextFunctionInput) -> {
            var isMigrated = Optional.of(editingContext)
                    .filter(EditingContext.class::isInstance)
                    .map(EditingContext.class::cast)
                    .map(siriusWebEditingContext -> siriusWebEditingContext.getViews().stream()
                            .anyMatch(view -> view.eResource().eAdapters().stream()
                                    .filter(ResourceMetadataAdapter.class::isInstance)
                                    .map(ResourceMetadataAdapter.class::cast)
                                    .filter(resourceMetadataAdapter -> resourceMetadataAdapter.getLastMigrationData() != null)
                                    .anyMatch(resourceMetadataAdapter -> resourceMetadataAdapter.getLastMigrationData().migrationVersion().equals(lastMigrationData.migrationVersion())
                                            && resourceMetadataAdapter.getLastMigrationData().lastMigrationPerformed().equals(lastMigrationData.lastMigrationPerformed()))
                            ))
                    .orElse(false);
            return new ExecuteEditingContextFunctionSuccessPayload(executeEditingContextFunctionInput.id(), isMigrated);
        };

        var mono = this.executeEditingContextFunctionRunner.execute(new ExecuteEditingContextFunctionInput(UUID.randomUUID(), editingContextId, function));
        StepVerifier.create(mono)
                .expectNextMatches(predicate)
                .thenCancel()
                .verify();
    }

    private void testIsMigrationSuccessful(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            var optionalDiagramDescription = siriusWebEditingContext.getViews().stream().flatMap(view -> view.getDescriptions().stream())
                    .filter(representationDescription -> representationDescription.getName().equals(MigrationIdentifiers.MIGRATION_NODE_STYLE_DESCRIPTION_COLOR_STUDIO_DIAGRAM)).findFirst();
            assertThat(optionalDiagramDescription).isPresent();
            assertThat(optionalDiagramDescription.get()).isInstanceOf(DiagramDescription.class);
            optionalDiagramDescription.ifPresent(representationDescription -> {
                if (representationDescription instanceof DiagramDescription diagramDescription) {
                    assertThat(diagramDescription.getNodeDescriptions()).hasSize(1);
                    diagramDescription.getNodeDescriptions().forEach(nodeDescription -> {
                        if (nodeDescription.getStyle() instanceof RectangularNodeStyleDescription rectangularNodeStyleDescription) {
                            assertThat(rectangularNodeStyleDescription.getBackground()).isNotNull();
                            assertThat(rectangularNodeStyleDescription.getBackground().getName()).isEqualTo("color_empty");
                        }
                    });
                }
            });
        }
    }


}
