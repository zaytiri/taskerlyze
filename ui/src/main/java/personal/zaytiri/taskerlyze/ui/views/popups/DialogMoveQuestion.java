package personal.zaytiri.taskerlyze.ui.views.popups;

import io.github.palexdev.materialfx.controls.MFXButton;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.QuestionEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.CategoryLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.IdentifiableItem;
import personal.zaytiri.taskerlyze.ui.views.elements.MoveEntity;
import personal.zaytiri.taskerlyze.ui.views.popups.interfaces.Dialog;


public class DialogMoveQuestion extends Dialog<Integer> {
    private final MoveEntity moveEntity;
    private int questionId;

    public DialogMoveQuestion() {
        super("Move Question:");

        moveEntity = new MoveEntity(CategoryLoader.getCategoryLoader());
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    protected void setOptionsBeforeShow() {
        var questionName = new QuestionEntity(questionId).get().getQuestion();
        moveEntity.setDescription("You are about to move question '" + questionName + "'. Search by category name to move it into a different category.");

        setDialogContentNode(moveEntity);

        addDialogAction(new MFXButton("Move"), event -> {
            moveQuestion();
            closeDialog();
        });
    }

    private void moveQuestion() {
        IdentifiableItem<String> selectedItem = moveEntity.getSelected();
        if (selectedItem.getItemId() == 0) {
            return;
        }

        QuestionEntity entityToBeMoved = new QuestionEntity(questionId).get();

        entityToBeMoved.setCategoryId(selectedItem.getItemId());

        Pair<QuestionEntity, Pair<Boolean, String>> didUpdate = entityToBeMoved.update();

        result.setStatus(didUpdate.getValue().getKey());

        if (!result.isSuccessful()) {
            System.out.println(didUpdate.getValue().getValue());
            return;
        }

        closeDialog();
    }

}
