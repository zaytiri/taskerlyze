package personal.zaytiri.taskerlyze.app.api.controllers;

import personal.zaytiri.taskerlyze.app.api.controllers.base.Controller;
import personal.zaytiri.taskerlyze.app.api.controllers.result.CodeResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.MessageResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;

import java.util.List;
import java.util.Map;

public class TaskController implements Controller<Task> {

    @Override
    public OperationResult<Task> createOrUpdate(Task request) {
        boolean isCreated = request.createOrUpdate();

        MessageResult message = new MessageResult();
        if (isCreated) {
            message.setCode(CodeResult.CREATED);
        } else {
            message.setCode(CodeResult.NOT_CREATED);
        }

        return new OperationResult<>(isCreated, message, request);
    }

    @Override
    public OperationResult<Task> delete(int id) {
        Task taskToBeDeleted = Task.getInstance();

        taskToBeDeleted.setId(id);

        boolean isDeleted = taskToBeDeleted.delete();

        MessageResult message = new MessageResult();
        if (isDeleted) {
            message.setCode(CodeResult.DELETED);
        } else {
            message.setCode(CodeResult.NOT_DELETED);
        }

        return new OperationResult<>(isDeleted, message, null);
    }

    @Override
    public OperationResult<Task> get(int id) {
        Task task = Task.getInstance();

        task.setId(id);

        Task taskPopulated = task.get();

        MessageResult message = new MessageResult();
        if (taskPopulated != null) {
            message.setCode(CodeResult.FOUND);
        } else {
            message.setCode(CodeResult.NOT_FOUND);
        }

        return new OperationResult<>(taskPopulated != null, message, taskPopulated);
    }

    @Override
    public OperationResult<List<Task>> get(Map<String, Pair<String, Object>> filters, Pair<String, String> orderByColumn) {
        Task task = Task.getInstance();

        List<Task> tasks = task.get(filters, orderByColumn);

        MessageResult message = new MessageResult();
        if (!tasks.isEmpty()) {
            message.setCode(CodeResult.FOUND);
        } else {
            message.setCode(CodeResult.NOT_FOUND);
        }

        return new OperationResult<>(!tasks.isEmpty(), message, tasks);
    }

    /**
     * Sets the status of a task with a specific given id.
     * It will set the done property to the opposite that is already on the database.
     *
     * @param id to set the status
     * @return OperationResult<Task>
     */
    public OperationResult<Task> setDone(int id) {
        Task task = Task.getInstance();
        task.setId(id);

        boolean isTaskUpdated = task.setTaskStatus();
        task = task.get();

        MessageResult message = new MessageResult();
        if (isTaskUpdated) {
            message.setCode(CodeResult.SUCCESS);
        } else {
            message.setCode(CodeResult.FAIL);
        }

        return new OperationResult<>(isTaskUpdated, message, task);
    }
}
