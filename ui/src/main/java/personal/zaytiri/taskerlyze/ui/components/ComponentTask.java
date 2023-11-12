package personal.zaytiri.taskerlyze.ui.components;

import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import personal.zaytiri.taskerlyze.app.api.controllers.TaskController;
import personal.zaytiri.taskerlyze.ui.logic.entities.SubTaskEntity;

import java.io.IOException;

public class ComponentTask extends TitledPane {
    private final IntegerProperty taskId = new SimpleIntegerProperty();
    private final StringProperty taskName = new SimpleStringProperty();
    private final BooleanProperty isTaskDone = new SimpleBooleanProperty();
    private final ListProperty<SubTaskEntity> subTasks = new SimpleListProperty<>();
    @FXML
    CheckBox checkBox;
    @FXML
    Label task;
    @FXML
    Button swapButton;

    @FXML
    Button addNewSubTaskButton;
    @FXML
    Accordion subTasksAccordion;

    @FXML
    TitledPane subTasksNotFoundTitledPane;

    public ComponentTask() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component-task.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public Button getAddNewSubTaskButton() {
        return addNewSubTaskButton;
    }

    public ObservableList<SubTaskEntity> getSubTasks() {
        return subTasks.get();
    }

    public void setSubTasks(ObservableList<SubTaskEntity> subTasks) {
        this.subTasks.set(subTasks);

        if (subTasks.isEmpty()) {
            subTasksNotFoundTitledPane.setVisible(true);
        }

        for (SubTaskEntity st : subTasks) {
            ComponentSubTask comp = new ComponentSubTask();
            comp.setTaskName(st.getName());
            comp.setTaskId(st.getId());
            comp.setIsTaskDone(st.isTaskDone());
            comp.setSubTaskId(st.getTaskId());

            subTasksAccordion.getPanes().add(comp);
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

    public ListProperty<SubTaskEntity> subTasksProperty() {
        return subTasks;
    }

    public IntegerProperty taskIdProperty() {
        return taskId;
    }

    public StringProperty taskNameProperty() {
        return taskName;
    }

    @FXML
    private void initialize() {
        setCheckBoxOnAction();
    }

    private void setCheckBoxOnAction() {
        checkBox.setOnAction(event -> {
            TaskController taskController = new TaskController();
            taskController.setDone(getTaskId(), checkBox.isSelected());
        });
    }
}
