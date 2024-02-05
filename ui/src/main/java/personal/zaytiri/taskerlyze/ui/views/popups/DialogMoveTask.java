package personal.zaytiri.taskerlyze.ui.views.popups;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.util.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.CategoryLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.IdentifiableItem;
import personal.zaytiri.taskerlyze.ui.views.elements.MoveEntity;
import personal.zaytiri.taskerlyze.ui.views.popups.interfaces.Dialog;


public class DialogMoveTask extends Dialog<Integer> {
    private final MoveEntity moveEntity;
    private int taskId;

    public DialogMoveTask() {
        super("Move Task:");
        moveEntity = new MoveEntity(CategoryLoader.getCategoryLoader());
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    protected void setOptionsBeforeShow() {
        var taskName = new TaskEntity(taskId).get().getName();
        moveEntity.setDescription("You are about to move task '" + taskName + "'. Search by category name to move it into a different category.");

        setDialogContentNode(moveEntity);

        addDialogAction(new MFXButton("Move"), event -> {
            moveTask();
            closeDialog();
        });
    }

    private void moveTask() {
        IdentifiableItem<String> selectedItem = moveEntity.getSelected();
        if (selectedItem.getItemId() == 0) {
            return;
        }

        TaskEntity entityToBeMoved = new TaskEntity(taskId).get();

        entityToBeMoved.setCategoryId(selectedItem.getItemId());

        Pair<TaskEntity, Pair<Boolean, String>> didUpdate = entityToBeMoved.update();

        result.setStatus(didUpdate.getValue().getKey());

        if (Boolean.FALSE.equals(didUpdate.getValue().getKey())) {
            return;
        }

        ifSuccessful.handle(new ActionEvent());

        closeDialog();
    }
}
