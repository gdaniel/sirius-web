/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.view.builder.generated.tree;

/**
 * Builder for ContributedTreeItemContextMenuEntryBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ContributedTreeItemContextMenuEntryBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.tree.ContributedTreeItemContextMenuEntry.
     * @generated
     */
    private org.eclipse.sirius.components.view.tree.ContributedTreeItemContextMenuEntry contributedTreeItemContextMenuEntry = org.eclipse.sirius.components.view.tree.TreeFactory.eINSTANCE.createContributedTreeItemContextMenuEntry();

    /**
     * Return instance org.eclipse.sirius.components.view.tree.ContributedTreeItemContextMenuEntry.
     * @generated
     */
    protected org.eclipse.sirius.components.view.tree.ContributedTreeItemContextMenuEntry getContributedTreeItemContextMenuEntry() {
        return this.contributedTreeItemContextMenuEntry;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.tree.ContributedTreeItemContextMenuEntry.
     * @generated
     */
    public org.eclipse.sirius.components.view.tree.ContributedTreeItemContextMenuEntry build() {
        return this.getContributedTreeItemContextMenuEntry();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public ContributedTreeItemContextMenuEntryBuilder name(java.lang.String value) {
        this.getContributedTreeItemContextMenuEntry().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public ContributedTreeItemContextMenuEntryBuilder labelExpression(java.lang.String value) {
        this.getContributedTreeItemContextMenuEntry().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for IconURLExpression.
     *
     * @generated
     */
    public ContributedTreeItemContextMenuEntryBuilder iconURLExpression(java.lang.String value) {
        this.getContributedTreeItemContextMenuEntry().setIconURLExpression(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public ContributedTreeItemContextMenuEntryBuilder preconditionExpression(java.lang.String value) {
        this.getContributedTreeItemContextMenuEntry().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for ContributionId.
     *
     * @generated
     */
    public ContributedTreeItemContextMenuEntryBuilder contributionId(java.lang.String value) {
        this.getContributedTreeItemContextMenuEntry().setContributionId(value);
        return this;
    }

}

