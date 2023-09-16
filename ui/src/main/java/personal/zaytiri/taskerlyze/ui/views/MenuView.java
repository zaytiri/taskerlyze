package personal.zaytiri.taskerlyze.ui.views;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import personal.zaytiri.taskerlyze.ui.components.NewTaskDialog;
import personal.zaytiri.taskerlyze.ui.logic.entities.Result;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

public class MenuView {
    private final TasksView tasksView;

    public MenuView(TasksView tasksView) {
        this.tasksView = tasksView;
    }

    public void setButtonsSetOnAction(BorderPane menuButtons) {
        HBox hboxT = (HBox) menuButtons.getTop();
        Button exitButton = (Button) hboxT.getChildren().get(0);

        BorderPane bp = (BorderPane) menuButtons.getCenter();
        VBox vboxB = (VBox) bp.getBottom();
        Button settingsButton = (Button) vboxB.getChildren().get(0);
        Button swicthProfileButton = (Button) vboxB.getChildren().get(1);

        HBox hboxC = (HBox) bp.getCenter();
        VBox vbox = (VBox) hboxC.getChildren().get(0);
        Button newTask = (Button) vbox.getChildren().get(0);
        Button newSubTask = (Button) vbox.getChildren().get(1);

        setNewTaskButtonOnAction(newTask);
    }

    private void setNewTaskButtonOnAction(Button newTask) {
        newTask.setOnAction(event -> {
            Result<TaskEntity> taskResult = new Result<>(new TaskEntity());
            NewTaskDialog dialog = new NewTaskDialog(taskResult);
            dialog.showStage();

            if (taskResult.getResult() != null) {
                tasksView.populateTasksView();
            }
            event.consume();
        });
    }
}
