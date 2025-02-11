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
package org.eclipse.sirius.components.view.builder.generated.form;

/**
 * Builder for LinkDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class LinkDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.LinkDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.LinkDescription linkDescription = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createLinkDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.form.LinkDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.LinkDescription getLinkDescription() {
        return this.linkDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.LinkDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.LinkDescription build() {
        return this.getLinkDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public LinkDescriptionBuilder name(java.lang.String value) {
        this.getLinkDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public LinkDescriptionBuilder labelExpression(java.lang.String value) {
        this.getLinkDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for HelpExpression.
     *
     * @generated
     */
    public LinkDescriptionBuilder helpExpression(java.lang.String value) {
        this.getLinkDescription().setHelpExpression(value);
        return this;
    }
    /**
     * Setter for DiagnosticsExpression.
     *
     * @generated
     */
    public LinkDescriptionBuilder diagnosticsExpression(java.lang.String value) {
        this.getLinkDescription().setDiagnosticsExpression(value);
        return this;
    }

    /**
     * Setter for ValueExpression.
     *
     * @generated
     */
    public LinkDescriptionBuilder valueExpression(java.lang.String value) {
        this.getLinkDescription().setValueExpression(value);
        return this;
    }
    /**
     * Setter for Style.
     *
     * @generated
     */
    public LinkDescriptionBuilder style(org.eclipse.sirius.components.view.form.LinkDescriptionStyle value) {
        this.getLinkDescription().setStyle(value);
        return this;
    }
    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public LinkDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.form.ConditionalLinkDescriptionStyle ... values) {
        for (org.eclipse.sirius.components.view.form.ConditionalLinkDescriptionStyle value : values) {
            this.getLinkDescription().getConditionalStyles().add(value);
        }
        return this;
    }


}

