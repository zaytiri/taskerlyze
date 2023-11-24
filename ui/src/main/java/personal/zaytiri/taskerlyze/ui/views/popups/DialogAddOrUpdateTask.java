package personal.zaytiri.taskerlyze.ui.views.popups;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.CategoryEntity;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.CategoryLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.Dialog;

import java.util.ArrayList;
import java.util.List;

public class DialogAddOrUpdateTask extends Dialog<TaskEntity> {
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
    private List<Pair<Integer, String>> categoryPairs;
    private int categoryId;
    private TaskEntity newOrExistingTask;

    public DialogAddOrUpdateTask() {
        super("dialog-new-task", "Add new task:");
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void showDialog() {
        populate();
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

    private void populate() {
        if (this.id == 0) {
            newOrExistingTask = new TaskEntity();
            return;
        }

        newOrExistingTask = new TaskEntity().get(this.id);

        name.setText(newOrExistingTask.getName());
        setCategoryId(newOrExistingTask.getCategoryId());
        description.setText(newOrExistingTask.getDescription());
        url.setText(newOrExistingTask.getUrl());
        priority.setText(String.valueOf(newOrExistingTask.getPriority()));
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

            newOrExistingTask
                    .setName(name.getText())
                    .setDescription(description.getText())
                    .setCategoryId(getCategoryId(category.getSelectionModel().getSelectedItem()))
                    .setUrl(url.getText())
                    .setPriority(priorityAsNumber);

            Pair<TaskEntity, Pair<Boolean, String>> response = newOrExistingTask.create();
            boolean isSuccessfulFromApi = response.getValue().getKey();
            String errorMessageFromApi = response.getValue().getValue();

            result.setStatus(isSuccessfulFromApi);

            if (!result.isSuccessful()) {
                errorMessage.setText(errorMessageFromApi);
                return;
            }

            closeDialog();
        });
    }
}
