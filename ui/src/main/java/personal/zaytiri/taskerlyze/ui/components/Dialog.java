package personal.zaytiri.taskerlyze.ui.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import personal.zaytiri.taskerlyze.ui.logic.Configuration;
import personal.zaytiri.taskerlyze.ui.logic.entities.Result;

import java.io.IOException;

public abstract class Dialog<T> extends AnchorPane {
    private final Stage primaryStage;
    private final Stage stage;
    protected Result<T> result = new Result<>();
    protected int id;

    protected Dialog(String fileName, String dialogTitle) {
        this.primaryStage = Configuration.getInstance().getPrimaryStage();
        stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/" + fileName + ".fxml"));
            loader.setRoot(this);
            loader.setController(this);

            stage.setScene(new Scene(loader.load()));
            stage.setTitle(dialogTitle);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Result<T> getResult() {
        return this.result;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract void showDialog();

    protected void closeDialog() {
        stage.close();
    }

    protected void show() {
        setStageSize();
        stage.showAndWait();
    }

    private void setStageSize() {
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
}
