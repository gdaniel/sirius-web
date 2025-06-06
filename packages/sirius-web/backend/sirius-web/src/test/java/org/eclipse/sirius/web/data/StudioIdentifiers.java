/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.data;

import java.util.UUID;

/**
 * Used to store some test identifiers related to the studio projects.
 *
 * @author sbegaudeau
 */
public final class StudioIdentifiers {

    public static final String EMPTY_STUDIO_PROJECT = "250cabc0-a211-438c-8015-2d2aa136eb81";

    public static final UUID EMPTY_STUDIO_EDITING_CONTEXT_ID = UUID.fromString("bd3017e3-d95f-4535-8701-af6ba982619f");

    public static final String SAMPLE_STUDIO_EDITING_CONTEXT_ID = "e344d967-a639-4f6c-9c00-a466d51063c6";

    public static final String SAMPLE_STUDIO_PROJECT = "01234836-0902-418a-900a-4c0afd20323e";

    public static final UUID DOMAIN_DOCUMENT = UUID.fromString("f0e490c1-79f1-49a0-b1f2-3637f2958148");

    public static final UUID DOMAIN_OBJECT = UUID.fromString("f8204cb6-3705-48a5-bee3-ad7e7d6cbdaf");

    public static final UUID ROOT_ENTITY_OBJECT = UUID.fromString("c341bf91-d315-4264-9787-c51b121a6375");

    public static final UUID NAMED_ELEMENT_ENTITY_OBJECT = UUID.fromString("c6fdba07-dea5-4a53-99c7-7eefc1bfdfcc");

    public static final UUID LABEL_ATTRIBUTE_OBJECT = UUID.fromString("7ac92c9d-3cb6-4374-9774-11bb62962fe2");

    public static final UUID DESCRIPTION_ATTRIBUTE_OBJECT = UUID.fromString("d51d676c-0cb7-414b-8358-bacbc5d33942");

    public static final UUID NAME_ATTRIBUTE_OBJECT = UUID.fromString("520bb7c9-5f28-40f7-bda0-b35dd593876d");

    public static final UUID HUMAN_ENTITY_OBJECT = UUID.fromString("1731ffb5-bfb0-46f3-a23d-0c0650300005");

    public static final UUID VIEW_DOCUMENT = UUID.fromString("ed2a5355-991d-458f-87f1-ea3a18b1f104");

    public static final UUID FORM_DESCRIPTION_OBJECT = UUID.fromString("ed20cb85-a58a-47ad-bc0d-749ec8b2ea03");

    public static final UUID GROUP_OBJECT = UUID.fromString("28d8d6de-7d6f-4434-9293-0ac4ef2461ac");

    public static final UUID DIAGRAM_DESCRIPTION_OBJECT = UUID.fromString("7384dc2c-1b43-45c7-9c74-f972b28774c8");

    public static final UUID HUMAN_NODE_DESCRIPTION_OBJECT = UUID.fromString("e91e6e23-1440-4fbf-b31c-3a21bf25d85b");

    public static final String DIAGRAM_DESCRIPTION_ID = "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=fc1d7b23-2818-4874-bb30-8831ea287a44&sourceElementId=7384dc2c-1b43-45c7-9c74-f972b28774c8";

    public static final UUID PLACEHOLDER_IMAGE_OBJECT = UUID.fromString("7f8ce6ef-a23f-4c62-a6f8-381d5c237742");

    public static final String INSTANCE_EDITING_CONTEXT_ID = "63f4353f-0c71-4122-93be-2d359fc0fa16";

    public static final UUID ROOT_OBJECT = UUID.fromString("87fa4553-6889-4ce6-b017-d013987f9fae");

    public static final UUID ELLIPSE_NODE_STYLE_DESCRIPTION_OBJECT = UUID.fromString("3b3637a2-c397-4837-b42f-25fee34e5af2");

    public static final UUID HUMAN_INSIDE_LABEL_STYLE_OBJECT = UUID.fromString("c8338087-e98e-43bd-ae1a-879b64308a7d");

    public static final String TABLE_ID = "d28d9ecb-102a-4eee-9d26-55543c5acb7f";

    public static final String TABLE_DESCRIPTION_ID = "siriusComponents://representationDescription?kind=tableDescription&sourceKind=view&sourceId=" +
            StudioIdentifiers.VIEW_DOCUMENT + "&sourceElementId=" + StudioIdentifiers.TABLE_ID;

    public static final UUID RECTANGULAR_NODE_STYLE_OBJECT = UUID.fromString("d8f8f5f4-5044-45ec-860a-aa1122e192e7");

    public static final UUID EDGE_STYLE_OBJECT = UUID.fromString("a2a3713f-57bc-422b-92e8-b22ed69e94a8");

    private StudioIdentifiers() {
        // Prevent instantiation
    }
}
