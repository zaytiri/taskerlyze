package personal.zaytiri.taskerlyze.ui.views.popups;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.CategoryEntity;
import personal.zaytiri.taskerlyze.ui.views.popups.interfaces.Dialog;

public class DialogAddOrUpdateCategory extends Dialog<CategoryEntity> {
    @FXML
    public TextField name;
    @FXML
    public Button buttonCreate;
    @FXML
    public Label errorMessage;
    private CategoryEntity newOrExistingCategory;

    public DialogAddOrUpdateCategory() {
        super("dialog-new-category", "Add new category:");
    }

    @Override
    public void showDialog() {
        populate();
        show();
    }

    @FXML
    private void initialize() {
        setOnActionCreateButton();
    }

    private void populate() {
        if (this.id == 0) {
            newOrExistingCategory = new CategoryEntity();
            return;
        }

        newOrExistingCategory = new CategoryEntity().get(this.id);
        name.setText(newOrExistingCategory.getName());
    }

    private void setOnActionCreateButton() {
        buttonCreate.setOnAction(event -> {
            newOrExistingCategory
                    .setName(name.getText());

            Pair<CategoryEntity, Pair<Boolean, String>> response = newOrExistingCategory.createOrUpdate();
            boolean isSuccessfulFromApi = response.getValue().getKey();
            String errorMessageFromApi = response.getValue().getValue();

            result.setStatus(isSuccessfulFromApi);

            if (!result.isSuccessful()) {
                errorMessage.setText(errorMessageFromApi);
                return;
            }

            result.setResult(response.getKey());

            closeDialog();
        });
    }
}
