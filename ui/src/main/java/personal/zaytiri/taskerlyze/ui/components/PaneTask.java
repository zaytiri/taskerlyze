package personal.zaytiri.taskerlyze.ui.components;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import personal.zaytiri.taskerlyze.ui.logic.SubTaskLoader;
import personal.zaytiri.taskerlyze.ui.logic.entities.Result;
import personal.zaytiri.taskerlyze.ui.logic.entities.SubTaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

import java.io.IOException;

public class PaneTask extends TitledPane {
    private final IntegerProperty taskId = new SimpleIntegerProperty();
    private final StringProperty taskName = new SimpleStringProperty();
    private final BooleanProperty isTaskDone = new SimpleBooleanProperty();
    @FXML
    private CheckBox checkBox;
    @FXML
    private Label task;
    @FXML
    private Button swapButton;
    @FXML
    private Button addNewSubTaskButton;
    @FXML
    private Accordion subTasksAccordion;
    @FXML
    private TitledPane notFoundMessage;
    private SubTaskLoader subTaskLoader;

    public PaneTask() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/pane-task.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public void addSubTaskButtonSetOnAction() {
        addNewSubTaskButton.setOnAction(event -> {
            Result<TaskEntity> taskEntityResult = new Result<>(new TaskEntity());
            DialogNewSubTask dialog = new DialogNewSubTask(taskEntityResult, new Stage());
            dialog.setTaskId(getTaskId());
            dialog.showStage();

            if (taskEntityResult.isSuccessful()) {
                setSubTasks();
            }

            event.consume();
        });
    }

    public int getTaskId() {
        return taskId.get();
    }

    public void setTaskId(int taskId) {
        this.taskId.set(taskId);
    }

    public void setIsTaskDone(boolean isTaskDone) {
        this.isTaskDone.set(isTaskDone);
        checkBox.setSelected(this.isTaskDone.get());
    }

    public void setSubTasks() {
        subTasksAccordion.getPanes().remove(1, subTasksAccordion.getPanes().size());

        subTaskLoader.setTaskId(getTaskId());
        ObservableList<SubTaskEntity> subTasks = FXCollections.observableList(subTaskLoader.load());

        if (subTasks.isEmpty()) {
            subTasksAccordion.getPanes().add(1, notFoundMessage);
        }

        for (SubTaskEntity st : subTasks) {
            PaneSubTask comp = new PaneSubTask();
            comp.setTaskName(st.getName());
            comp.setTaskId(st.getId());
            comp.setIsTaskDone(st.isTaskDone());
            comp.setSubTaskId(st.getTaskId());

            subTasksAccordion.getPanes().add(comp);
        }
    }

    public void setTaskName(String taskName) {
        this.taskName.set(taskName);
        task.setText(this.taskName.get());
    }

    @FXML
    private void initialize() {
        subTaskLoader = new SubTaskLoader();
        setCheckBoxOnAction();
        addSubTaskButtonSetOnAction();
    }

    private void setCheckBoxOnAction() {
        checkBox.setOnAction(event -> {
            TaskEntity task = new TaskEntity()
                    .setId(getTaskId())
                    .setTaskDone(checkBox.isSelected());
            task.setDone();
        });
    }
}
