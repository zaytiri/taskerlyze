package personal.zaytiri.taskerlyze.app.api.controllers;

import personal.zaytiri.taskerlyze.app.api.controllers.base.Controller;
import personal.zaytiri.taskerlyze.app.api.controllers.result.CodeResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.MessageResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Task;

public class TaskController extends Controller<Task> {

    
    /**
     * Sets the status of a task with a specific given id.
     * It will set the done property to the opposite that is already on the database.
     *
     * @param id to set the status
     * @return OperationResult<Task>
     */
    public OperationResult<Task> setDone(int id) {
        Task task = new Task().getInstance();
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

    @Override
    protected Task getEntityInstance(int id) {
        return new Task().getInstance().setId(id);
    }

    @Override
    protected Task getEntityInstance() {
        return new Task().getInstance();
    }
}
