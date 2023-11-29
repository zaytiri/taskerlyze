package personal.zaytiri.taskerlyze.ui.views.popups;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.CategoryEntity;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.CategoryLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.IdentifiableItem;
import personal.zaytiri.taskerlyze.ui.views.popups.interfaces.Dialog;

public class DialogAddOrUpdateTask extends Dialog<TaskEntity> {
    @FXML
    public TextField name;
    @FXML
    public ComboBox<IdentifiableItem<String>> category;
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

    @FXML
    private void initialize() {
        overrideIdentifiableItemDisplayInListView();
        setOnActionCreateButton();
    }

    private void overrideIdentifiableItemDisplayInListView() {
        category.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(IdentifiableItem<String> item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getItemDisplay() == null) {
                    setText(null);
                } else {
                    setText(item.getItemDisplay());
                }
            }
        });
        category.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(IdentifiableItem<String> item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getItemDisplay() == null) {
                    setText(null);
                } else {
                    setText(item.getItemDisplay());
                }
            }
        });
    }

    private void populate() {
        if (this.id == 0) {
            newOrExistingTask = new TaskEntity();
            return;
        }

        newOrExistingTask = new TaskEntity(this.id).get();

        name.setText(newOrExistingTask.getName());
        setCategoryId(newOrExistingTask.getCategoryId());
        description.setText(newOrExistingTask.getDescription());
        url.setText(newOrExistingTask.getUrl());
        priority.setText(String.valueOf(newOrExistingTask.getPriority()));
    }

    private void populateCategories() {
        CategoryLoader loader = new CategoryLoader();

        setDefaultCategoryAsArchive();

        for (CategoryEntity cat : loader.load()) {
            IdentifiableItem<String> item = new IdentifiableItem<>();
            item.setItemId(cat.getId());
            item.setItemDisplay(cat.getName());

            category.getItems().add(item);

            if (item.getItemId() == categoryId) {
                category.getSelectionModel().select(item);
            }
        }
    }

    private void setDefaultCategoryAsArchive() {
        IdentifiableItem<String> defaultItem = new IdentifiableItem<>();
        defaultItem.setItemId(0);
        defaultItem.setItemDisplay("Archive");
        category.getItems().add(defaultItem);
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
                    .setCategoryId(category.getSelectionModel().getSelectedItem().getItemId())
                    .setUrl(url.getText())
                    .setPriority(priorityAsNumber);

            Pair<TaskEntity, Pair<Boolean, String>> response = newOrExistingTask.createOrUpdate();
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
