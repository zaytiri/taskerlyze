package personal.zaytiri.taskerlyze.ui.views.popups;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.globals.UiGlobalMessage;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MessageType;
import personal.zaytiri.taskerlyze.ui.views.elements.AddText;
import personal.zaytiri.taskerlyze.ui.views.popups.interfaces.Dialog;

public class DialogAddAchieved extends Dialog<Boolean> {
    private final AddText achieved;
    private int taskId;

    public DialogAddAchieved() {
        super("Set achieved:");

        achieved = new AddText();
        achieved.setDescription("The task has been assigned as COMPLETED. Describe, with few words, what has been achieved by completing this task. You can do this anytime when editing a task.");
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    protected void setOptionsBeforeShow() {
        MFXFontIcon warnIcon = new MFXFontIcon("fas-circle-info", 18);
        dialogContent.setHeaderIcon(warnIcon);
        addDialogContentStyle("mfx-info-dialog");

        setDialogContentNode(achieved);

        addDialogAction(new MFXButton("Add"), event -> {
            addAchievedField();
            closeDialog();
        });
    }

    private void addAchievedField() {
        var achievedText = achieved.getInput();

        if (achievedText.isEmpty()) {
            return;
        }

        var newTask = new TaskEntity(this.taskId).get();
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
    }
}
