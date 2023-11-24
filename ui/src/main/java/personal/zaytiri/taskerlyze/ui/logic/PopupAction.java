package personal.zaytiri.taskerlyze.ui.logic;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import personal.zaytiri.taskerlyze.ui.components.DialogAddOrUpdateCategory;
import personal.zaytiri.taskerlyze.ui.components.DialogAddOrUpdateSubTask;
import personal.zaytiri.taskerlyze.ui.components.DialogAddOrUpdateTask;

public class PopupAction {
    private PopupAction() {
    }

    public static void showDialogForAddingCategory(EventHandler<ActionEvent> ifSuccessful) {
        DialogAddOrUpdateCategory dialog = new DialogAddOrUpdateCategory();
        dialog.showDialog();

        if (dialog.getResult().isSuccessful()) {
            ifSuccessful.handle(new ActionEvent());
        }
    }

    public static void showDialogForAddingSubTask(int taskId, EventHandler<ActionEvent> ifSuccessful) {
        DialogAddOrUpdateSubTask dialog = new DialogAddOrUpdateSubTask();
        dialog.setTaskId(taskId);
        dialog.showDialog();

        if (dialog.getResult().isSuccessful()) {
            ifSuccessful.handle(new ActionEvent());
        }
    }

    public static void showDialogForAddingTask(EventHandler<ActionEvent> ifSuccessful) {
        DialogAddOrUpdateTask dialog = new DialogAddOrUpdateTask();
        dialog.showDialog();

        if (dialog.getResult().isSuccessful()) {
            ifSuccessful.handle(new ActionEvent());
        }
    }

    public static void showDialogForEditingCategory(int id, EventHandler<ActionEvent> ifSuccessful) {
        DialogAddOrUpdateCategory dialog = new DialogAddOrUpdateCategory();
        dialog.setId(id);
        dialog.showDialog();

        if (dialog.getResult().isSuccessful()) {
            ifSuccessful.handle(new ActionEvent());
        }
    }

    public static void showDialogForEditingSubTask(int id, EventHandler<ActionEvent> ifSuccessful) {
        DialogAddOrUpdateSubTask dialog = new DialogAddOrUpdateSubTask();
        dialog.setId(id);
        dialog.showDialog();

        if (dialog.getResult().isSuccessful()) {
            ifSuccessful.handle(new ActionEvent());
        }
    }

    public static void showDialogForEditingTask(int id, EventHandler<ActionEvent> ifSuccessful) {
        DialogAddOrUpdateTask dialog = new DialogAddOrUpdateTask();
        dialog.setId(id);
        dialog.showDialog();

        if (dialog.getResult().isSuccessful()) {
            ifSuccessful.handle(new ActionEvent());
        }
    }
}
