package personal.zaytiri.taskerlyze.ui.views.modifiables;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.SubTaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.SubTaskLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.KeyBindable;

import java.io.IOException;

public class PaneCreateUpdateSubTask extends TitledPane {
    private final KeyBindable keyBinding;
    private SubTaskEntity newOrExistingSubtask = new SubTaskEntity();
    @FXML
    private TextField subtaskName;
    private int taskId;
    private int subtaskId;
    private boolean isSuccessful;

    public PaneCreateUpdateSubTask() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/pane-create-update-sub-task.fxml"));
        keyBinding = new KeyBindable();
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    @FXML
    public void initialize() {
        keyBinding.addEnterKeyBinding(subtaskName, evt -> create());
        keyBinding.addEscapeKeyBinding(subtaskName, evt -> removePaneFromParent());

        Platform.runLater(() -> {
            populate();

            subtaskName.selectAll();
            subtaskName.requestFocus();
        });
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSubtaskId(int subtaskId) {
        this.subtaskId = subtaskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    private void create() {
        newOrExistingSubtask
                .setName(subtaskName.getText())
                .setTaskId(taskId);

        Pair<SubTaskEntity, Pair<Boolean, String>> response = newOrExistingSubtask.createOrUpdate();
        boolean isSuccessfulFromApi = response.getValue().getKey();
        String errorMessageFromApi = response.getValue().getValue();

        if (!isSuccessfulFromApi) {
            System.out.println(errorMessageFromApi);
            return;
        }

        removePaneFromParent();

        SubTaskLoader.getSubTaskLoader().load();
    }

    private void populate() {
        if (this.subtaskId == 0) {
            return;
        }

        newOrExistingSubtask = new SubTaskEntity(this.subtaskId).get();
        subtaskName.setText(newOrExistingSubtask.getName());
    }

    private void removePaneFromParent() {
        Accordion parent = (Accordion) this.getParent();
        parent.getPanes().remove(this);
    }

}
