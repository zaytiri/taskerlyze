package personal.zaytiri.taskerlyze.ui.logic.loaders;

import personal.zaytiri.taskerlyze.app.api.controllers.SettingsController;
import personal.zaytiri.taskerlyze.app.api.domain.Settings;
import personal.zaytiri.taskerlyze.ui.logic.entities.SettingsEntity;
import personal.zaytiri.taskerlyze.ui.logic.mappers.SettingsMapper;

import java.util.ArrayList;
import java.util.List;

public class SettingsLoader implements Loadable<SettingsEntity> {
    private int settingsId;

    @Override
    public List<SettingsEntity> load() {
        SettingsEntity settingsToBeReturned = new SettingsEntity();

        Settings settings = new SettingsController().get(settingsId).getResult();

        List<SettingsEntity> allSettings = new ArrayList<>();

        if (settings != null) {
            allSettings.add(new SettingsMapper().mapToUiObject(settings, settingsToBeReturned));
        }

        return allSettings;
    }

    public void setSettingsId(int settingsId) {
        this.settingsId = settingsId;
    }
}
