/**
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
 */
package org.eclipse.sirius.components.view.tree;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Contributed Tree Item Context Menu
 * Entry</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.tree.ContributedTreeItemContextMenuEntry#getContributionId
 * <em>Contribution Id</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.tree.TreePackage#getContributedTreeItemContextMenuEntry()
 * @model
 * @generated
 */
public interface ContributedTreeItemContextMenuEntry extends TreeItemContextMenuEntry {
    /**
     * Returns the value of the '<em><b>Contribution Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Contribution Id</em>' attribute.
     * @see #setContributionId(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getContributedTreeItemContextMenuEntry_ContributionId()
     * @model dataType="org.eclipse.sirius.components.view.Identifier"
     * @generated
     */
    String getContributionId();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.tree.ContributedTreeItemContextMenuEntry#getContributionId
     * <em>Contribution Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Contribution Id</em>' attribute.
     * @see #getContributionId()
     * @generated
     */
    void setContributionId(String value);

} // ContributedTreeItemContextMenuEntry
