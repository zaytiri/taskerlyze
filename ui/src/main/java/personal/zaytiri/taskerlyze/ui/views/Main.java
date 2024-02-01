package personal.zaytiri.taskerlyze.ui.views;

import io.github.palexdev.materialfx.theming.JavaFXThemes;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import personal.zaytiri.taskerlyze.ui.controllers.MainController;
import personal.zaytiri.taskerlyze.ui.logic.globals.Configuration;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Configuration.getInstance().setPrimaryStage(stage);

        MainController main = new MainController();
        main.setAppPreConfigurations();

        Configuration.getInstance().setMainPane(main.getMainPane());

        Parent root = main.getLoadedComponent();

        Scene scene = configureMainScene(root, stage);

        UserAgentBuilder.builder()
                .themes(JavaFXThemes.MODENA) // Optional if you don't need JavaFX's default theme, still recommended though
                .themes(MaterialFXStylesheets.forAssemble(true)) // Adds the MaterialFX's default theme. The boolean argument is to include legacy controls
                .setDeploy(true) // Whether to deploy each theme's assets on a temporary dir on the disk
                .setResolveAssets(true) // Whether to try resolving @import statements and resources urls
                .build() // Assembles all the added themes into a single CSSFragment (very powerful class check its documentation)
                .setGlobal(); // Finally, sets the produced stylesheet as the global User-Agent stylesheet

        stage.setScene(scene);
        stage.show();
    }

    private Scene configureMainScene(Parent root, Stage stage) {

        Pair<Double, Double> size = Configuration.getStageSize();
        Scene scene = new Scene(root, size.getValue(), size.getKey());
        Configuration.configureStage(stage, root);

        return scene;
    }
}