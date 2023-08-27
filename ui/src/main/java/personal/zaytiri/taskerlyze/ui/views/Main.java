package personal.zaytiri.taskerlyze.ui.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import personal.zaytiri.taskerlyze.ui.Configuration;
import personal.zaytiri.taskerlyze.ui.controllers.MainController;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/main.fxml"));
        Parent root = loader.load();


        Scene scene = configureMainScene(root, stage);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private Scene configureMainScene(Parent root, Stage stage){

        Pair<Double, Double> size = Configuration.getStageSize();
        Scene scene = new Scene(root, size.getValue(), size.getKey());
        Configuration.configureStage(stage, root);

        return scene;
    }
}