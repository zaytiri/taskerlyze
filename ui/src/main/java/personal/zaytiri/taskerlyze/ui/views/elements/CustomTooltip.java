package personal.zaytiri.taskerlyze.ui.views.elements;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tooltip;

import java.io.IOException;

public class CustomTooltip extends Tooltip {
    public CustomTooltip() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/custom-tooltip.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }
}
