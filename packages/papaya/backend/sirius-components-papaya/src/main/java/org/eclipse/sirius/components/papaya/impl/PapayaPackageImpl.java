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
package org.eclipse.sirius.components.papaya.impl;

import static org.eclipse.sirius.components.papaya.PapayaPackage.CLASS;
import static org.eclipse.sirius.components.papaya.PapayaPackage.CONTAINER;

import java.time.Instant;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.components.papaya.AnnotableElement;
import org.eclipse.sirius.components.papaya.Annotation;
import org.eclipse.sirius.components.papaya.AnnotationField;
import org.eclipse.sirius.components.papaya.ApplicationConcern;
import org.eclipse.sirius.components.papaya.Attribute;
import org.eclipse.sirius.components.papaya.Channel;
import org.eclipse.sirius.components.papaya.Classifier;
import org.eclipse.sirius.components.papaya.Command;
import org.eclipse.sirius.components.papaya.Component;
import org.eclipse.sirius.components.papaya.ComponentExchange;
import org.eclipse.sirius.components.papaya.ComponentPort;
import org.eclipse.sirius.components.papaya.Constructor;
import org.eclipse.sirius.components.papaya.ContainingLink;
import org.eclipse.sirius.components.papaya.Contribution;
import org.eclipse.sirius.components.papaya.Controller;
import org.eclipse.sirius.components.papaya.DataType;
import org.eclipse.sirius.components.papaya.Domain;
import org.eclipse.sirius.components.papaya.EnumLiteral;
import org.eclipse.sirius.components.papaya.Event;
import org.eclipse.sirius.components.papaya.Folder;
import org.eclipse.sirius.components.papaya.FolderElement;
import org.eclipse.sirius.components.papaya.GenericType;
import org.eclipse.sirius.components.papaya.Interface;
import org.eclipse.sirius.components.papaya.InterfaceImplementation;
import org.eclipse.sirius.components.papaya.Iteration;
import org.eclipse.sirius.components.papaya.Link;
import org.eclipse.sirius.components.papaya.Message;
import org.eclipse.sirius.components.papaya.MessageEmitter;
import org.eclipse.sirius.components.papaya.MessageListener;
import org.eclipse.sirius.components.papaya.ModelElement;
import org.eclipse.sirius.components.papaya.NamedElement;
import org.eclipse.sirius.components.papaya.Operation;
import org.eclipse.sirius.components.papaya.OperationalActivity;
import org.eclipse.sirius.components.papaya.OperationalActor;
import org.eclipse.sirius.components.papaya.OperationalCapability;
import org.eclipse.sirius.components.papaya.OperationalEntity;
import org.eclipse.sirius.components.papaya.OperationalInteraction;
import org.eclipse.sirius.components.papaya.OperationalProcess;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Parameter;
import org.eclipse.sirius.components.papaya.Priority;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.components.papaya.ProvidedService;
import org.eclipse.sirius.components.papaya.Publication;
import org.eclipse.sirius.components.papaya.Query;
import org.eclipse.sirius.components.papaya.RecordComponent;
import org.eclipse.sirius.components.papaya.ReferencingLink;
import org.eclipse.sirius.components.papaya.Repository;
import org.eclipse.sirius.components.papaya.RequiredService;
import org.eclipse.sirius.components.papaya.Service;
import org.eclipse.sirius.components.papaya.Subscription;
import org.eclipse.sirius.components.papaya.Tag;
import org.eclipse.sirius.components.papaya.Task;
import org.eclipse.sirius.components.papaya.Type;
import org.eclipse.sirius.components.papaya.TypeParameter;
import org.eclipse.sirius.components.papaya.TypedElement;
import org.eclipse.sirius.components.papaya.Visibility;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class PapayaPackageImpl extends EPackageImpl implements PapayaPackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass modelElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass tagEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass linkEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass referencingLinkEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass containingLinkEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass namedElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass containerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass folderEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass folderElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass projectEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass operationalCapabilityEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass operationalEntityEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass operationalActorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass operationalProcessEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass operationalActivityEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass operationalInteractionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass iterationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass taskEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass contributionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass componentEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass componentPortEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass componentExchangeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass providedServiceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass requiredServiceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass annotableElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass packageEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass typeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass typedElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass genericTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass annotationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass annotationFieldEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass classifierEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass typeParameterEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass interfaceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass interfaceImplementationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass classEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass constructorEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass attributeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass operationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass parameterEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass recordEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass recordComponentEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass dataTypeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass enumEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass enumLiteralEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass applicationConcernEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass controllerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass domainEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass serviceEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass messageListenerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass messageEmitterEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass messageEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass eventEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass commandEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass queryEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass repositoryEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass channelEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass subscriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass publicationEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum priorityEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EEnum visibilityEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EDataType instantEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
     * EPackage.Registry} by the package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
     * performs initialization of the package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private PapayaPackageImpl() {
        super(eNS_URI, PapayaFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     *
     * <p>
     * This method is used to initialize {@link PapayaPackage#eINSTANCE} when that field is accessed. Clients should not
     * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static PapayaPackage init() {
        if (isInited)
            return (PapayaPackage) EPackage.Registry.INSTANCE.getEPackage(PapayaPackage.eNS_URI);

        // Obtain or create and register package
        Object registeredPapayaPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        PapayaPackageImpl thePapayaPackage = registeredPapayaPackage instanceof PapayaPackageImpl ? (PapayaPackageImpl) registeredPapayaPackage : new PapayaPackageImpl();

        isInited = true;

        // Create package meta-data objects
        thePapayaPackage.createPackageContents();

        // Initialize created meta-data
        thePapayaPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        thePapayaPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(PapayaPackage.eNS_URI, thePapayaPackage);
        return thePapayaPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getModelElement() {
        return this.modelElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getModelElement_Tags() {
        return (EReference) this.modelElementEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getModelElement_Links() {
        return (EReference) this.modelElementEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTag() {
        return this.tagEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTag_Key() {
        return (EAttribute) this.tagEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTag_Value() {
        return (EAttribute) this.tagEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getLink() {
        return this.linkEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLink_Kind() {
        return (EAttribute) this.linkEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getLink_Source() {
        return (EReference) this.linkEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getReferencingLink() {
        return this.referencingLinkEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getReferencingLink_Target() {
        return (EReference) this.referencingLinkEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getContainingLink() {
        return this.containingLinkEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getContainingLink_Target() {
        return (EReference) this.containingLinkEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getNamedElement() {
        return this.namedElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNamedElement_Name() {
        return (EAttribute) this.namedElementEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getNamedElement_Description() {
        return (EAttribute) this.namedElementEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getContainer() {
        return this.containerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getContainer_Folders() {
        return (EReference) this.containerEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getContainer_Elements() {
        return (EReference) this.containerEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getFolder() {
        return this.folderEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getFolderElement() {
        return this.folderElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getProject() {
        return this.projectEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getProject_Homepage() {
        return (EAttribute) this.projectEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getOperationalCapability() {
        return this.operationalCapabilityEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getOperationalCapability_Constraints() {
        return (EAttribute) this.operationalCapabilityEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getOperationalEntity() {
        return this.operationalEntityEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getOperationalActor() {
        return this.operationalActorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getOperationalActor_Type() {
        return (EAttribute) this.operationalActorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getOperationalProcess() {
        return this.operationalProcessEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getOperationalActivity() {
        return this.operationalActivityEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getOperationalActivity_Precondition() {
        return (EAttribute) this.operationalActivityEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getOperationalActivity_Postcondition() {
        return (EAttribute) this.operationalActivityEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getOperationalInteraction() {
        return this.operationalInteractionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getOperationalInteraction_Type() {
        return (EAttribute) this.operationalInteractionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getOperationalInteraction_Frequency() {
        return (EAttribute) this.operationalInteractionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getOperationalInteraction_Protocol() {
        return (EAttribute) this.operationalInteractionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getOperationalInteraction_Latency() {
        return (EAttribute) this.operationalInteractionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getIteration() {
        return this.iterationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getIteration_StartDate() {
        return (EAttribute) this.iterationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getIteration_EndDate() {
        return (EAttribute) this.iterationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getIteration_Tasks() {
        return (EReference) this.iterationEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getIteration_Contributions() {
        return (EReference) this.iterationEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTask() {
        return this.taskEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTask_Priority() {
        return (EAttribute) this.taskEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTask_Cost() {
        return (EAttribute) this.taskEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTask_Targets() {
        return (EReference) this.taskEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTask_Tasks() {
        return (EReference) this.taskEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTask_StartDate() {
        return (EAttribute) this.taskEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTask_EndDate() {
        return (EAttribute) this.taskEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTask_Done() {
        return (EAttribute) this.taskEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTask_Dependencies() {
        return (EReference) this.taskEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getContribution() {
        return this.contributionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getContribution_RelatedTasks() {
        return (EReference) this.contributionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getContribution_Targets() {
        return (EReference) this.contributionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getContribution_Done() {
        return (EAttribute) this.contributionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getComponent() {
        return this.componentEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getComponent_Dependencies() {
        return (EReference) this.componentEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getComponent_AllDependencies() {
        return (EReference) this.componentEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getComponent_UsedAsDependencyBy() {
        return (EReference) this.componentEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getComponent_Components() {
        return (EReference) this.componentEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getComponent_AllComponents() {
        return (EReference) this.componentEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getComponent_Packages() {
        return (EReference) this.componentEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getComponent_Ports() {
        return (EReference) this.componentEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getComponent_ProvidedServices() {
        return (EReference) this.componentEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getComponent_RequiredServices() {
        return (EReference) this.componentEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getComponentPort() {
        return this.componentPortEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getComponentPort_Protocol() {
        return (EAttribute) this.componentPortEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getComponentExchange() {
        return this.componentExchangeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getComponentExchange_Ports() {
        return (EReference) this.componentExchangeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getProvidedService() {
        return this.providedServiceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getProvidedService_Contracts() {
        return (EReference) this.providedServiceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRequiredService() {
        return this.requiredServiceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRequiredService_Contracts() {
        return (EReference) this.requiredServiceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getAnnotableElement() {
        return this.annotableElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAnnotableElement_Annotations() {
        return (EReference) this.annotableElementEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getPackage() {
        return this.packageEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getPackage_QualifiedName() {
        return (EAttribute) this.packageEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPackage_Types() {
        return (EReference) this.packageEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPackage_Packages() {
        return (EReference) this.packageEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getType() {
        return this.typeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getType_QualifiedName() {
        return (EAttribute) this.typeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getType_Visibility() {
        return (EAttribute) this.typeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getType_Types() {
        return (EReference) this.typeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTypedElement() {
        return this.typedElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTypedElement_Type() {
        return (EReference) this.typedElementEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getGenericType() {
        return this.genericTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getGenericType_RawType() {
        return (EReference) this.genericTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getGenericType_TypeArguments() {
        return (EReference) this.genericTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getAnnotation() {
        return this.annotationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getAnnotation_Fields() {
        return (EReference) this.annotationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getAnnotationField() {
        return this.annotationFieldEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getClassifier() {
        return this.classifierEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getClassifier_TypeParameters() {
        return (EReference) this.classifierEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTypeParameter() {
        return this.typeParameterEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getInterface() {
        return this.interfaceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInterface_Extends() {
        return (EReference) this.interfaceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInterface_ExtendedBy() {
        return (EReference) this.interfaceEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInterface_Operations() {
        return (EReference) this.interfaceEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInterface_ImplementedBy() {
        return (EReference) this.interfaceEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInterface_Subtypes() {
        return (EReference) this.interfaceEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInterface_AllSubtypes() {
        return (EReference) this.interfaceEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getInterfaceImplementation() {
        return this.interfaceImplementationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getInterfaceImplementation_Implements() {
        return (EReference) this.interfaceImplementationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getClass_() {
        return this.classEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getClass_Abstract() {
        return (EAttribute) this.classEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getClass_Final() {
        return (EAttribute) this.classEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getClass_Static() {
        return (EAttribute) this.classEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getClass_Extends() {
        return (EReference) this.classEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getClass_ExtendedBy() {
        return (EReference) this.classEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getClass_AllExtendedBy() {
        return (EReference) this.classEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getClass_Constructors() {
        return (EReference) this.classEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getClass_Attributes() {
        return (EReference) this.classEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getClass_Operations() {
        return (EReference) this.classEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getConstructor() {
        return this.constructorEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getConstructor_Parameters() {
        return (EReference) this.constructorEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getConstructor_Visibility() {
        return (EAttribute) this.constructorEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getAttribute() {
        return this.attributeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getAttribute_Visibility() {
        return (EAttribute) this.attributeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getAttribute_Final() {
        return (EAttribute) this.attributeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getAttribute_Static() {
        return (EAttribute) this.attributeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getOperation() {
        return this.operationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getOperation_Visibility() {
        return (EAttribute) this.operationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getOperation_Abstract() {
        return (EAttribute) this.operationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getOperation_Final() {
        return (EAttribute) this.operationEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getOperation_Static() {
        return (EAttribute) this.operationEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getOperation_Parameters() {
        return (EReference) this.operationEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getParameter() {
        return this.parameterEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRecord() {
        return this.recordEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRecord_Components() {
        return (EReference) this.recordEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getRecord_Operations() {
        return (EReference) this.recordEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRecordComponent() {
        return this.recordComponentEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDataType() {
        return this.dataTypeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEnum() {
        return this.enumEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEnum_Literals() {
        return (EReference) this.enumEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEnumLiteral() {
        return this.enumLiteralEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getApplicationConcern() {
        return this.applicationConcernEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getApplicationConcern_Controllers() {
        return (EReference) this.applicationConcernEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getApplicationConcern_Services() {
        return (EReference) this.applicationConcernEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getApplicationConcern_Events() {
        return (EReference) this.applicationConcernEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getApplicationConcern_Commands() {
        return (EReference) this.applicationConcernEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getApplicationConcern_Queries() {
        return (EReference) this.applicationConcernEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getApplicationConcern_Domains() {
        return (EReference) this.applicationConcernEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getController() {
        return this.controllerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getController_Calls() {
        return (EReference) this.controllerEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDomain() {
        return this.domainEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDomain_Services() {
        return (EReference) this.domainEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDomain_Repositories() {
        return (EReference) this.domainEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDomain_Events() {
        return (EReference) this.domainEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDomain_Commands() {
        return (EReference) this.domainEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDomain_Queries() {
        return (EReference) this.domainEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDomain_Dependencies() {
        return (EReference) this.domainEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getService() {
        return this.serviceEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getService_Calls() {
        return (EReference) this.serviceEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getMessageListener() {
        return this.messageListenerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMessageListener_Subscriptions() {
        return (EReference) this.messageListenerEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getMessageEmitter() {
        return this.messageEmitterEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMessageEmitter_Publications() {
        return (EReference) this.messageEmitterEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getMessage() {
        return this.messageEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMessage_EmittedBy() {
        return (EReference) this.messageEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getMessage_ListenedBy() {
        return (EReference) this.messageEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEvent() {
        return this.eventEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getEvent_CausedBy() {
        return (EReference) this.eventEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getCommand() {
        return this.commandEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getQuery() {
        return this.queryEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getRepository() {
        return this.repositoryEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getChannel() {
        return this.channelEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getSubscription() {
        return this.subscriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSubscription_Channel() {
        return (EReference) this.subscriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getSubscription_Message() {
        return (EReference) this.subscriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getPublication() {
        return this.publicationEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPublication_Channel() {
        return (EReference) this.publicationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getPublication_Message() {
        return (EReference) this.publicationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getPriority() {
        return this.priorityEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EEnum getVisibility() {
        return this.visibilityEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EDataType getInstant() {
        return this.instantEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public PapayaFactory getPapayaFactory() {
        return (PapayaFactory) this.getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but
     * its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void createPackageContents() {
        if (this.isCreated)
            return;
        this.isCreated = true;

        // Create classes and their features
        this.modelElementEClass = this.createEClass(MODEL_ELEMENT);
        this.createEReference(this.modelElementEClass, MODEL_ELEMENT__TAGS);
        this.createEReference(this.modelElementEClass, MODEL_ELEMENT__LINKS);

        this.tagEClass = this.createEClass(TAG);
        this.createEAttribute(this.tagEClass, TAG__KEY);
        this.createEAttribute(this.tagEClass, TAG__VALUE);

        this.linkEClass = this.createEClass(LINK);
        this.createEAttribute(this.linkEClass, LINK__KIND);
        this.createEReference(this.linkEClass, LINK__SOURCE);

        this.referencingLinkEClass = this.createEClass(REFERENCING_LINK);
        this.createEReference(this.referencingLinkEClass, REFERENCING_LINK__TARGET);

        this.containingLinkEClass = this.createEClass(CONTAINING_LINK);
        this.createEReference(this.containingLinkEClass, CONTAINING_LINK__TARGET);

        this.namedElementEClass = this.createEClass(NAMED_ELEMENT);
        this.createEAttribute(this.namedElementEClass, NAMED_ELEMENT__NAME);
        this.createEAttribute(this.namedElementEClass, NAMED_ELEMENT__DESCRIPTION);

        this.containerEClass = this.createEClass(CONTAINER);
        this.createEReference(this.containerEClass, CONTAINER__FOLDERS);
        this.createEReference(this.containerEClass, CONTAINER__ELEMENTS);

        this.folderEClass = this.createEClass(FOLDER);

        this.folderElementEClass = this.createEClass(FOLDER_ELEMENT);

        this.projectEClass = this.createEClass(PROJECT);
        this.createEAttribute(this.projectEClass, PROJECT__HOMEPAGE);

        this.operationalCapabilityEClass = this.createEClass(OPERATIONAL_CAPABILITY);
        this.createEAttribute(this.operationalCapabilityEClass, OPERATIONAL_CAPABILITY__CONSTRAINTS);

        this.operationalEntityEClass = this.createEClass(OPERATIONAL_ENTITY);

        this.operationalActorEClass = this.createEClass(OPERATIONAL_ACTOR);
        this.createEAttribute(this.operationalActorEClass, OPERATIONAL_ACTOR__TYPE);

        this.operationalProcessEClass = this.createEClass(OPERATIONAL_PROCESS);

        this.operationalActivityEClass = this.createEClass(OPERATIONAL_ACTIVITY);
        this.createEAttribute(this.operationalActivityEClass, OPERATIONAL_ACTIVITY__PRECONDITION);
        this.createEAttribute(this.operationalActivityEClass, OPERATIONAL_ACTIVITY__POSTCONDITION);

        this.operationalInteractionEClass = this.createEClass(OPERATIONAL_INTERACTION);
        this.createEAttribute(this.operationalInteractionEClass, OPERATIONAL_INTERACTION__TYPE);
        this.createEAttribute(this.operationalInteractionEClass, OPERATIONAL_INTERACTION__FREQUENCY);
        this.createEAttribute(this.operationalInteractionEClass, OPERATIONAL_INTERACTION__PROTOCOL);
        this.createEAttribute(this.operationalInteractionEClass, OPERATIONAL_INTERACTION__LATENCY);

        this.iterationEClass = this.createEClass(ITERATION);
        this.createEAttribute(this.iterationEClass, ITERATION__START_DATE);
        this.createEAttribute(this.iterationEClass, ITERATION__END_DATE);
        this.createEReference(this.iterationEClass, ITERATION__TASKS);
        this.createEReference(this.iterationEClass, ITERATION__CONTRIBUTIONS);

        this.taskEClass = this.createEClass(TASK);
        this.createEAttribute(this.taskEClass, TASK__PRIORITY);
        this.createEAttribute(this.taskEClass, TASK__COST);
        this.createEReference(this.taskEClass, TASK__TARGETS);
        this.createEReference(this.taskEClass, TASK__TASKS);
        this.createEAttribute(this.taskEClass, TASK__START_DATE);
        this.createEAttribute(this.taskEClass, TASK__END_DATE);
        this.createEAttribute(this.taskEClass, TASK__DONE);
        this.createEReference(this.taskEClass, TASK__DEPENDENCIES);

        this.contributionEClass = this.createEClass(CONTRIBUTION);
        this.createEReference(this.contributionEClass, CONTRIBUTION__RELATED_TASKS);
        this.createEReference(this.contributionEClass, CONTRIBUTION__TARGETS);
        this.createEAttribute(this.contributionEClass, CONTRIBUTION__DONE);

        this.componentEClass = this.createEClass(COMPONENT);
        this.createEReference(this.componentEClass, COMPONENT__DEPENDENCIES);
        this.createEReference(this.componentEClass, COMPONENT__ALL_DEPENDENCIES);
        this.createEReference(this.componentEClass, COMPONENT__USED_AS_DEPENDENCY_BY);
        this.createEReference(this.componentEClass, COMPONENT__COMPONENTS);
        this.createEReference(this.componentEClass, COMPONENT__ALL_COMPONENTS);
        this.createEReference(this.componentEClass, COMPONENT__PACKAGES);
        this.createEReference(this.componentEClass, COMPONENT__PORTS);
        this.createEReference(this.componentEClass, COMPONENT__PROVIDED_SERVICES);
        this.createEReference(this.componentEClass, COMPONENT__REQUIRED_SERVICES);

        this.componentPortEClass = this.createEClass(COMPONENT_PORT);
        this.createEAttribute(this.componentPortEClass, COMPONENT_PORT__PROTOCOL);

        this.componentExchangeEClass = this.createEClass(COMPONENT_EXCHANGE);
        this.createEReference(this.componentExchangeEClass, COMPONENT_EXCHANGE__PORTS);

        this.providedServiceEClass = this.createEClass(PROVIDED_SERVICE);
        this.createEReference(this.providedServiceEClass, PROVIDED_SERVICE__CONTRACTS);

        this.requiredServiceEClass = this.createEClass(REQUIRED_SERVICE);
        this.createEReference(this.requiredServiceEClass, REQUIRED_SERVICE__CONTRACTS);

        this.annotableElementEClass = this.createEClass(ANNOTABLE_ELEMENT);
        this.createEReference(this.annotableElementEClass, ANNOTABLE_ELEMENT__ANNOTATIONS);

        this.packageEClass = this.createEClass(PACKAGE);
        this.createEAttribute(this.packageEClass, PACKAGE__QUALIFIED_NAME);
        this.createEReference(this.packageEClass, PACKAGE__TYPES);
        this.createEReference(this.packageEClass, PACKAGE__PACKAGES);

        this.typeEClass = this.createEClass(TYPE);
        this.createEAttribute(this.typeEClass, TYPE__QUALIFIED_NAME);
        this.createEAttribute(this.typeEClass, TYPE__VISIBILITY);
        this.createEReference(this.typeEClass, TYPE__TYPES);

        this.typedElementEClass = this.createEClass(TYPED_ELEMENT);
        this.createEReference(this.typedElementEClass, TYPED_ELEMENT__TYPE);

        this.genericTypeEClass = this.createEClass(GENERIC_TYPE);
        this.createEReference(this.genericTypeEClass, GENERIC_TYPE__RAW_TYPE);
        this.createEReference(this.genericTypeEClass, GENERIC_TYPE__TYPE_ARGUMENTS);

        this.annotationEClass = this.createEClass(ANNOTATION);
        this.createEReference(this.annotationEClass, ANNOTATION__FIELDS);

        this.annotationFieldEClass = this.createEClass(ANNOTATION_FIELD);

        this.classifierEClass = this.createEClass(CLASSIFIER);
        this.createEReference(this.classifierEClass, CLASSIFIER__TYPE_PARAMETERS);

        this.typeParameterEClass = this.createEClass(TYPE_PARAMETER);

        this.interfaceEClass = this.createEClass(INTERFACE);
        this.createEReference(this.interfaceEClass, INTERFACE__EXTENDS);
        this.createEReference(this.interfaceEClass, INTERFACE__EXTENDED_BY);
        this.createEReference(this.interfaceEClass, INTERFACE__IMPLEMENTED_BY);
        this.createEReference(this.interfaceEClass, INTERFACE__SUBTYPES);
        this.createEReference(this.interfaceEClass, INTERFACE__ALL_SUBTYPES);
        this.createEReference(this.interfaceEClass, INTERFACE__OPERATIONS);

        this.interfaceImplementationEClass = this.createEClass(INTERFACE_IMPLEMENTATION);
        this.createEReference(this.interfaceImplementationEClass, INTERFACE_IMPLEMENTATION__IMPLEMENTS);

        this.classEClass = this.createEClass(CLASS);
        this.createEAttribute(this.classEClass, CLASS__ABSTRACT);
        this.createEAttribute(this.classEClass, CLASS__FINAL);
        this.createEAttribute(this.classEClass, CLASS__STATIC);
        this.createEReference(this.classEClass, CLASS__EXTENDS);
        this.createEReference(this.classEClass, CLASS__EXTENDED_BY);
        this.createEReference(this.classEClass, CLASS__ALL_EXTENDED_BY);
        this.createEReference(this.classEClass, CLASS__CONSTRUCTORS);
        this.createEReference(this.classEClass, CLASS__ATTRIBUTES);
        this.createEReference(this.classEClass, CLASS__OPERATIONS);

        this.constructorEClass = this.createEClass(CONSTRUCTOR);
        this.createEReference(this.constructorEClass, CONSTRUCTOR__PARAMETERS);
        this.createEAttribute(this.constructorEClass, CONSTRUCTOR__VISIBILITY);

        this.attributeEClass = this.createEClass(ATTRIBUTE);
        this.createEAttribute(this.attributeEClass, ATTRIBUTE__VISIBILITY);
        this.createEAttribute(this.attributeEClass, ATTRIBUTE__FINAL);
        this.createEAttribute(this.attributeEClass, ATTRIBUTE__STATIC);

        this.operationEClass = this.createEClass(OPERATION);
        this.createEAttribute(this.operationEClass, OPERATION__VISIBILITY);
        this.createEAttribute(this.operationEClass, OPERATION__ABSTRACT);
        this.createEAttribute(this.operationEClass, OPERATION__FINAL);
        this.createEAttribute(this.operationEClass, OPERATION__STATIC);
        this.createEReference(this.operationEClass, OPERATION__PARAMETERS);

        this.parameterEClass = this.createEClass(PARAMETER);

        this.recordEClass = this.createEClass(RECORD);
        this.createEReference(this.recordEClass, RECORD__COMPONENTS);
        this.createEReference(this.recordEClass, RECORD__OPERATIONS);

        this.recordComponentEClass = this.createEClass(RECORD_COMPONENT);

        this.dataTypeEClass = this.createEClass(DATA_TYPE);

        this.enumEClass = this.createEClass(ENUM);
        this.createEReference(this.enumEClass, ENUM__LITERALS);

        this.enumLiteralEClass = this.createEClass(ENUM_LITERAL);

        this.applicationConcernEClass = this.createEClass(APPLICATION_CONCERN);
        this.createEReference(this.applicationConcernEClass, APPLICATION_CONCERN__CONTROLLERS);
        this.createEReference(this.applicationConcernEClass, APPLICATION_CONCERN__SERVICES);
        this.createEReference(this.applicationConcernEClass, APPLICATION_CONCERN__EVENTS);
        this.createEReference(this.applicationConcernEClass, APPLICATION_CONCERN__COMMANDS);
        this.createEReference(this.applicationConcernEClass, APPLICATION_CONCERN__QUERIES);
        this.createEReference(this.applicationConcernEClass, APPLICATION_CONCERN__DOMAINS);

        this.controllerEClass = this.createEClass(CONTROLLER);
        this.createEReference(this.controllerEClass, CONTROLLER__CALLS);

        this.domainEClass = this.createEClass(DOMAIN);
        this.createEReference(this.domainEClass, DOMAIN__SERVICES);
        this.createEReference(this.domainEClass, DOMAIN__REPOSITORIES);
        this.createEReference(this.domainEClass, DOMAIN__EVENTS);
        this.createEReference(this.domainEClass, DOMAIN__COMMANDS);
        this.createEReference(this.domainEClass, DOMAIN__QUERIES);
        this.createEReference(this.domainEClass, DOMAIN__DEPENDENCIES);

        this.serviceEClass = this.createEClass(SERVICE);
        this.createEReference(this.serviceEClass, SERVICE__CALLS);

        this.messageListenerEClass = this.createEClass(MESSAGE_LISTENER);
        this.createEReference(this.messageListenerEClass, MESSAGE_LISTENER__SUBSCRIPTIONS);

        this.messageEmitterEClass = this.createEClass(MESSAGE_EMITTER);
        this.createEReference(this.messageEmitterEClass, MESSAGE_EMITTER__PUBLICATIONS);

        this.messageEClass = this.createEClass(MESSAGE);
        this.createEReference(this.messageEClass, MESSAGE__EMITTED_BY);
        this.createEReference(this.messageEClass, MESSAGE__LISTENED_BY);

        this.eventEClass = this.createEClass(EVENT);
        this.createEReference(this.eventEClass, EVENT__CAUSED_BY);

        this.commandEClass = this.createEClass(COMMAND);

        this.queryEClass = this.createEClass(QUERY);

        this.repositoryEClass = this.createEClass(REPOSITORY);

        this.channelEClass = this.createEClass(CHANNEL);

        this.subscriptionEClass = this.createEClass(SUBSCRIPTION);
        this.createEReference(this.subscriptionEClass, SUBSCRIPTION__CHANNEL);
        this.createEReference(this.subscriptionEClass, SUBSCRIPTION__MESSAGE);

        this.publicationEClass = this.createEClass(PUBLICATION);
        this.createEReference(this.publicationEClass, PUBLICATION__CHANNEL);
        this.createEReference(this.publicationEClass, PUBLICATION__MESSAGE);

        // Create enums
        this.priorityEEnum = this.createEEnum(PRIORITY);
        this.visibilityEEnum = this.createEEnum(VISIBILITY);

        // Create data types
        this.instantEDataType = this.createEDataType(INSTANT);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any
     * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void initializePackageContents() {
        if (this.isInitialized)
            return;
        this.isInitialized = true;

        // Initialize package
        this.setName(eNAME);
        this.setNsPrefix(eNS_PREFIX);
        this.setNsURI(eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.referencingLinkEClass.getESuperTypes().add(this.getLink());
        this.containingLinkEClass.getESuperTypes().add(this.getLink());
        this.namedElementEClass.getESuperTypes().add(this.getModelElement());
        this.folderEClass.getESuperTypes().add(this.getNamedElement());
        this.folderEClass.getESuperTypes().add(this.getContainer());
        this.projectEClass.getESuperTypes().add(this.getNamedElement());
        this.projectEClass.getESuperTypes().add(this.getContainer());
        this.operationalCapabilityEClass.getESuperTypes().add(this.getNamedElement());
        this.operationalCapabilityEClass.getESuperTypes().add(this.getFolderElement());
        this.operationalEntityEClass.getESuperTypes().add(this.getNamedElement());
        this.operationalEntityEClass.getESuperTypes().add(this.getFolderElement());
        this.operationalActorEClass.getESuperTypes().add(this.getNamedElement());
        this.operationalActorEClass.getESuperTypes().add(this.getFolderElement());
        this.operationalProcessEClass.getESuperTypes().add(this.getNamedElement());
        this.operationalProcessEClass.getESuperTypes().add(this.getFolderElement());
        this.operationalActivityEClass.getESuperTypes().add(this.getNamedElement());
        this.operationalActivityEClass.getESuperTypes().add(this.getFolderElement());
        this.operationalInteractionEClass.getESuperTypes().add(this.getNamedElement());
        this.iterationEClass.getESuperTypes().add(this.getNamedElement());
        this.iterationEClass.getESuperTypes().add(this.getFolderElement());
        this.taskEClass.getESuperTypes().add(this.getNamedElement());
        this.taskEClass.getESuperTypes().add(this.getFolderElement());
        this.contributionEClass.getESuperTypes().add(this.getNamedElement());
        this.contributionEClass.getESuperTypes().add(this.getFolderElement());
        this.componentEClass.getESuperTypes().add(this.getNamedElement());
        this.componentEClass.getESuperTypes().add(this.getFolderElement());
        this.componentPortEClass.getESuperTypes().add(this.getNamedElement());
        this.componentExchangeEClass.getESuperTypes().add(this.getNamedElement());
        this.providedServiceEClass.getESuperTypes().add(this.getNamedElement());
        this.requiredServiceEClass.getESuperTypes().add(this.getNamedElement());
        this.packageEClass.getESuperTypes().add(this.getNamedElement());
        this.packageEClass.getESuperTypes().add(this.getAnnotableElement());
        this.packageEClass.getESuperTypes().add(this.getFolderElement());
        this.typeEClass.getESuperTypes().add(this.getNamedElement());
        this.typeEClass.getESuperTypes().add(this.getAnnotableElement());
        this.typedElementEClass.getESuperTypes().add(this.getNamedElement());
        this.typedElementEClass.getESuperTypes().add(this.getAnnotableElement());
        this.genericTypeEClass.getESuperTypes().add(this.getModelElement());
        this.genericTypeEClass.getESuperTypes().add(this.getAnnotableElement());
        this.annotationEClass.getESuperTypes().add(this.getType());
        this.annotationFieldEClass.getESuperTypes().add(this.getTypedElement());
        this.classifierEClass.getESuperTypes().add(this.getType());
        this.typeParameterEClass.getESuperTypes().add(this.getNamedElement());
        this.interfaceEClass.getESuperTypes().add(this.getClassifier());
        this.classEClass.getESuperTypes().add(this.getClassifier());
        this.classEClass.getESuperTypes().add(this.getInterfaceImplementation());
        this.constructorEClass.getESuperTypes().add(this.getAnnotableElement());
        this.attributeEClass.getESuperTypes().add(this.getTypedElement());
        this.operationEClass.getESuperTypes().add(this.getTypedElement());
        this.parameterEClass.getESuperTypes().add(this.getTypedElement());
        this.recordEClass.getESuperTypes().add(this.getClassifier());
        this.recordEClass.getESuperTypes().add(this.getInterfaceImplementation());
        this.recordComponentEClass.getESuperTypes().add(this.getTypedElement());
        this.dataTypeEClass.getESuperTypes().add(this.getType());
        this.enumEClass.getESuperTypes().add(this.getType());
        this.enumLiteralEClass.getESuperTypes().add(this.getNamedElement());
        this.enumLiteralEClass.getESuperTypes().add(this.getAnnotableElement());
        this.applicationConcernEClass.getESuperTypes().add(this.getNamedElement());
        this.applicationConcernEClass.getESuperTypes().add(this.getFolderElement());
        this.controllerEClass.getESuperTypes().add(this.getNamedElement());
        this.controllerEClass.getESuperTypes().add(this.getMessageEmitter());
        this.controllerEClass.getESuperTypes().add(this.getMessageListener());
        this.domainEClass.getESuperTypes().add(this.getNamedElement());
        this.domainEClass.getESuperTypes().add(this.getFolderElement());
        this.serviceEClass.getESuperTypes().add(this.getNamedElement());
        this.serviceEClass.getESuperTypes().add(this.getMessageListener());
        this.serviceEClass.getESuperTypes().add(this.getMessageEmitter());
        this.messageEClass.getESuperTypes().add(this.getNamedElement());
        this.eventEClass.getESuperTypes().add(this.getMessage());
        this.commandEClass.getESuperTypes().add(this.getMessage());
        this.queryEClass.getESuperTypes().add(this.getMessage());
        this.repositoryEClass.getESuperTypes().add(this.getNamedElement());
        this.channelEClass.getESuperTypes().add(this.getNamedElement());
        this.channelEClass.getESuperTypes().add(this.getFolderElement());

        // Initialize classes, features, and operations; add parameters
        this.initEClass(this.modelElementEClass, ModelElement.class, "ModelElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getModelElement_Tags(), this.getTag(), null, "tags", null, 0, -1, ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getModelElement_Links(), this.getLink(), this.getLink_Source(), "links", null, 0, -1, ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.tagEClass, Tag.class, "Tag", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getTag_Key(), this.ecorePackage.getEString(), "key", null, 1, 1, Tag.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);
        this.initEAttribute(this.getTag_Value(), this.ecorePackage.getEString(), "value", null, 1, 1, Tag.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.linkEClass, Link.class, "Link", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getLink_Kind(), this.ecorePackage.getEString(), "kind", null, 1, 1, Link.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getLink_Source(), this.getModelElement(), this.getModelElement_Links(), "source", null, 1, 1, Link.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.referencingLinkEClass, ReferencingLink.class, "ReferencingLink", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getReferencingLink_Target(), this.getModelElement(), null, "target", null, 1, 1, ReferencingLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.containingLinkEClass, ContainingLink.class, "ContainingLink", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getContainingLink_Target(), this.getModelElement(), null, "target", null, 1, 1, ContainingLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.namedElementEClass, NamedElement.class, "NamedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getNamedElement_Name(), this.ecorePackage.getEString(), "name", null, 1, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getNamedElement_Description(), this.ecorePackage.getEString(), "description", null, 0, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.containerEClass, org.eclipse.sirius.components.papaya.Container.class, "Container", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getContainer_Folders(), this.getFolder(), null, "folders", null, 0, -1, org.eclipse.sirius.components.papaya.Container.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getContainer_Elements(), this.getFolderElement(), null, "elements", null, 0, -1, org.eclipse.sirius.components.papaya.Container.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.folderEClass, Folder.class, "Folder", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.folderElementEClass, FolderElement.class, "FolderElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.projectEClass, Project.class, "Project", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getProject_Homepage(), this.ecorePackage.getEString(), "homepage", null, 0, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.operationalCapabilityEClass, OperationalCapability.class, "OperationalCapability", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getOperationalCapability_Constraints(), this.ecorePackage.getEString(), "constraints", null, 0, 1, OperationalCapability.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.operationalEntityEClass, OperationalEntity.class, "OperationalEntity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.operationalActorEClass, OperationalActor.class, "OperationalActor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getOperationalActor_Type(), this.ecorePackage.getEString(), "type", null, 1, 1, OperationalActor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.operationalProcessEClass, OperationalProcess.class, "OperationalProcess", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.operationalActivityEClass, OperationalActivity.class, "OperationalActivity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getOperationalActivity_Precondition(), this.ecorePackage.getEString(), "precondition", null, 0, 1, OperationalActivity.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getOperationalActivity_Postcondition(), this.ecorePackage.getEString(), "postcondition", null, 0, 1, OperationalActivity.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.operationalInteractionEClass, OperationalInteraction.class, "OperationalInteraction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getOperationalInteraction_Type(), this.ecorePackage.getEString(), "type", null, 0, 1, OperationalInteraction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getOperationalInteraction_Frequency(), this.ecorePackage.getEString(), "frequency", null, 0, 1, OperationalInteraction.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getOperationalInteraction_Protocol(), this.ecorePackage.getEString(), "protocol", null, 0, 1, OperationalInteraction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getOperationalInteraction_Latency(), this.ecorePackage.getEString(), "latency", null, 0, 1, OperationalInteraction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.iterationEClass, Iteration.class, "Iteration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getIteration_StartDate(), this.getInstant(), "startDate", null, 0, 1, Iteration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getIteration_EndDate(), this.getInstant(), "endDate", null, 0, 1, Iteration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getIteration_Tasks(), this.getTask(), null, "tasks", null, 0, -1, Iteration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getIteration_Contributions(), this.getContribution(), null, "contributions", null, 0, -1, Iteration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.taskEClass, Task.class, "Task", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getTask_Priority(), this.getPriority(), "priority", null, 0, 1, Task.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);
        this.initEAttribute(this.getTask_Cost(), this.ecorePackage.getEInt(), "cost", null, 0, 1, Task.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTask_Targets(), this.getModelElement(), null, "targets", null, 0, -1, Task.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTask_Tasks(), this.getTask(), null, "tasks", null, 0, -1, Task.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTask_StartDate(), this.getInstant(), "startDate", null, 0, 1, Task.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTask_EndDate(), this.getInstant(), "endDate", null, 0, 1, Task.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
                IS_ORDERED);
        this.initEAttribute(this.getTask_Done(), this.ecorePackage.getEBoolean(), "done", null, 0, 1, Task.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTask_Dependencies(), this.getTask(), null, "dependencies", null, 0, -1, Task.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.contributionEClass, Contribution.class, "Contribution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getContribution_RelatedTasks(), this.getTask(), null, "relatedTasks", null, 0, -1, Contribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getContribution_Targets(), this.getModelElement(), null, "targets", null, 0, -1, Contribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getContribution_Done(), this.ecorePackage.getEBoolean(), "done", null, 0, 1, Contribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.componentEClass, Component.class, "Component", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getComponent_Dependencies(), this.getComponent(), this.getComponent_UsedAsDependencyBy(), "dependencies", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getComponent_AllDependencies(), this.getComponent(), null, "allDependencies", null, 0, -1, Component.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getComponent_UsedAsDependencyBy(), this.getComponent(), this.getComponent_Dependencies(), "usedAsDependencyBy", null, 0, -1, Component.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getComponent_Components(), this.getComponent(), null, "components", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getComponent_AllComponents(), this.getComponent(), null, "allComponents", null, 0, -1, Component.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getComponent_Packages(), this.getPackage(), null, "packages", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getComponent_Ports(), this.getComponentPort(), null, "ports", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getComponent_ProvidedServices(), this.getProvidedService(), null, "providedServices", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getComponent_RequiredServices(), this.getRequiredService(), null, "requiredServices", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.componentPortEClass, ComponentPort.class, "ComponentPort", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getComponentPort_Protocol(), this.ecorePackage.getEString(), "protocol", null, 0, 1, ComponentPort.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
                !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.componentExchangeEClass, ComponentExchange.class, "ComponentExchange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getComponentExchange_Ports(), this.getComponentPort(), null, "ports", null, 1, -1, ComponentExchange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.providedServiceEClass, ProvidedService.class, "ProvidedService", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getProvidedService_Contracts(), this.getInterface(), null, "contracts", null, 0, -1, ProvidedService.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.requiredServiceEClass, RequiredService.class, "RequiredService", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getRequiredService_Contracts(), this.getInterface(), null, "contracts", null, 0, -1, RequiredService.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.annotableElementEClass, AnnotableElement.class, "AnnotableElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getAnnotableElement_Annotations(), this.getAnnotation(), null, "annotations", null, 0, -1, AnnotableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.packageEClass, org.eclipse.sirius.components.papaya.Package.class, "Package", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getPackage_QualifiedName(), this.ecorePackage.getEString(), "qualifiedName", null, 1, 1, org.eclipse.sirius.components.papaya.Package.class, IS_TRANSIENT, IS_VOLATILE,
                !IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPackage_Types(), this.getType(), null, "types", null, 0, -1, org.eclipse.sirius.components.papaya.Package.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPackage_Packages(), this.getPackage(), null, "packages", null, 0, -1, org.eclipse.sirius.components.papaya.Package.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.typeEClass, Type.class, "Type", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getType_QualifiedName(), this.ecorePackage.getEString(), "qualifiedName", null, 1, 1, Type.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getType_Visibility(), this.getVisibility(), "visibility", "PUBLIC", 1, 1, Type.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getType_Types(), this.getType(), null, "types", null, 0, -1, Type.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.typedElementEClass, TypedElement.class, "TypedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getTypedElement_Type(), this.getGenericType(), null, "type", null, 1, 1, TypedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.genericTypeEClass, GenericType.class, "GenericType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getGenericType_RawType(), this.getType(), null, "rawType", null, 1, 1, GenericType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getGenericType_TypeArguments(), this.getGenericType(), null, "typeArguments", null, 0, -1, GenericType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.annotationEClass, Annotation.class, "Annotation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getAnnotation_Fields(), this.getAnnotationField(), null, "fields", null, 0, -1, Annotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.annotationFieldEClass, AnnotationField.class, "AnnotationField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.classifierEClass, Classifier.class, "Classifier", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getClassifier_TypeParameters(), this.getTypeParameter(), null, "typeParameters", null, 0, -1, Classifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.typeParameterEClass, TypeParameter.class, "TypeParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.interfaceEClass, Interface.class, "Interface", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getInterface_Extends(), this.getInterface(), this.getInterface_ExtendedBy(), "extends", null, 0, -1, Interface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getInterface_ExtendedBy(), this.getInterface(), this.getInterface_Extends(), "extendedBy", null, 0, -1, Interface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getInterface_ImplementedBy(), this.getInterfaceImplementation(), this.getInterfaceImplementation_Implements(), "implementedBy", null, 0, -1, Interface.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getInterface_Subtypes(), this.getClassifier(), null, "subtypes", null, 0, -1, Interface.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getInterface_AllSubtypes(), this.getClassifier(), null, "allSubtypes", null, 0, -1, Interface.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getInterface_Operations(), this.getOperation(), null, "operations", null, 0, -1, Interface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.interfaceImplementationEClass, InterfaceImplementation.class, "InterfaceImplementation", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getInterfaceImplementation_Implements(), this.getInterface(), this.getInterface_ImplementedBy(), "implements", null, 0, -1, InterfaceImplementation.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.classEClass, org.eclipse.sirius.components.papaya.Class.class, "Class", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getClass_Abstract(), this.ecorePackage.getEBoolean(), "abstract", null, 0, 1, org.eclipse.sirius.components.papaya.Class.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getClass_Final(), this.ecorePackage.getEBoolean(), "final", null, 0, 1, org.eclipse.sirius.components.papaya.Class.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getClass_Static(), this.ecorePackage.getEBoolean(), "static", null, 0, 1, org.eclipse.sirius.components.papaya.Class.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getClass_Extends(), this.getClass_(), this.getClass_ExtendedBy(), "extends", null, 0, 1, org.eclipse.sirius.components.papaya.Class.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getClass_ExtendedBy(), this.getClass_(), this.getClass_Extends(), "extendedBy", null, 0, -1, org.eclipse.sirius.components.papaya.Class.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getClass_AllExtendedBy(), this.getClass_(), null, "allExtendedBy", null, 0, -1, org.eclipse.sirius.components.papaya.Class.class, IS_TRANSIENT, IS_VOLATILE,
                !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getClass_Constructors(), this.getConstructor(), null, "constructors", null, 0, -1, org.eclipse.sirius.components.papaya.Class.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getClass_Attributes(), this.getAttribute(), null, "attributes", null, 0, -1, org.eclipse.sirius.components.papaya.Class.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getClass_Operations(), this.getOperation(), null, "operations", null, 0, -1, org.eclipse.sirius.components.papaya.Class.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.constructorEClass, Constructor.class, "Constructor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getConstructor_Parameters(), this.getParameter(), null, "parameters", null, 0, -1, Constructor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getConstructor_Visibility(), this.getVisibility(), "visibility", "PUBLIC", 1, 1, Constructor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.attributeEClass, Attribute.class, "Attribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getAttribute_Visibility(), this.getVisibility(), "visibility", "PUBLIC", 1, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getAttribute_Final(), this.ecorePackage.getEBoolean(), "final", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getAttribute_Static(), this.ecorePackage.getEBoolean(), "static", null, 0, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.operationEClass, Operation.class, "Operation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getOperation_Visibility(), this.getVisibility(), "visibility", "PUBLIC", 1, 1, Operation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getOperation_Abstract(), this.ecorePackage.getEBoolean(), "abstract", null, 0, 1, Operation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getOperation_Final(), this.ecorePackage.getEBoolean(), "final", null, 0, 1, Operation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getOperation_Static(), this.ecorePackage.getEBoolean(), "static", null, 0, 1, Operation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
                IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getOperation_Parameters(), this.getParameter(), null, "parameters", null, 0, -1, Operation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.recordEClass, org.eclipse.sirius.components.papaya.Record.class, "Record", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getRecord_Components(), this.getRecordComponent(), null, "components", null, 0, -1, org.eclipse.sirius.components.papaya.Record.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getRecord_Operations(), this.getOperation(), null, "operations", null, 0, -1, org.eclipse.sirius.components.papaya.Record.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.recordComponentEClass, RecordComponent.class, "RecordComponent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.dataTypeEClass, DataType.class, "DataType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.enumEClass, org.eclipse.sirius.components.papaya.Enum.class, "Enum", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getEnum_Literals(), this.getEnumLiteral(), null, "literals", null, 0, -1, org.eclipse.sirius.components.papaya.Enum.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.enumLiteralEClass, EnumLiteral.class, "EnumLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.applicationConcernEClass, ApplicationConcern.class, "ApplicationConcern", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getApplicationConcern_Controllers(), this.getController(), null, "controllers", null, 0, -1, ApplicationConcern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getApplicationConcern_Services(), this.getService(), null, "services", null, 0, -1, ApplicationConcern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getApplicationConcern_Events(), this.getEvent(), null, "events", null, 0, -1, ApplicationConcern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getApplicationConcern_Commands(), this.getCommand(), null, "commands", null, 0, -1, ApplicationConcern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getApplicationConcern_Queries(), this.getQuery(), null, "queries", null, 0, -1, ApplicationConcern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getApplicationConcern_Domains(), this.getDomain(), null, "domains", null, 0, -1, ApplicationConcern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.controllerEClass, Controller.class, "Controller", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getController_Calls(), this.getService(), null, "calls", null, 0, -1, Controller.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.domainEClass, Domain.class, "Domain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getDomain_Services(), this.getService(), null, "services", null, 0, -1, Domain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDomain_Repositories(), this.getRepository(), null, "repositories", null, 0, -1, Domain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDomain_Events(), this.getEvent(), null, "events", null, 0, -1, Domain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDomain_Commands(), this.getCommand(), null, "commands", null, 0, -1, Domain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDomain_Queries(), this.getQuery(), null, "queries", null, 0, -1, Domain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDomain_Dependencies(), this.getDomain(), null, "dependencies", null, 0, -1, Domain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.serviceEClass, Service.class, "Service", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getService_Calls(), this.getService(), null, "calls", null, 0, -1, Service.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.messageListenerEClass, MessageListener.class, "MessageListener", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getMessageListener_Subscriptions(), this.getSubscription(), null, "subscriptions", null, 0, -1, MessageListener.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.messageEmitterEClass, MessageEmitter.class, "MessageEmitter", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getMessageEmitter_Publications(), this.getPublication(), null, "publications", null, 0, -1, MessageEmitter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.messageEClass, Message.class, "Message", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getMessage_EmittedBy(), this.getPublication(), this.getPublication_Message(), "emittedBy", null, 0, -1, Message.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getMessage_ListenedBy(), this.getSubscription(), this.getSubscription_Message(), "listenedBy", null, 0, -1, Message.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.eventEClass, Event.class, "Event", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getEvent_CausedBy(), this.getMessage(), null, "causedBy", null, 0, -1, Event.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.commandEClass, Command.class, "Command", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.queryEClass, Query.class, "Query", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.repositoryEClass, Repository.class, "Repository", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.channelEClass, Channel.class, "Channel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.subscriptionEClass, Subscription.class, "Subscription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getSubscription_Channel(), this.getChannel(), null, "channel", null, 0, 1, Subscription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getSubscription_Message(), this.getMessage(), this.getMessage_ListenedBy(), "message", null, 0, 1, Subscription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.publicationEClass, Publication.class, "Publication", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getPublication_Channel(), this.getChannel(), null, "channel", null, 0, 1, Publication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
                IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getPublication_Message(), this.getMessage(), this.getMessage_EmittedBy(), "message", null, 0, 1, Publication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Initialize enums and add enum literals
        this.initEEnum(this.priorityEEnum, Priority.class, "Priority");
        this.addEEnumLiteral(this.priorityEEnum, Priority.P1);
        this.addEEnumLiteral(this.priorityEEnum, Priority.P2);
        this.addEEnumLiteral(this.priorityEEnum, Priority.P3);
        this.addEEnumLiteral(this.priorityEEnum, Priority.P4);
        this.addEEnumLiteral(this.priorityEEnum, Priority.P5);

        this.initEEnum(this.visibilityEEnum, Visibility.class, "Visibility");
        this.addEEnumLiteral(this.visibilityEEnum, Visibility.PUBLIC);
        this.addEEnumLiteral(this.visibilityEEnum, Visibility.PROTECTED);
        this.addEEnumLiteral(this.visibilityEEnum, Visibility.PACKAGE);
        this.addEEnumLiteral(this.visibilityEEnum, Visibility.PRIVATE);

        // Initialize data types
        this.initEDataType(this.instantEDataType, Instant.class, "Instant", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        this.createResource(eNS_URI);
    }

} // PapayaPackageImpl
