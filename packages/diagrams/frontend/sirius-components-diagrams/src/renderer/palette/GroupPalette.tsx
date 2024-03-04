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

import { gql, useMutation, useQuery } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { Paper } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import { memo, useCallback, useEffect, useState } from 'react';
import { useOnSelectionChange } from 'reactflow';
import { Tool } from '../Tool';
import {
  GQLDiagramDescription,
  GQLGetGroupToolSectionsVariables,
  GQLInvokeSingleClickOnGroupToolData,
  GQLInvokeSingleClickOnGroupToolInput,
  GQLInvokeSingleClickOnGroupToolPayload,
  GQLInvokeSingleClickOnGroupToolSuccessPayload,
  GQLInvokeSingleClickOnGroupToolVariables,
  GQLSingleClickOnGroupTool,
  GroupPaletteProps,
} from './GroupPalette.types';
import {
  ContextualPaletteStyleProps,
  GQLErrorPayload,
  GQLGetToolSectionsData,
  GQLPalette,
  GQLRepresentationDescription,
  GQLTool,
} from './Palette.types';
import { PalettePortal } from './PalettePortal';

const usePaletteStyle = makeStyles((theme) => ({
  palette: {
    border: `1px solid ${theme.palette.divider}`,
    borderRadius: '2px',
    zIndex: 2,
    position: 'fixed',
    display: 'flex',
    alignItems: 'center',
  },
  paletteContent: {
    display: 'grid',
    gridTemplateColumns: ({ toolCount }: ContextualPaletteStyleProps) => `repeat(${Math.min(toolCount, 10)}, 36px)`,
    gridTemplateRows: '28px',
    gridAutoRows: '28px',
    placeItems: 'center',
  },
  toolIcon: {
    color: theme.palette.text.primary,
  },
}));

const ToolFields = gql`
  fragment ToolFields on Tool {
    __typename
    id
    label
    iconURL
    ... on SingleClickOnDiagramElementTool {
      targetDescriptions {
        id
      }
      appliesToDiagramRoot
      selectionDescriptionId
    }
  }
`;

const invokeSingleClickOnGroupToolMutation = gql`
  mutation invokeSingleClickOnGroupTool($input: InvokeSingleClickOnGroupToolInput!) {
    invokeSingleClickOnGroupTool(input: $input) {
      __typename
      ... on InvokeSingleClickOnGroupToolSuccessPayload {
        newSelection {
          entries {
            id
            label
            kind
          }
        }
        messages {
          body
          level
        }
      }
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const getGroupPaletteQuery = gql`
  ${ToolFields}
  query getGroupPalette($editingContextId: ID!, $diagramId: ID!, $diagramElementIds: [ID!]!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $diagramId) {
          description {
            ... on DiagramDescription {
              groupPalette(diagramElementIds: $diagramElementIds) {
                id
                tools {
                  ...ToolFields
                }
                toolSections {
                  id
                  label
                  iconURL
                  tools {
                    ...ToolFields
                  }
                }
              }
            }
          }
        }
      }
    }
  }
`;

const isSingleClickOnGroupTool = (tool: GQLTool): tool is GQLSingleClickOnGroupTool =>
  tool.__typename === 'SingleClickOnGroupTool';

const isDiagramDescription = (
  representationDescription: GQLRepresentationDescription
): representationDescription is GQLDiagramDescription => representationDescription.__typename === 'DiagramDescription';

const isInvokeSingleClickSuccessPayload = (
  payload: GQLInvokeSingleClickOnGroupToolPayload
): payload is GQLInvokeSingleClickOnGroupToolSuccessPayload =>
  payload.__typename === 'InvokeSingleClickOnGroupToolSuccessPayload';

const isErrorPayload = (payload: GQLInvokeSingleClickOnGroupToolPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const GroupPalette = memo(({ editingContextId, diagramId }: GroupPaletteProps) => {
  // const [multiSelection, setMultiSelection] = useState<boolean>(false);
  const [selectedElementIds, setSelectedElementIds] = useState<string[]>([]);
  const { addErrorMessage, addMessages } = useMultiToast();
  useOnSelectionChange({
    onChange: ({ nodes }) => {
      console.log('Selection changed');
      console.log(nodes);
      if (nodes.filter((node) => node.selected).length > 1) {
        setSelectedElementIds(nodes.filter((node) => node.selected).map((node) => node.id));
      } else {
        setSelectedElementIds([]);
      }
    },
  });

  console.log('selected element length ' + selectedElementIds.length);
  // [FRO] Can't put that inside the if(selectedElementIds.length > 1), why?
  // I either get an error (more hooks rendered than during the previous render), or nothing happens
  const { data: paletteData, error: paletteError } = useQuery<GQLGetToolSectionsData, GQLGetGroupToolSectionsVariables>(
    getGroupPaletteQuery,
    {
      variables: {
        editingContextId,
        diagramId,
        diagramElementIds: selectedElementIds,
      },
      skip: selectedElementIds.length <= 1,
    }
  );

  const description: GQLRepresentationDescription | undefined =
    paletteData?.viewer.editingContext.representation.description;
  const groupPalette: GQLPalette | null =
    description && isDiagramDescription(description) ? description.groupPalette : null;

  const toolCount = groupPalette
    ? groupPalette.tools.filter(isSingleClickOnGroupTool).length /*+
      groupPalette.toolSections.filter(
        (toolSection) => toolSection.tools.filter(isSingleClickOnDiagramElementTool).length > 0
      ).length*/
    : 0;

  const classes = usePaletteStyle({ toolCount });

  // TODO proper location for the palette
  const paletteX = 0;
  const paletteY = 0;

  // let x: number = 0;
  // let y: number = 0;
  // const { x: viewportX, y: viewportY, zoom: viewportZoom } = useViewport();
  // if (viewportZoom !== 0 && paletteX && paletteY) {
  //   x = (paletteX - viewportX) / viewportZoom;
  //   y = (paletteY - viewportY) / viewportZoom;
  // }

  useEffect(() => {
    if (paletteError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
  }, [paletteError]);

  const [invokeSingleClickOnGroupTool] = useMutation<
    GQLInvokeSingleClickOnGroupToolData,
    GQLInvokeSingleClickOnGroupToolVariables
  >(invokeSingleClickOnGroupToolMutation);

  const handleGroupToolClick = useCallback(
    async (tool: GQLTool) => {
      if (isSingleClickOnGroupTool(tool)) {
        const { id: toolId } = tool;
        const input: GQLInvokeSingleClickOnGroupToolInput = {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: diagramId,
          diagramElementIds: selectedElementIds,
          toolId,
          selectedObjectIds: [],
        };

        const { data } = await invokeSingleClickOnGroupTool({
          variables: { input },
        });
        if (data) {
          const { invokeSingleClickOnGroupTool } = data;
          if (isInvokeSingleClickSuccessPayload(invokeSingleClickOnGroupTool)) {
            addMessages(invokeSingleClickOnGroupTool.messages);
          }
          if (isErrorPayload(invokeSingleClickOnGroupTool)) {
            addMessages(invokeSingleClickOnGroupTool.messages);
          }
        }
      }
    },
    [editingContextId, diagramId, selectedElementIds, invokeSingleClickOnGroupToolMutation, isSingleClickOnGroupTool]
  );

  const shouldRender = groupPalette && selectedElementIds.length > 1;
  if (!shouldRender) {
    console.log("shouldn't render");
    return null;
  }
  console.log('should render');

  return (
    <PalettePortal>
      <Paper
        className={classes.palette}
        style={{ position: 'absolute', left: paletteX, top: paletteY }}
        data-testid="GroupPalette">
        <div className={classes.paletteContent}>
          {groupPalette?.tools.filter(isSingleClickOnGroupTool).map((tool) => (
            <Tool tool={tool} onClick={handleGroupToolClick} thumbnail key={tool.id} />
          ))}
        </div>
      </Paper>
    </PalettePortal>
  );
});
