import { GQLMessage } from '../Tool.types';
import { GQLPalette, GQLRepresentationDescription, GQLTool, GQLWorkbenchSelection } from './Palette.types';

export interface GroupPaletteProps {
  editingContextId: string;
  diagramId: string;
}

export interface GQLSingleClickOnGroupTool extends GQLTool {
  appliesToDiagramRoot: boolean;
  selectionDescriptionId: string;
}

export interface GQLInvokeSingleClickOnGroupToolVariables {
  input: GQLInvokeSingleClickOnGroupToolInput;
}

export interface GQLInvokeSingleClickOnGroupToolInput {
  id: string;
  editingContextId: string;
  representationId: string;
  diagramElementIds: string[];
  toolId: string;
  selectedObjectIds: string[];
}

export interface GQLInvokeSingleClickOnGroupToolData {
  invokeSingleClickOnGroupTool: GQLInvokeSingleClickOnGroupToolPayload;
}

export interface GQLInvokeSingleClickOnGroupToolPayload {
  __typename: string;
}

export interface GQLInvokeSingleClickOnGroupToolSuccessPayload extends GQLInvokeSingleClickOnGroupToolPayload {
  id: string;
  newSelection: GQLWorkbenchSelection;
  messages: GQLMessage[];
}

export interface GQLGetGroupToolSectionsVariables {
  editingContextId: string;
  diagramId: string;
  diagramElementIds: string[];
}

export interface GQLDiagramDescription extends GQLRepresentationDescription {
  groupPalette: GQLPalette;
}
