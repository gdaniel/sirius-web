/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
package org.eclipse.sirius.components.tables.graphql.datafetchers;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.api.IImageURLSanitizer;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.URLConstants;
import org.eclipse.sirius.components.tables.Line;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to concatenate the server image URL to the row header image path.
 *
 * @author frouene
 */
@QueryDataFetcher(type = "Line", field = "headerIconURLs")
public class LineHeaderIconURLsDataFetcher implements IDataFetcherWithFieldCoordinates<List<String>> {

    private final IImageURLSanitizer imageURLSanitizer;

    public LineHeaderIconURLsDataFetcher(IImageURLSanitizer imageURLSanitizer) {
        this.imageURLSanitizer = Objects.requireNonNull(imageURLSanitizer);
    }

    @Override
    public List<String> get(DataFetchingEnvironment environment) throws Exception {
        Line source = environment.getSource();

        return source.getHeaderIconURLs().stream()
                .map(url -> this.imageURLSanitizer.sanitize(URLConstants.IMAGE_BASE_PATH, url))
                .toList();
    }
}
