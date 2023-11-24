package personal.zaytiri.taskerlyze.ui.views.elements;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import personal.zaytiri.taskerlyze.ui.logic.entities.SubTaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.Clipboard;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MenuOptions;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.PopupAction;

import java.io.IOException;

public class PaneSubTask extends TitledPane {
    private final IntegerProperty taskId = new SimpleIntegerProperty();
    private final StringProperty taskName = new SimpleStringProperty();
    private final BooleanProperty isTaskDone = new SimpleBooleanProperty();
    private final IntegerProperty subTaskId = new SimpleIntegerProperty();
    private final MenuOptions contextMenu;
    @FXML
    CheckBox checkBox;
    @FXML
    Label subTask;

    public PaneSubTask() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/pane-sub-task.fxml"));
        this.contextMenu = new MenuOptions();

        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public int getSubTaskId() {
        return subTaskId.get();
    }

    public void setSubTaskId(int subTaskId) {
        this.subTaskId.set(subTaskId);
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
        subTask.setText(this.taskName.get());
    }

    @FXML
    public void initialize() {
        setCheckBoxOnAction();
    }

    public void setContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        setContextMenu(getTabContextMenu(ifSuccessful));
    }

    public void setIsTaskDone(boolean isTaskDone) {
        this.isTaskDone.set(isTaskDone);
        checkBox.setSelected(this.isTaskDone.get());
    }

    private void addAddSubtaskOptionForContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        this.contextMenu.addMenuItem("Add new sub-task", event -> PopupAction.showDialogForAddingSubTask(getTaskId(), ifSuccessful));
    }

    private void addCopyTextOptionForContextMenu() {
        this.contextMenu.addMenuItem("Copy text", event -> Clipboard.addTo(getTaskName()));
    }

    private void addEditSubtaskOptionForContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        this.contextMenu.addMenuItem("Edit", event -> PopupAction.showDialogForEditingSubTask(getSubTaskId(), ifSuccessful));
    }

    private void addRemoveSubtaskOptionForContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        this.contextMenu.addMenuItem("Remove (no confirmation)", event -> {
            SubTaskEntity subtask = new SubTaskEntity().setId(getSubTaskId());
            if (subtask.remove()) {
                ifSuccessful.handle(event);
            }
        });
    }

    private ContextMenu getTabContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        addRemoveSubtaskOptionForContextMenu(ifSuccessful);
        addEditSubtaskOptionForContextMenu(ifSuccessful);
        addAddSubtaskOptionForContextMenu(ifSuccessful);
        addCopyTextOptionForContextMenu();

        return contextMenu.buildContextMenu();
    }

    private void setCheckBoxOnAction() {
        checkBox.setOnAction(event -> {
            SubTaskEntity subTask = new SubTaskEntity()
                    .setId(getSubTaskId())
                    .setTaskDone(checkBox.isSelected());
            subTask.setDone();
        });
    }
}
