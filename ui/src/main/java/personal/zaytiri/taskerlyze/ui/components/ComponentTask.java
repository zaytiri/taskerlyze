package personal.zaytiri.taskerlyze.ui.components;

import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import personal.zaytiri.taskerlyze.app.api.controllers.TaskController;
import personal.zaytiri.taskerlyze.ui.logic.entities.Result;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

import java.io.IOException;

public class ComponentTask extends TitledPane {
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

        // todo: put this code into the fxml file for sub task
        Button addSubTask = new Button();
        addSubTask.setId("add-sub-task-btn");
        ImageView img = new ImageView("icons/plus.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        addSubTask.setGraphic(img);

        HBox hbox = new HBox();
        hbox.getChildren().add(addSubTask);
        hbox.setAlignment(Pos.CENTER);

        TitledPane tp = new TitledPane();
        tp.setId("sub-task-titled-pane");
        tp.setCollapsible(false);
        AnchorPane pane = new AnchorPane();
        AnchorPane.setRightAnchor(hbox, 0.0);
        AnchorPane.setLeftAnchor(hbox, 0.0);
        pane.getChildren().add(hbox);
        pane.setPrefWidth(365);

        tp.setGraphic(pane);
        subTasksView.getPanes().add(tp);

        for (TaskEntity st : subTasks) {
            ComponentSubTask comp = new ComponentSubTask();
            comp.setTaskName(st.getTaskName());
            comp.setTaskId(st.getTaskId());
            comp.setIsTaskDone(st.isTaskDone());

            subTasksView.getPanes().add(comp);
        }

        addSubTask.setOnAction(event -> {
            Result<TaskEntity> taskResult = new Result<>(new TaskEntity());
            DialogNewSubTask dialog = new DialogNewSubTask(taskResult, new Stage());
            dialog.showStage();

//            if (taskResult.getResult() != null) {
//                tasksView.refreshTabContent();
//            }
            event.consume();
        });

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
