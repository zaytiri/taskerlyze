package personal.zaytiri.taskerlyze.ui.views.components;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.FadeAnimation;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MessageType;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.UiGlobalMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Objects;

public class ComponentMessage extends TitledPane implements PropertyChangeListener {
    private final FadeAnimation fadeAnimation = new FadeAnimation();
    @FXML
    private TitledPane messageTitledPane;
    @FXML
    private Label messageType;
    @FXML
    private Label messageContent;

    public ComponentMessage() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component-message.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!Objects.equals(evt.getPropertyName(), "message")) {
            return;
        }

        Pair<MessageType, String> message = (Pair<MessageType, String>) evt.getNewValue();

        this.messageType.setText(String.valueOf(message.getKey()));
        this.messageContent.setText(message.getValue());

        showMessageDisplay(true);
        messageTitledPane.setExpanded(true);

        fadeAnimation.fadeOut(1000, 5 * (long) 1000, this.messageTitledPane, whenFadeFinished -> {
            showMessageDisplay(false);
            DoubleProperty opacity = this.messageTitledPane.opacityProperty();
            opacity.set(1);
        });
    }

    @FXML
    private void initialize() {
        showMessageDisplay(false);
        UiGlobalMessage.getUiGlobalMessage().addPropertyChangeListener(this);
    }

    private void showMessageDisplay(boolean show) {
        messageTitledPane.setVisible(show);
        messageTitledPane.setManaged(show);
        messageTitledPane.setDisable(!show);
    }
}
