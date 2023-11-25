package personal.zaytiri.taskerlyze.ui.views.popups;

import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.SubTaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.IdentifiableItem;
import personal.zaytiri.taskerlyze.ui.views.popups.interfaces.DialogMoveEntity;


public class DialogMoveSubTask extends DialogMoveEntity<TaskEntity, Integer> {
    public DialogMoveSubTask() {
        super(new TaskEntity());
    }

    @Override
    protected void setActionOnMoveButton() {
        moveButton.setOnAction(event -> {
            IdentifiableItem<String> selectedItem = itemsList.getSelectionModel().getSelectedItem();
            if (selectedItem.getItemId() == 0) {
                return;
            }

            SubTaskEntity entityToBeMoved = new SubTaskEntity().get(entityIdToBeMoved);

            entityToBeMoved.setTaskId(selectedItem.getItemId());

            Pair<SubTaskEntity, Pair<Boolean, String>> didUpdate = entityToBeMoved.update();

            result.setStatus(didUpdate.getValue().getKey());

            if (!result.isSuccessful()) {
                System.out.println("not updated");
                return;
            }

            closeDialog();
        });
    }

}
