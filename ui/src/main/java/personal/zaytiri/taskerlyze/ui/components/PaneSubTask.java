package personal.zaytiri.taskerlyze.ui.components;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import personal.zaytiri.taskerlyze.ui.logic.entities.SubTaskEntity;

import java.io.IOException;

public class PaneSubTask extends TitledPane {
    private final IntegerProperty taskId = new SimpleIntegerProperty();
    private final StringProperty taskName = new SimpleStringProperty();
    private final BooleanProperty isTaskDone = new SimpleBooleanProperty();
    private final IntegerProperty subTaskId = new SimpleIntegerProperty();
    @FXML
    CheckBox checkBox;
    @FXML
    Label subTask;

    public PaneSubTask() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/pane-sub-task.fxml"));
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

    @FXML
    public void initialize() {
        setCheckBoxOnAction();
    }

    public void setIsTaskDone(boolean isTaskDone) {
        this.isTaskDone.set(isTaskDone);
        checkBox.setSelected(this.isTaskDone.get());
    }

    public void setTaskId(int taskId) {
        this.taskId.set(taskId);
    }

    public void setTaskName(String taskName) {
        this.taskName.set(taskName);
        subTask.setText(this.taskName.get());
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
