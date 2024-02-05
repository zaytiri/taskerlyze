package personal.zaytiri.taskerlyze.ui.views.modifiables;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.util.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.SubTaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.globals.UiGlobalMessage;
import personal.zaytiri.taskerlyze.ui.logic.loaders.SubTaskLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.KeyBindable;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MessageType;

import java.io.IOException;

public class PaneCreateUpdateSubTask extends TitledPane {
    private final KeyBindable keyBinding;
    private SubTaskEntity newOrExistingSubtask = new SubTaskEntity();
    @FXML
    private TextField subtaskName;
    private int taskId;
    private int subtaskId;
    private boolean isSuccessful;
    @FXML
    private TitledPane mainSubTaskTitledPane;

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
        keyBinding.addEnterKeyBinding(mainSubTaskTitledPane, evt -> create());
        keyBinding.addEscapeKeyBinding(mainSubTaskTitledPane, evt -> removePaneFromParent());

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
        String messageFromApi = response.getValue().getValue();

        if (!isSuccessfulFromApi) {
            UiGlobalMessage.getUiGlobalMessage().setMessage(MessageType.ERROR, messageFromApi);
            return;
        }

        UiGlobalMessage.getUiGlobalMessage().setMessage(MessageType.NEUTRAL, messageFromApi);
        removePaneFromParent();


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
        SubTaskLoader.getSubTaskLoader().load();
    }

}
