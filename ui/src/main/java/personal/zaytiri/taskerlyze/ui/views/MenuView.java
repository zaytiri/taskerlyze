package personal.zaytiri.taskerlyze.ui.views;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import personal.zaytiri.taskerlyze.ui.components.DialogNewTask;
import personal.zaytiri.taskerlyze.ui.logic.entities.Result;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

import java.time.LocalDate;

public class MenuView {
    private final TasksView tasksView;
    private final CalendarView calView;
    private Stage primaryStage;

    public MenuView(TasksView tasksView, CalendarView calView) {
        this.tasksView = tasksView;
        this.calView = calView;
    }

    public void setButtonsSetOnAction(BorderPane menuButtons) {
        HBox hboxT = (HBox) menuButtons.getTop();
        VBox vboxT = (VBox) hboxT.getChildren().get(0);
        Button exit = (Button) vboxT.getChildren().get(0);
        Button today = (Button) vboxT.getChildren().get(1);

        BorderPane bp = (BorderPane) menuButtons.getCenter();
        VBox vboxB = (VBox) bp.getBottom();
        Button settings = (Button) vboxB.getChildren().get(0);
        Button swicthProfile = (Button) vboxB.getChildren().get(1);

        HBox hboxC = (HBox) bp.getCenter();
        VBox vbox = (VBox) hboxC.getChildren().get(0);
        Button newTask = (Button) vbox.getChildren().get(0);
        Button newSubTask = (Button) vbox.getChildren().get(1);

        setExitButtonOnAction(exit);
        setNewTaskButtonOnAction(newTask);
        setTodayButtonOnAction(today);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void setExitButtonOnAction(Button exit) {
        exit.setOnAction(event -> primaryStage.close());
        primaryStage.setOnCloseRequest(event -> primaryStage.close());
    }

    private void setNewTaskButtonOnAction(Button newTask) {
        newTask.setOnAction(event -> {
            Result<TaskEntity> taskResult = new Result<>(new TaskEntity());
            DialogNewTask dialog = new DialogNewTask(taskResult, primaryStage);
            dialog.showStage();

            if (taskResult.getResult() != null) {
                tasksView.refreshTabContent();
            }
            event.consume();
        });
    }

    private void setTodayButtonOnAction(Button today) {
        today.setOnAction(event -> calView.populateCalendar(LocalDate.now()));
    }
}
