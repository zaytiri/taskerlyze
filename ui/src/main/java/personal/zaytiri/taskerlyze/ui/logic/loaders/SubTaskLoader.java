package personal.zaytiri.taskerlyze.ui.logic.loaders;

import personal.zaytiri.taskerlyze.app.api.controllers.SubTaskController;
import personal.zaytiri.taskerlyze.app.api.domain.SubTask;
import personal.zaytiri.taskerlyze.ui.logic.entities.SubTaskEntity;

import java.util.ArrayList;
import java.util.List;

public class SubTaskLoader {
    private List<SubTaskEntity> loadedSubTasks;
    private int taskId;

    public List<SubTaskEntity> load() {
        List<SubTaskEntity> subTasksToBeReturned = new ArrayList<>();

        List<SubTask> subTasks = new SubTaskController().getSubTaskByTask(taskId).getResult();
        for (SubTask st : subTasks) {
            subTasksToBeReturned.add(new SubTaskEntity(st));
        }
        loadedSubTasks = new ArrayList<>();
        loadedSubTasks.addAll(subTasksToBeReturned);
        return loadedSubTasks;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;

    }
}
