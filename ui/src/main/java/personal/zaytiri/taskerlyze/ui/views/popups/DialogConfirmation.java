package personal.zaytiri.taskerlyze.ui.views.popups;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import personal.zaytiri.taskerlyze.ui.views.popups.interfaces.Dialog;


public class DialogConfirmation extends Dialog<Boolean> {
    private EventHandler<ActionEvent> afterSuccessful;
    @FXML
    private Label dialogContent;
    @FXML
    private Button buttonYes;
    @FXML
    private Button buttonNo;

    public DialogConfirmation() {
        super("dialog-confirmation", "Are you sure?");
    }

    @FXML
    public void initialize() {
        buttonNo.setOnAction(e -> closeDialog());
    }

    public void setAfterSuccessful(EventHandler<ActionEvent> afterSuccessful) {
        this.afterSuccessful = afterSuccessful;
    }

    public void setMessage(String message) {
        dialogContent.setText(message);
    }

    @Override
    protected void setOptionsBeforeShow() {
        buttonYes.setOnAction(e -> {
            this.afterSuccessful.handle(new ActionEvent());
            closeDialog();
        });
    }
}
