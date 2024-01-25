package personal.zaytiri.taskerlyze.ui.views.components;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.SettingsEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.SettingsLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MessageType;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.UiGlobalMessage;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.UiGlobalSettings;
import personal.zaytiri.taskerlyze.ui.views.popups.DialogConfirmation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentSettings extends AnchorPane {
    private final Map<Integer, String> profiles = new HashMap<>();
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
    private MFXComboBox<String> defaultProfile;
    @FXML
    private MFXButton openProgramSchedulerUrl;
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

    public int findProfileIdByName(String value) {

        for (Map.Entry<Integer, String> profile : profiles.entrySet()) {
            if (profile.getValue().equals(value)) {
                return profile.getKey();
            }
        }
        return 0;
    }

    @FXML
    public void initialize() {
        enableAutomaticReminders.selectedProperty().addListener((observable, oldValue, newValue) -> {
            remindTasksInDays.setDisable(!newValue);
            remindQuestionsInDays.setDisable(!newValue);
        });

        saveButton.setOnAction(event -> saveSettings());
        deleteButton.setOnAction(event -> {
            DialogConfirmation dialogConfirmation = new DialogConfirmation();
            dialogConfirmation.setMessage("You are about to delete ALL DATA.");
            dialogConfirmation.setAfterSuccessful(evt -> {
                this.settings.deleteData();
                mainView.initialize();
            });
            dialogConfirmation.showDialog();
        });

    }

    public void load() {
        var loader = new SettingsLoader();
        loader.setSettingsId(1);
        List<SettingsEntity> settings = loader.load();

        populateProfiles();
        var observableProfiles = FXCollections.observableList(new ArrayList<String>());
        for (Map.Entry<Integer, String> profile : profiles.entrySet()) {
            observableProfiles.add(profile.getValue());
        }
        defaultProfile.setItems(observableProfiles);
        defaultProfile.selectItem(profiles.get(2));

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
                .setDefaultProfile(findProfileIdByName(defaultProfile.getSelectedItem()));
    }

    private boolean isTextFieldNumbersOnly(MFXTextField textField) {
        int parsedInt = tryParseInt(textField.getText(), 0);
        return parsedInt != 0;
    }

    private void populateProfiles() {
//        CategoryLoader loader = new CategoryLoader();
//
//        setDefaultCategoryAsArchive();
//
//        for (CategoryEntity cat : loader.load()) {
//            IdentifiableItem<String> item = new IdentifiableItem<>();
//            item.setItemId(cat.getId());
//            item.setItemDisplay(cat.getName());
//
//            category.getItems().add(item);
//
//            if (item.getItemId() == categoryId) {
//                category.getSelectionModel().select(item);
//            }
//        }
        profiles.put(1, "profile 1");
        profiles.put(2, "profile 2");
        profiles.put(3, "profile 3");
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
}
