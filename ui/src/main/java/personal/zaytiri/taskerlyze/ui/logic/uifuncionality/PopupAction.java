package personal.zaytiri.taskerlyze.ui.logic.uifuncionality;

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

    public static boolean showDialogForAddingCategory(TabPane parent) {
        TabCreateUpdateCategory pane = new TabCreateUpdateCategory();
        parent.getTabs().add(0, pane);

        return pane.isSuccessful();
    }

    public static boolean showDialogForAddingQuestion(Accordion parent, int categoryId) {
        PaneCreateUpdateQuestion pane = new PaneCreateUpdateQuestion();
        pane.setCategoryId(categoryId);
        parent.getPanes().add(0, pane);

        return pane.isSuccessful();
    }

    public static boolean showDialogForAddingSubTask(Accordion parent, int taskId) {
        PaneCreateUpdateSubTask pane = new PaneCreateUpdateSubTask();
        pane.setTaskId(taskId);
        parent.getPanes().add(0, pane);

        return pane.isSuccessful();
    }

    public static boolean showDialogForAddingTask(Accordion parent, int categoryId) {
        PaneCreateUpdateTask pane = new PaneCreateUpdateTask();
        pane.setCategoryId(categoryId);
        parent.getPanes().add(0, pane);

        return pane.isSuccessful();
    }

    public static boolean showDialogForEditingCategory(TabPane parent, TabCategory currentTab, int categoryId) {
        TabCreateUpdateCategory pane = new TabCreateUpdateCategory();
        pane.setCategoryId(categoryId);
        int index = parent.getTabs().indexOf(currentTab);
        parent.getTabs().remove(currentTab);
        parent.getTabs().add(index, pane);

        return pane.isSuccessful();
    }

    public static boolean showDialogForEditingQuestion(int questionId, PaneQuestion currentQuestion, Accordion parent) {
        PaneCreateUpdateQuestion pane = new PaneCreateUpdateQuestion();
        pane.setQuestionId(questionId);
        int index = parent.getPanes().indexOf(currentQuestion);
        parent.getPanes().remove(currentQuestion);
        parent.getPanes().add(index, pane);

        return pane.isSuccessful();
    }

    public static boolean showDialogForEditingSubTask(int id, PaneSubTask currentSubtask, Accordion parent, int taskId) {
        PaneCreateUpdateSubTask pane = new PaneCreateUpdateSubTask();
        pane.setSubtaskId(id);
        pane.setTaskId(taskId);
        int index = parent.getPanes().indexOf(currentSubtask);
        parent.getPanes().remove(currentSubtask);
        parent.getPanes().add(index, pane);

        return pane.isSuccessful();
    }

    public static boolean showDialogForEditingTask(int id, PaneTask currentTask, Accordion parent) {
        PaneCreateUpdateTask pane = new PaneCreateUpdateTask();
        pane.setTaskId(id);
        int index = parent.getPanes().indexOf(currentTask);
        parent.getPanes().remove(currentTask);
        parent.getPanes().add(index, pane);

        return pane.isSuccessful();
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

