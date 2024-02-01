package personal.zaytiri.taskerlyze.ui.logic.globals;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import java.awt.*;

public class Configuration {
    private static Configuration INSTANCE;
    private Stage primaryStage;
    private AnchorPane mainPane;

    private Configuration() {
    }

    public static void configureStage(Stage stage, Parent root) {
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        Pair<Double, Double> size = getStageSize();
        positionStageAtRightSide(stage, size.getValue(), size.getKey());

        setStageDraggable(root, stage);
    }

    public static Configuration getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Configuration();
        }
        return INSTANCE;
    }

    public AnchorPane getMainPane() {
        return mainPane;
    }

    public void setMainPane(AnchorPane mainPane) {
        this.mainPane = mainPane;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage currentPrimaryStage) {
        primaryStage = currentPrimaryStage;
    }

    public static Pair<Double, Double> getStageSize() {
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double height = screenSize.getHeight() - 25;
        double width = 520;
        return new Pair<>(height, width);
    }

    private static void positionStageAtRightSide(Stage stage, double width, double height) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - width - 10);
        stage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - height - 12.5);
    }

    private static void setStageDraggable(Parent root, Stage stage) {
        root.setOnMousePressed(pressEvent -> root.setOnMouseDragged(dragEvent -> {
            stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));
    }

}
