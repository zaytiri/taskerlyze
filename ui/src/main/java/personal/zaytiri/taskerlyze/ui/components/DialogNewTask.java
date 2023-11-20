package personal.zaytiri.taskerlyze.ui.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.CategoryLoader;
import personal.zaytiri.taskerlyze.ui.logic.entities.CategoryEntity;
import personal.zaytiri.taskerlyze.ui.logic.entities.Result;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DialogNewTask extends AnchorPane {
    private final Stage primaryStage;
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
    private List<Pair<Integer, String>> categoryPairs;
    private int categoryId;

    public DialogNewTask(Result<TaskEntity> result, Stage primaryStage) {
        this.primaryStage = primaryStage;
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

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setStageSize() {
        double centerXPosition = primaryStage.getX() + primaryStage.getWidth() / 2d;
        double centerYPosition = primaryStage.getY() + primaryStage.getHeight() / 2d;

        // Hide the pop-up stage before it is shown and becomes relocated
        stage.setOnShowing(ev -> stage.hide());

        // Relocate the pop-up Stage
        stage.setOnShown(ev -> {
            stage.setX(centerXPosition - stage.getWidth() / 2d - 35);
            stage.setY(centerYPosition - stage.getHeight() / 2d - 50);
            stage.show();
        });

        stage.initOwner(primaryStage);
    }

    public void showStage() {
        populateCategories();
        setStageSize();
        stage.showAndWait();
    }

    private int getCategoryId(String byName) {
        for (Pair<Integer, String> categoryPair : categoryPairs) {
            if (!categoryPair.getValue().equals(byName)) {
                continue;
            }
            return categoryPair.getKey();
        }
        return 0;
    }

    private String getCategoryName(int id) {
        for (Pair<Integer, String> categoryPair : categoryPairs) {
            if (!categoryPair.getKey().equals(id)) {
                continue;
            }
            return categoryPair.getValue();
        }
        return "";
    }

    @FXML
    private void initialize() {
        setOnActionCreateButton();
    }

    private void populateCategories() {
        CategoryLoader loader = new CategoryLoader();

        categoryPairs = new ArrayList<>();

        for (CategoryEntity cat : loader.load()) {
            categoryPairs.add(cat.getPair());
            category.getItems().add(cat.getName());
        }
        category.getSelectionModel().select(getCategoryName(categoryId));
    }

    private void setOnActionCreateButton() {
        buttonCreate.setOnAction(event -> {
            TaskEntity newTask = new TaskEntity()
                    .setName(name.getText())
                    .setDescription(description.getText())
                    .setCategoryId(getCategoryId(category.getSelectionModel().getSelectedItem()))
                    .setUrl(url.getText())
                    .setPriority(Integer.parseInt(priority.getText()));

            result.setStatus(newTask.createOrUpdate());
            stage.close();
        });
    }
}
