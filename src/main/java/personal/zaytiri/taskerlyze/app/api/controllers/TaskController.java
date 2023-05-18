package personal.zaytiri.taskerlyze.app.api.controllers;

import personal.zaytiri.taskerlyze.app.api.controllers.base.Controller;
import personal.zaytiri.taskerlyze.app.api.controllers.result.CodeResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.MessageResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Task;

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

        Task taskPopulated = task.populate();

        MessageResult message = new MessageResult();
        if (taskPopulated != null) {
            message.setCode(CodeResult.FOUND);
        } else {
            message.setCode(CodeResult.NOT_FOUND);
        }

        return new OperationResult<>(taskPopulated != null, message, taskPopulated);
    }

    public OperationResult<Task> setDone(int id) {
        Task task = Task.getInstance();
        task.setId(id);

        boolean isTaskUpdated = task.setDone();
        task = task.populate();

        MessageResult message = new MessageResult();
        if (isTaskUpdated) {
            message.setCode(CodeResult.SUCCESS);
        } else {
            message.setCode(CodeResult.FAIL);
        }

        return new OperationResult<>(isTaskUpdated, message, task);
    }
}
