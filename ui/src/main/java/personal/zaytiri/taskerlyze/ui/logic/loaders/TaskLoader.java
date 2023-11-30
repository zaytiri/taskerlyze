package personal.zaytiri.taskerlyze.ui.logic.loaders;

import com.google.common.base.Stopwatch;
import personal.zaytiri.taskerlyze.app.api.controllers.TaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TaskLoader {
    private static TaskLoader INSTANCE;
    private final PropertyChangeSupport support;
    private final List<TaskEntity> loadedTasks = new ArrayList<>();
    private LocalDate activeDay = null;
    private int activeCategoryId = 0;

    private TaskLoader() {
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public int getActiveCategoryId() {
        return activeCategoryId;
    }

    public void setActiveCategoryId(int currentActiveCategoryId) {
        activeCategoryId = currentActiveCategoryId;
        load();
    }

    public LocalDate getActiveDay() {
        return activeDay;
    }

    public void setActiveDay(LocalDate currentActiveDay) {
        support.firePropertyChange("activeDay", activeDay, currentActiveDay);
        activeDay = currentActiveDay;
        load();
    }

    public static TaskLoader getTaskLoader() {
        if (INSTANCE == null) {
            INSTANCE = new TaskLoader();
        }
        return INSTANCE;
    }

    public void load() {
        if (activeDay == null) {
            return;
        }

        Stopwatch loadSW = Stopwatch.createStarted();

        OperationResult<List<Task>> taskResult = new TaskController().getTasksByCategoryAndCompletedAtDate(activeCategoryId, activeDay);

        List<TaskEntity> tasksToBeReturned = new ArrayList<>();
        for (Task task : taskResult.getResult()) {
            tasksToBeReturned.add(new TaskEntity(task).setCategoryId(task.getCategoryId()));
        }
        support.firePropertyChange("loadedTasks", loadedTasks, tasksToBeReturned);

        loadedTasks.clear();
        loadedTasks.addAll(tasksToBeReturned);

        loadSW.stop();
        System.out.println("load took " + loadSW.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    public void refresh() {
        load();
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
}
