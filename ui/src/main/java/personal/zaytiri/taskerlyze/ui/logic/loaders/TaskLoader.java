package personal.zaytiri.taskerlyze.ui.logic.loaders;

import personal.zaytiri.taskerlyze.app.api.controllers.TaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskLoader implements Loader<TaskEntity> {
public class TaskLoader implements Loader<TaskEntity>, Findable<Pair<Integer, String>> {

    private final int categoryId;
    private final LocalDate date;

    public TaskLoader(int categoryId, LocalDate date) {
        this.categoryId = categoryId;
        this.date = date;
    @Override
    public List<Pair<Integer, String>> find(String subString) {
        OperationResult<List<Task>> result = new TaskController().findNameBySubString(subString);
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
}
