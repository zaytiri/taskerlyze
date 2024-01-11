package personal.zaytiri.taskerlyze.ui.views.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TitledPane;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.TaskLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.Categorable;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MenuOptions;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.PopupAction;
import personal.zaytiri.taskerlyze.ui.views.elements.PaneTask;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

public class ComponentTasks extends Categorable implements PropertyChangeListener {
    private final MenuOptions contextMenu;
    private ObservableList<TaskEntity> tasks;
    @FXML
    private Accordion mainTasks;
    @FXML
    private TitledPane notFoundMessage;

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
        TaskLoader.getTaskLoader().setActiveCategoryId(getCategoryId());
    }

    @FXML
    public void initialize() {
        TaskLoader.getTaskLoader().addPropertyChangeListener(this);
        this.notFoundMessage.setContextMenu(getTabContextMenu());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!(evt.getNewValue() instanceof List)) {
            return;
        }

        this.tasks = FXCollections.observableList((List<TaskEntity>) evt.getNewValue());
        if (this.tasks.isEmpty() || this.tasks.get(0).getCategoryId() == this.getCategoryId()) {
            setTasks();
        }
    }

    private void addAddTaskOptionForContextMenu() {
        this.contextMenu.addMenuItem("Add new task", event -> {
            if (PopupAction.showDialogForAddingTask(mainTasks, TaskLoader.getTaskLoader().getActiveCategoryId())) {
                TaskLoader.getTaskLoader().refresh();
            }
        });
    }

    private ContextMenu getTabContextMenu() {
        addAddTaskOptionForContextMenu();

        return contextMenu.buildContextMenu();
    }

    private void setTasks() {
        ObservableList<TitledPane> panes = mainTasks.getPanes();

        panes.clear();

        if (tasks.isEmpty()) {
            panes.add(0, notFoundMessage);
        }

        for (TaskEntity t : tasks) {
            PaneTask comp = new PaneTask();

            comp.setId(String.valueOf(t.getId()));
            comp.setTaskId(t.getId());
            comp.setTaskName(t.getName());
            comp.setIsTaskDone(t.isTaskDone());

            comp.setContextMenu();

            panes.add(comp);
        }
    }
}
