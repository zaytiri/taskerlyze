package personal.zaytiri.taskerlyze.ui.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.SubTaskEntity;

public class DialogNewSubTask extends Dialog<SubTaskEntity> {
    @FXML
    public TextField name;
    @FXML
    public Button buttonCreate;
    @FXML
    public Label errorMessage;
    private int taskId;
    private SubTaskEntity newOrExistingSubTask;

    public DialogNewSubTask() {
        super("dialog-new-sub-task", "Add new subtask:");
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void showDialog() {
        populate();
        show();
    }

    @FXML
    private void initialize() {
        setOnActionCreateButton();
    }

    private void populate() {
        if (this.id == 0) {
            newOrExistingSubTask = new SubTaskEntity();
            return;
        }
        newOrExistingSubTask = new SubTaskEntity().get(this.id);
        name.setText(newOrExistingSubTask.getName());
    }

    private void setOnActionCreateButton() {
        buttonCreate.setOnAction(event -> {
            newOrExistingSubTask
                    .setName(name.getText())
                    .setTaskId(taskId);

            Pair<SubTaskEntity, Pair<Boolean, String>> response = newOrExistingSubTask.createOrUpdate();
            boolean isSuccessfulFromApi = response.getValue().getKey();
            String errorMessageFromApi = response.getValue().getValue();

            result.setStatus(isSuccessfulFromApi);

            if (!result.isSuccessful()) {
                errorMessage.setText(errorMessageFromApi);
                return;
            }

            closeDialog();
        });
    }
}
