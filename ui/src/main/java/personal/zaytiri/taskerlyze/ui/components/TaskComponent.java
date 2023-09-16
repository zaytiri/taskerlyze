package personal.zaytiri.taskerlyze.ui.components;

import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

import java.io.IOException;

public class TaskComponent extends TitledPane {
    private final IntegerProperty taskId = new SimpleIntegerProperty();
    private final StringProperty taskName = new SimpleStringProperty();
    private final BooleanProperty isTaskDone = new SimpleBooleanProperty();
    private final ListProperty<TaskEntity> subTasks = new SimpleListProperty<>();
    @FXML
    CheckBox checkBox;
    @FXML
    Label task;
    @FXML
    Button swapButton;
    @FXML
    BorderPane mainBorderPane;

    public TaskComponent() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/task-pane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public ObservableList<TaskEntity> getSubTasks() {
        return subTasks.get();
    }

    public void setSubTasks(ObservableList<TaskEntity> subTasks) {
        this.subTasks.set(subTasks);

        Accordion subTasksView = new Accordion();
        subTasksView.setId("sub-tasks-accordion");

        if (subTasks.isEmpty()) {
            TitledPane tp = new TitledPane();
            tp.setCollapsible(false);
            BorderPane pane = new BorderPane();
            pane.setCenter(new Label("No sub tasks found."));
            tp.setGraphic(pane);
            subTasksView.getPanes().add(tp);
        }
        
        for (TaskEntity st : subTasks) {
            SubTaskComponent comp = new SubTaskComponent();
            comp.setTaskName(st.getTaskName());
            comp.setTaskId(st.getTaskId());
            comp.setIsTaskDone(st.isTaskDone());

            subTasksView.getPanes().add(comp);
        }

        mainBorderPane.setCenter(subTasksView);
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

    public ListProperty<TaskEntity> subTasksProperty() {
        return subTasks;
    }

    public IntegerProperty taskIdProperty() {
        return taskId;
    }

    public StringProperty taskNameProperty() {
        return taskName;
    }
}
