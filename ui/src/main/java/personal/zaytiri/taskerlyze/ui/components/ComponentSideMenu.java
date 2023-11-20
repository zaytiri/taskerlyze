package personal.zaytiri.taskerlyze.ui.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import personal.zaytiri.taskerlyze.ui.logic.Configuration;
import personal.zaytiri.taskerlyze.ui.logic.TaskLoader;
import personal.zaytiri.taskerlyze.ui.logic.entities.Result;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.time.LocalDate;

public class ComponentSideMenu extends AnchorPane {
    private PropertyChangeSupport support;
    @FXML
    private Button exit;
    @FXML
    private Button today;
    @FXML
    private Button settings;
    @FXML
    private Button switchProfile;
    @FXML
    private Button newTask;

    public ComponentSideMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component-side-menu.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    @FXML
    public void initialize() {
        support = new PropertyChangeSupport(this);
        setButtonsSetOnAction();
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void setButtonsSetOnAction() {
        setExitButtonOnAction(exit);
        setNewTaskButtonOnAction(newTask);
        setTodayButtonOnAction(today);
    }


    private void setExitButtonOnAction(Button exit) {
        Stage primaryStage = Configuration.getInstance().getPrimaryStage();
        exit.setOnAction(event -> primaryStage.close());
        primaryStage.setOnCloseRequest(event -> primaryStage.close());
    }

    private void setNewTaskButtonOnAction(Button newTask) {
        newTask.setOnAction(event -> {
            Result<TaskEntity> taskResult = new Result<>(new TaskEntity());
            DialogNewTask dialog = new DialogNewTask(taskResult);
            dialog.setCategoryId(TaskLoader.getTaskLoader().getActiveCategoryId());
            dialog.showDialog();

            if (taskResult.getResult() != null) {
                TaskLoader.getTaskLoader().refresh();
            }
            event.consume();
        });
    }

    private void setTodayButtonOnAction(Button today) {
        today.setOnAction(event -> support.firePropertyChange("currentDate", LocalDate.now().minusDays(7), LocalDate.now()));
    }
}
