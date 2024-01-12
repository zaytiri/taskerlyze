package personal.zaytiri.taskerlyze.ui.logic.uifuncionality;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Accordion;
import javafx.scene.control.TabPane;
import personal.zaytiri.taskerlyze.ui.views.components.TabCategory;
import personal.zaytiri.taskerlyze.ui.views.elements.PaneQuestion;
import personal.zaytiri.taskerlyze.ui.views.elements.PaneSubTask;
import personal.zaytiri.taskerlyze.ui.views.elements.PaneTask;
import personal.zaytiri.taskerlyze.ui.views.modifiables.PaneCreateUpdateQuestion;
import personal.zaytiri.taskerlyze.ui.views.modifiables.PaneCreateUpdateSubTask;
import personal.zaytiri.taskerlyze.ui.views.modifiables.PaneCreateUpdateTask;
import personal.zaytiri.taskerlyze.ui.views.modifiables.TabCreateUpdateCategory;
import personal.zaytiri.taskerlyze.ui.views.popups.DialogMoveQuestion;
import personal.zaytiri.taskerlyze.ui.views.popups.DialogMoveSubTask;
import personal.zaytiri.taskerlyze.ui.views.popups.DialogMoveTask;

public class PopupAction {
    private PopupAction() {
    }

    public static void showDialogForAddingCategory(TabPane parent) {
        TabCreateUpdateCategory pane = new TabCreateUpdateCategory();
        parent.getTabs().add(0, pane);
    }

    public static void showDialogForAddingQuestion(Accordion parent, int categoryId, EventHandler<ActionEvent> ifSuccessful) {
        PaneCreateUpdateQuestion pane = new PaneCreateUpdateQuestion();
        pane.setIfSuccessful(ifSuccessful);
        pane.setCategoryId(categoryId);
        parent.getPanes().add(0, pane);
    }

    public static void showDialogForAddingSubTask(Accordion parent, int taskId) {
        PaneCreateUpdateSubTask pane = new PaneCreateUpdateSubTask();
        pane.setTaskId(taskId);
        parent.getPanes().add(0, pane);
    }

    public static void showDialogForAddingTask(Accordion parent, int categoryId, EventHandler<ActionEvent> ifSuccessful) {
        PaneCreateUpdateTask pane = new PaneCreateUpdateTask();
        pane.setCategoryId(categoryId);
        pane.setIfSuccessful(ifSuccessful);
        parent.getPanes().add(0, pane);
    }

    public static void showDialogForEditingCategory(TabPane parent, TabCategory currentTab, int categoryId) {
        TabCreateUpdateCategory pane = new TabCreateUpdateCategory();
        pane.setCategoryId(categoryId);
        int index = parent.getTabs().indexOf(currentTab);
        parent.getTabs().remove(currentTab);
        parent.getTabs().add(index, pane);
    }

    public static void showDialogForEditingQuestion(int questionId, PaneQuestion currentQuestion, Accordion parent, EventHandler<ActionEvent> ifSuccessful) {
        PaneCreateUpdateQuestion pane = new PaneCreateUpdateQuestion();
        pane.setQuestionId(questionId);
        pane.setIfSuccessful(ifSuccessful);
        int index = parent.getPanes().indexOf(currentQuestion);
        parent.getPanes().remove(currentQuestion);
        parent.getPanes().add(index, pane);
    }

    public static void showDialogForEditingSubTask(int id, PaneSubTask currentSubtask, Accordion parent, int taskId) {
        PaneCreateUpdateSubTask pane = new PaneCreateUpdateSubTask();
        pane.setSubtaskId(id);
        pane.setTaskId(taskId);
        int index = parent.getPanes().indexOf(currentSubtask);
        parent.getPanes().remove(currentSubtask);
        parent.getPanes().add(index, pane);
    }

    public static void showDialogForEditingTask(int id, PaneTask currentTask, Accordion parent, EventHandler<ActionEvent> ifSuccessful) {
        PaneCreateUpdateTask pane = new PaneCreateUpdateTask();
        pane.setTaskId(id);
        pane.setIfSuccessful(ifSuccessful);
        int index = parent.getPanes().indexOf(currentTask);
        parent.getPanes().remove(currentTask);
        parent.getPanes().add(index, pane);
    }

    public static boolean showDialogForMovingQuestion(int questionId) {
        DialogMoveQuestion dialog = new DialogMoveQuestion();
        dialog.setEntityToBeMoved(questionId);
        dialog.showDialog();

        return dialog.getResult().isSuccessful();
    }

    public static boolean showDialogForMovingSubTask(int entityIdToBeMoved) {
        DialogMoveSubTask dialog = new DialogMoveSubTask();
        dialog.setEntityToBeMoved(entityIdToBeMoved);
        dialog.showDialog();

        return dialog.getResult().isSuccessful();
    }

    public static boolean showDialogForMovingTask(int taskId) {
        DialogMoveTask dialog = new DialogMoveTask();
        dialog.setEntityToBeMoved(taskId);
        dialog.showDialog();

        return dialog.getResult().isSuccessful();
    }
}

