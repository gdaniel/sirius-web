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
package org.eclipse.sirius.web.application.studio.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterators;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.document.services.EObjectRandomIDManager;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.library.dto.PublishLibrariesInput;
import org.eclipse.sirius.web.application.library.services.api.ILibraryPublicationHandler;
import org.eclipse.sirius.web.application.studio.services.api.IStudioCapableEditingContextPredicate;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.repositories.ILibraryRepository;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Handles the publication of libraries from studios.
 *
 * @author gdaniel
 */
@Service
public class StudioLibraryPublicationHandler implements ILibraryPublicationHandler {

    private final IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate;

    private final IEditingContextSearchService editingContextSearchService;

    private final ILibraryRepository libraryRepository;

    private final Logger logger = LoggerFactory.getLogger(StudioLibraryPublicationHandler.class);

    private final Predicate<EObject> isLibraryCandidate = eObject -> eObject instanceof Domain || eObject instanceof RepresentationDescription;


    public StudioLibraryPublicationHandler(IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate, IEditingContextSearchService editingContextSearchService, ILibraryRepository libraryRepository) {
        this.studioCapableEditingContextPredicate = Objects.requireNonNull(studioCapableEditingContextPredicate);
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.libraryRepository = Objects.requireNonNull(libraryRepository);
    }

    @Override
    public boolean canHandle(PublishLibrariesInput input) {
        return this.studioCapableEditingContextPredicate.test(input.projectId());
    }

    @Override
    public IResult<List<Library>> handle(PublishLibrariesInput input) {
        final IResult<List<Library>> result;
        List<Library> persistedLibraries = new ArrayList<>();
        Optional<IEditingContext> optionalEditingContext = this.editingContextSearchService.findById(input.projectId());
        if (optionalEditingContext.isPresent() && optionalEditingContext.get() instanceof EditingContext editingContext) {
            ResourceSet rSet = editingContext.getDomain().getResourceSet();
            rSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put(IEMFEditingContext.RESOURCE_SCHEME, new JSONResourceFactory());

            Map<EObject, Set<EObject>> dependencies = this.computeDependencies(rSet);
            this.reduceTransitiveDependencies(dependencies);

            // Move elements that will produce a library in a dedicated resource, this allows to serialize their content.
            // Note that we have to move all the elements before serializing the content to ensure that all the references
            // stay consistent with the produced libraries.
            for (Map.Entry<EObject, Set<EObject>> dependencyEntry : dependencies.entrySet()) {
                EObject source = dependencyEntry.getKey();
                if (this.isLibraryCandidate.test(source)) {
                    Optional<String> optionalLibraryName = this.getLibraryName(source);
                    if (optionalLibraryName.isPresent()) {
                        // Generate a stable URI to ease the import of libraries.
                        String resourceId = input.projectId() + optionalLibraryName.get() + input.version();
                        Resource libraryResource = rSet.createResource(new JSONResourceFactory().createResourceURI(UUID.nameUUIDFromBytes(resourceId.getBytes()).toString()));
                        libraryResource.getContents().add(source);
                        // Move dependencies that won't produce a library into the resource.
                        for (EObject dependency : dependencyEntry.getValue()) {
                            if (!this.isLibraryCandidate.test(dependency)) {
                                libraryResource.getContents().add(dependency);
                            }
                        }
                    }

                }
            }

            // Cache the created libraries to resolve inter-library dependencies.
            Map<EObject, Library> libraries = new HashMap<>();

            for (EObject source : dependencies.keySet()) {
                if (this.isLibraryCandidate.test(source)) {
                    Optional<String> optionalContent = this.toJson(source.eResource());
                    Optional<String> optionalName = this.getLibraryName(source);
                    if (optionalContent.isPresent() && optionalName.isPresent()) {
                        long librariesWithSameNameCount = libraries.values().stream().filter(library -> Objects.equals(library.getName(), optionalName.get())).count();
                        String name = optionalName.get();
                        if (librariesWithSameNameCount > 0) {
                            // Handle duplicated names by suffixing an index
                            name = name + librariesWithSameNameCount;
                        }
                        Library library = Library.newLibrary()
                                .project(AggregateReference.to(input.projectId()))
                                .name(name)
                                .content(optionalContent.get())
                                .version(input.version())
                                .description(input.description())
                                .build(input);
                        libraries.put(source, library);
                    }
                }
            }

            // Set the dependencies between libraries.
            for (Map.Entry<EObject, Set<EObject>> dependencyEntry : dependencies.entrySet()) {
                Library sourceLibrary = libraries.get(dependencyEntry.getKey());
                for (EObject dependency : dependencyEntry.getValue()) {
                    Library dependencyLibrary = libraries.get(dependency);
                    if (dependencyLibrary != null) {
                        sourceLibrary.addDependency(input, dependencyLibrary.getId());
                    }
                }
            }
            persistedLibraries = this.libraryRepository.saveAll(libraries.values());
            result = new Success<>(persistedLibraries);
        } else {
            result = new Failure<>("Cannot find project " + input.projectId());
        }
        return result;
    }


    private Map<EObject, Set<EObject>> computeDependencies(ResourceSet rSet) {
        List<Domain> domains = new ArrayList<>();
        Map<EObject, Set<EObject>> result = new HashMap<>();
        // Find references from elements that will be in a different library.
        rSet.getResources().stream()
            .flatMap(r -> StreamSupport.stream(Spliterators.spliteratorUnknownSize(r.getAllContents(), 0), false))
            .forEach(eObject -> {
                if (eObject instanceof Domain domain) {
                    // Cache the domains, we need them to resolve domainTypes.
                    domains.add(domain);
                    result.computeIfAbsent(domain, k -> new HashSet<>());
                } else if (eObject instanceof RepresentationDescription representationDescription) {
                    result.computeIfAbsent(representationDescription, k -> new HashSet<>());
                }
                EObject dependency = this.getContainingLibraryCandidate(eObject).orElse(eObject);
                ECrossReferenceAdapter.getCrossReferenceAdapter(eObject)
                    .getInverseReferences(eObject)
                    .forEach(setting -> {
                        if (setting.getEStructuralFeature() instanceof EReference eReference
                                && !eReference.isContainment()) {
                            EObject parent = this.getContainingLibraryCandidate(setting.getEObject()).orElse(setting.getEObject());
                            if (parent != dependency) {
                                result.computeIfAbsent(parent, k -> new HashSet<>()).add(dependency);
                            }
                        }
                    });
            });
        // Find domainTypes that refer to domains in another library.
        rSet.getResources().stream()
            .flatMap(r -> StreamSupport.stream(Spliterators.spliteratorUnknownSize(r.getAllContents(), 0), false))
            .forEach(eObject -> {
                EObject root = this.getContainingLibraryCandidate(eObject).orElse(eObject);
                eObject.eClass().getEAllAttributes().stream()
                    .filter(eAttribute -> eAttribute.getName().equals("domainType"))
                    .findFirst()
                    .ifPresent(domainTypeAttribute -> {
                        String domainType = (String) eObject.eGet(domainTypeAttribute);
                        if (domainType != null) {
                            String[] splittedDomainType = domainType.split("::");
                            String domainName = splittedDomainType[0];
                            domains.stream().filter(d -> d.getName().equals(domainName)).findFirst()
                                .ifPresent(d -> result.computeIfAbsent(root, k -> new HashSet<>()).add(d));
                        }

                    });
            });
        return result;
    }

    private void reduceTransitiveDependencies(Map<EObject, Set<EObject>> dependencies) {
        for (Map.Entry<EObject, Set<EObject>> entry : dependencies.entrySet()) {
            entry.getValue().removeIf(dependency ->
                entry.getValue().stream()
                    .filter(e -> e != dependency)
                    .flatMap(e -> dependencies.getOrDefault(e, Set.of()).stream())
                    .anyMatch(e -> e == dependency));
        }
    }

    private Optional<EObject> getContainingLibraryCandidate(EObject eObject) {
        final Optional<EObject> result;
        if (eObject instanceof RepresentationDescription || eObject instanceof Domain) {
            result = Optional.of(eObject);
        } else if (eObject == null) {
            result = Optional.empty();
        } else {
            result = this.getContainingLibraryCandidate(eObject.eContainer());
        }
        return result;
    }

    private Optional<String> toJson(Resource resource) {
        Optional<String> result = Optional.empty();
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Map<String, Object> saveOptions = new HashMap<>();
            saveOptions.put(JsonResource.OPTION_ENCODING, JsonResource.ENCODING_UTF_8);
            saveOptions.put(JsonResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
            saveOptions.put(JsonResource.OPTION_ID_MANAGER, new EObjectRandomIDManager());

            resource.save(outputStream, saveOptions);

            result = Optional.of(outputStream.toString());
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return result;
    }

    private Optional<String> getLibraryName(EObject eObject) {
        Optional<String> result = Optional.empty();
        if (eObject instanceof RepresentationDescription representationDescription) {
            result = Optional.ofNullable(representationDescription.getName());
        } else if (eObject instanceof Domain domain) {
            result = Optional.ofNullable(domain.getName());
        }
        return result;
    }
}
