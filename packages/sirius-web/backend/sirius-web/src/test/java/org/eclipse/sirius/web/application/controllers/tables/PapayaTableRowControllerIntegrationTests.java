/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST and others.
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
package org.eclipse.sirius.web.application.controllers.tables;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.tables.tests.TableEventPayloadConsumer.assertRefreshedTableThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.tables.TableEventInput;
import org.eclipse.sirius.components.collaborative.tables.dto.InvokeRowContextMenuEntryInput;
import org.eclipse.sirius.components.collaborative.tables.dto.ResetTableRowsHeightInput;
import org.eclipse.sirius.components.collaborative.tables.dto.ResizeTableRowInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.tests.graphql.InvokeRowContextMenuEntryMutationRunner;
import org.eclipse.sirius.components.tables.tests.graphql.ResetTableRowsHeightMutationRunner;
import org.eclipse.sirius.components.tables.tests.graphql.ResizeTableRowMutationRunner;
import org.eclipse.sirius.components.tables.tests.graphql.RowContextMenuQueryRunner;
import org.eclipse.sirius.components.tables.tests.graphql.RowFiltersQueryRunner;
import org.eclipse.sirius.components.tables.tests.graphql.TableEventSubscriptionRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.papaya.representations.table.PackageTableRowContextMenuProvider;
import org.eclipse.sirius.web.papaya.representations.table.PackageTableRowFiltersProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedTableSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.representation.RepresentationIdBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of the table's row with a papaya model.
 *
 * @author Jerome Gout
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class PapayaTableRowControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedTableSubscription givenCreatedTableSubscription;

    @Autowired
    private ResizeTableRowMutationRunner resizeTableRowMutationRunner;

    @Autowired
    private ResetTableRowsHeightMutationRunner resetTableRowsHeightMutationRunner;

    @Autowired
    private InvokeRowContextMenuEntryMutationRunner invokeRowContextMenuEntryMutationRunner;

    @Autowired
    private RowContextMenuQueryRunner rowContextMenuQueryRunner;

    @Autowired
    private RowFiltersQueryRunner rowFiltersQueryRunner;

    @Autowired
    private TableEventSubscriptionRunner tableEventSubscriptionRunner;

    @Autowired
    private IGivenCommittedTransaction givenCommittedTransaction;

    @Autowired
    private RepresentationIdBuilder representationIdBuilder;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToTable() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                "papaya_package_table_description",
                PapayaIdentifiers.SIRIUS_WEB_DOMAIN_PACKAGE.toString(),
                "Table"
        );
        return this.givenCreatedTableSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table, when a row resize mutation is triggered, then the representation is refreshed with the new row height")
    public void givenTableWhenRowResizeMutationTriggeredThenTheRepresentationIsRefreshedWithNewRowHeight() {
        var flux = this.givenSubscriptionToTable();

        var rowRef = new AtomicReference<Line>();
        var tableId = new AtomicReference<String>();

        Consumer<Object> initialTableContentConsumer = assertRefreshedTableThat(table -> {
            tableId.set(table.getId());
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(3);
            rowRef.set(table.getLines().get(0));
            assertThat(table.getLines().get(0).getHeight()).isEqualTo(53);
        });

        Runnable resizeRow = () -> {
            var lineToChange = rowRef.get();
            var resizeTableRowInput = new ResizeTableRowInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    tableId.get(), tableId.get(), lineToChange.getId().toString(), 100);
            var result = this.resizeTableRowMutationRunner.run(resizeTableRowInput);

            String typename = JsonPath.read(result, "$.data.resizeTableRow.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedTableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(3);
            assertThat(table.getLines().get(0).getHeight()).isEqualTo(100);
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .then(resizeRow)
                .consumeNextWith(updatedTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table with a resized row, when a reset rows height mutation is triggered, then the representation is refreshed with no row height")
    public void givenTableWithAResizedRowWhenRowAResetRowsHeightMutationIsTriggeredThenTheRepresentationIsRefreshedWithNoRowHeight() {
        this.givenCommittedTransaction.commit();

        var tableEventInput = new TableEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), PapayaIdentifiers.PAPAYA_PACKAGE_TABLE_REPRESENTATION.toString());
        var flux = this.tableEventSubscriptionRunner.run(tableEventInput);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        var tableId = new AtomicReference<String>();

        Consumer<Object> initialTableContentConsumer = assertRefreshedTableThat(table -> {
            tableId.set(table.getId());
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(3);
            assertThat(table.getLines().get(1).getHeight()).isEqualTo(100);
        });

        Runnable resetRows = () -> {
            var resetTableRowsHeightInput = new ResetTableRowsHeightInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    tableId.get(), tableId.get());
            var result = this.resetTableRowsHeightMutationRunner.run(resetTableRowsHeightInput);

            String typename = JsonPath.read(result, "$.data.resetTableRowsHeight.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedTableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(3);
            assertThat(table.getLines().get(1).getHeight()).isEqualTo(53);
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .then(resetRows)
                .consumeNextWith(updatedTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table, when row context menu entries are queried, then the correct entries are returned")
    public void giveATableWhenRowContextMenuEntriesAreQueriedThenTheCorrectEntriesAreReturned() {
        this.givenCommittedTransaction.commit();

        var tableEventInput = new TableEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), PapayaIdentifiers.PAPAYA_PACKAGE_TABLE_REPRESENTATION.toString());
        var flux = this.tableEventSubscriptionRunner.run(tableEventInput);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        var tableId = new AtomicReference<String>();
        var rowId = new AtomicReference<UUID>();

        Consumer<Object> initialTableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(3);
            tableId.set(table.getId());
            rowId.set(table.getLines().get(0).getId());
        });

        Runnable getContextMenuActions = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "representationId", this.representationIdBuilder.buildTableRepresentationId(tableId.get(), null, "NEXT", 10, List.of(), List.of(), List.of()),
                    "tableId", tableId.get(),
                    "rowId", rowId.get().toString()
            );
            var result = this.rowContextMenuQueryRunner.run(variables);

            List<String> actionLabels = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.rowContextMenuEntries[*].label");
            assertThat(actionLabels).isNotEmpty().hasSize(1);
            assertThat(actionLabels.get(0)).isEqualTo("Delete row");
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .then(getContextMenuActions)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table with pagination in next data, when row context menu entries are queried, then the correct entries are returned")
    public void giveATableWithPaginationInNextDataWhenRowContextMenuEntriesAreQueriedThenTheCorrectEntriesAreReturned() {
        var representationId = this.representationIdBuilder.buildTableRepresentationId(PapayaIdentifiers.PAPAYA_PACKAGE_TABLE_REPRESENTATION.toString(),
                PapayaIdentifiers.PAPAYA_SUCCESS_CLASS_OBJECT.toString(), "NEXT", 1, List.of(), List.of(), List.of());
        var tableEventInput = new TableEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), representationId);
        var flux = this.tableEventSubscriptionRunner.run(tableEventInput);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        var tableId = new AtomicReference<String>();
        var rowId = new AtomicReference<UUID>();

        Consumer<Object> initialTableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(1);
            tableId.set(table.getId());
            rowId.set(table.getLines().get(0).getId());
        });

        Runnable getContextMenuActions = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "representationId",
                    this.representationIdBuilder.buildTableRepresentationId(tableId.get(), PapayaIdentifiers.PAPAYA_SUCCESS_CLASS_OBJECT.toString(), "NEXT", 1, List.of(), List.of(), List.of()),
                    "tableId", tableId.get(),
                    "rowId", rowId.get().toString()
            );
            var result = this.rowContextMenuQueryRunner.run(variables);

            List<String> actionLabels = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.rowContextMenuEntries[*].label");
            assertThat(actionLabels).isNotEmpty().hasSize(1);
            assertThat(actionLabels.get(0)).isEqualTo("Delete row");
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .then(getContextMenuActions)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }


    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table, when a row context menu entry is triggered, then the entry is correctly invoked")
    public void giveATableWhenARowContextMenuEntryIsTriggeredThenTheEntryIsCorrectlyInvoked() {
        this.givenCommittedTransaction.commit();

        var tableEventInput = new TableEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), PapayaIdentifiers.PAPAYA_PACKAGE_TABLE_REPRESENTATION.toString());
        var flux = this.tableEventSubscriptionRunner.run(tableEventInput);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        var tableId = new AtomicReference<String>();
        var rowId = new AtomicReference<UUID>();

        Consumer<Object> initialTableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(3);
            tableId.set(table.getId());
            rowId.set(table.getLines().get(0).getId());
        });

        Runnable invokeDeleteRowAction = () -> {
            var invokeRowContextMenuEntryInput = new InvokeRowContextMenuEntryInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    tableId.get(),
                    tableId.get(),
                    rowId.get(),
                    PackageTableRowContextMenuProvider.DELETE_ID
            );
            var result = this.invokeRowContextMenuEntryMutationRunner.run(invokeRowContextMenuEntryInput);

            String typename = JsonPath.read(result, "$.data.invokeRowContextMenuEntry.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedTableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(2);
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .then(invokeDeleteRowAction)
                .consumeNextWith(updatedTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table representation, when we expand a row, then child of expanded row is returned")
    public void givenTableRepresentationWhenWeExpandARowThenTheChildOfExpandedRowIsReturned() {
        var flux = this.givenSubscriptionToTable();

        var tableId = new AtomicReference<String>();
        Consumer<Object> initialTableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getColumns()).hasSize(6);
            assertThat(table.getLines()).hasSize(3);
            assertThat(table.getLines().get(0).getDepthLevel()).isEqualTo(0);
            assertThat(table.getLines().get(1).getDepthLevel()).isEqualTo(0);
            tableId.set(table.getId());
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        String representationId = this.representationIdBuilder.buildTableRepresentationId(tableId.get(), null, "NEXT", 10, List.of(PapayaIdentifiers.PAPAYA_FAILURE_CLASS_OBJECT.toString()), List.of(), List.of());
        var tableEventInput = new TableEventInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), representationId);
        var expandedFlux = this.tableEventSubscriptionRunner.run(tableEventInput);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        Consumer<Object> expandedTableContentConsumer = assertRefreshedTableThat(table -> {
            assertThat(table).isNotNull();
            assertThat(table.getLines()).hasSize(4);
            assertThat(table.getLines().get(0).getDepthLevel()).isEqualTo(0);
            assertThat(table.getLines().get(1).getDepthLevel()).isEqualTo(0);
            assertThat(table.getLines().get(2).getDepthLevel()).isEqualTo(1);
            assertThat(table.getLines().get(3).getDepthLevel()).isEqualTo(0);
        });

        StepVerifier.create(expandedFlux)
                .consumeNextWith(expandedTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a table id, when we request its row filters, then the list is returned")
    public void givenTableIdWhenWeRequestItsRowFiltersThenTheListIsReturned() {
        var flux = this.givenSubscriptionToTable();

        var tableId = new AtomicReference<String>();
        Consumer<Object> initialTableContentConsumer = assertRefreshedTableThat(table -> tableId.set(table.getId()));

        StepVerifier.create(flux)
                .consumeNextWith(initialTableContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

        Map<String, Object> variables = Map.of(
                "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                "representationId", PapayaIdentifiers.PAPAYA_PACKAGE_TABLE_REPRESENTATION.toString(),
                "tableId", tableId.get()
        );
        var result = this.rowFiltersQueryRunner.run(variables);

        List<String> tableFilterIds = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.rowFilters[*].id");
        assertThat(tableFilterIds)
                .isNotEmpty()
                .anySatisfy(tableFilterId -> assertThat(tableFilterId).isEqualTo(PackageTableRowFiltersProvider.HIDE_RECORDS_ROW_FILTER_ID));
    }
}
