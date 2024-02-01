package personal.zaytiri.taskerlyze.ui.views.popups;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import personal.zaytiri.taskerlyze.ui.views.popups.interfaces.Dialog;


public class DialogConfirmation extends Dialog<Boolean> {
    private EventHandler<ActionEvent> afterSuccessful;

    public DialogConfirmation() {
        super("Are you sure?");
    }

    public void setAfterSuccessful(EventHandler<ActionEvent> afterSuccessful) {
        this.afterSuccessful = afterSuccessful;
    }

    @Override
    protected void setOptionsBeforeShow() {
        MFXFontIcon warnIcon = new MFXFontIcon("fas-circle-exclamation", 18);
        dialogContent.setHeaderIcon(warnIcon);
        addDialogContentStyle("mfx-warn-dialog");
        
        addDialogAction(new MFXButton("Yes"), event -> {
            this.afterSuccessful.handle(new ActionEvent());
            closeDialog();
        });

        addDialogAction(new MFXButton("No"), event -> closeDialog());
    }
}
