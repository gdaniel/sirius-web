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
package org.eclipse.sirius.web.domain.boundedcontexts.library;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.AbstractValidatingAggregateRoot;
import org.eclipse.sirius.web.domain.boundedcontexts.library.events.LibraryContentUpdatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.library.events.LibraryCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.library.events.LibraryDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.library.events.LibraryDependencyAddedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.library.events.LibraryDependencyRemovedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.library.events.LibraryDescriptionUpdatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.library.events.LibraryNameUpdatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.library.events.LibraryVersionUpdatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

/**
 * The aggregate root of the library bounded context.
 *
 * @author gdaniel
 */
@Table("library")
public class Library extends AbstractValidatingAggregateRoot<Library> implements Persistable<UUID> {

    @Transient
    private boolean isNew;

    @Id
    private UUID id;

    @Column("project_id")
    private AggregateReference<Project, String> project;

    private String name;

    private String content;

    @MappedCollection(idColumn = "library_id")
    private Set<LibraryDependency> dependencies = new LinkedHashSet<>();

    private String version;

    private String description;

    private Instant createdOn;

    private Instant lastModifiedOn;

    @Override
    public UUID getId() {
        return this.id;
    }

    public AggregateReference<Project, String> getProject() {
        return this.project;
    }

    public String getName() {
        return this.name;
    }

    public void updateName(ICause cause, String newName) {
        if (!Objects.equals(this.name, newName)) {
            this.name = newName;
            this.lastModifiedOn = Instant.now();

            this.registerEvent(new LibraryNameUpdatedEvent(UUID.randomUUID(), this.lastModifiedOn, cause, this));
        }
    }

    public String getContent() {
        return this.content;
    }

    public void updateContent(ICause cause, String newContent) {
        if (!Objects.equals(this.content, newContent)) {
            this.content = newContent;
            this.lastModifiedOn = Instant.now();

            this.registerEvent(new LibraryContentUpdatedEvent(UUID.randomUUID(), this.lastModifiedOn, cause, this));
        }
    }

    public Set<LibraryDependency> getDependencies() {
        return Collections.unmodifiableSet(this.dependencies);
    }

    public void addDependency(ICause cause, UUID dependencyId) {
        var newDependency = new LibraryDependency(dependencyId);

        this.dependencies.add(newDependency);
        this.lastModifiedOn = Instant.now();

        this.registerEvent(new LibraryDependencyAddedEvent(UUID.randomUUID(), this.lastModifiedOn, cause, this, newDependency));
    }

    public void removeNature(ICause cause, String dependencyId) {
        this.dependencies.stream()
                .filter(dependency -> dependency.dependencyLibraryId().equals(dependencyId))
                .findFirst()
                .ifPresent(dependency -> {
                    this.dependencies.remove(dependency);
                    this.lastModifiedOn = Instant.now();

                    this.registerEvent(new LibraryDependencyRemovedEvent(UUID.randomUUID(), this.lastModifiedOn, cause, this, dependency));
                });
    }

    public String getVersion() {
        return this.version;
    }

    public void updateVersion(ICause cause, String newVersion) {
        if (!Objects.equals(this.version, newVersion)) {
            this.version = newVersion;
            this.lastModifiedOn = Instant.now();

            this.registerEvent(new LibraryVersionUpdatedEvent(UUID.randomUUID(), this.lastModifiedOn, cause, this));
        }
    }

    public String getDescription() {
        return this.description;
    }

    public void updateDescription(ICause cause, String newDescription) {
        if (!Objects.equals(this.description, newDescription)) {
            this.description = newDescription;
            this.lastModifiedOn = Instant.now();

            this.registerEvent(new LibraryDescriptionUpdatedEvent(UUID.randomUUID(), this.lastModifiedOn, cause, this));
        }
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Instant getLastModifiedOn() {
        return this.lastModifiedOn;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public void dispose(ICause cause) {
        this.registerEvent(new LibraryDeletedEvent(UUID.randomUUID(), this.lastModifiedOn, cause, this));
    }

    public static Builder newLibrary() {
        return new Builder();
    }

    /**
     * Used to create new libraries.
     *
     * @author gdaniel
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private AggregateReference<Project, String> project;

        private String name;

        private String content;

        private Set<LibraryDependency> dependencies = new LinkedHashSet<>();

        private String version;

        private String description;

        public Builder project(AggregateReference<Project, String> project) {
            this.project = Objects.requireNonNull(project);
            return this;
        }

        public Builder name(String name) {
            this.name = Objects.requireNonNull(name);
            return this;
        }

        public Builder content(String content) {
            this.content = Objects.requireNonNull(content);
            return this;
        }

        public Builder dependencies(Collection<UUID> dependencies) {
            this.dependencies = dependencies.stream()
                .map(LibraryDependency::new)
                .collect(Collectors.toSet());
            return this;
        }

        public Builder version(String version) {
            this.version = Objects.requireNonNull(version);
            return this;
        }

        public Builder description(String description) {
            this.description = Objects.requireNonNull(description);
            return this;
        }

        public Library build(ICause cause) {
            var library = new Library();
            library.isNew = true;
            library.id = UUID.randomUUID();
            library.project = Objects.requireNonNull(this.project);
            library.name = Objects.requireNonNull(this.name);
            library.content = Objects.requireNonNull(this.content);
            library.dependencies = Objects.requireNonNull(this.dependencies);
            library.version = Objects.requireNonNull(this.version);
            library.description = Objects.requireNonNull(this.description);

            var now = Instant.now();
            library.createdOn = now;
            library.lastModifiedOn = now;

            library.registerEvent(new LibraryCreatedEvent(UUID.randomUUID(), now, cause, library));
            return library;
        }
    }

}
