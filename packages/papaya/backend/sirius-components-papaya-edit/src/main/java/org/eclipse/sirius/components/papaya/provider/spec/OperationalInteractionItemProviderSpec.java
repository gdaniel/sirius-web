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

package org.eclipse.sirius.components.papaya.provider.spec;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.sirius.components.papaya.OperationalInteraction;
import org.eclipse.sirius.components.papaya.provider.OperationalInteractionItemProvider;
import org.eclipse.sirius.components.papaya.provider.spec.label.NamedElementStyledTextProvider;

/**
 * Customization of the item provider implementation generated by EMF.
 *
 * @author sbegaudeau
 */
public class OperationalInteractionItemProviderSpec extends OperationalInteractionItemProvider {
    public OperationalInteractionItemProviderSpec(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("papaya/full/obj16/OperationalInteraction.svg"));
    }

    @Override
    public Object getStyledText(Object object) {
        if (object instanceof OperationalInteraction operationalInteraction) {
            return new NamedElementStyledTextProvider().getStyledText(operationalInteraction, this.getString("_UI_OperationalInteraction_type"));
        }
        return super.getStyledText(object);
    }
}
