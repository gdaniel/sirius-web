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
package org.eclipse.sirius.components.collaborative.gantt.service;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.gantt.api.IGanttContext;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttTaskService;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.ChangeGanttColumnInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.ChangeTaskCollapseStateInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.CreateGanttTaskDependencyInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.CreateGanttTaskInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.DeleteGanttTaskDependencyInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.DeleteGanttTaskInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.DropGanttTaskInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.EditGanttTaskInput;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.Task;
import org.eclipse.sirius.components.gantt.TemporalType;
import org.eclipse.sirius.components.gantt.description.GanttDescription;
import org.eclipse.sirius.components.gantt.renderer.events.ChangeGanttColumnEvent;
import org.eclipse.sirius.components.gantt.renderer.events.ChangeGanttTaskCollapseStateEvent;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Service used to manage tasks.
 *
 * @author lfasani
 */
@Service
public class GanttTaskService implements IGanttTaskService {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IFeedbackMessageService feedbackMessageService;

    private final IObjectService objectService;

    public GanttTaskService(IRepresentationDescriptionSearchService representationDescriptionSearchService, IFeedbackMessageService feedbackMessageService, IObjectService objectService) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public IPayload createTask(CreateGanttTaskInput createGanttTaskInput, IEditingContext editingContext, Gantt gantt) {
        IPayload payload = new ErrorPayload(createGanttTaskInput.id(), "Create task failed");

        Optional<GanttDescription> ganttDescriptionOpt = this.findGanttDescription(gantt.descriptionId(), editingContext);

        if (ganttDescriptionOpt.isPresent()) {
            VariableManager variableManager = new VariableManager();
            Optional<Object> targetObjectOpt = Optional.empty();
            String currentTaskId = createGanttTaskInput.currentTaskId();
            if (currentTaskId != null) {
                targetObjectOpt = this.getTaskSemanticObject(currentTaskId, gantt, editingContext);
            } else {
                targetObjectOpt = this.objectService.getObject(editingContext, gantt.targetObjectId());
            }
            if (targetObjectOpt.isPresent()) {
                variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
                variableManager.put(VariableManager.SELF, targetObjectOpt.get());
                ganttDescriptionOpt.get().createTaskProvider().accept(variableManager);
            }

            payload = this.getPayload(createGanttTaskInput.id());
        }

        return payload;
    }

    @Override
    public IPayload deleteTask(DeleteGanttTaskInput deleteGanttTaskInput, IEditingContext editingContext, Gantt gantt) {
        IPayload payload = new ErrorPayload(deleteGanttTaskInput.id(), "Delete task failed");

        Optional<Task> taskOpt = this.findTask(task -> Objects.equals(task.id(), deleteGanttTaskInput.taskId()), gantt.tasks());
        Optional<GanttDescription> ganttDescriptionOpt = this.findGanttDescription(gantt.descriptionId(), editingContext);

        if (taskOpt.isPresent() && ganttDescriptionOpt.isPresent()) {
            Optional<Object> targetObjectOpt = this.objectService.getObject(editingContext, taskOpt.get().targetObjectId());
            if (targetObjectOpt.isPresent()) {
                VariableManager variableManager = new VariableManager();
                variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
                variableManager.put(VariableManager.SELF, targetObjectOpt.get());
                ganttDescriptionOpt.get().deleteTaskProvider().accept(variableManager);

                payload = this.getPayload(deleteGanttTaskInput.id());
            }
        }
        return payload;
    }

    @Override
    public IPayload editTask(EditGanttTaskInput editGanttTaskInput, IEditingContext editingContext, Gantt gantt) {
        IPayload payload = new ErrorPayload(editGanttTaskInput.id(), "Edit task failed");

        Optional<Task> taskOpt = this.findTask(task -> Objects.equals(task.id(), editGanttTaskInput.taskId()), gantt.tasks());
        Optional<GanttDescription> ganttDescriptionOpt = this.findGanttDescription(gantt.descriptionId(), editingContext);

        if (taskOpt.isPresent() && ganttDescriptionOpt.isPresent()) {
            Optional<Object> targetObjectOpt = this.objectService.getObject(editingContext, taskOpt.get().targetObjectId());
            if (targetObjectOpt.isPresent()) {
                VariableManager variableManager = new VariableManager();
                variableManager.put(VariableManager.SELF, targetObjectOpt.get());
                variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
                variableManager.put(GanttDescription.NEW_NAME, editGanttTaskInput.newDetail().name());
                variableManager.put(GanttDescription.NEW_DESCRIPTION, editGanttTaskInput.newDetail().description());
                variableManager.put(GanttDescription.NEW_START_TIME, getTemporal(editGanttTaskInput.newDetail().startTime(), editGanttTaskInput.newDetail().temporalType()));
                variableManager.put(GanttDescription.NEW_END_TIME, getTemporal(editGanttTaskInput.newDetail().endTime(), editGanttTaskInput.newDetail().temporalType()));
                variableManager.put(GanttDescription.NEW_PROGRESS, editGanttTaskInput.newDetail().progress());
                ganttDescriptionOpt.get().editTaskProvider().accept(variableManager);

                payload = this.getPayload(editGanttTaskInput.id());
            }
        }

        return payload;
    }

    private Temporal getTemporal(String temporalString, TemporalType temporalType) {
        Temporal temporal = null;
        if (temporalString != null) {
            if (TemporalType.DATE.equals(temporalType)) {
                temporal = LocalDate.parse(temporalString);
            } else if (TemporalType.DATE_TIME.equals(temporalType)) {
                temporal = Instant.parse(temporalString);
            }
        }

        return temporal;
    }

    private IPayload getPayload(UUID payloadId) {
        IPayload payload = null;
        List<Message> feedbackMessages = this.feedbackMessageService.getFeedbackMessages();
        Optional<Message> errorMsgOpt = feedbackMessages.stream().filter(msg -> MessageLevel.ERROR.equals(msg.level())).findFirst();
        if (errorMsgOpt.isPresent()) {
            payload = new ErrorPayload(payloadId, errorMsgOpt.get().body(), feedbackMessages);
        } else {
            payload = new SuccessPayload(payloadId, feedbackMessages);
        }
        return payload;
    }

    private Optional<Task> findTask(Predicate<Task> condition, List<Task> candidates) {
        Optional<Task> result = Optional.empty();
        for (Task task : candidates) {
            if (condition.test(task)) {
                result = Optional.of(task);
            } else {
                result = this.findTask(condition, task.subTasks());
            }
            if (result.isPresent()) {
                break;
            }
        }
        return result;
    }

    private Optional<GanttDescription> findGanttDescription(String ganttDescriptionId, IEditingContext editingContext) {
        return this.representationDescriptionSearchService.findById(editingContext, ganttDescriptionId).filter(GanttDescription.class::isInstance).map(GanttDescription.class::cast);
    }

    @Override
    public IPayload dropTask(DropGanttTaskInput dropTaskInput, IEditingContext editingContext, Gantt gantt) {
        IPayload payload = new ErrorPayload(dropTaskInput.id(), "Drop task failed");

        Optional<Task> droppedTaskOpt = this.findTask(task -> Objects.equals(task.id(), dropTaskInput.droppedTaskId()), gantt.tasks());
        Optional<Task> targetTaskOpt = this.findTask(task -> Objects.equals(task.id(), dropTaskInput.targetTaskId()), gantt.tasks());
        Optional<GanttDescription> ganttDescriptionOpt = this.findGanttDescription(gantt.descriptionId(), editingContext);

        if (droppedTaskOpt.isPresent() && ganttDescriptionOpt.isPresent()) {
            String targetObjectId = targetTaskOpt.map(Task::targetObjectId).orElseGet(gantt::targetObjectId);
            Optional<Object> draggedObjectOpt = this.objectService.getObject(editingContext, droppedTaskOpt.get().targetObjectId());
            Optional<Object> targetObjectOpt = this.objectService.getObject(editingContext, targetObjectId);
            if (draggedObjectOpt.isPresent() && targetObjectOpt.isPresent()) {
                VariableManager variableManager = new VariableManager();
                variableManager.put(VariableManager.SELF, draggedObjectOpt.get());
                variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
                variableManager.put(GanttDescription.SOURCE_OBJECT, draggedObjectOpt.get());
                variableManager.put(GanttDescription.TARGET_OBJECT, targetObjectOpt.get());
                variableManager.put(GanttDescription.SOURCE_TASK, droppedTaskOpt.get());
                variableManager.put(GanttDescription.TARGET_TASK_OR_GANTT, targetObjectOpt.get());
                variableManager.put(GanttDescription.TARGET_DROP_INDEX, dropTaskInput.dropIndex());
                ganttDescriptionOpt.get().dropTaskProvider().accept(variableManager);

                payload = this.getPayload(dropTaskInput.id());
            }
        }
        return payload;
    }

    @Override
    public IPayload createTaskDependency(CreateGanttTaskDependencyInput createTaskDependencyInput, IEditingContext editingContext, Gantt gantt) {
        IPayload payload = new ErrorPayload(createTaskDependencyInput.id(), "Create task dependency failed");

        Optional<GanttDescription> ganttDescriptionOpt = this.findGanttDescription(gantt.descriptionId(), editingContext);

        if (ganttDescriptionOpt.isPresent()) {
            VariableManager variableManager = new VariableManager();
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);

            Optional<Object> sourceObjectOpt = Optional.of(createTaskDependencyInput.sourceTaskId())
                    .flatMap(taskId -> this.getTaskSemanticObject(taskId, gantt, editingContext));

            Optional<Object> targetObjectOpt = Optional.of(createTaskDependencyInput.targetTaskId())
                    .flatMap(taskId -> this.getTaskSemanticObject(taskId, gantt, editingContext));

            if (sourceObjectOpt.isPresent() && targetObjectOpt.isPresent()) {
                variableManager.put(GanttDescription.SOURCE_OBJECT, sourceObjectOpt.get());
                variableManager.put(GanttDescription.TARGET_OBJECT, targetObjectOpt.get());
                ganttDescriptionOpt.get().createTaskDependencyProvider().accept(variableManager);
            }

            payload = this.getPayload(createTaskDependencyInput.id());
        }

        return payload;
    }

    @Override
    public IPayload deleteTaskDependency(DeleteGanttTaskDependencyInput deleteTaskDependencyInput, IEditingContext editingContext, Gantt gantt) {
        IPayload payload = new ErrorPayload(deleteTaskDependencyInput.id(), "Delete task dependency failed");

        Optional<GanttDescription> ganttDescriptionOpt = this.findGanttDescription(gantt.descriptionId(), editingContext);

        if (ganttDescriptionOpt.isPresent()) {
            VariableManager variableManager = new VariableManager();
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);

            Optional<Object> sourceObjectOpt = Optional.of(deleteTaskDependencyInput.sourceTaskId())
                    .flatMap(taskId -> this.getTaskSemanticObject(taskId, gantt, editingContext));

            Optional<Object> targetObjectOpt = Optional.of(deleteTaskDependencyInput.targetTaskId())
                    .flatMap(taskId -> this.getTaskSemanticObject(taskId, gantt, editingContext));

            if (sourceObjectOpt.isPresent() && targetObjectOpt.isPresent()) {
                variableManager.put(GanttDescription.SOURCE_OBJECT, sourceObjectOpt.get());
                variableManager.put(GanttDescription.TARGET_OBJECT, targetObjectOpt.get());
                ganttDescriptionOpt.get().deleteTaskDependencyProvider().accept(variableManager);
            }

            payload = this.getPayload(deleteTaskDependencyInput.id());
        }

        return payload;
    }

    private Optional<Object> getTaskSemanticObject(String taskId, Gantt gantt, IEditingContext editingContext) {
        Optional<Object> targetObjectOpt = this.findTask(task -> Objects.equals(task.id(), taskId), gantt.tasks())
                .map(task -> this.objectService.getObject(editingContext, task.targetObjectId()))
                .map(Optional::get);
        if (targetObjectOpt.isEmpty()) {
            this.feedbackMessageService.addFeedbackMessage(new Message(MessageFormat.format("The current task of id ''{0}'' is not found", taskId), MessageLevel.ERROR));
        }
        return targetObjectOpt;
    }

    @Override
    public IPayload changeTaskCollapseState(ChangeTaskCollapseStateInput changeTaskCollapseStateInput, IEditingContext editingContext, IGanttContext ganttContext) {
        ganttContext.setGanttEvent(new ChangeGanttTaskCollapseStateEvent(changeTaskCollapseStateInput.taskId(), changeTaskCollapseStateInput.collapsed()));
        return this.getPayload(changeTaskCollapseStateInput.id());
    }

    @Override
    public IPayload changeGanttColumn(ChangeGanttColumnInput changeGanttColumnInput, IEditingContext editingContext, IGanttContext ganttContext) {
        ganttContext.setGanttEvent(new ChangeGanttColumnEvent(changeGanttColumnInput.columnId(), changeGanttColumnInput.displayed(), changeGanttColumnInput.width()));
        return this.getPayload(changeGanttColumnInput.id());
    }
}
