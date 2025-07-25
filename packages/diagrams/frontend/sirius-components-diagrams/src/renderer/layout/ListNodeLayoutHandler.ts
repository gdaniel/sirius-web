/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { Node } from '@xyflow/react';
import { NodeData } from '../DiagramRenderer.types';
import { ListNodeData } from '../node/ListNode.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';
import { computePreviousSize } from './bounds';
import { ForcedDimensions, RawDiagram } from './layout.types';
import { getBorderNodeExtent } from './layoutBorderNodes';
import {
  applyRatioOnNewNodeSizeValue,
  computeNodesBox,
  findNodeIndex,
  getDefaultOrMinHeight,
  getDefaultOrMinWidth,
  getEastBorderNodeFootprintHeight,
  getInsideLabelWidthConstraint,
  getHeaderHeightFootprint,
  getNorthBorderNodeFootprintWidth,
  getSouthBorderNodeFootprintWidth,
  getWestBorderNodeFootprintHeight,
  setBorderNodesPosition,
} from './layoutNode';

export class ListNodeLayoutHandler implements INodeLayoutHandler<ListNodeData> {
  public canHandle(node: Node<NodeData, DiagramNodeType>) {
    return node.type === 'listNode';
  }

  public handle(
    layoutEngine: ILayoutEngine,
    previousDiagram: RawDiagram | null,
    node: Node<ListNodeData, 'listNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    directChildren: Node<NodeData, DiagramNodeType>[],
    newlyAddedNode: Node<NodeData, DiagramNodeType> | undefined,
    forceDimensions?: ForcedDimensions
  ) {
    const nodeIndex = findNodeIndex(visibleNodes, node.id);
    const nodeElement = document.getElementById(`${node.id}-listNode-${nodeIndex}`);
    const nodeElementChild =
      nodeElement?.children &&
      Array.from(nodeElement.children).filter((child) => !child.classList.contains('react-flow__resize-control'))[0];
    const borderWidth = nodeElementChild ? parseFloat(window.getComputedStyle(nodeElementChild).borderLeftWidth) : 0;

    if (directChildren.length > 0) {
      this.handleParentNode(
        layoutEngine,
        previousDiagram,
        node,
        visibleNodes,
        directChildren,
        newlyAddedNode,
        borderWidth,
        forceDimensions
      );
    } else {
      this.handleLeafNode(previousDiagram, node, visibleNodes, borderWidth, forceDimensions);
    }
  }

  handleLeafNode(
    previousDiagram: RawDiagram | null,
    node: Node<ListNodeData, 'listNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    borderWidth: number,
    forceDimensions?: ForcedDimensions
  ) {
    const labelElement = document.getElementById(`${node.id}-label-${findNodeIndex(visibleNodes, node.id)}`);

    const nodeMinComputeWidth = getInsideLabelWidthConstraint(node.data.insideLabel, labelElement) + borderWidth * 2;
    const nodeMinComputeHeight = (labelElement?.getBoundingClientRect().height ?? 0) + borderWidth * 2;
    const nodeWith = forceDimensions?.width ?? getDefaultOrMinWidth(nodeMinComputeWidth, node);
    const nodeHeight = forceDimensions?.height ?? getDefaultOrMinHeight(nodeMinComputeHeight, node);

    const previousNode = (previousDiagram?.nodes ?? []).find((previouseNode) => previouseNode.id === node.id);
    const previousDimensions = computePreviousSize(previousNode, node);

    const heightLostSincePrevDiagram: number =
      previousDiagram?.nodes
        .filter((prevNode) => prevNode.parentId === node.id && !prevNode.hidden)
        .reduce<number>((height, node) => height + (node.height ?? 0), 0) ?? 0;

    if (node.data.resizedByUser) {
      if (nodeMinComputeWidth > previousDimensions.width) {
        node.width = nodeMinComputeWidth;
      } else {
        node.width = previousDimensions.width;
      }
      if (nodeMinComputeHeight > previousDimensions.height) {
        node.height = nodeMinComputeHeight;
      } else {
        node.height = getDefaultOrMinHeight(previousDimensions.height - heightLostSincePrevDiagram, node);
      }
    } else {
      node.width = nodeWith;
      node.height = nodeHeight;
    }
  }

  private handleParentNode(
    layoutEngine: ILayoutEngine,
    previousDiagram: RawDiagram | null,
    node: Node<ListNodeData, 'listNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    directChildren: Node<NodeData, DiagramNodeType>[],
    newlyAddedNode: Node<NodeData, DiagramNodeType> | undefined,
    borderWidth: number,
    forceDimensions?: ForcedDimensions
  ) {
    layoutEngine.layoutNodes(previousDiagram, visibleNodes, directChildren, newlyAddedNode, forceDimensions);

    const nodeIndex = findNodeIndex(visibleNodes, node.id);
    const labelElement = document.getElementById(`${node.id}-label-${nodeIndex}`);
    const headerHeightFootprint = getHeaderHeightFootprint(labelElement, node.data.insideLabel, 'TOP', borderWidth);

    const borderNodes = directChildren.filter((node) => node.data.isBorderNode);
    const directNodesChildren = directChildren.filter((child) => !child.data.isBorderNode);
    const northBorderNodeFootprintWidth = getNorthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);
    const southBorderNodeFootprintWidth = getSouthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);

    const previousNode: Node<NodeData, string> | undefined = (previousDiagram?.nodes ?? []).find(
      (previouseNode) => previouseNode.id === node.id
    );

    const heightLostSincePrevDiagram: number =
      previousDiagram?.nodes
        .filter((prevNode) => prevNode.parentId === node.id && !prevNode.hidden)
        .filter((prevNode) => !directChildren.map((child) => child.id).includes(prevNode.id))
        .reduce<number>((height, node) => height + (node.height ?? 0), 0) ?? 0;

    if (!forceDimensions) {
      let previousChildrenContentBoxWidthToConsider: number = getDefaultOrMinWidth(0, node) - borderWidth * 2;
      let previousChildrenContentBoxHeightToConsider: number = getDefaultOrMinHeight(0, node) - borderWidth * 2;
      if (node.data.resizedByUser) {
        previousChildrenContentBoxWidthToConsider = (previousNode?.width ?? node.width ?? 0) - borderWidth * 2;
        previousChildrenContentBoxHeightToConsider =
          (previousNode?.height ?? node.height ?? 0) - borderWidth * 2 - headerHeightFootprint;
      }
      const fixedWidth: number = Math.max(
        directNodesChildren.reduce<number>(
          (widerWidth, child) => Math.max(child.width ?? 0, widerWidth),
          getInsideLabelWidthConstraint(node.data.insideLabel, labelElement)
        ),
        northBorderNodeFootprintWidth,
        southBorderNodeFootprintWidth,
        previousChildrenContentBoxWidthToConsider
      );
      const nonGrowableChilds = directNodesChildren.filter(
        (child) => !node.data.growableNodeIds.includes(child.data.descriptionId) || child.data.resizedByUser
      );
      nonGrowableChilds.forEach((nonGrowableChild) => {
        layoutEngine.layoutNodes(previousDiagram, visibleNodes, [nonGrowableChild], newlyAddedNode, {
          width: fixedWidth,
          height: null,
        });
      });
      previousChildrenContentBoxHeightToConsider -= nonGrowableChilds.reduce<number>(
        (height, node) => height + (node.height ?? 0),
        0
      );
      previousChildrenContentBoxHeightToConsider -= node.data.topGap + node.data.bottomGap;

      const growableChilds = directNodesChildren.filter(
        (child) => node.data.growableNodeIds.includes(child.data.descriptionId) && !child.data.resizedByUser
      );
      const childHeight: number = previousChildrenContentBoxHeightToConsider / growableChilds.length;
      growableChilds.forEach((growableChild) => {
        layoutEngine.layoutNodes(previousDiagram, visibleNodes, [growableChild], newlyAddedNode, {
          width: fixedWidth,
          height: Math.max(growableChild.height ?? 0, childHeight),
        });
      });
    }

    directNodesChildren.forEach((child, index) => {
      child.position = {
        x: borderWidth,
        y: headerHeightFootprint + node.data.topGap,
      };
      const previousSibling = directNodesChildren[index - 1];
      if (previousSibling) {
        child.position = { ...child.position, y: previousSibling.position.y + (previousSibling.height ?? 0) };
      }
    });

    const childrenContentBox = computeNodesBox(visibleNodes, directNodesChildren);

    const labelOnlyWidth = getInsideLabelWidthConstraint(node.data.insideLabel, labelElement);
    const nodeMinComputeWidth = Math.max(childrenContentBox.width, labelOnlyWidth) + borderWidth * 2;

    const directChildrenAwareNodeHeight =
      childrenContentBox.y + childrenContentBox.height + borderWidth + node.data.bottomGap;

    const eastBorderNodeFootprintHeight = getEastBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);
    const westBorderNodeFootprintHeight = getWestBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);

    const nodeMinComputeHeight = Math.max(
      directChildrenAwareNodeHeight,
      eastBorderNodeFootprintHeight,
      westBorderNodeFootprintHeight
    );

    const nodeWidth = forceDimensions?.width ?? getDefaultOrMinWidth(nodeMinComputeWidth, node);
    const nodeHeight = forceDimensions?.height ?? getDefaultOrMinHeight(nodeMinComputeHeight, node);

    const previousDimensions = computePreviousSize(previousNode, node);
    if (node.data.resizedByUser) {
      if (nodeMinComputeWidth > previousDimensions.width) {
        node.width = nodeMinComputeWidth;
      } else {
        node.width = previousDimensions.width;
      }
      if (nodeMinComputeHeight > previousDimensions.height) {
        node.height = nodeMinComputeHeight;
      } else {
        node.height = getDefaultOrMinHeight(previousDimensions.height - heightLostSincePrevDiagram, node);
      }
    } else {
      node.width = nodeWidth;
      node.height = nodeHeight;
    }

    if (node.data.nodeDescription?.keepAspectRatio) {
      applyRatioOnNewNodeSizeValue(node);
    }
    // Update border nodes positions
    borderNodes.forEach((borderNode) => {
      borderNode.extent = getBorderNodeExtent(node, borderNode);
    });
    setBorderNodesPosition(borderNodes, node, previousDiagram);
  }
}
