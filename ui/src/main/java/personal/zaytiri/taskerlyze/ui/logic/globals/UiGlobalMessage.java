package personal.zaytiri.taskerlyze.ui.logic.globals;

import javafx.util.Pair;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MessageType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class UiGlobalMessage {
    private static UiGlobalMessage INSTANCE;
    private final PropertyChangeSupport support;

    private UiGlobalMessage() {
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public static UiGlobalMessage getUiGlobalMessage() {
        if (INSTANCE == null) {
            INSTANCE = new UiGlobalMessage();
        }
        return INSTANCE;
    }

    public void setMessage(MessageType type, String message) {
        support.firePropertyChange("message", new Pair<>(null, null), new Pair<>(type, message));
    }
}
