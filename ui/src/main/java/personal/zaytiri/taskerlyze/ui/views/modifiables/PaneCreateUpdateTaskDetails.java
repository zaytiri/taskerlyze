package personal.zaytiri.taskerlyze.ui.views.modifiables;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import personal.zaytiri.taskerlyze.ui.logic.DateConversion;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

import java.io.IOException;
import java.time.LocalDate;

public class PaneCreateUpdateTaskDetails extends BorderPane {
    @FXML
    private TextField priorityContent;
    @FXML
    private TextField dueDateContent;
    @FXML
    private Label completedDateContent;
    @FXML
    private TextField descriptionContent;
    @FXML
    private TextField achievedContent;
    @FXML
    private TextField urlContent;
    private TaskEntity task;

    public PaneCreateUpdateTaskDetails() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/pane-create-update-task-details.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public TaskEntity getTask() {
        Integer priorityAsNumber = tryParseInt(priorityContent.getText());
        if (priorityAsNumber == null) {
            System.out.println("Priority must be a number.");
            return null;
        }

        return task.setPriority(Integer.parseInt(priorityContent.getText()))
                .setDescription(descriptionContent.getText())
                .setAchieved(achievedContent.getText())
                .setUrl(urlContent.getText());
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    public void load() {
        priorityContent.setText(String.valueOf(task.getPriority()));
//        dueDateContent.setText(task.getDueDate());
        completedDateContent.setText(getFormattedCompletedAtDate(task.getCompletedAt()));
        descriptionContent.setText(task.getDescription());
        achievedContent.setText(task.getAchieved());
        urlContent.setText(task.getUrl());
    }

    public Integer tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String getFormattedCompletedAtDate(LocalDate date) {
        String completedAtFormatted = "-----------";
        if (date != null && (!date.isEqual(LocalDate.MIN))) {
            completedAtFormatted = DateConversion.formatDateWithAbbreviatedMonth(date);
        }
        return completedAtFormatted;
    }
}
