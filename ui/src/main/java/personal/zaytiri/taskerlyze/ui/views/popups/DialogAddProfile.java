package personal.zaytiri.taskerlyze.ui.views.popups;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import personal.zaytiri.taskerlyze.ui.logic.entities.ProfileEntity;
import personal.zaytiri.taskerlyze.ui.logic.globals.UiGlobalMessage;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MessageType;
import personal.zaytiri.taskerlyze.ui.views.elements.AddText;
import personal.zaytiri.taskerlyze.ui.views.popups.interfaces.Dialog;

public class DialogAddProfile extends Dialog<Boolean> {
    private final AddText achieved;
    private int profileId;
    private EventHandler<ActionEvent> afterSuccessful;

    public DialogAddProfile() {
        super("Add profile:");

        achieved = new AddText();
        achieved.setDescription("You are about to create a new profile. Set profile's name:");

    }

    public void setAfterSuccessful(EventHandler<ActionEvent> afterSuccessful) {
        this.afterSuccessful = afterSuccessful;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
        achieved.setInput(new ProfileEntity(profileId).get().getName());
    }

    @Override
    protected void setOptionsBeforeShow() {

        MFXFontIcon warnIcon = new MFXFontIcon("fas-circle-info", 18);
        dialogContent.setHeaderIcon(warnIcon);
        addDialogContentStyle("mfx-info-dialog");

        setDialogContentNode(achieved);

        addDialogAction(new MFXButton("Add"), event -> {
            addProfile();
            closeDialog();
        });
    }

    private void addProfile() {
        var profileNameText = achieved.getInput();

        if (profileNameText.isEmpty()) {
            return;
        }

        var newProfile = new ProfileEntity(this.profileId);
        newProfile.setName(profileNameText);

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
    }
}
