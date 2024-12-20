/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import { gql, useLazyQuery } from '@apollo/client';
import {
  GQLGetExpandAllTreePathData,
  GQLGetExpandAllTreePathVariables,
  GQLTreeItem,
  TreeItemContextMenuComponentProps,
} from '@eclipse-sirius/sirius-components-trees';
import UnfoldMore from '@mui/icons-material/UnfoldMore';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { Fragment, forwardRef, useEffect } from 'react';
//import { TreeViewState } from '@eclipse-sirius/sirius-components-trees';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';

const getExpandAllTreePathQuery = gql`
  query getExpandAllTreePath($editingContextId: ID!, $treeId: ID!, $treeItemId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        expandAllTreePath(treeId: $treeId, treeItemId: $treeItemId) {
          treeItemIdsToExpand
          maxDepth
        }
      }
    }
  }
`;

export const ExpandAllTreeItemContextMenuContribution = forwardRef(
  (
    { editingContextId, treeId, item, readOnly, onExpandedElementChange, onClose }: TreeItemContextMenuComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    // if (!treeId.startsWith('explorer://') || !item.hasChildren) {
    //   return null;
    // }

    // const [state, setState] = useState<TreeViewState>({
    //   expanded: [],
    //   maxDepth: 1,
    // });

    // console.log('state ??');
    // console.log(state.expanded);

    const [getExpandAllTreePath, { loading, data, error }] = useLazyQuery<
      GQLGetExpandAllTreePathData,
      GQLGetExpandAllTreePathVariables
    >(getExpandAllTreePathQuery);

    useEffect(() => {
      console.log('???');
      if (!loading) {
        console.log('!!!');
        if (data) {
          console.log('data!');
          const { treeItemIdsToExpand, maxDepth } = data.viewer.editingContext.expandAllTreePath;
          const expanded = ['b0492687-0131-4038-a88a-a1d7088d140f', 'af9fe027-9c52-47c3-92e5-b91ab0554a55'];

          const newExpanded: string[] = [...expanded];
          treeItemIdsToExpand.forEach((itemToExpand) => {
            if (!expanded.includes(itemToExpand)) {
              newExpanded.push(itemToExpand);
            }
          });

          onExpandedElementChange(newExpanded, maxDepth);
          onClose();
          // const { expanded, maxDepth } = state;
          // if (expandAllTreePathData.viewer?.editingContext?.expandAllTreePath) {
          //   const { treeItemIdsToExpand, maxDepth: expandedMaxDepth } =
          //     expandAllTreePathData.viewer.editingContext.expandAllTreePath;
          //   const newExpanded: string[] = [...expanded];

          //   treeItemIdsToExpand?.forEach((itemToExpand) => {
          //     if (!expanded.includes(itemToExpand)) {
          //       newExpanded.push(itemToExpand);
          //     }
          //   });
          //   console.log('test');
          //   console.log(newExpanded);
          //   setState((prevState) => ({
          //     ...prevState,
          //     expanded: newExpanded,
          //     maxDepth: Math.max(expandedMaxDepth, maxDepth),
          //   }));
          // }
        }
      }
    }, [loading, data]);

    const { addErrorMessage } = useMultiToast();
    useEffect(() => {
      console.log('pouet');
      if (error) {
        addErrorMessage(error.message);
      }
    }, [error]);

    const onExpandAll = (treeItem: GQLTreeItem) => {
      const variables: GQLGetExpandAllTreePathVariables = {
        editingContextId,
        treeId,
        treeItemId: treeItem.id,
      };
      console.log('onExpandAll');
      console.log(variables);
      getExpandAllTreePath({ variables });
      console.log('done expand all');
    };

    return (
      <Fragment key="expand-all-tree-item-context-menu-contribution">
        <MenuItem
          key="expand-all"
          data-testid="expand-all"
          onClick={() => {
            onExpandAll(item);
            // onClose();
          }}
          disabled={readOnly}
          ref={ref}
          aria-disabled>
          <ListItemIcon>
            <UnfoldMore fontSize="small" />
          </ListItemIcon>
          <ListItemText primary="Expand all test" />
        </MenuItem>
      </Fragment>
    );
  }
);
