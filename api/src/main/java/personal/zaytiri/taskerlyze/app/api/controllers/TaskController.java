package personal.zaytiri.taskerlyze.app.api.controllers;

import personal.zaytiri.taskerlyze.app.api.controllers.base.Controller;
import personal.zaytiri.taskerlyze.app.api.controllers.result.CodeResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.MessageResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;

import java.util.List;

public class TaskController extends Controller<Task> {

    /**
     * Gets all tasks associated to a category.
     *
     * @param categoryId which is the identifier for a specific category.
     * @return OperationResult<Task>
     */
    public OperationResult<Pair<Category, List<Task>>> getTasksByCategory(int categoryId) {
        Task task = new Task().getInstance();
        task.setCategoryId(categoryId);

        Pair<Category, List<Task>> tasks = task.getTasksByCategory();

        MessageResult message = new MessageResult();
        if (tasks.getValue().isEmpty()) {
            message.setCode(CodeResult.NOT_FOUND);
        } else {
            message.setCode(CodeResult.FOUND);
        }

        return new OperationResult<>(!tasks.getValue().isEmpty(), message, tasks);
    }
    
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
