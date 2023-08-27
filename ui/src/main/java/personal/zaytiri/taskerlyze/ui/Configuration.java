package personal.zaytiri.taskerlyze.ui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import java.awt.*;

public class Configuration {
    private Configuration() {
    }

    public static void configureStage(Stage stage, Parent root){
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        Pair<Double, Double> size = getStageSize();
        positionStageAtRightSide(stage, size.getValue(), size.getKey());

        setStageDraggable(root, stage);
    }

    private static void positionStageAtRightSide(Stage stage, double width, double height){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - width - 10);
        stage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - height - 12.5);
    }

    public static Pair<Double, Double> getStageSize(){
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double height = screenSize.getHeight() - 25;
        double width = 450;
        return new Pair<>(height, width);
    }

    private static void setStageDraggable(Parent root, Stage stage){
        root.setOnMousePressed(pressEvent -> root.setOnMouseDragged(dragEvent -> {
            stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));
    }

}