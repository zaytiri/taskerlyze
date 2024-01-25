package personal.zaytiri.taskerlyze.ui.logic.uifuncionality;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class GlobalProfiles {
    private static GlobalProfiles INSTANCE;
    private final PropertyChangeSupport support;

    private GlobalProfiles() {
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public static GlobalProfiles getGlobalProfiles() {
        if (INSTANCE == null) {
            INSTANCE = new GlobalProfiles();
        }
        return INSTANCE;
    }

    public void setProfiles(List<IdentifiableItem<String>> profiles) {
        support.firePropertyChange("profiles", new ArrayList<IdentifiableItem<String>>(), profiles);
    }
}
