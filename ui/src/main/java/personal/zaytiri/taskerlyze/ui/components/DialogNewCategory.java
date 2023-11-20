package personal.zaytiri.taskerlyze.ui.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.ui.logic.Configuration;
import personal.zaytiri.taskerlyze.ui.logic.entities.CategoryEntity;
import personal.zaytiri.taskerlyze.ui.logic.entities.Result;

import java.io.IOException;

public class DialogNewCategory extends DialogPane {
    private final Stage stage;
    private final Stage primaryStage;
    @FXML
    public TextField textField;
    @FXML
    public ButtonType buttonTypeCreate;
    Result<Category> result;

    public DialogNewCategory(Result<Category> result) {
        this.primaryStage = Configuration.getInstance().getPrimaryStage();
        this.result = result;

        stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/dialog-new-category.fxml"));
            loader.setRoot(this);
            loader.setController(this);

            stage.setScene(new Scene(loader.load()));
            stage.setTitle("New Category");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
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
        stage.showAndWait();
    }

    @FXML
    private void initialize() {
        setOnActionCreateButton();
    }

    private void setOnActionCreateButton() {
        Button btn = (Button) lookupButton(buttonTypeCreate);
        btn.setOnAction(event -> {
            CategoryEntity newCat = new CategoryEntity()
                    .setName(textField.getText());

            result.setStatus(newCat.createOrUpdate());
            stage.close();
        });
    }
}
