/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
package org.eclipse.sirius.components.diagrams.tests.builder.label;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.HeaderSeparatorDisplayMode;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.components.LabelType;

/**
 * The builder used to create a label.
 *
 * @author gcoutable
 */
public final class LabelBuilder {

    public static final String DEFAULT_COLOR_TRANSPARENT = "transparent";

    public Label basicLabel(String text, LabelType labelType) {
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color("black")
                .fontSize(14)
                .bold(false)
                .italic(false)
                .underline(false)
                .strikeThrough(false)
                .iconURL(List.of())
                .background(DEFAULT_COLOR_TRANSPARENT)
                .borderColor(DEFAULT_COLOR_TRANSPARENT)
                .borderSize(0)
                .borderStyle(LineStyle.Solid)
                .build();

        return Label.newLabel(UUID.randomUUID().toString())
                .type(Objects.requireNonNull(labelType).getValue())
                .text(Objects.requireNonNull(text))
                .style(labelStyle)
                .build();
    }

    public InsideLabel basicInsideLabel(String text, LabelType labelType, boolean isHeader) {
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color("black")
                .fontSize(14)
                .bold(false)
                .italic(false)
                .underline(false)
                .strikeThrough(false)
                .iconURL(List.of())
                .background(DEFAULT_COLOR_TRANSPARENT)
                .borderColor(DEFAULT_COLOR_TRANSPARENT)
                .borderSize(0)
                .borderStyle(LineStyle.Solid)
                .build();

        return InsideLabel.newLabel(UUID.randomUUID().toString())
                .text(Objects.requireNonNull(text))
                .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                .style(labelStyle)
                .isHeader(isHeader)
                .headerSeparatorDisplayMode(HeaderSeparatorDisplayMode.IF_CHILDREN)
                .customizedStyleProperties(Set.of())
                .build();
    }

}
