package personal.zaytiri.taskerlyze.ui.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.Result;
import personal.zaytiri.taskerlyze.ui.logic.entities.SubTaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

public class DialogNewSubTask extends Dialog {
    @FXML
    public TextField name;
    @FXML
    public Button buttonCreate;
    @FXML
    public Label errorMessage;
    Result<TaskEntity> result;
    private int taskId;

    public DialogNewSubTask(Result<TaskEntity> result) {
        super("dialog-new-sub-task", "Add new subtask:");
        this.result = result;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void showDialog() {
        show();
    }

    @FXML
    private void initialize() {
        setOnActionCreateButton();
    }

    private void setOnActionCreateButton() {
        buttonCreate.setOnAction(event -> {
            SubTaskEntity newTask = new SubTaskEntity()
                    .setName(name.getText())
                    .setTaskId(taskId);

            Pair<Boolean, String> response = newTask.create();
            boolean isSuccessfulFromApi = response.getKey();
            String errorMessageFromApi = response.getValue();

            result.setStatus(isSuccessfulFromApi);

            if (!result.isSuccessful()) {
                errorMessage.setText(errorMessageFromApi);
                return;
            }

            closeDialog();
        });
    }
}
