package personal.zaytiri.taskerlyze.ui.views.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.ContextMenu;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.TaskLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.*;
import personal.zaytiri.taskerlyze.ui.views.elements.PaneTask;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.*;

public class ComponentTasks extends Categorable implements PropertyChangeListener {
    private final MenuOptions contextMenu;
    private final Map<Integer, Cache<PaneTask>> cache = new HashMap<>();
    private List<PaneTask> paneTasks;
    private boolean reloadUiElements = true;
    private List<TaskEntity> tasks;
    @FXML
    private Accordion mainTasks;
    @FXML
    private PaneTask notFoundMessage;

    public ComponentTasks() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component-tasks.fxml"));
        this.contextMenu = new MenuOptions();

        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    @Override
    public Categorable getNewInstance() {
        return new ComponentTasks();
    }

    public void loadView() {
        var activeCategoryId = UiGlobalFilter.getUiGlobalFilter().getActiveCategoryId();
        var activeDay = UiGlobalFilter.getUiGlobalFilter().getActiveDay();
        var activeProfileId = UiGlobalFilter.getUiGlobalFilter().getActiveProfileId();

        var currentCache = cache.get(activeProfileId);

        if (currentCache == null) {
            cache.put(UiGlobalFilter.getUiGlobalFilter().getActiveProfileId(), new Cache<>());
            currentCache = cache.get(activeProfileId);
        }

        if (activeDay == null) {
            return;
        }

        var cacheKey = new Pair<>(getCategoryId(), activeDay);
        var cacheTasks = currentCache.findByKey(cacheKey);
        if (!reloadUiElements && cacheTasks != null) {
            mainTasks.getPanes().clear();
            mainTasks.getPanes().addAll(cacheTasks.getValue());
            return;
        }

        var loader = new TaskLoader();
        loader.setCategoryId(activeCategoryId);
        loader.setDate(activeDay);
        var tasks = loader.load();

        if (tasks == null) {
            return;
        }

        this.tasks = tasks;
        setTasks();
        mainTasks.getPanes().clear();
        mainTasks.getPanes().addAll(paneTasks);
        currentCache.replace(new Pair<>(getCategoryId(), activeDay), paneTasks);
        setReload(false);
    }

    public void setReload(boolean toReload) {
        reloadUiElements = toReload;
    }

    @FXML
    public void initialize() {
        UiGlobalFilter.getUiGlobalFilter().addPropertyChangeListener(this);
        this.notFoundMessage.setContextMenu(getTabContextMenu());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!Objects.equals(evt.getPropertyName(), "toReload")) {
            return;
        }

        var activeCategoryId = UiGlobalFilter.getUiGlobalFilter().getActiveCategoryId();
        if (getCategoryId() != activeCategoryId) {
            return;
        }

        setReload((boolean) evt.getNewValue());
        loadView();
    }

    private void addAddTaskOptionForContextMenu() {
        this.contextMenu.addMenuItem("Add new task", event -> PopupAction.showDialogForAddingTask(mainTasks, UiGlobalFilter.getUiGlobalFilter().getActiveCategoryId(), evt -> {
            setReload(true);
            this.loadView();
        }));
    }

    private ContextMenu getTabContextMenu() {
        addAddTaskOptionForContextMenu();

        return contextMenu.buildContextMenu();
    }

    private void setTasks() {
        paneTasks = new ArrayList<>();


        if (tasks.isEmpty()) {
            paneTasks.add(0, notFoundMessage);
        }

        for (TaskEntity t : tasks) {
            PaneTask comp = new PaneTask();

            comp.addPropertyChangeListener(this);

            comp.setId(String.valueOf(t.getId()));
            comp.setTaskId(t.getId());
            comp.setTaskName(t.getName());
            comp.setIsTaskDone(t.isTaskDone());

            comp.setContextMenu();

            paneTasks.add(comp);
        }
    }
}
