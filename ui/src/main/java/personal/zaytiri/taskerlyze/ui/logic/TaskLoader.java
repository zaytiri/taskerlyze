package personal.zaytiri.taskerlyze.ui.logic;

import personal.zaytiri.taskerlyze.app.api.controllers.TaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class TaskLoader {
    private static TaskLoader INSTANCE;
    private final PropertyChangeSupport support;
    private List<TaskEntity> loadedTasks;

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

    public static TaskLoader getTaskLoader() {
        if (INSTANCE == null) {
            INSTANCE = new TaskLoader();
        }
        return INSTANCE;
    }

    public void load() {
        if (activeCategoryId == 0 || activeDay == null) {
            return;
        }
        // todo: get tasks by category but also by day to reduce the ammount of unnecessary data from the db.
        //  consider that I also want to show done tasks in other days and undone tasks in the current active day.
        OperationResult<List<Task>> taskResult = new TaskController().getTasksByCategory(activeCategoryId);
        List<TaskEntity> tasksToBeReturned = new ArrayList<>();

        for (Task task : taskResult.getResult()) {

            LocalDate completedAtTask = null;
            //todo: fix the issue where the tasks from the current date are appearing in that day as well but for all the months.
            if (task.getCompletedAt() != null) {
                completedAtTask = task.getCompletedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (activeDay.getDayOfMonth() != completedAtTask.getDayOfMonth()) {
                    continue;
                }
            } else {
                if (activeDay.getDayOfMonth() != LocalDate.now().getDayOfMonth()) {
                    continue;
                }
            }

            tasksToBeReturned.add(new TaskEntity(task));
        }
        support.firePropertyChange("loadedTasks", loadedTasks, tasksToBeReturned);

        loadedTasks = new ArrayList<>();
        loadedTasks.addAll(tasksToBeReturned);
    }

    public void refresh() {
        load();
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void setActiveDay(LocalDate currentActiveDay) {
        activeDay = currentActiveDay;
        load();
    }
}
