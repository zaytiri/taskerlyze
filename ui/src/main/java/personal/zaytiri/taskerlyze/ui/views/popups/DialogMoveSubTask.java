package personal.zaytiri.taskerlyze.ui.views.popups;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.SubTaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.TaskLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.IdentifiableItem;
import personal.zaytiri.taskerlyze.ui.views.elements.MoveEntity;
import personal.zaytiri.taskerlyze.ui.views.popups.interfaces.Dialog;


public class DialogMoveSubTask extends Dialog<Integer> {
    private final MoveEntity moveEntity;
    private int subTaskId;

    public DialogMoveSubTask() {
        super("Move Sub-task:");

        moveEntity = new MoveEntity(new TaskLoader());
    }

    public void setSubTaskId(int subTaskId) {
        this.subTaskId = subTaskId;
    }

    @Override
    protected void setOptionsBeforeShow() {
        var subTaskName = new SubTaskEntity(subTaskId).get().getName();
        moveEntity.setDescription("You are about to move sub-task '" + subTaskName + "'. Search by task name to move it into a different task.");
        setDialogContentNode(moveEntity);

        addDialogAction(new MFXButton("Move"), event -> {
            moveSubTask();
            closeDialog();
        });
    }

    private void moveSubTask() {
        IdentifiableItem<String> selectedItem = moveEntity.getSelected();
        if (selectedItem.getItemId() == 0) {
            return;
        }

        SubTaskEntity entityToBeMoved = new SubTaskEntity(subTaskId).get();

        entityToBeMoved.setTaskId(selectedItem.getItemId());

        Pair<SubTaskEntity, Pair<Boolean, String>> didUpdate = entityToBeMoved.update();

        result.setStatus(didUpdate.getValue().getKey());

        if (Boolean.FALSE.equals(didUpdate.getValue().getKey())) {
            return;
        }

        ifSuccessful.handle(new ActionEvent());

        closeDialog();
    }
}
