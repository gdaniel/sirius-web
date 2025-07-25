/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.browser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.collaborative.browser.api.IModelBrowserRootCandidateSearchProvider;
import org.eclipse.sirius.components.core.CoreImageConstants;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.core.api.SemanticKindConstants;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.springframework.stereotype.Service;

/**
 * This class is used to provide the description of the model browser tree.
 *
 * @author pcdavid
 */
@Service
public class ModelBrowserDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    public static final String CONTAINER_DESCRIPTION_ID = UUID.nameUUIDFromBytes("model_browser_container_tree_description".getBytes()).toString();

    public static final String REFERENCE_DESCRIPTION_ID = UUID.nameUUIDFromBytes("model_browser_reference_tree_description".getBytes()).toString();

    public static final String REPRESENTATION_NAME = "Model Browser";

    public static final String DOCUMENT_KIND = "siriusWeb://document";

    public static final String PREFIX = "modelBrowser://";

    public static final String MODEL_BROWSER_CONTAINER_PREFIX = "modelBrowser://container";

    public static final String MODEL_BROWSER_REFERENCE_PREFIX = "modelBrowser://reference";

    private final IObjectService objectService;

    private final IIdentityService identityService;

    private final ILabelService labelService;

    private final IURLParser urlParser;

    private final IEMFKindService emfKindService;

    private final List<IModelBrowserRootCandidateSearchProvider> candidateProviders;

    private final IModelBrowserRootCandidateSearchProvider defaultCandidateProvider;

    public ModelBrowserDescriptionProvider(IObjectService objectService, IIdentityService identityService, ILabelService labelService, IURLParser urlParser, IEMFKindService emfKindService, List<IModelBrowserRootCandidateSearchProvider> candidateProviders) {
        this.objectService = Objects.requireNonNull(objectService);
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
        this.urlParser = Objects.requireNonNull(urlParser);
        this.emfKindService = Objects.requireNonNull(emfKindService);
        this.candidateProviders = Objects.requireNonNull(candidateProviders);
        this.defaultCandidateProvider = new ModelBrowserDefaultCandidateSearchProvider();
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        Function<VariableManager, Boolean> containerDescriptionIsSelectableProvider = variableManager -> {
            EClass referenceKind = this.resolveReferenceEClass(variableManager).orElse(null);
            return this.isContainerSelectable(variableManager, referenceKind);
        };
        var containerDescription = this.getModelBrowserDescription(CONTAINER_DESCRIPTION_ID, containerDescriptionIsSelectableProvider, this::getCreationScopeElements, MODEL_BROWSER_CONTAINER_PREFIX);

        Function<VariableManager, Boolean> referenceDescriptionIsSelectableProvider = variableManager -> {
            EClass targetType = this.resolveTargetType(variableManager).orElse(null);
            boolean isContainment = this.resolveIsContainment(variableManager);
            return this.isTypeSelectable(variableManager, targetType, isContainment);
        };
        var referenceDescription = this.getModelBrowserDescription(REFERENCE_DESCRIPTION_ID, referenceDescriptionIsSelectableProvider, this::getSearchScopeElements, MODEL_BROWSER_REFERENCE_PREFIX);

        return List.of(containerDescription, referenceDescription);
    }

    private TreeDescription getModelBrowserDescription(String descriptionId, Function<VariableManager, Boolean> isSelectableProvider,
            Function<VariableManager, List<?>> elementsProvider, String treeId) {

        return TreeDescription.newTreeDescription(descriptionId)
                .label(REPRESENTATION_NAME)
                .idProvider(variableManager -> variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class).orElse(treeId))
                .treeItemIdProvider(this::getTreeItemId)
                .kindProvider(this::getKind)
                .labelProvider(this::getLabel)
                .targetObjectIdProvider(variableManager -> variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class)
                        .map(IEditingContext::getId)
                        .orElse(null))
                .treeItemIconURLsProvider(this::getImageURL)
                .editableProvider(this::isEditable)
                .deletableProvider(this::isDeletable)
                .selectableProvider(isSelectableProvider)
                .elementsProvider(elementsProvider)
                .hasChildrenProvider(variableManager -> this.hasChildren(variableManager))
                .childrenProvider(variableManager -> this.getChildren(variableManager))
                // This predicate will NOT be used while creating the model browser, but we don't want to see the description of the
                // model browser in the list of representations that can be created. Thus, we will return false all the time.
                .canCreatePredicate(variableManager -> false)
                .deleteHandler(this::getDeleteHandler)
                .renameHandler(this::getRenameHandler)
                .treeItemObjectProvider(this::getTreeItemObject)
                .parentObjectProvider(this::getParentObject)
                .treeItemLabelProvider(this::getLabel)
                .iconURLsProvider(variableManager -> List.of())
                .build();
    }

    private boolean isContainerSelectable(VariableManager variableManager, EClass referenceKind) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        boolean isSelectable = false;
        if (self instanceof Resource) {
            isSelectable = true;
        } else if (self instanceof EObject selfEObject && referenceKind != null) {
            var optionalEditingDomain = variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class)
                    .map(IEMFEditingContext::getDomain);
            if (optionalEditingDomain.isPresent()) {
                Collection<?> newChildDescriptors = optionalEditingDomain.get().getNewChildDescriptors(selfEObject, null);

                isSelectable = newChildDescriptors.stream()
                        .filter(CommandParameter.class::isInstance)
                        .map(CommandParameter.class::cast)
                        .anyMatch(commandParameter -> referenceKind.isInstance(commandParameter.getValue()));
            }
        }
        return isSelectable;
    }

    private boolean isTypeSelectable(VariableManager variableManager, EClass targetType, boolean isContainment) {
        var optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);
        if (optionalSelf.isPresent() && targetType != null) {
            return targetType.isInstance(optionalSelf.get())
                    && this.resolveOwnerEObject(variableManager).map(eObject -> !(isContainment && EcoreUtil.isAncestor(optionalSelf.get(), eObject))).orElse(true);
        } else {
            return false;
        }
    }

    private Optional<EObject> resolveOwnerEObject(VariableManager variableManager) {
        var optionalTreeId = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class);
        if (optionalTreeId.isPresent() && optionalTreeId.get().startsWith(PREFIX) && optionalEditingContext.isPresent()) {
            Map<String, List<String>> parameters = this.urlParser.getParameterValues(optionalTreeId.get());
            String ownerId = parameters.get("ownerId").get(0);

            return this.objectService.getObject(optionalEditingContext.get(), ownerId)
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast);
        } else {
            return Optional.empty();
        }
    }

    private Optional<EClass> resolveReferenceEClass(VariableManager variableManager) {
        var optionalTreeId = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class);
        if (optionalTreeId.isPresent() && optionalTreeId.get().startsWith(PREFIX) && optionalEditingContext.isPresent()) {
            Registry ePackageRegistry = optionalEditingContext.get().getDomain().getResourceSet().getPackageRegistry();
            Map<String, List<String>> parameters = this.urlParser.getParameterValues(optionalTreeId.get());
            String refContainer = parameters.get("ownerKind").get(0);

            String ePackageName = this.emfKindService.getEPackageName(refContainer);
            String eClassName = this.emfKindService.getEClassName(refContainer);

            return this.findEPackage(ePackageRegistry, ePackageName)
                    .map(ePackage -> ePackage.getEClassifier(eClassName))
                    .filter(EClass.class::isInstance)
                    .map(EClass.class::cast);
        } else {
            return Optional.empty();
        }
    }

    private Optional<EClass> resolveTargetType(VariableManager variableManager) {
        var optionalTreeId = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class);
        if (optionalTreeId.isPresent() && optionalTreeId.get().startsWith(PREFIX) && optionalEditingContext.isPresent()) {
            Registry ePackageRegistry = optionalEditingContext.get().getDomain().getResourceSet().getPackageRegistry();
            Map<String, List<String>> parameters = this.urlParser.getParameterValues(optionalTreeId.get());
            String kind = parameters.get("targetType").get(0);

            String ePackageName = this.emfKindService.getEPackageName(kind);
            String eClassName = this.emfKindService.getEClassName(kind);

            return this.findEPackage(ePackageRegistry, ePackageName)
                    .map(ePackage -> ePackage.getEClassifier(eClassName))
                    .filter(EClass.class::isInstance)
                    .map(EClass.class::cast);
        } else {
            return Optional.empty();
        }
    }

    private boolean resolveIsContainment(VariableManager variableManager) {
        var optionalTreeId = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class);
        if (optionalTreeId.isPresent() && optionalTreeId.get().startsWith(PREFIX)) {
            Map<String, List<String>> parameters = this.urlParser.getParameterValues(optionalTreeId.get());
            String isContainment = parameters.get("isContainment").get(0);
            return Boolean.parseBoolean(isContainment);
        } else {
            return false;
        }
    }

    public Optional<EPackage> findEPackage(Registry ePackageRegistry, String ePackageName) {
        return ePackageRegistry.values().stream().map(object -> {
            if (object instanceof EPackage.Descriptor) {
                return ((EPackage.Descriptor) object).getEPackage();
            }
            return object;
        }).filter(EPackage.class::isInstance).map(EPackage.class::cast).filter(ePackage -> ePackage.getName().equals(ePackageName)).findFirst();
    }

    private String getTreeItemId(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        String id = null;
        if (self instanceof Resource resource) {
            id = resource.getURI().path().substring(1);
        } else if (self instanceof EObject) {
            id = this.identityService.getId(self);
        }
        return id;
    }

    private String getKind(VariableManager variableManager) {
        String kind;
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        if (self instanceof Resource) {
            kind = DOCUMENT_KIND;
        } else {
            kind = this.identityService.getKind(self);
        }
        return kind;
    }

    private StyledString getLabel(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        String label = "";
        if (self instanceof Resource resource) {
            label = this.getResourceLabel(resource);
        } else if (self instanceof EObject) {
            StyledString styledString = this.labelService.getStyledLabel(self);
            if (!styledString.toString().isBlank()) {
                return styledString;
            } else {
                var kind = this.identityService.getKind(self);
                label = this.urlParser.getParameterValues(kind).get(SemanticKindConstants.ENTITY_ARGUMENT).get(0);
            }
        }

        return StyledString.of(label);
    }

    private String getResourceLabel(Resource resource) {
        return resource.eAdapters().stream()
                .filter(ResourceMetadataAdapter.class::isInstance)
                .map(ResourceMetadataAdapter.class::cast)
                .findFirst()
                .map(ResourceMetadataAdapter::getName)
                .orElse(resource.getURI().lastSegment());
    }

    private boolean isEditable(VariableManager variableManager) {
        return false;

    }

    private boolean isDeletable(VariableManager variableManager) {
        return false;
    }

    private List<String> getImageURL(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        List<String> imageURL = List.of(CoreImageConstants.DEFAULT_SVG);
        if (self instanceof EObject) {
            imageURL = this.labelService.getImagePaths(self);
        } else if (self instanceof Resource) {
            imageURL = List.of("/reference-widget-images/Resource.svg");
        }
        return imageURL;
    }

    private List<? extends Object> getSearchScopeElements(VariableManager variableManager) {
        var optionalTreeId = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class);
        if (optionalTreeId.isPresent() && optionalTreeId.get().startsWith(PREFIX) && optionalEditingContext.isPresent()) {
            Map<String, List<String>> parameters = this.urlParser.getParameterValues(optionalTreeId.get());
            String descriptionId = parameters.get("descriptionId").get(0);
            String ownerId = parameters.get("ownerId").get(0);
            var semanticOwner = this.objectService.getObject(optionalEditingContext.get(), ownerId).get();

            return this.candidateProviders.stream()
                    .filter(provider -> provider.canHandle(descriptionId))
                    .findFirst()
                    .orElse(this.defaultCandidateProvider)
                    .getRootElementsForReference(semanticOwner, descriptionId, optionalEditingContext.get());
        }
        return Collections.emptyList();
    }

    private List<? extends Object> getCreationScopeElements(VariableManager variableManager) {
        var optionalResourceSet = variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class)
                .map(IEMFEditingContext::getDomain)
                .map(EditingDomain::getResourceSet);

        if (optionalResourceSet.isPresent()) {
            var resourceSet = optionalResourceSet.get();
            return resourceSet.getResources().stream()
                    .filter(resource -> resource.getURI() != null && IEMFEditingContext.RESOURCE_SCHEME.equals(resource.getURI().scheme()))
                    .sorted(Comparator.nullsLast(Comparator.comparing(this::getResourceLabel, String.CASE_INSENSITIVE_ORDER)))
                    .toList();
        }
        return List.of();
    }

    private boolean hasChildren(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        boolean hasChildren = false;
        if (self instanceof Resource resource) {
            hasChildren = !resource.getContents().isEmpty();
        } else if (self instanceof EObject eObject) {
            hasChildren = !eObject.eContents().isEmpty();
        }
        return hasChildren;
    }

    private List<Object> getChildren(VariableManager variableManager) {
        List<Object> result = new ArrayList<>();

        List<String> expandedIds = new ArrayList<>();
        Object objects = variableManager.getVariables().get(TreeRenderer.EXPANDED);
        if (objects instanceof List<?> list) {
            expandedIds = list.stream().filter(String.class::isInstance).map(String.class::cast).toList();
        }

        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);

        if (optionalEditingContext.isPresent()) {
            String id = this.getTreeItemId(variableManager);
            if (expandedIds.contains(id)) {
                Object self = variableManager.getVariables().get(VariableManager.SELF);

                if (self instanceof Resource resource) {
                    result.addAll(resource.getContents());
                } else if (self instanceof EObject) {
                    List<Object> contents = this.objectService.getContents(self);
                    result.addAll(contents);
                }
            }
        }
        return result;
    }

    private IStatus getDeleteHandler(VariableManager variableManager) {
        return new Failure("");
    }

    private IStatus getRenameHandler(VariableManager variableManager, String newLabel) {
        return new Failure("");
    }

    private Object getTreeItemObject(VariableManager variableManager) {
        Object result = null;
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        var optionalId = variableManager.get(TreeDescription.ID, String.class);
        if (optionalId.isPresent() && optionalEditingContext.isPresent()) {
            var optionalObject = this.objectService.getObject(optionalEditingContext.get(), optionalId.get());
            if (optionalObject.isPresent()) {
                result = optionalObject.get();
            } else {
                var optionalEditingDomain = Optional.of(optionalEditingContext.get())
                        .filter(IEMFEditingContext.class::isInstance)
                        .map(IEMFEditingContext.class::cast)
                        .map(IEMFEditingContext::getDomain);

                if (optionalEditingDomain.isPresent()) {
                    var editingDomain = optionalEditingDomain.get();
                    ResourceSet resourceSet = editingDomain.getResourceSet();
                    URI uri = new JSONResourceFactory().createResourceURI(optionalId.get());

                    result = resourceSet.getResources().stream()
                            .filter(resource -> resource.getURI().equals(uri))
                            .findFirst()
                            .orElse(null);
                }
            }
        }
        return result;
    }

    private Object getParentObject(VariableManager variableManager) {
        Object result = null;
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        if (self instanceof EObject eObject) {
            Object semanticContainer = eObject.eContainer();
            if (semanticContainer == null) {
                semanticContainer = eObject.eResource();
            }
            result = semanticContainer;
        }
        return result;
    }
}
