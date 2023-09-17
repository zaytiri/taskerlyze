package personal.zaytiri.taskerlyze.ui.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import personal.zaytiri.taskerlyze.app.api.controllers.CategoryController;
import personal.zaytiri.taskerlyze.app.api.controllers.TaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.ui.logic.entities.Result;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

import java.io.IOException;
import java.util.List;

public class DialogNewTask extends AnchorPane {
    private final Stage stage;
    @FXML
    public TextField name;
    @FXML
    public ComboBox<String> category;
    @FXML
    public TextField description;
    @FXML
    public TextField url;
    @FXML
    public TextField priority;
    @FXML
    public Button buttonCreate;
    Result<TaskEntity> result;

    public DialogNewTask(Result<TaskEntity> result, Stage primaryStage) {
        this.result = result;

        stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/dialog-new-task.fxml"));
            loader.setRoot(this);
            loader.setController(this);

            stage.setScene(new Scene(loader.load()));
            stage.setTitle("New Task");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        stage.showAndWait();
    }

    @FXML
    private void initialize() {

        populateCategories();

        buttonCreate.setOnAction(event -> {
            TaskController taskController = new TaskController();
            Task newTask = new Task().getInstance();

            System.out.println(priority.getText());
            newTask
                    .setName(name.getText())
                    .setDescription(description.getText())
                    .setCategoryId(1)
                    .setUrl(url.getText())
                    .setPriority(Integer.parseInt(priority.getText()));

            OperationResult<Task> newTaskResult = taskController.createOrUpdate(newTask);

            result.setStatus(newTaskResult.getStatus());
            stage.close();
        });
    }

    private void populateCategories() {
        CategoryController catController = new CategoryController();
        OperationResult<List<Category>> categories = catController.get(null, null);

        for (Category cat : categories.getResult()) {
            category.getItems().add(cat.getName());
        }
    }
}
