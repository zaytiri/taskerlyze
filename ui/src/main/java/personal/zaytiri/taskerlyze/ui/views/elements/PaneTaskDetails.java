package personal.zaytiri.taskerlyze.ui.views.elements;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

import java.io.IOException;

public class PaneTaskDetails extends BorderPane {
    private int taskId;
    @FXML
    private Label priorityContent;
    @FXML
    private Label dueDateContent;
    @FXML
    private Label completedDateContent;
    @FXML
    private Label descriptionContent;
    @FXML
    private Label achievedContent;
    private boolean isFilled = false;

    public PaneTaskDetails() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/pane-task-details.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public void load() {
        if (isFilled) {
            return;
        }

        TaskEntity task = new TaskEntity(taskId).get();

        priorityContent.setText(String.valueOf(task.getPriority()));
//        dueDateContent.setText(task.getDueDate());
//        completedDateContent.setText(task.getCompletedAt());
        descriptionContent.setText(task.getDescription());
//        achievedContent.setText(task.getAchieved());

        isFilled = true;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
