package personal.zaytiri.taskerlyze.ui.views.popups;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import personal.zaytiri.taskerlyze.ui.logic.entities.ProfileEntity;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MessageType;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.UiGlobalMessage;
import personal.zaytiri.taskerlyze.ui.views.popups.interfaces.Dialog;

public class DialogAddProfile extends Dialog<Boolean> {
    @FXML
    private TextField profileName;
    @FXML
    private Button buttonAdd;
    private int profileId;
    private EventHandler<ActionEvent> afterSuccessful;

    public DialogAddProfile() {
        super("dialog-add-profile", "New profile:");
    }

    public void setAfterSuccessful(EventHandler<ActionEvent> afterSuccessful) {
        this.afterSuccessful = afterSuccessful;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
        profileName.setText(new ProfileEntity(profileId).get().getName());
    }

    @Override
    protected void setOptionsBeforeShow() {
        buttonAdd.setOnAction(event -> {
            var profileNameText = profileName.getText();

            if (profileNameText.isEmpty()) {
                return;
            }

            var newProfile = new ProfileEntity();
            newProfile.setName(profileNameText);

            if (this.profileId != 0) {
                newProfile.setId(this.profileId);
            }

            var response = newProfile.createOrUpdate();

            boolean isSuccessfulFromApi = response.getValue().getKey();
            String messageFromApi = response.getValue().getValue();

            if (!isSuccessfulFromApi) {
                UiGlobalMessage.getUiGlobalMessage().setMessage(MessageType.ERROR, messageFromApi);
                return;
            }

            afterSuccessful.handle(new ActionEvent());

            result.setStatus(true);
            closeDialog();
        });
    }
}
