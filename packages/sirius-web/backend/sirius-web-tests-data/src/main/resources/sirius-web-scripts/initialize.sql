-- Sample empty UML project
INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '7ba7bda7-13b9-422a-838b-e45a3597e952',
  'UML Sample',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  '7ba7bda7-13b9-422a-838b-e45a3597e952',
  'uml'
);
INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  '503a1f9b-13f7-4394-94df-ddbf32840a31',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO project_semantic_data (
  id,
  project_id,
  semantic_data_id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '71c594d1-5908-4973-8c9a-6ac8fd235334',
  '7ba7bda7-13b9-422a-838b-e45a3597e952',
  '503a1f9b-13f7-4394-94df-ddbf32840a31',
  'main',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);

-- Sample empty SysML project
INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '4164c661-e0cb-4071-b25d-8516440bb8e8',
  'SysML Sample',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  '4164c661-e0cb-4071-b25d-8516440bb8e8',
  'sysml'
);
INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  '86fa5d90-a602-4083-b3c1-65912b93b673',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO project_semantic_data (
  id,
  project_id,
  semantic_data_id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '4ec2c683-f2bd-4db7-97d1-dd401656a57d',
  '4164c661-e0cb-4071-b25d-8516440bb8e8',
  '86fa5d90-a602-4083-b3c1-65912b93b673',
  'main',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO project_image (
  id,
  project_id,
  label,
  content_type,
  content,
  created_on,
  last_modified_on
) VALUES (
  'ff37f0eb-effb-4c57-b17f-76bc7ea64f5b',
  '4164c661-e0cb-4071-b25d-8516440bb8e8',
  'Placeholder',
  'image/svg+xml',
  '<svg version="1.1" xmlns="http://www.w3.org/2000/svg"><rect width="10px" height="10px" fill="black" /></svg>'::bytea,
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);


-- Sample Ecore project
INSERT INTO project (
  id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '99d336a2-3049-439a-8853-b104ffb22653',
  'Ecore Sample',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO nature (
  project_id,
  name
) VALUES (
  '99d336a2-3049-439a-8853-b104ffb22653',
  'ecore'
);
INSERT INTO semantic_data (
  id,
  created_on,
  last_modified_on
) VALUES (
  'cb133bf0-d7aa-4a83-a277-0972919dd46a',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO project_semantic_data (
  id,
  project_id,
  semantic_data_id,
  name,
  created_on,
  last_modified_on
) VALUES (
  '749e6ce0-dda4-4178-b80d-f828679681ea',
  '99d336a2-3049-439a-8853-b104ffb22653',
  'cb133bf0-d7aa-4a83-a277-0972919dd46a',
  'main',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO semantic_data_domain (
  semantic_data_id,
  uri
) VALUES (
  'cb133bf0-d7aa-4a83-a277-0972919dd46a',
  'http://www.eclipse.org/emf/2002/Ecore'
);
INSERT INTO document (
  id,
  semantic_data_id,
  name,
  content,
  created_on,
  last_modified_on
) VALUES (
  '48dc942a-6b76-4133-bca5-5b29ebee133d',
  'cb133bf0-d7aa-4a83-a277-0972919dd46a',
  'Ecore',
  '{
    "json":{
      "version":"1.0",
      "encoding":"utf-8"
    },
    "ns":{
      "ecore":"http://www.eclipse.org/emf/2002/Ecore"
    },
    "content":[
      {
        "id":"3237b215-ae23-48d7-861e-f542a4b9a4b8",
        "eClass":"ecore:EPackage",
        "data":{
          "name":"Sample",
          "eClassifiers":[
            {
              "id":"f0eecd16-d9da-4c98-a422-c73897bc48f5",
              "eClass":"ecore:EClass",
              "data":{
                "name":"SampleEClass"
              }
            }
          ]
        }
      }
    ]
  }',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO representation_metadata (
  id,
  semantic_data_id,
  target_object_id,
  description_id,
  label,
  documentation,
  kind,
  created_on,
  last_modified_on
) VALUES (
  'e81eec5c-42d6-491c-8bcc-9beb951356f8',
  'cb133bf0-d7aa-4a83-a277-0972919dd46a',
  '3237b215-ae23-48d7-861e-f542a4b9a4b8',
  '69030a1b-0b5f-3c1d-8399-8ca260e4a672',
  'EPackage Portal',
  'documentation',
  'siriusComponents://representation?type=Portal',
  '2024-01-02 9:42:0.000',
  '2024-01-02 9:42:0.000'
);
INSERT INTO representation_metadata_icon_url (
  representation_metadata_id,
  url,
  index
) VALUES (
  'e81eec5c-42d6-491c-8bcc-9beb951356f8',
  '/portal-images/portal.svg',
  0
);
INSERT INTO representation_content (
  id,
  content,
  created_on,
  last_modified_on,
  last_migration_performed,
  migration_version
) VALUES (
  'e81eec5c-42d6-491c-8bcc-9beb951356f8',
  '{
    "id":"e81eec5c-42d6-491c-8bcc-9beb951356f8",
    "kind":"siriusComponents://representation?type=Portal",
    "descriptionId":"69030a1b-0b5f-3c1d-8399-8ca260e4a672",
    "targetObjectId":"3237b215-ae23-48d7-861e-f542a4b9a4b8",
    "views":[
      {
        "id":"9e277e97-7f71-4bdd-99af-9eeb8bd7f2df",
        "representationId":"05e44ccc-9363-443f-a816-25fc73e3e7f7"
      }
    ],
    "layoutData":[
      {
        "portalViewId":"9e277e97-7f71-4bdd-99af-9eeb8bd7f2df",
        "x":0,
        "y":0,
        "width":500,
        "height":200
      }
    ]
  }',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000',
  'none',
  '0'
);
INSERT INTO representation_metadata (
  id,
  semantic_data_id,
  target_object_id,
  description_id,
  label,
  documentation,
  kind,
  created_on,
  last_modified_on
) VALUES (
  '05e44ccc-9363-443f-a816-25fc73e3e7f7',
  'cb133bf0-d7aa-4a83-a277-0972919dd46a',
  '3237b215-ae23-48d7-861e-f542a4b9a4b8',
  '69030a1b-0b5f-3c1d-8399-8ca260e4a672',
  'Portal',
  '',
  'siriusComponents://representation?type=Portal',
  '2024-01-01 9:42:0.000',
  '2024-01-03 9:42:0.000'
);
INSERT INTO representation_content (
  id,
  content,
  created_on,
  last_modified_on,
  last_migration_performed,
  migration_version
) VALUES (
  '05e44ccc-9363-443f-a816-25fc73e3e7f7',
  '{
    "id":"05e44ccc-9363-443f-a816-25fc73e3e7f7",
    "kind":"siriusComponents://representation?type=Portal",
    "descriptionId":"69030a1b-0b5f-3c1d-8399-8ca260e4a672",
    "targetObjectId":"3237b215-ae23-48d7-861e-f542a4b9a4b8",
    "views":[],
    "layoutData":[]
  }',
  '2024-01-01 9:42:0.000',
  '2024-01-02 9:42:0.000',
  'none',
  '0'
);
