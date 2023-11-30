package personal.zaytiri.taskerlyze.ui.views.elements;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.SubTaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.SubTaskLoader;

import java.io.IOException;

public class PaneCreateUpdateSubTask extends TitledPane {
    private SubTaskEntity newOrExistingSubtask = new SubTaskEntity();
    @FXML
    private TextField subtaskName;
    private int taskId;
    private int subtaskId;

    public PaneCreateUpdateSubTask() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/pane-create-update-sub-task.fxml"));

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
        subtaskName.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                create();
                ev.consume();
            }
        });

        Platform.runLater(this::populate);
        Platform.runLater(() -> {
            subtaskName.requestFocus();
            subtaskName.selectAll();
        });
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

        Accordion parent = (Accordion) this.getParent();
        parent.getPanes().remove(this);
        SubTaskLoader.getSubTaskLoader().load();
    }

    private void populate() {
        if (this.subtaskId == 0) {
            return;
        }

        newOrExistingSubtask = new SubTaskEntity(this.subtaskId).get();
        subtaskName.setText(newOrExistingSubtask.getName());
    }

}
