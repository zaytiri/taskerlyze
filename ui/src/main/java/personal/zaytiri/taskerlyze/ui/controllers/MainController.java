package personal.zaytiri.taskerlyze.ui.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import personal.zaytiri.taskerlyze.ui.logic.entities.ProfileEntity;
import personal.zaytiri.taskerlyze.ui.logic.entities.SettingsEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.ProfileLoader;
import personal.zaytiri.taskerlyze.ui.logic.loaders.SettingsLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.UiGlobalFilter;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.UiGlobalSettings;
import personal.zaytiri.taskerlyze.ui.views.popups.DialogAddProfile;

import java.io.IOException;
import java.util.List;

public class MainController extends AnchorPane {
    private final Parent loadedComponent;

    public MainController() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/main.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        setAppPreConfigurations();
        try {
            loadedComponent = loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public Parent getLoadedComponent() {
        return loadedComponent;
    }

    public void setAppPreConfigurations() {
        loadSettings();
        checkProfile();
    }

    private void checkProfile() {
        var loader = new ProfileLoader();
        List<ProfileEntity> profilesFromApi = loader.load();

        if (profilesFromApi.isEmpty()) {
            DialogAddProfile addProfile = new DialogAddProfile();
            addProfile.setAfterSuccessful(event -> {
                UiGlobalFilter.getUiGlobalFilter().setActiveProfileId(1);
            });
            addProfile.showDialog();
            return;
        }

        UiGlobalFilter.getUiGlobalFilter().setActiveProfileId(UiGlobalSettings.getUiGlobalMessage().getDefaultProfile());
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