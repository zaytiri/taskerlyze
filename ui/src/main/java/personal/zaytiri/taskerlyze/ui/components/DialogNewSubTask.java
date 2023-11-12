package personal.zaytiri.taskerlyze.ui.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import personal.zaytiri.taskerlyze.app.api.controllers.SubTaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.SubTask;
import personal.zaytiri.taskerlyze.ui.logic.entities.Result;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

import java.io.IOException;

public class DialogNewSubTask extends AnchorPane {
    private final Stage primaryStage;
    private final Stage stage;
    @FXML
    public TextField name;
    @FXML
    public Button buttonCreate;
    Result<TaskEntity> result;
    private int taskId;

    public DialogNewSubTask(Result<TaskEntity> result, Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.result = result;

        stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/dialog-new-sub-task.fxml"));
            loader.setRoot(this);
            loader.setController(this);

            stage.setScene(new Scene(loader.load()));
            stage.setTitle("New Sub Task");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void showStage() {
        double centerXPosition = primaryStage.getX() + primaryStage.getWidth() / 2d;
        double centerYPosition = primaryStage.getY() + primaryStage.getHeight() / 2d;

        stage.setOnShowing(ev -> stage.hide());

        stage.setOnShown(ev -> {
            stage.setX(centerXPosition - stage.getWidth() / 2d - 35);
            stage.setY(centerYPosition - stage.getHeight() / 2d - 50);
            stage.show();
        });

        stage.initOwner(primaryStage);
        stage.showAndWait();
    }

    @FXML
    private void initialize() {

        buttonCreate.setOnAction(event -> {
            SubTaskController subTaskController = new SubTaskController();
            SubTask newTask = new SubTask().getInstance();

            newTask.setName(name.getText());
            newTask.setTaskId(this.taskId);

            OperationResult<SubTask> newSubTaskResult = subTaskController.createOrUpdate(newTask);

            result.setStatus(newSubTaskResult.getStatus());
            stage.close();
        });
    }
}
