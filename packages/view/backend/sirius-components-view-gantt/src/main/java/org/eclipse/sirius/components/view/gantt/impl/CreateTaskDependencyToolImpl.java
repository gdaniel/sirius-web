/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.view.gantt.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.sirius.components.view.gantt.CreateTaskDependencyTool;
import org.eclipse.sirius.components.view.gantt.GanttPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Create Task Dependency Tool</b></em>'. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class CreateTaskDependencyToolImpl extends TaskToolImpl implements CreateTaskDependencyTool {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected CreateTaskDependencyToolImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return GanttPackage.Literals.CREATE_TASK_DEPENDENCY_TOOL;
    }

} // CreateTaskDependencyToolImpl
