package personal.zaytiri.taskerlyze.app.api.controllers;

import personal.zaytiri.taskerlyze.app.api.controllers.base.Controller;
import personal.zaytiri.taskerlyze.app.api.controllers.result.CodeResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.MessageResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.SubTask;

import java.util.List;

public class SubTaskController extends Controller<SubTask> {
    /**
     * Gets all subtasks associated to a task.
     *
     * @param taskId which is the identifier for a specific task.
     * @return OperationResult<Task>
     */
    public OperationResult<List<SubTask>> getSubTaskByTask(int taskId) {
        SubTask task = new SubTask().getInstance();
        task.setTaskId(taskId);

        List<SubTask> tasks = task.getSubTasksByTask();

        MessageResult message = new MessageResult();
        if (tasks.isEmpty()) {
            message.setCode(CodeResult.NOT_FOUND);
        } else {
            message.setCode(CodeResult.FOUND);
        }

        return new OperationResult<>(!tasks.isEmpty(), message, tasks);
    }

    /**
     * Sets the status of a subtask to a given boolean with a specific given id.
     *
     * @param id     to set the status
     * @param isDone which is the boolean representing if a subtask is done or not
     * @return OperationResult<SubTask>
     */
    public OperationResult<SubTask> setDone(int id, boolean isDone) {
        SubTask task = new SubTask().getInstance();
        task.setId(id);

        boolean isTaskUpdated = task.setTaskStatus(isDone);
        task = task.get();

        MessageResult message = new MessageResult();
        if (isTaskUpdated) {
            message.setCode(CodeResult.SUCCESS);
        } else {
            message.setCode(CodeResult.FAIL);
        }

        return new OperationResult<>(isTaskUpdated, message, task);
    }

    @Override
    protected SubTask getEntityInstance(int id) {
        return new SubTask().getInstance().setId(id);
    }

    @Override
    protected SubTask getEntityInstance() {
        return new SubTask().getInstance();
    }
}
