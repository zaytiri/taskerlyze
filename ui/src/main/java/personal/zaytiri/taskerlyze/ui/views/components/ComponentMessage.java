package personal.zaytiri.taskerlyze.ui.views.components;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MessageType;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.UiGlobalMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ComponentMessage extends TitledPane implements PropertyChangeListener {
    @FXML
    private TitledPane messageTitledPane;
    @FXML
    private Label messageType;
    @FXML
    private VBox messageContent;

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
        if (evt.getPropertyName() != "message") {
            return;
        }

        Pair<MessageType, String> message = (Pair<MessageType, String>) evt.getNewValue();

        this.messageType.setText(String.valueOf(message.getKey()));
        this.messageContent.getChildren().add(new Label(message.getValue()));

        showMessageDisplay(true);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                FadeTransition ft = new FadeTransition(Duration.millis(3000), messageTitledPane);
                ft.setFromValue(1.0);
                ft.setToValue(0);
                ft.setAutoReverse(true);
                ft.play();
                ft.setOnFinished(event -> showMessageDisplay(true));
            }
        }, 10 * (long) 1000); // 10 seconds
    }

    @FXML
    private void initialize() {
        UiGlobalMessage.getUiGlobalMessage().addPropertyChangeListener(this);
    }

    private void showMessageDisplay(boolean show) {
        messageTitledPane.setVisible(show);
        messageTitledPane.setManaged(show);
        messageTitledPane.setDisable(!show);
    }
}
