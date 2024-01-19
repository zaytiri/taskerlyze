package personal.zaytiri.taskerlyze.ui.views.popups;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MessageType;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.UiGlobalMessage;
import personal.zaytiri.taskerlyze.ui.views.popups.interfaces.Dialog;

public class DialogAddAchieved extends Dialog<Boolean> {
    @FXML
    private TextField achieved;
    @FXML
    private Button buttonAdd;
    private int taskId;

    public DialogAddAchieved() {
        super("dialog-add-achieved", "Achieved:");
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    protected void setOptionsBeforeShow() {
        buttonAdd.setOnAction(event -> {
            var achievedText = achieved.getText();

            if (achievedText.isEmpty()) {
                return;
            }

            var newTask = new TaskEntity(this.taskId);
            newTask.setAchieved(achievedText);

            var response = newTask.update();

            boolean isSuccessfulFromApi = response.getValue().getKey();
            String messageFromApi = response.getValue().getValue();

            if (!isSuccessfulFromApi) {
                UiGlobalMessage.getUiGlobalMessage().setMessage(MessageType.ERROR, messageFromApi);
                return;
            }

            result.setStatus(true);
            closeDialog();
        });
    }
}
