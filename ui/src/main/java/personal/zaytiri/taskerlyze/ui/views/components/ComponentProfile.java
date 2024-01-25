package personal.zaytiri.taskerlyze.ui.views.components;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import personal.zaytiri.taskerlyze.ui.logic.entities.ProfileEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.ProfileLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.IdentifiableItem;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.UiGlobalFilter;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.UiGlobalSettings;

import java.io.IOException;

public class ComponentProfile extends AnchorPane {
    private final ObservableList<IdentifiableItem<String>> profiles = FXCollections.observableArrayList();
    @FXML
    private MFXComboBox<IdentifiableItem<String>> switchProfileOptions;

    public ComponentProfile() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component-profile.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }


    private void displayDefaultProfile() {
        var profileId = UiGlobalFilter.getUiGlobalFilter().getActiveProfileId();

        if (profileId == 0) {
            profileId = UiGlobalSettings.getUiGlobalMessage().getDefaultProfile();
        }

        IdentifiableItem<String> selectedItem = null;
        for (IdentifiableItem<String> iden : profiles) {
            if (iden.getItemId() == profileId) {
                selectedItem = iden;
            }
        }
        this.switchProfileOptions.selectItem(selectedItem);
    }

    @FXML
    private void initialize() {
        populateProfiles();
        displayDefaultProfile();

        switchProfileOptions.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (switchProfileOptions.getSelectedItem() == null) {
                return;
            }

            UiGlobalFilter.getUiGlobalFilter().setActiveProfileId(switchProfileOptions.getSelectedItem().getItemId());
        });
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
        switchProfileOptions.setItems(profiles);
        switchProfileOptions.setConverter(converter);
    }
}
