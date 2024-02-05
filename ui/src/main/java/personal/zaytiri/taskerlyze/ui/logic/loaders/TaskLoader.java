package personal.zaytiri.taskerlyze.ui.logic.loaders;

import javafx.util.Pair;
import personal.zaytiri.taskerlyze.app.api.controllers.TaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.globals.UiGlobalFilter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskLoader implements Loadable<TaskEntity>, Findable<Pair<Integer, String>> {

    private int categoryId;
    private LocalDate date;

    @Override
    public List<Pair<Integer, String>> find(String subString) {
        OperationResult<List<Task>> result = new TaskController().findNameBySubString(subString, UiGlobalFilter.getUiGlobalFilter().getActiveProfileId());
        List<Pair<Integer, String>> tasksToBeReturned = new ArrayList<>();
        for (Task task : result.getResult()) {
            tasksToBeReturned.add(new Pair<>(task.getId(), task.getName()));
        }
        return tasksToBeReturned;
    }

    @Override
    public List<TaskEntity> load() {
        List<TaskEntity> tasksToBeReturned = new ArrayList<>();

        OperationResult<List<Task>> taskResult = new TaskController().getTasksByCategoryAndCompletedAtDate(categoryId, date);
        for (Task task : taskResult.getResult()) {
            tasksToBeReturned.add(new TaskEntity(task));
        }

        return tasksToBeReturned;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
