package personal.zaytiri.taskerlyze.ui.views.elements;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AddText extends VBox {
    @FXML
    private MFXTextField textField;
    @FXML
    private Label label;

    public AddText() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/add-text.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public String getInput() {
        return textField.getText();
    }

    public void setInput(String input) {
        this.textField.setText(input);
    }

    public void setDescription(String text) {
        this.label.setText(text);
    }
}
