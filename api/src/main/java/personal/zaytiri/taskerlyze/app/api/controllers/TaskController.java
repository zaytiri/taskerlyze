package personal.zaytiri.taskerlyze.app.api.controllers;

import personal.zaytiri.taskerlyze.app.api.controllers.base.ControllerFindable;
import personal.zaytiri.taskerlyze.app.api.controllers.result.CodeResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.MessageResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Task;

import java.time.LocalDate;
import java.util.List;

public class TaskController extends ControllerFindable<Task> {
    /**
     * Gets all achievements from all tasks in a specific day.
     *
     * @param date which is the date to get all the achievements from.
     * @return OperationResult<String>
     */
    public OperationResult<List<String>> getTaskAchievementsFromDay(LocalDate date) {
        Task task = getEntityInstance();
        task.setCompletedAt(date);

        List<String> achievements = task.getTaskAchievementsFromDay();

        MessageResult message = new MessageResult();
        if (achievements.isEmpty()) {
            message.setCode(CodeResult.NOT_FOUND);
        } else {
            message.setCode(CodeResult.FOUND);
        }

        return new OperationResult<>(!achievements.isEmpty(), message, achievements);
    }

    /**
     * Gets all tasks associated to a category.
     *
     * @param categoryId which is the identifier for a specific category.
     * @return OperationResult<Task>
     */
    public OperationResult<List<Task>> getTasksByCategoryAndCompletedAtDate(int categoryId, LocalDate date) {
        Task task = getEntityInstance();
        task.setCategoryId(categoryId);
        task.setCompletedAt(date);

        List<Task> tasks = task.getTasksByCategoryAndCompletedAtDate();

        MessageResult message = new MessageResult();
        if (tasks.isEmpty()) {
            message.setCode(CodeResult.NOT_FOUND);
        } else {
            message.setCode(CodeResult.FOUND);
        }

        return new OperationResult<>(!tasks.isEmpty(), message, tasks);
    }

    /**
     * Sets the status of a task to a given boolean with a specific given id.
     *
     * @param id     to set the status
     * @param isDone which is the boolean representing if a task is done or not
     * @return OperationResult<Task>
     */
    public OperationResult<Task> setDone(int id, boolean isDone) {
        Task task = getEntityInstance();
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
    protected Task getEntityInstance(int id) {
        return new Task().getInstance().setId(id);
    }

    @Override
    protected Task getEntityInstance() {
        return new Task().getInstance();
    }
}
