package personal.zaytiri.taskerlyze.ui.views;

import javafx.collections.FXCollections;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
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
import java.util.Objects;

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

    public void refreshTasks() {
        setTasks(catView.getActiveTabCategory(), calView.getActiveDayInToggleGroup());
    }

    public void setSubTasksButtonOnAction(ComponentTask comp) {
        comp.getAddNewSubTaskButton().setOnAction(event -> {
            Result<TaskEntity> taskEntityResult = new Result<>(new TaskEntity());
            DialogNewSubTask dialog = new DialogNewSubTask(taskEntityResult, new Stage());
            dialog.setTaskId(comp.getTaskId());
            dialog.showStage();

            if (taskEntityResult.isSuccessful()) {
                refreshTasks();
            }

            event.consume();
        });
    }

    public void setSubTasksList(ComponentTask comp) {
        List<SubTask> subTasks = new SubTaskController().getSubTaskByTask(Integer.parseInt(comp.getId())).getResult();
        List<SubTaskEntity> subTaskEntities = new ArrayList<>();
        for (SubTask st : subTasks) {
            subTaskEntities.add(new SubTaskEntity(st.getId(), st.getName(), st.isDone(false), Integer.parseInt(comp.getId())));
        }
        comp.setSubTasks(FXCollections.observableList(subTaskEntities));
        setSubTasksButtonOnAction(comp);
    }

    private void setLabelDaysOnAction() {
        for (ToggleButton tb : calView.getDayToggleButtons()) {

            tb.setOnMouseClicked(event -> {
                LabelDay day = (LabelDay) tb.getGraphic();

                LocalDate date = calView.labelDayToLocalDate(day);
                calView.populateMonth(date);
                calView.populateYear(date);

                setTasks(catView.getActiveTabCategory(), day);
            });
        }
    }

    private void setTabCategoriesOnAction() {
        for (TabCategory tab : catView.getTabs()) {
            tab.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (Boolean.TRUE.equals(newValue)) {
                    setTasks(tab, calView.getActiveDayInToggleGroup());
                }
            });
        }
    }

    private void setTasks(TabCategory tabCategory, LabelDay activeDay) {
        Accordion tasks = tabCategory.getTasksAccordion();

        TitledPane expandedPane = tasks.getExpandedPane();
        tasks.getPanes().clear();

        OperationResult<List<Task>> taskResult = new TaskController().getTasksByCategory(tabCategory.getCategoryId());

        if (!taskResult.getStatus()) {
            tabCategory.showNotFoundMessage();
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

            comp.setId(String.valueOf(t.getId()));
            comp.setTaskId(t.getId());
            comp.setTaskName(t.getName());
            comp.setIsTaskDone(t.isDone(false));

            setSubTasksList(comp);

            if (expandedPane != null && Objects.equals(expandedPane.getId(), comp.getId())) {
                tasks.setExpandedPane(comp);
            }

            tasks.getPanes().add(comp);
        }
    }
}
