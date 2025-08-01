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
import { Task, TaskOrEmpty } from '@ObeoNetwork/gantt-task-react';
import { ApolloError, OnDataOptions, useSubscription } from '@apollo/client';
import {
  RepresentationComponentProps,
  RepresentationLoadingIndicator,
  useMultiToast,
  useSelection,
} from '@eclipse-sirius/sirius-components-core';
import Typography from '@mui/material/Typography';
import { useState } from 'react';
import { flushSync } from 'react-dom';
import { makeStyles } from 'tss-react/mui';
import { useGanttMutations } from '../graphql/mutation/useGanttMutations';
import {
  GQLErrorPayload,
  GQLGanttEventPayload,
  GQLGanttEventSubscription,
  GQLGanttRefreshedEventPayload,
  GQLTaskDetail,
  SelectableTask,
} from '../graphql/subscription/GanttSubscription.types';
import { ganttEventSubscription } from '../graphql/subscription/ganttSubscription';
import { formatDate, getTaskFromGQLTask, updateTask } from '../helper/helper';
import { Gantt } from './Gantt';
import { GanttRepresentationState } from './GanttRepresentation.types';
const useGanttRepresentationStyles = makeStyles()((theme) => ({
  page: {
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
    overflowY: 'scroll',
  },
  complete: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  toolbar: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'flex-end',
    alignItems: 'center',
    maxHeight: theme.spacing(4),
    textTransform: 'none',
  },
  toolbarAction: {
    paddingRight: theme.spacing(1),
    whiteSpace: 'nowrap',
  },
}));

const isGanttRefreshedEventPayload = (payload: GQLGanttEventPayload): payload is GQLGanttRefreshedEventPayload =>
  payload.__typename === 'GanttRefreshedEventPayload';
const isErrorPayload = (payload: GQLGanttEventPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

/**
 * Connect the Gantt component to the GraphQL API.
 */
export const GanttRepresentation = ({ editingContextId, representationId }: RepresentationComponentProps) => {
  const { classes } = useGanttRepresentationStyles();
  const { addErrorMessage, addMessages } = useMultiToast();
  const { setSelection } = useSelection();

  const [{ id, gantt, complete }, setState] = useState<GanttRepresentationState>({
    id: crypto.randomUUID(),
    gantt: null,
    complete: false,
  });

  const onError = ({ message }: ApolloError) => {
    addErrorMessage(message);
  };

  const onData = ({ data }: OnDataOptions<GQLGanttEventSubscription>) => {
    flushSync(() => {
      if (data.data) {
        const { ganttEvent } = data.data;
        if (isGanttRefreshedEventPayload(ganttEvent)) {
          setState((previousState) => {
            return { ...previousState, gantt: ganttEvent.gantt };
          });
        } else if (isErrorPayload(ganttEvent)) {
          addMessages(ganttEvent.messages);
        }
      }
    });
  };

  useSubscription<GQLGanttEventSubscription>(ganttEventSubscription, {
    variables: {
      input: {
        id,
        editingContextId,
        ganttId: representationId,
      },
    },
    fetchPolicy: 'no-cache',
    onData,
    onError,
    onComplete: () => {
      setState((previousState) => {
        return { ...previousState, complete: true, gantt: null };
      });
    },
  });

  //---------------------------------
  // Mutations
  const {
    deleteTask,
    editTask,
    createTask,
    dropTask,
    createTaskDependency,
    deleteTaskDependency,
    changeTaskCollapseState,
    changeColumn,
  } = useGanttMutations(editingContextId, representationId);

  const handleEditTask = (task: TaskOrEmpty) => {
    const newDetail: GQLTaskDetail = {
      name: task.name,
      description: task.description ?? '',
      startTime: formatDate((task as Task)?.start, (task as SelectableTask)?.temporalType, false),
      endTime: formatDate((task as Task)?.end, (task as SelectableTask)?.temporalType, true),
      progress: (task as Task)?.progress,
      computeStartEndDynamically: task.isDisabled,
      temporalType: (task as SelectableTask)?.temporalType,
    };

    // to avoid blink because useMutation implies a re-render as the task value is the old one
    updateTask(gantt, task.id, { ...newDetail, collapsed: (task as Task)?.hideChildren });
    editTask(task.id, newDetail);
  };

  const onExpandCollapse = () => {};

  let content: JSX.Element | null = null;
  if (complete) {
    content = (
      <div className={classes.complete}>
        <Typography variant="h5" align="center">
          The Gantt does not exist anymore
        </Typography>
      </div>
    );
  } else if (!gantt) {
    return <RepresentationLoadingIndicator />;
  } else {
    const tasks = getTaskFromGQLTask(gantt.tasks, '');
    content = (
      <Gantt
        representationId={representationId}
        tasks={tasks}
        gqlColumns={gantt.columns}
        gqlDateRounding={gantt.dateRounding}
        setSelection={setSelection}
        onCreateTask={createTask}
        onEditTask={handleEditTask}
        onDeleteTask={deleteTask}
        onExpandCollapse={onExpandCollapse}
        onDropTask={dropTask}
        onCreateTaskDependency={createTaskDependency}
        onChangeTaskCollapseState={changeTaskCollapseState}
        onChangeColumn={changeColumn}
        onDeleteTaskDependency={deleteTaskDependency}
      />
    );
  }
  return <>{content}</>;
};
