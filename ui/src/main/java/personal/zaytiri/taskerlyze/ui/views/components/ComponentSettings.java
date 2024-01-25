package personal.zaytiri.taskerlyze.ui.views.components;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.ProfileEntity;
import personal.zaytiri.taskerlyze.ui.logic.entities.SettingsEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.ProfileLoader;
import personal.zaytiri.taskerlyze.ui.logic.loaders.SettingsLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.*;
import personal.zaytiri.taskerlyze.ui.views.popups.DialogAddProfile;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ComponentSettings extends AnchorPane {
    private final ObservableList<IdentifiableItem<String>> profiles = FXCollections.observableArrayList();
    @FXML
    private MFXButton editProfileButton;
    @FXML
    private MFXComboBox<IdentifiableItem<String>> defaultProfile;
    @FXML
    private MFXComboBox<IdentifiableItem<String>> editProfileOptions;
    @FXML
    private MFXComboBox<IdentifiableItem<String>> deleteProfileOptions;
    @FXML
    private MFXButton newProfileButton;
    @FXML
    private MFXButton deleteProfileButton;
    @FXML
    private MFXButton openProgramSchedulerUrl;
    @FXML
    private MFXToggleButton enableDarkMode;
    private SettingsEntity settings = new SettingsEntity();
    @FXML
    private MFXToggleButton enableAlwaysOnTop;
    @FXML
    private MFXToggleButton enablePopUpAchieved;
    @FXML
    private MFXToggleButton enableConfirmationPopup;
    @FXML
    private MFXToggleButton enableAutomaticTasks;
    @FXML
    private MFXToggleButton enableAutomaticReminders;
    @FXML
    private MFXTextField remindTasksInDays;
    @FXML
    private MFXTextField remindQuestionsInDays;
    @FXML
    private MFXButton deleteButton;
    @FXML
    private MFXButton importButton;
    @FXML
    private MFXButton exportButton;
    @FXML
    private MFXButton saveButton;
    private ComponentMainView mainView;

    public ComponentSettings() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component-settings.fxml"));

        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    @FXML
    public void initialize() {
        enableAutomaticReminders.selectedProperty().addListener((observable, oldValue, newValue) -> {
            remindTasksInDays.setDisable(!newValue);
            remindQuestionsInDays.setDisable(!newValue);
        });

        saveButton.setOnAction(event -> saveSettings());
        deleteButton.setOnAction(event -> PopupAction.showConfirmationDialog(
                "You are about to delete ALL DATA. This action is IRREVERSIBLE.",
                evt -> {
                    this.settings.deleteData();
                    mainView.initialize();
                },
                false
        ));
        newProfileButton.setOnAction(event -> {
            DialogAddProfile addProfile = new DialogAddProfile();
            addProfile.setAfterSuccessful(evt -> populateProfiles());
            addProfile.showDialog();
        });
        editProfileButton.setOnAction(event -> {
            if (editProfileOptions.getSelectedItem() == null) {
                return;
            }

            DialogAddProfile addProfile = new DialogAddProfile();
            addProfile.setProfileId(editProfileOptions.getSelectedItem().getItemId());
            addProfile.setAfterSuccessful(evt -> populateProfiles());
            addProfile.showDialog();
        });
        deleteProfileButton.setOnAction(event -> {
            if (deleteProfileOptions.getSelectedItem() == null) {
                return;
            }

            if (profiles.size() == 1) {
                UiGlobalMessage.getUiGlobalMessage().setMessage(
                        MessageType.ERROR,
                        "There must be at least one profile created. Cannot delete last profile.");
                return;
            }
            PopupAction.showConfirmationDialog(
                    "You are about to delete the following profile: " + deleteProfileOptions.getSelectedItem().getItemDisplay() + ". This action is IRREVERSIBLE.",
                    evt -> {
                        ProfileEntity toDelete = new ProfileEntity();
                        if (toDelete.remove()) {
                            populateProfiles();
                        }
                    },
                    false
            );
        });
        openProgramSchedulerUrl.setOnAction(event -> {
            String taskUrl = "https://github.com/zaytiri/program-scheduler";
            try {
                Desktop.getDesktop().browse(new URL(taskUrl).toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        populateProfiles();
    }

    public void load() {
        var loader = new SettingsLoader();
        loader.setSettingsId(1);
        List<SettingsEntity> settings = loader.load();

        if (settings.isEmpty()) {
            return;
        }
        this.settings = settings.get(0);

        enableDarkMode.setSelected(this.settings.isDarkMode());
        enableAlwaysOnTop.setSelected(this.settings.isAlwaysOnTop());
        enablePopUpAchieved.setSelected(this.settings.isShowAchievedPopup());
        enableConfirmationPopup.setSelected(this.settings.isShowConfirmationPopup());
        enableAutomaticTasks.setSelected(this.settings.isAutomaticTasks());
        enableAutomaticReminders.setSelected(this.settings.isAutomaticReminders());

        remindTasksInDays.setDisable(!enableAutomaticReminders.isSelected());
        remindQuestionsInDays.setDisable(!enableAutomaticReminders.isSelected());

        remindTasksInDays.setText(String.valueOf(this.settings.getRemindTaskInDays()));
        remindQuestionsInDays.setText(String.valueOf(this.settings.getRemindQuestionsInDays()));

        selectCurrentDefaultProfile();
    }

    public void setComponentMainView(ComponentMainView mainView) {
        this.mainView = mainView;
    }

    public int tryParseInt(String value, int defaultVal) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    private void getSettingsToSave() {
        this.settings
                .setDarkMode(enableDarkMode.isSelected())
                .setAlwaysOnTop(enableAlwaysOnTop.isSelected())
                .setShowAchievedPopup(enablePopUpAchieved.isSelected())
                .setShowConfirmationPopup(enableConfirmationPopup.isSelected())
                .setAutomaticTasks(enableAutomaticTasks.isSelected())
                .setAutomaticReminders(enableAutomaticReminders.isSelected())
                .setRemindTaskInDays(Integer.parseInt(remindTasksInDays.getText()))
                .setRemindQuestionsInDays(Integer.parseInt(remindQuestionsInDays.getText()))
                .setDefaultProfile(defaultProfile.getSelectedItem().getItemId());
    }

    private boolean isTextFieldNumbersOnly(MFXTextField textField) {
        int parsedInt = tryParseInt(textField.getText(), 0);
        return parsedInt != 0;
    }

    private void populateProfiles() {
        ProfileLoader loader = new ProfileLoader();

        profiles.clear();

        for (ProfileEntity prof : loader.load()) {
            var identifier = new IdentifiableItem<String>();
            identifier.setItemId(prof.getId());
            identifier.setItemDisplay(prof.getName());
            profiles.add(identifier);
        }

        StringConverter<IdentifiableItem<String>> converter = FunctionalStringConverter.to(profile -> (profile == null) ? "" : profile.getItemDisplay());
        defaultProfile.setItems(profiles);
        defaultProfile.setConverter(converter);

        editProfileOptions.setItems(profiles);
        editProfileOptions.setConverter(converter);

        deleteProfileOptions.setItems(profiles);
        deleteProfileOptions.setConverter(converter);
    }

    private void saveSettings() {
        if (enableAutomaticReminders.isSelected() && !(isTextFieldNumbersOnly(remindTasksInDays) || isTextFieldNumbersOnly(remindQuestionsInDays))) {
            UiGlobalMessage.getUiGlobalMessage().setMessage(MessageType.ERROR, "Input can only have numbers.");
            return;
        }

        getSettingsToSave();

        Pair<SettingsEntity, Pair<Boolean, String>> result = this.settings.createOrUpdate();

        if (Boolean.FALSE.equals(result.getValue().getKey())) {
            UiGlobalMessage.getUiGlobalMessage().setMessage(MessageType.ERROR, result.getValue().getValue());
        }

        UiGlobalSettings.getUiGlobalMessage().setSettings(settings);
        UiGlobalMessage.getUiGlobalMessage().setMessage(MessageType.NEUTRAL, result.getValue().getValue());
    }

    private void selectCurrentDefaultProfile() {

        if (this.settings.getDefaultProfile() == 0) {
            return;
        }

        IdentifiableItem<String> selectedItem = null;
        for (IdentifiableItem<String> iden : profiles) {
            if (iden.getItemId() == this.settings.getDefaultProfile()) {
                selectedItem = iden;
            }
        }
        defaultProfile.selectItem(selectedItem);
    }
}
