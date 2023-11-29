package personal.zaytiri.taskerlyze.ui.views.elements;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.SubTaskLoader;
import personal.zaytiri.taskerlyze.ui.logic.loaders.TaskLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.Clipboard;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MenuOptions;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.PopupAction;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class PaneTask extends TitledPane {
    private final IntegerProperty taskId = new SimpleIntegerProperty();
    private final StringProperty taskName = new SimpleStringProperty();
    private final BooleanProperty isTaskDone = new SimpleBooleanProperty();
    private final MenuOptions contextMenu;
    @FXML
    private CheckBox checkBox;
    @FXML
    private Label task;
    @FXML
    private Button swapButton;
    @FXML
    private BorderPane mainBorderPane;

    public PaneTask() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/pane-task.fxml"));
        this.contextMenu = new MenuOptions();

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public int getTaskId() {
        return taskId.get();
    }

    public void setTaskId(int taskId) {
        this.taskId.set(taskId);
    }

    public String getTaskName() {
        return taskName.get();
    }

    public void setTaskName(String taskName) {
        this.taskName.set(taskName);
        task.setText(this.taskName.get());
    }

    public void setContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        setContextMenu(getTabContextMenu(ifSuccessful));
    }

    public void setIsTaskDone(boolean isTaskDone) {
        this.isTaskDone.set(isTaskDone);
        checkBox.setSelected(this.isTaskDone.get());
    }


    private void addAddSubtaskOptionForContextMenu() {
        this.contextMenu.addMenuItem("Add new sub-task", event -> PopupAction.showDialogForAddingSubTask(getTaskId(), nestedEvent -> loadSubTasks()));
    }

    private void addAddTaskOptionForContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        this.contextMenu.addMenuItem("Add new task", event -> PopupAction.showDialogForAddingTask(TaskLoader.getTaskLoader().getActiveCategoryId(), ifSuccessful));
    }

    private void addCopyTextOptionForContextMenu() {
        this.contextMenu.addMenuItem("Copy text", event -> Clipboard.addTo(getTaskName()));
    }

    private void addCopyUrlOptionForContextMenu() {
        String taskUrl = new TaskEntity(getTaskId()).get().getUrl();
        this.contextMenu.addMenuItem("Copy URL", event -> Clipboard.addTo(taskUrl));
    }

    private void addEditTaskOptionForContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        this.contextMenu.addMenuItem("Edit", event -> PopupAction.showDialogForEditingTask(getTaskId(), ifSuccessful));
    }

    private void addMoveTaskOptionForContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        this.contextMenu.addMenuItem("Move", event -> PopupAction.showDialogForMovingTask(getTaskId(), ifSuccessful));
    }

    private void addOpenURLTaskOptionForContextMenu() {
        this.contextMenu.addMenuItem("Open URL in browser", event -> {
            String taskUrl = new TaskEntity(getTaskId()).get().getUrl();
            try {
                Desktop.getDesktop().browse(new URL(taskUrl).toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void addRemoveTaskOptionForContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        this.contextMenu.addMenuItem("Remove (no confirmation)", event -> {
            TaskEntity task = new TaskEntity(getTaskId());
            if (task.remove()) {
                ifSuccessful.handle(event);
            }
        });
    }

    private ContextMenu getTabContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        addAddTaskOptionForContextMenu(ifSuccessful);
        addEditTaskOptionForContextMenu(ifSuccessful);
        addRemoveTaskOptionForContextMenu(ifSuccessful);
        addAddSubtaskOptionForContextMenu();
        addMoveTaskOptionForContextMenu(ifSuccessful);
        addCopyTextOptionForContextMenu();
        addCopyUrlOptionForContextMenu();
        addOpenURLTaskOptionForContextMenu();

        return contextMenu.buildContextMenu();
    }

    @FXML
    private void initialize() {
        subTaskLoader = new SubTaskLoader();
        setCheckBoxOnAction();
        populateSubTasksWhenTaskExpands();
    }

    private void loadSubTasks() {
        SubTaskLoader.getSubTaskLoader().setTaskId(getTaskId());
        SubTaskLoader.getSubTaskLoader().load();
    }

    private void populateSubTasksWhenTaskExpands() {
        this.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
            if (Boolean.TRUE.equals(isNowExpanded)) {
                loadSubTasks();
            }
        });
    }

    private void setCheckBoxOnAction() {
        checkBox.setOnAction(event -> {
            TaskEntity task = new TaskEntity(getTaskId())
                    .setTaskDone(checkBox.isSelected());
            task.setDone();
        });
    }
}
