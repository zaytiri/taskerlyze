package personal.zaytiri.taskerlyze.ui.logic.uifuncionality;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Accordion;
import javafx.scene.control.TabPane;
import personal.zaytiri.taskerlyze.ui.views.components.TabCreateUpdateCategory;
import personal.zaytiri.taskerlyze.ui.views.elements.PaneCreateUpdateTask;
import personal.zaytiri.taskerlyze.ui.views.popups.*;

public class PopupAction {
    private PopupAction() {
    }

    public static void showDialogForAddingCategory(TabPane parent) {
        TabCreateUpdateCategory pane = new TabCreateUpdateCategory();
        parent.getTabs().add(pane);
    }

    public static void showDialogForAddingSubTask(int taskId, EventHandler<ActionEvent> ifSuccessful) {
        DialogAddOrUpdateSubTask dialog = new DialogAddOrUpdateSubTask();
        dialog.setTaskId(taskId);
        dialog.showDialog();

        if (dialog.getResult().isSuccessful()) {
            ifSuccessful.handle(new ActionEvent());
        }
    }

    public static void showDialogForAddingTask(Accordion parent, int categoryId) {
        PaneCreateUpdateTask pane = new PaneCreateUpdateTask();
        pane.setCategoryId(categoryId);
        parent.getPanes().add(0, pane);
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

    public static void showDialogForMovingSubTask(int entityIdToBeMoved, EventHandler<ActionEvent> ifSuccessful) {
        DialogMoveSubTask dialog = new DialogMoveSubTask();
        dialog.setEntityToBeMoved(entityIdToBeMoved);
        dialog.showDialog();

        if (dialog.getResult().isSuccessful()) {
            ifSuccessful.handle(new ActionEvent());
        }
    }

    public static void showDialogForMovingTask(int taskId, EventHandler<ActionEvent> ifSuccessful) {
        DialogMoveTask dialog = new DialogMoveTask();
        dialog.setEntityToBeMoved(taskId);
        dialog.showDialog();

        if (dialog.getResult().isSuccessful()) {
            ifSuccessful.handle(new ActionEvent());
        }
    }
}
