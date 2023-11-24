package personal.zaytiri.taskerlyze.ui.logic;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import personal.zaytiri.taskerlyze.ui.components.DialogNewCategory;
import personal.zaytiri.taskerlyze.ui.components.DialogNewSubTask;
import personal.zaytiri.taskerlyze.ui.components.DialogNewTask;

public class PopupAction {
    private PopupAction() {
    }

    public static void showDialogForAddingCategory(EventHandler<ActionEvent> ifSuccessful) {
        DialogNewCategory dialog = new DialogNewCategory();
        dialog.showDialog();

        if (dialog.getResult().isSuccessful()) {
            ifSuccessful.handle(new ActionEvent());
        }
    }

    public static void showDialogForAddingSubTask(int taskId, EventHandler<ActionEvent> ifSuccessful) {
        DialogNewSubTask dialog = new DialogNewSubTask();
        dialog.setTaskId(taskId);
        dialog.showDialog();

        if (dialog.getResult().isSuccessful()) {
            ifSuccessful.handle(new ActionEvent());
        }
    }

    public static void showDialogForAddingTask(EventHandler<ActionEvent> ifSuccessful) {
        DialogNewTask dialog = new DialogNewTask();
        dialog.showDialog();

        if (dialog.getResult().isSuccessful()) {
            ifSuccessful.handle(new ActionEvent());
        }
    }

    public static void showDialogForEditingCategory(int id, EventHandler<ActionEvent> ifSuccessful) {
        DialogNewCategory dialog = new DialogNewCategory();
        dialog.setId(id);
        dialog.showDialog();

        if (dialog.getResult().isSuccessful()) {
            ifSuccessful.handle(new ActionEvent());
        }
    }

    public static void showDialogForEditingSubTask(int id, EventHandler<ActionEvent> ifSuccessful) {
        DialogNewSubTask dialog = new DialogNewSubTask();
        dialog.setId(id);
        dialog.showDialog();

        if (dialog.getResult().isSuccessful()) {
            ifSuccessful.handle(new ActionEvent());
        }
    }

    public static void showDialogForEditingTask(int id, EventHandler<ActionEvent> ifSuccessful) {
        DialogNewTask dialog = new DialogNewTask();
        dialog.setId(id);
        dialog.showDialog();

        if (dialog.getResult().isSuccessful()) {
            ifSuccessful.handle(new ActionEvent());
        }
    }
}
