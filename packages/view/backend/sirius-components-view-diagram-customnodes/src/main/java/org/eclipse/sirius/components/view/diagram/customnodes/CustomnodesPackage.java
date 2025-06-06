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
package org.eclipse.sirius.components.view.diagram.customnodes;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.diagram.customnodes.CustomnodesFactory
 * @model kind="package"
 * @generated
 */
public interface CustomnodesPackage extends EPackage {
    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "customnodes";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://www.eclipse.org/sirius-web/customnodes";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "customnodes";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    CustomnodesPackage eINSTANCE = org.eclipse.sirius.components.view.diagram.customnodes.impl.CustomnodesPackageImpl.init();

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.diagram.customnodes.impl.EllipseNodeStyleDescriptionImpl <em>Ellipse
     * Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.diagram.customnodes.impl.EllipseNodeStyleDescriptionImpl
     * @see org.eclipse.sirius.components.view.diagram.customnodes.impl.CustomnodesPackageImpl#getEllipseNodeStyleDescription()
     * @generated
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_COLOR = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_COLOR;

    /**
     * The feature id for the '<em><b>Border Radius</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_RADIUS;

    /**
     * The feature id for the '<em><b>Border Size</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_SIZE = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_SIZE;

    /**
     * The feature id for the '<em><b>Border Line Style</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE = DiagramPackage.NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE;

    /**
     * The feature id for the '<em><b>Children Layout Strategy</b></em>' containment reference. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY = DiagramPackage.NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY;

    /**
     * The feature id for the '<em><b>Background</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION__BACKGROUND = DiagramPackage.NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Ellipse Node Style Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION_FEATURE_COUNT = DiagramPackage.NODE_STYLE_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Ellipse Node Style Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ELLIPSE_NODE_STYLE_DESCRIPTION_OPERATION_COUNT = DiagramPackage.NODE_STYLE_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.diagram.customnodes.EllipseNodeStyleDescription <em>Ellipse Node Style
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Ellipse Node Style Description</em>'.
     * @see org.eclipse.sirius.components.view.diagram.customnodes.EllipseNodeStyleDescription
     * @generated
     */
    EClass getEllipseNodeStyleDescription();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.diagram.customnodes.EllipseNodeStyleDescription#getBackground
     * <em>Background</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Background</em>'.
     * @see org.eclipse.sirius.components.view.diagram.customnodes.EllipseNodeStyleDescription#getBackground()
     * @see #getEllipseNodeStyleDescription()
     * @generated
     */
    EReference getEllipseNodeStyleDescription_Background();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    CustomnodesFactory getCustomnodesFactory();

    /**
     * <!-- begin-user-doc --> Defines literals for the meta objects that represent
     * <ul>
     * <li>each class,</li>
     * <li>each feature of each class,</li>
     * <li>each operation of each class,</li>
     * <li>each enum,</li>
     * <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     *
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.diagram.customnodes.impl.EllipseNodeStyleDescriptionImpl
         * <em>Ellipse Node Style Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.diagram.customnodes.impl.EllipseNodeStyleDescriptionImpl
         * @see org.eclipse.sirius.components.view.diagram.customnodes.impl.CustomnodesPackageImpl#getEllipseNodeStyleDescription()
         * @generated
         */
        EClass ELLIPSE_NODE_STYLE_DESCRIPTION = eINSTANCE.getEllipseNodeStyleDescription();

        /**
         * The meta object literal for the '<em><b>Background</b></em>' reference feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EReference ELLIPSE_NODE_STYLE_DESCRIPTION__BACKGROUND = eINSTANCE.getEllipseNodeStyleDescription_Background();

    }

} // CustomnodesPackage
