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
package org.eclipse.sirius.web.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.dto.CreateChildInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to create a child object with the GraphQL API.
 *
 * @author sbegaudeau
 */
@Service
public class CreateChildMutationRunner implements IMutationRunner<CreateChildInput> {

    private static final String CREATE_CHILD = """
            mutation createChild($input: CreateChildInput!) {
              createChild(input: $input) {
                __typename
                ... on CreateChildSuccessPayload {
                  object {
                    id
                    label
                    kind
                  }
                }
               ... on ErrorPayload {
                    message
                    messages {
                      level
                      body
                    }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public CreateChildMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(CreateChildInput input) {
        return this.graphQLRequestor.execute(CREATE_CHILD, input);
    }
}
