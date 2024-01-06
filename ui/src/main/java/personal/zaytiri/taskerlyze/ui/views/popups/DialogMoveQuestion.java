package personal.zaytiri.taskerlyze.ui.views.popups;

import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.CategoryEntity;
import personal.zaytiri.taskerlyze.ui.logic.entities.QuestionEntity;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.IdentifiableItem;
import personal.zaytiri.taskerlyze.ui.views.popups.interfaces.DialogMoveEntity;


public class DialogMoveQuestion extends DialogMoveEntity<CategoryEntity, Integer> {
    public DialogMoveQuestion() {
        super(new CategoryEntity());
    }

    @Override
    protected void setActionOnMoveButton() {
        moveButton.setOnAction(event -> {
            IdentifiableItem<String> selectedItem = itemsList.getSelectionModel().getSelectedItem();
            if (selectedItem.getItemId() == 0) {
                return;
            }

            QuestionEntity entityToBeMoved = new QuestionEntity(entityIdToBeMoved).get();

            entityToBeMoved.setCategoryId(selectedItem.getItemId());

            Pair<QuestionEntity, Pair<Boolean, String>> didUpdate = entityToBeMoved.update();

            result.setStatus(didUpdate.getValue().getKey());

            if (!result.isSuccessful()) {
                System.out.println(didUpdate.getValue().getValue());
                return;
            }

            closeDialog();
        });
    }

}
