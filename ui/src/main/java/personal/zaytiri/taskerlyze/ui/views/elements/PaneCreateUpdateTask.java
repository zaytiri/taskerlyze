package personal.zaytiri.taskerlyze.ui.views.elements;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.TaskLoader;

import java.io.IOException;

public class PaneCreateUpdateTask extends TitledPane {
    private int categoryId;
    @FXML
    private TextField taskName;
    private TaskEntity newTask;

    public PaneCreateUpdateTask() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/pane-create-update-task.fxml"));

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

    private void create() {
        newTask = new TaskEntity();
        newTask
                .setName(taskName.getText())
                .setCategoryId(categoryId);

        Pair<TaskEntity, Pair<Boolean, String>> response = newTask.createOrUpdate();
        boolean isSuccessfulFromApi = response.getValue().getKey();
        String errorMessageFromApi = response.getValue().getValue();

        if (!isSuccessfulFromApi) {
            System.out.println(errorMessageFromApi);
            return;
        }

        Accordion parent = (Accordion) this.getParent();
        parent.getPanes().remove(this);
        TaskLoader.getTaskLoader().load();
    }

    @FXML
    private void initialize() {

        taskName.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                create();
                ev.consume();
            }
        });
    }
}
