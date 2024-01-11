package personal.zaytiri.taskerlyze.ui.logic.loaders;

import com.google.common.base.Stopwatch;
import personal.zaytiri.taskerlyze.app.api.controllers.TaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.Cache;

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
    
    private final Cache cache = new Cache();
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

        List<TaskEntity> tasksToBeReturned = loadFromApiOrCache();

        support.firePropertyChange("loadedTasks", loadedTasks, tasksToBeReturned);

        loadedTasks.clear();
        loadedTasks.addAll(tasksToBeReturned);

        loadSW.stop();
        System.out.println("load took " + loadSW.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    public void refresh() {
        setCacheReloadFor(activeCategoryId, activeDay, true);
        load();
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void setCacheReloadFor(int categoryId, LocalDate date, boolean toBeReloaded) {
        var currentCache = cache.findByKey(new Pair<>(categoryId, date));
        cache.replace(currentCache.getKey(), new Pair<>(toBeReloaded, currentCache.getValue().getValue()));
    }

    private List<TaskEntity> loadFromApiOrCache() {
        List<TaskEntity> tasksToBeReturned = new ArrayList<>();
        var cacheKey = new Pair<>(activeCategoryId, activeDay);
        var currentCache = cache.findByKey(cacheKey);

        if (currentCache != null) {

            if (Boolean.FALSE.equals(currentCache.getValue().getKey())) {
                tasksToBeReturned.addAll(currentCache.getValue().getValue());

                cache.replace(cacheKey, new Pair<>(false, tasksToBeReturned));
                return tasksToBeReturned;
            }
        }

        OperationResult<List<Task>> taskResult = new TaskController().getTasksByCategoryAndCompletedAtDate(activeCategoryId, activeDay);
        for (Task task : taskResult.getResult()) {
            tasksToBeReturned.add(new TaskEntity(task));
        }

        if (currentCache != null) {
            cache.replace(cacheKey, new Pair<>(false, tasksToBeReturned));
        } else {
            cache.put(cacheKey, new Pair<>(false, tasksToBeReturned));
        }

        return tasksToBeReturned;
    }
}
