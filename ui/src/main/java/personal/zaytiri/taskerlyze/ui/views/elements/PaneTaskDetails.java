package personal.zaytiri.taskerlyze.ui.views.elements;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.DateConversion;

import java.io.IOException;
import java.time.LocalDate;

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
        TaskEntity task = new TaskEntity(taskId).get();

        priorityContent.setText(String.valueOf(task.getPriority()));
//        dueDateContent.setText(task.getDueDate());
        completedDateContent.setText(getFormattedCompletedAtDate(task.getCompletedAt()));
        descriptionContent.setText(task.getDescription());
        achievedContent.setText(task.getAchieved());
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    private String getFormattedCompletedAtDate(LocalDate date) {
        LocalDate completedAt = date;
        String completedAtFormatted = "-----------";
        if (!completedAt.isEqual(LocalDate.MIN)) {
            completedAtFormatted = DateConversion.formatDateWithAbbreviatedMonth(completedAt);
        }
        return completedAtFormatted;
    }


}
