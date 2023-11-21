package personal.zaytiri.taskerlyze.ui.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.CategoryLoader;
import personal.zaytiri.taskerlyze.ui.logic.entities.CategoryEntity;
import personal.zaytiri.taskerlyze.ui.logic.entities.Result;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

import java.util.ArrayList;
import java.util.List;

public class DialogNewTask extends Dialog {
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
    @FXML
    public Label errorMessage;
    Result<TaskEntity> result;
    private List<Pair<Integer, String>> categoryPairs;
    private int categoryId;

    public DialogNewTask(Result<TaskEntity> result) {
        super("dialog-new-task", "Add new task:");
        this.result = result;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void showDialog() {
        populateCategories();
        show();
    }

    public Integer tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
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
            Integer priorityAsNumber = tryParseInt(priority.getText());
            if (priorityAsNumber == null) {
                errorMessage.setText("Priority must be a number.");
                return;
            }

            TaskEntity newTask = new TaskEntity()
                    .setName(name.getText())
                    .setDescription(description.getText())
                    .setCategoryId(getCategoryId(category.getSelectionModel().getSelectedItem()))
                    .setUrl(url.getText())
                    .setPriority(priorityAsNumber);

            Pair<Boolean, String> response = newTask.create();
            boolean isSuccessfulFromApi = response.getKey();
            String errorMessageFromApi = response.getValue();

            result.setStatus(isSuccessfulFromApi);

            if (!result.isSuccessful()) {
                errorMessage.setText(errorMessageFromApi);
                return;
            }

            closeDialog();
        });
    }
}
