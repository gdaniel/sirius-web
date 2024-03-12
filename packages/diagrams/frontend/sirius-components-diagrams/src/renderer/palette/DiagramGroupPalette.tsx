import { memo, useContext } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { GroupPalette } from './GroupPalette';

export const DiagramGroupPalette = memo(() => {
  console.log('Diagram Group Palette Rendered');
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);
  console.log('' + diagramId + editingContextId);

  return <GroupPalette editingContextId={editingContextId} diagramId={diagramId} />;
});
