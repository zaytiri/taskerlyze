package personal.zaytiri.taskerlyze.ui.logic.loaders;

import personal.zaytiri.taskerlyze.app.api.controllers.SettingsController;
import personal.zaytiri.taskerlyze.app.api.domain.Settings;
import personal.zaytiri.taskerlyze.ui.logic.entities.SettingsEntity;
import personal.zaytiri.taskerlyze.ui.logic.mappers.SettingsMapper;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SettingsLoader {
    private static SettingsLoader INSTANCE;
    private final PropertyChangeSupport support;
    private SettingsEntity loadedSettings = new SettingsEntity();

    private SettingsLoader() {
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public static SettingsLoader getSettingsLoader() {
        if (INSTANCE == null) {
            INSTANCE = new SettingsLoader();
        }
        return INSTANCE;
    }

    public SettingsEntity load() {
        SettingsEntity settingsToBeReturned = new SettingsEntity();

        Settings settings = new SettingsController().get(0).getResult();

        settingsToBeReturned = new SettingsMapper().mapToUiObject(settings, settingsToBeReturned);

        support.firePropertyChange("loadedSettings", loadedSettings, settingsToBeReturned);

        loadedSettings = settingsToBeReturned;

        return loadedSettings;
    }

}
