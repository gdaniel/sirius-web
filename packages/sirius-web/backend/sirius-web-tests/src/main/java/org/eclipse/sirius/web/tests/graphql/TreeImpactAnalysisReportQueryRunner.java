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
package org.eclipse.sirius.web.tests.graphql;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to get an impact analysis report from a tree.
 *
 * @author gdaniel
 */
@Service
public class TreeImpactAnalysisReportQueryRunner implements IQueryRunner {

    private static final String TREE_IMPACT_ANALYSIS_REPORT_QUERY = """
            query getImpactAnalysisReport($editingContextId: ID!, $representationId: ID!, $treeItemId: ID!, $menuEntryId: ID!) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  representation(representationId: $representationId) {
                    description {
                      ... on TreeDescription {
                        treeImpactAnalysisReport(treeItemId: $treeItemId, menuEntryId: $menuEntryId) {
                          nbElementDeleted
                          nbElementModified
                          nbElementCreated
                          additionalReports
                        }
                      }
                    }
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public TreeImpactAnalysisReportQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(TREE_IMPACT_ANALYSIS_REPORT_QUERY, variables);
    }

}
