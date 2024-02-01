package personal.zaytiri.taskerlyze.ui.views.elements;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import personal.zaytiri.taskerlyze.ui.logic.entities.SubTaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.globals.UiGlobalMessage;
import personal.zaytiri.taskerlyze.ui.logic.loaders.SubTaskLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.Clipboard;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MenuOptions;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MessageType;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.PopupAction;

import java.io.IOException;

public class PaneSubTask extends TitledPane {
    private final IntegerProperty taskId = new SimpleIntegerProperty();
    private final StringProperty subTaskName = new SimpleStringProperty();
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

    public String getSubTaskName() {
        return subTaskName.get();
    }

    public void setSubTaskName(String subTaskName) {
        this.subTaskName.set(subTaskName);
        subTask.setText(this.subTaskName.get());
    }

    public int getTaskId() {
        return taskId.get();
    }

    public void setTaskId(int taskId) {
        this.taskId.set(taskId);
    }

    @FXML
    public void initialize() {
        setCheckBoxOnAction();
    }

    public void setContextMenu() {
        setContextMenu(getTabContextMenu());
    }

    public void setIsTaskDone(boolean isTaskDone) {
        this.isTaskDone.set(isTaskDone);
        checkBox.setSelected(this.isTaskDone.get());
    }

    private void addAddSubtaskOptionForContextMenu() {
        this.contextMenu.addMenuItem("Add new sub-task", event -> PopupAction.showDialogForAddingSubTask((Accordion) this.getParent(), getTaskId()));
    }

    private void addCopyTextOptionForContextMenu() {
        this.contextMenu.addMenuItem("Copy text", event -> Clipboard.addTo(getSubTaskName()));
    }

    private void addEditSubtaskOptionForContextMenu() {
        this.contextMenu.addMenuItem("Edit", event -> PopupAction.showDialogForEditingSubTask(getSubTaskId(), this, (Accordion) this.getParent(), getTaskId()));
    }

    private void addMoveSubtaskOptionForContextMenu() {
        this.contextMenu.addMenuItem("Move", event -> {
            if (PopupAction.showDialogForMovingSubTask(getSubTaskId())) {
                reloadSubTasks();
                UiGlobalMessage.getUiGlobalMessage().setMessage(MessageType.WARNING, "Task was moved to another task.");
            }
        });
    }

    private void addRemoveSubtaskOptionForContextMenu() {
        this.contextMenu.addMenuItem("Remove", event -> {
            PopupAction.showConfirmationDialog(
                    "You are about to remove the following sub-task: '" + getSubTaskName() + "'.",
                    evt -> {
                        SubTaskEntity subtask = new SubTaskEntity(getSubTaskId());
                        if (subtask.remove()) {
                            reloadSubTasks();
                        }
                    },
                    true
            );
        });
    }

    private ContextMenu getTabContextMenu() {
        addAddSubtaskOptionForContextMenu();
        addEditSubtaskOptionForContextMenu();
        addMoveSubtaskOptionForContextMenu();
        addCopyTextOptionForContextMenu();
        addRemoveSubtaskOptionForContextMenu();

        return contextMenu.buildContextMenu();
    }

    private void reloadSubTasks() {
        SubTaskLoader.getSubTaskLoader().setTaskId(getTaskId());
        SubTaskLoader.getSubTaskLoader().load();
    }

    private void setCheckBoxOnAction() {
        checkBox.setOnAction(event -> {
            SubTaskEntity subTask = new SubTaskEntity(getSubTaskId())
                    .setTaskDone(checkBox.isSelected());
            subTask.setDone();
        });
    }
}
