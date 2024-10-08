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
package org.eclipse.sirius.components.view.builder.generated.form;

/**
 * Builder for SelectDescriptionStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class SelectDescriptionStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.SelectDescriptionStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.SelectDescriptionStyle selectDescriptionStyle = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createSelectDescriptionStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.form.SelectDescriptionStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.SelectDescriptionStyle getSelectDescriptionStyle() {
        return this.selectDescriptionStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.SelectDescriptionStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.SelectDescriptionStyle build() {
        return this.getSelectDescriptionStyle();
    }

    /**
     * Setter for FontSize.
     *
     * @generated
     */
    public SelectDescriptionStyleBuilder fontSize(java.lang.Integer value) {
        this.getSelectDescriptionStyle().setFontSize(value);
        return this;
    }
    /**
     * Setter for Italic.
     *
     * @generated
     */
    public SelectDescriptionStyleBuilder italic(java.lang.Boolean value) {
        this.getSelectDescriptionStyle().setItalic(value);
        return this;
    }
    /**
     * Setter for Bold.
     *
     * @generated
     */
    public SelectDescriptionStyleBuilder bold(java.lang.Boolean value) {
        this.getSelectDescriptionStyle().setBold(value);
        return this;
    }
    /**
     * Setter for Underline.
     *
     * @generated
     */
    public SelectDescriptionStyleBuilder underline(java.lang.Boolean value) {
        this.getSelectDescriptionStyle().setUnderline(value);
        return this;
    }
    /**
     * Setter for StrikeThrough.
     *
     * @generated
     */
    public SelectDescriptionStyleBuilder strikeThrough(java.lang.Boolean value) {
        this.getSelectDescriptionStyle().setStrikeThrough(value);
        return this;
    }
    /**
     * Setter for BackgroundColor.
     *
     * @generated
     */
    public SelectDescriptionStyleBuilder backgroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getSelectDescriptionStyle().setBackgroundColor(value);
        return this;
    }
    /**
     * Setter for ForegroundColor.
     *
     * @generated
     */
    public SelectDescriptionStyleBuilder foregroundColor(org.eclipse.sirius.components.view.UserColor value) {
        this.getSelectDescriptionStyle().setForegroundColor(value);
        return this;
    }
    /**
     * Setter for ShowIcon.
     *
     * @generated
     */
    public SelectDescriptionStyleBuilder showIcon(java.lang.Boolean value) {
        this.getSelectDescriptionStyle().setShowIcon(value);
        return this;
    }

}

