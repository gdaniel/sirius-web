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
package org.eclipse.sirius.components.view.emf.form.converters;

import static org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter.VARIABLE_MANAGER;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IReadOnlyObjectPredicate;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to indicate if a widget is read only.
 *
 * @author sbegaudeau
 */
public class ReadOnlyValueProvider implements Function<VariableManager, Boolean> {

    private final IReadOnlyObjectPredicate readOnlyObjectPredicate;

    private final AQLInterpreter interpreter;

    private final String expression;

    public ReadOnlyValueProvider(IReadOnlyObjectPredicate readOnlyObjectPredicate, AQLInterpreter interpreter, String expression) {
        this.readOnlyObjectPredicate = Objects.requireNonNull(readOnlyObjectPredicate);
        this.interpreter = Objects.requireNonNull(interpreter);
        this.expression = expression;
    }

    @Override
    public Boolean apply(VariableManager variableManager) {
        var isReadOnly = variableManager.get(VariableManager.SELF, Object.class)
                .filter(this.readOnlyObjectPredicate)
                .isPresent();
        if (isReadOnly) {
            return true;
        }

        VariableManager childVariableManager = variableManager.createChild();
        childVariableManager.put(VARIABLE_MANAGER, variableManager);
        Result result = this.interpreter.evaluateExpression(childVariableManager.getVariables(), this.expression);
        return result.asBoolean()
                .map(value -> !value)
                .orElse(Boolean.FALSE);
    }
}
