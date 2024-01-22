package personal.zaytiri.taskerlyze.ui.controllers;

import personal.zaytiri.taskerlyze.ui.logic.entities.SettingsEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.SettingsLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.UiGlobalSettings;

import java.util.List;

public class MainController {

    public void setAppPreConfigurations() {
        loadSettings();
    }

    private void loadSettings() {
        var loader = new SettingsLoader();
        loader.setSettingsId(1);
        List<SettingsEntity> settingsFromApi = loader.load();

        SettingsEntity settings = null;

        if (settingsFromApi.isEmpty()) {
            settings = new SettingsEntity()
                    .setDarkMode(true)
                    .setAlwaysOnTop(false)
                    .setShowAchievedPopup(true)
                    .setShowConfirmationPopup(true)
                    .setAutomaticTasks(false)
                    .setAutomaticReminders(false)
                    .setRemindTaskInDays(0)
                    .setRemindQuestionsInDays(0)
                    .setDefaultProfile(1);


            settings.createOrUpdate();
        } else {
            settings = settingsFromApi.get(0);
        }
        
        UiGlobalSettings.getUiGlobalMessage().setSettings(settings);
    }
}