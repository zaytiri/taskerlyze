package personal.zaytiri.taskerlyze.ui.views;

import javafx.collections.FXCollections;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import personal.zaytiri.taskerlyze.app.api.controllers.TaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.components.ComponentTask;
import personal.zaytiri.taskerlyze.ui.components.LabelDay;
import personal.zaytiri.taskerlyze.ui.components.TabCategory;
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
                setTabContent(catView.getActiveTabCategory(), (LabelDay) tb.getGraphic());
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

        TaskController taskController = new TaskController();
        OperationResult<Pair<Category, List<Task>>> taskResult = taskController.getTasksByCategory(tabCategory.getCategoryId());

        if (!taskResult.getStatus()) {
            BorderPane pane = new BorderPane();
            pane.setCenter(new Label("No tasks found. Create a new task to get started."));
            tabCategory.setContent(pane);
            return;
        }

        LocalDate activeDayTask = LocalDate.of(activeDay.getYear(), activeDay.getMonth(), activeDay.getDay());
        for (Task t : taskResult.getResult().getValue()) {
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
            comp.setSubTasks(FXCollections.observableList(new ArrayList<>() {{
                add(new TaskEntity(1, "asdsad", true));
            }}));
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
