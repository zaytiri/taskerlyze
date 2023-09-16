package personal.zaytiri.taskerlyze.ui.components;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;

import java.io.IOException;

public class SubTaskComponent extends TitledPane {
    private final IntegerProperty taskId = new SimpleIntegerProperty();
    private final StringProperty taskName = new SimpleStringProperty();
    private final BooleanProperty isTaskDone = new SimpleBooleanProperty();
    @FXML
    CheckBox checkBox;
    @FXML
    Label subTask;

    public SubTaskComponent() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/sub-task-pane.fxml"));
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
        subTask.setText(this.taskName.get());
    }

    public boolean isIsTaskDone() {
        return isTaskDone.get();
    }

    public void setIsTaskDone(boolean isTaskDone) {
        this.isTaskDone.set(isTaskDone);
        checkBox.setSelected(this.isTaskDone.get());
    }

    public BooleanProperty isTaskDoneProperty() {
        return isTaskDone;
    }

    public IntegerProperty taskIdProperty() {
        return taskId;
    }

    public StringProperty taskNameProperty() {
        return taskName;
    }
}
