package personal.zaytiri.taskerlyze.ui.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.CategoryEntity;
import personal.zaytiri.taskerlyze.ui.logic.entities.Result;

public class DialogNewCategory extends Dialog {
    @FXML
    public TextField name;
    @FXML
    public Button buttonCreate;
    @FXML
    public Label errorMessage;
    Result<Category> result;

    public DialogNewCategory(Result<Category> result) {
        super("dialog-new-category", "Add new category:");
        this.result = result;
    }

    @Override
    public void showDialog() {
        show();
    }

    @FXML
    private void initialize() {
        setOnActionCreateButton();
    }

    private void setOnActionCreateButton() {
        buttonCreate.setOnAction(event -> {
            CategoryEntity newCat = new CategoryEntity()
                    .setName(name.getText());

            Pair<Boolean, String> response = newCat.create();
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
