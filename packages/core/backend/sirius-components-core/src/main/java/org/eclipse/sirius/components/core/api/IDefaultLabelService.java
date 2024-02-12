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
package org.eclipse.sirius.components.core.api;

import java.util.List;
import java.util.Optional;

/**
 * Interface of the default service interacting with label objects.
 *
 * @author mcharfadi
 */
public interface IDefaultLabelService {

    String getLabel(Object object);

    String getFullLabel(Object object);

    List<String> getImagePath(Object object);

    Optional<String> getLabelField(Object object);

    boolean isLabelEditable(Object object);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author mcharfadi
     */
    class NoOp implements IDefaultLabelService {

        @Override
        public String getLabel(Object object) {
            return "";
        }

        @Override
        public String getFullLabel(Object object) {
            return "";
        }

        @Override
        public List<String> getImagePath(Object object) {
            return List.of();
        }

        @Override
        public Optional<String> getLabelField(Object object) {
            return Optional.empty();
        }

        @Override
        public boolean isLabelEditable(Object object) {
            return false;
        }

    }
}
