package personal.zaytiri.taskerlyze.ui.views;

import javafx.collections.FXCollections;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import personal.zaytiri.taskerlyze.app.api.controllers.SubTaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.TaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.SubTask;
import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.ui.components.ComponentTask;
import personal.zaytiri.taskerlyze.ui.components.DialogNewSubTask;
import personal.zaytiri.taskerlyze.ui.components.LabelDay;
import personal.zaytiri.taskerlyze.ui.components.TabCategory;
import personal.zaytiri.taskerlyze.ui.logic.entities.Result;
import personal.zaytiri.taskerlyze.ui.logic.entities.SubTaskEntity;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class TasksView {
    private final CalendarView calView;
    private final CategoryView catView;

    public TasksView(CategoryView catView, CalendarView calView) {
        this.calView = calView;
        this.catView = catView;
    }

    public void populateTasksView() {
        setLabelDaysOnAction();
        setTabCategoriesOnAction();
    }

    public void refreshTabContent() {
        setTabContent(catView.getActiveTabCategory(), calView.getActiveDayInToggleGroup());
    }

    private void setLabelDaysOnAction() {
        for (ToggleButton tb : calView.getDayToggleButtons()) {

            tb.setOnMouseClicked(event -> {
                LabelDay day = (LabelDay) tb.getGraphic();

                LocalDate date = calView.labelDayToLocalDate(day);
                calView.populateMonth(date);
                calView.populateYear(date);

                setTabContent(catView.getActiveTabCategory(), day);
            });
        }
    }

    private void setTabCategoriesOnAction() {
        for (TabCategory tab : catView.getTabs()) {
            tab.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (Boolean.TRUE.equals(newValue)) {
                    setTabContent(tab, calView.getActiveDayInToggleGroup());
                }
            });
        }
    }

    private void setTabContent(TabCategory tabCategory, LabelDay activeDay) {
        Accordion tasks = new Accordion();
        tasks.setId("tasks-accordion");

        OperationResult<List<Task>> taskResult = new TaskController().getTasksByCategory(tabCategory.getCategoryId());

        if (!taskResult.getStatus()) {
            BorderPane pane = new BorderPane();
            pane.setCenter(new Label("No tasks found. Create a new task to get started."));
            tabCategory.setContent(pane);
            return;
        }

        LocalDate activeDayTask = calView.labelDayToLocalDate(activeDay);
        for (Task t : taskResult.getResult()) {
            ComponentTask comp = new ComponentTask();

            LocalDate completedAtTask = null;
            if (t.getCompletedAt() != null) {
                completedAtTask = t.getCompletedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (activeDayTask.getDayOfMonth() != completedAtTask.getDayOfMonth()) {
                    continue;
                }
            } else {
                if (activeDayTask.getDayOfMonth() != LocalDate.now().getDayOfMonth()) {
                    continue;
                }
            }

            comp.setTaskId(t.getId());
            comp.setTaskName(t.getName());
            comp.setIsTaskDone(t.isDone(false));
            List<SubTask> subTasks = new SubTaskController().getSubTaskByTask(t.getId()).getResult();
            List<SubTaskEntity> subTaskEntities = new ArrayList<>();
            for (SubTask st : subTasks) {
                subTaskEntities.add(new SubTaskEntity(t.getId(), st.getName(), st.isDone(false), st.getId()));
            }
            comp.setSubTasks(FXCollections.observableList(subTaskEntities));
            comp.getAddNewSubTaskButton().setOnAction(event -> {
                Result<TaskEntity> taskEntityResult = new Result<>(new TaskEntity());
                DialogNewSubTask dialog = new DialogNewSubTask(taskEntityResult, new Stage());
                dialog.setTaskId(comp.getTaskId());
                dialog.showStage();

                if (taskResult.getResult() != null) {
                    refreshTabContent();
                }
                event.consume();
            });
            tasks.getPanes().add(comp);
        }

        AnchorPane scrollAnchorPane = new AnchorPane();
        scrollAnchorPane.getChildren().add(tasks);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(scrollAnchorPane);
        AnchorPane mainPane = new AnchorPane();
        mainPane.getChildren().add(scrollPane);

        tabCategory.setContent(mainPane);
    }
}
