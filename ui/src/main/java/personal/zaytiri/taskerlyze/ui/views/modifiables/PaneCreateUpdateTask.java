package personal.zaytiri.taskerlyze.ui.views.modifiables;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.KeyBindable;

import java.io.IOException;

public class PaneCreateUpdateTask extends TitledPane {
    private final PaneCreateUpdateTaskDetails paneTaskDetails = new PaneCreateUpdateTaskDetails();
    private final KeyBindable keyBinding;
    private int categoryId;
    private int taskId;
    @FXML
    private TextField taskName;
    private TaskEntity newOrExistingTask = new TaskEntity();
    @FXML
    private BorderPane mainBorderPane;
    private EventHandler<ActionEvent> ifSuccessful;

    public PaneCreateUpdateTask() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/pane-create-update-task.fxml"));

        keyBinding = new KeyBindable();

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setIfSuccessful(EventHandler<ActionEvent> event) {
        this.ifSuccessful = event;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    private void create() {
        newOrExistingTask = paneTaskDetails.getTask();
        if (newOrExistingTask == null) {
            return;
        }

        newOrExistingTask
                .setName(taskName.getText());

        if (categoryId != 0) {
            newOrExistingTask.setCategoryId(categoryId);
        }

        Pair<TaskEntity, Pair<Boolean, String>> response = newOrExistingTask.createOrUpdate();
        boolean isSuccessfulFromApi = response.getValue().getKey();
        String errorMessageFromApi = response.getValue().getValue();

        if (!isSuccessfulFromApi) {
            System.out.println(errorMessageFromApi);
            return;
        }

        removePaneFromParent();

        this.ifSuccessful.handle(new ActionEvent());
    }

    @FXML
    private void initialize() {
        keyBinding.addEnterKeyBinding(taskName, evt -> create());
        keyBinding.addEscapeKeyBinding(taskName, evt -> removePaneFromParent());

        Platform.runLater(() -> {
            populate();

            this.setExpanded(true);
            taskName.selectAll();
            taskName.requestFocus();
        });
    }

    private void populate() {
        if (this.taskId != 0) {
            newOrExistingTask = new TaskEntity(this.taskId).get();
            taskName.setText(newOrExistingTask.getName());
        }

        paneTaskDetails.setTask(newOrExistingTask);
        paneTaskDetails.load();
        mainBorderPane.setCenter(paneTaskDetails);
    }

    private void removePaneFromParent() {
        Accordion parent = (Accordion) this.getParent();
        parent.getPanes().remove(this);
    }


}
