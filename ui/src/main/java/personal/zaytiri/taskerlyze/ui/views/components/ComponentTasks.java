package personal.zaytiri.taskerlyze.ui.views.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.TaskLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MenuOptions;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.PopupAction;
import personal.zaytiri.taskerlyze.ui.views.elements.PaneTask;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

public class ComponentTasks extends AnchorPane implements PropertyChangeListener {
    private final MenuOptions contextMenu;
    private ObservableList<TaskEntity> tasks;
    @FXML
    private Accordion mainTasks;
    @FXML
    private TitledPane notFoundMessage;
    private int categoryId;

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

    @FXML
    public void initialize() {
        TaskLoader.getTaskLoader().addPropertyChangeListener(this);
        this.notFoundMessage.setContextMenu(getTabContextMenu());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.tasks = FXCollections.observableList((List<TaskEntity>) evt.getNewValue());
        if (this.tasks.isEmpty() || this.tasks.get(0).getCategoryId() == this.categoryId) {
            setTasks();
        }
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    private void addAddTaskOptionForContextMenu() {
        this.contextMenu.addMenuItem("Add new task", event -> PopupAction.showDialogForAddingTask(TaskLoader.getTaskLoader().getActiveCategoryId(), ifSuccessful -> TaskLoader.getTaskLoader().load()));
    }

    private ContextMenu getTabContextMenu() {
        addAddTaskOptionForContextMenu();

        return contextMenu.buildContextMenu();
    }

    private void setTasks() {
        List<TitledPane> panes = mainTasks.getPanes();

        panes.removeAll(panes);

        if (tasks.isEmpty()) {
            panes.add(0, notFoundMessage);
        }

        for (TaskEntity t : tasks) {
            PaneTask comp = new PaneTask();

            comp.setId(String.valueOf(t.getId()));
            comp.setTaskId(t.getId());
            comp.setTaskName(t.getName());
            comp.setIsTaskDone(t.isTaskDone());

            comp.setContextMenu(event -> TaskLoader.getTaskLoader().load());

            panes.add(comp);
        }
    }
}
