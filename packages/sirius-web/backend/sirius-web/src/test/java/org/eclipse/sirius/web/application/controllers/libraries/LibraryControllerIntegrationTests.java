/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.controllers.libraries;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.library.dto.PublishLibrariesInput;
import org.eclipse.sirius.web.application.library.dto.PublishLibrariesSuccessPayload;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.LibraryDependency;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.LibrariesQueryRunner;
import org.eclipse.sirius.web.tests.graphql.PublishLibrariesMutationRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to get libraries from the GraphQL API.
 *
 * @author gdaniel
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibraryControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private LibrariesQueryRunner librariesQueryRunner;

    @Autowired
    private PublishLibrariesMutationRunner publishLibrariesMutationRunner;

    @Autowired
    private ILibrarySearchService librarySearchService;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a set of libraries, when a query is performed, then the libraries are returned")
    public void givenSetOfLibrariesWhenQueryIsPerformedThenTheLibrariesAreReturned() {
        Map<String, Object> variables = Map.of("page", 0, "limit", 2);
        var result = this.librariesQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.libraries.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.libraries.pageInfo.hasNextPage");
        assertThat(hasNextPage).isTrue();

        String startCursor = JsonPath.read(result, "$.data.viewer.libraries.pageInfo.startCursor");
        assertThat(startCursor).isNotBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.libraries.pageInfo.endCursor");
        assertThat(endCursor).isNotBlank();

        int count = JsonPath.read(result, "$.data.viewer.libraries.pageInfo.count");
        assertThat(count).isGreaterThan(2);

        List<String> libraryIds = JsonPath.read(result, "$.data.viewer.libraries.edges[*].node.id");
        assertThat(libraryIds).hasSize(2);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a valid studio project ID, when the mutation is performed, then the libraries are published")
    public void givenValidStudioProjectIdWhenMutationIsPerformedThenLibrariesArePublished() {
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
        var page = this.librarySearchService.findAll(PageRequest.of(1, 1));
        long initialLibraryCount = page.getTotalElements();

        String version = "0.0.1";
        String description = "Initial version";

        var input = new PublishLibrariesInput(UUID.randomUUID(), StudioIdentifiers.SAMPLE_STUDIO_PROJECT, version, description);
        var result = this.publishLibrariesMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();

        String typename = JsonPath.read(result, "$.data.publishLibraries.__typename");
        assertThat(typename).isEqualTo(PublishLibrariesSuccessPayload.class.getSimpleName());

        List<String> libraryIds = JsonPath.read(result, "$.data.publishLibraries.libraries[*].id");
        long updatedLibraryCount = this.librarySearchService.findAll(PageRequest.of(1, 1)).getTotalElements();
        assertThat(updatedLibraryCount).isEqualTo(initialLibraryCount + 5);


        List<Library> libraries = libraryIds.stream().map(id -> this.librarySearchService.findById(UUID.fromString(id))).map(o -> o.get()).toList();
        assertThat(libraries).map(Library::getName)
            .hasSize(5)
            .containsAll(List.of("buck", "Human Form", "New Table Description", "Root Diagram", "Root Diagram1"));
        Optional<Library> buckLibrary = libraries.stream()
                .filter(library -> library.getName().equals("buck"))
                .findFirst();
        assertThat(buckLibrary.isPresent());
        this.assertThatLibraryHasCorrectVersionDescriptionAndDependencies(buckLibrary.get(), version, description, List.of());

        Optional<Library> humanFormLibrary = libraries.stream()
                .filter(library -> library.getName().equals("Human Form"))
                .findFirst();
        assertThat(humanFormLibrary).isPresent();
        this.assertThatLibraryHasCorrectVersionDescriptionAndDependencies(humanFormLibrary.get(), version, description, List.of(buckLibrary.get().getId()));

        Optional<Library> newTableDescriptionLibrary = libraries.stream()
                .filter(library -> library.getName().equals("New Table Description"))
                .findFirst();
        assertThat(newTableDescriptionLibrary).isPresent();
        this.assertThatLibraryHasCorrectVersionDescriptionAndDependencies(newTableDescriptionLibrary.get(), version, description, List.of(buckLibrary.get().getId()));

        Optional<Library> rootDiagramDescriptionLibrary = libraries.stream()
                .filter(library -> library.getName().equals("Root Diagram"))
                .findFirst();
        assertThat(rootDiagramDescriptionLibrary).isPresent();
        this.assertThatLibraryHasCorrectVersionDescriptionAndDependencies(rootDiagramDescriptionLibrary.get(), version, description, List.of(buckLibrary.get().getId()));

        Optional<Library> rootDiagram1DescriptionLibrary = libraries.stream()
                .filter(library -> library.getName().equals("Root Diagram1"))
                .findFirst();
        assertThat(rootDiagram1DescriptionLibrary).isPresent();
        this.assertThatLibraryHasCorrectVersionDescriptionAndDependencies(rootDiagram1DescriptionLibrary.get(), version, description, List.of(buckLibrary.get().getId()));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a valid non-studio project ID, when the mutation is performed, then no library is published")
    public void givenValidNonStudioProjectIdWhenMutationIsPerformedThenNoLibraryIsPublished() {
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
        var page = this.librarySearchService.findAll(PageRequest.of(1, 1));
        long initialLibraryCount = page.getTotalElements();

        var input = new PublishLibrariesInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT, "1.0.0", "Sample description");
        var result = this.publishLibrariesMutationRunner.run(input);
        TestTransaction.flagForCommit();
        TestTransaction.end();

        String typename = JsonPath.read(result, "$.data.publishLibraries.__typename");
        assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());

        var updatedPage = this.librarySearchService.findAll(PageRequest.of(1, 1));
        assertThat(updatedPage.getTotalElements()).isEqualTo(initialLibraryCount);

    }

    private void assertThatLibraryHasCorrectVersionDescriptionAndDependencies(Library library, String version, String description, List<UUID> dependencyIds) {
        assertThat(library)
            .returns(version, Library::getVersion)
            .returns(description, Library::getDescription)
            .extracting(Library::getDependencies)
            .asInstanceOf(InstanceOfAssertFactories.set(LibraryDependency.class))
            .hasSize(dependencyIds.size())
            .map(LibraryDependency::dependencyLibraryId)
            .containsAll(dependencyIds);
    }
}
