package personal.zaytiri.taskerlyze.ui.views.popups;

import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.CategoryLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.IdentifiableItem;
import personal.zaytiri.taskerlyze.ui.views.popups.interfaces.DialogMoveEntity;


public class DialogMoveTask extends DialogMoveEntity<Integer> {
    public DialogMoveTask() {
        super(CategoryLoader.getCategoryLoader());
    }

    @Override
    protected void setActionOnMoveButton() {
        moveButton.setOnAction(event -> {
            IdentifiableItem<String> selectedItem = itemsList.getSelectionModel().getSelectedItem();
            if (selectedItem.getItemId() == 0) {
                return;
            }

            TaskEntity entityToBeMoved = new TaskEntity(entityIdToBeMoved).get();

            entityToBeMoved.setCategoryId(selectedItem.getItemId());

            Pair<TaskEntity, Pair<Boolean, String>> didUpdate = entityToBeMoved.update();

            result.setStatus(didUpdate.getValue().getKey());

            if (!result.isSuccessful()) {
                System.out.println(didUpdate.getValue().getValue());
                return;
            }

            closeDialog();
        });
    }

}
