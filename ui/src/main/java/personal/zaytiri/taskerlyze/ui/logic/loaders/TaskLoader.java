package personal.zaytiri.taskerlyze.ui.logic.loaders;

import personal.zaytiri.taskerlyze.app.api.controllers.TaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskLoader {

    public List<TaskEntity> load(int categoryId, LocalDate date) {
        List<TaskEntity> tasksToBeReturned = new ArrayList<>();

        OperationResult<List<Task>> taskResult = new TaskController().getTasksByCategoryAndCompletedAtDate(categoryId, date);
        for (Task task : taskResult.getResult()) {
            tasksToBeReturned.add(new TaskEntity(task));
        }

        return tasksToBeReturned;
    }

}
