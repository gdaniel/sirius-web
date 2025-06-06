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
package org.eclipse.sirius.components.view.emf.form.api;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.form.FormDescription;

/**
 * Interface to provide ids for FormDescription.
 *
 * @author pcdavid
 */
public interface IFormIdProvider extends IRepresentationDescriptionIdProvider<FormDescription> {

    String FORM_DESCRIPTION_KIND = PREFIX + "?kind=formDescription";

    String FORM_ELEMENT_DESCRIPTION_PREFIX = "siriusComponents://formElementDescription";

    @Override
    String getId(FormDescription formDescription);

    String getFormElementDescriptionId(EObject formElement);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author pcdavid
     */
    class NoOp implements IFormIdProvider {

        @Override
        public String getId(FormDescription formDescription) {
            return "";
        }

        @Override
        public String getFormElementDescriptionId(EObject widgetDescription) {
            return "";
        }
    }
}
