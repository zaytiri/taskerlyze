package personal.zaytiri.taskerlyze.ui.logic.loaders;

import personal.zaytiri.taskerlyze.app.api.controllers.SubTaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.SubTask;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.SubTaskEntity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class SubTaskLoader implements Findable<Pair<Integer, String>> {
    private static SubTaskLoader INSTANCE;
    private final PropertyChangeSupport support;
    private final List<SubTaskEntity> loadedSubTasks = new ArrayList<>();
    private int taskId;

    private SubTaskLoader() {
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    @Override
    public List<Pair<Integer, String>> find(String subString) {
        OperationResult<List<SubTask>> result = new SubTaskController().findNameBySubString(subString);
        List<Pair<Integer, String>> subtaskToBeReturned = new ArrayList<>();
        for (SubTask subtask : result.getResult()) {
            subtaskToBeReturned.add(new Pair<>(subtask.getId(), subtask.getName()));
        }
        return subtaskToBeReturned;
    }

    public static SubTaskLoader getSubTaskLoader() {
        if (INSTANCE == null) {
            INSTANCE = new SubTaskLoader();
        }
        return INSTANCE;
    }

    public List<SubTaskEntity> load() {
        List<SubTaskEntity> subTasksToBeReturned = new ArrayList<>();

        List<SubTask> subTasks = new SubTaskController().getSubTaskByTask(taskId).getResult();
        for (SubTask st : subTasks) {
            subTasksToBeReturned.add(new SubTaskEntity(st));
        }
        support.firePropertyChange("loadedSubTasks", loadedSubTasks, subTasksToBeReturned);

        loadedSubTasks.clear();
        loadedSubTasks.addAll(subTasksToBeReturned);
        return loadedSubTasks;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;

    }
}
