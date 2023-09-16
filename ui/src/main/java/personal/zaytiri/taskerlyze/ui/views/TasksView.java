package personal.zaytiri.taskerlyze.ui.views;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import personal.zaytiri.taskerlyze.app.api.controllers.CategoryController;
import personal.zaytiri.taskerlyze.app.api.controllers.TaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.components.NewCategoryDialog;
import personal.zaytiri.taskerlyze.ui.components.TaskComponent;
import personal.zaytiri.taskerlyze.ui.logic.entities.Result;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

import java.util.ArrayList;
import java.util.List;

public class TasksView {
    private final TabPane mainTabPane;
    private Tab defaultTab;

    public TasksView(TabPane mainTabPane) {
        this.mainTabPane = mainTabPane;
        createDefaultTab();
    }

    public void populateTasksView() {
        mainTabPane.getTabs().clear();

        CategoryController catController = new CategoryController();
        OperationResult<List<Category>> categories = catController.get(null, null);

        if (!categories.getStatus()) {
            BorderPane pane = new BorderPane();
            pane.setCenter(new Label("No categories found. Create a new category to get started."));
            defaultTab.setContent(pane);
        }

        mainTabPane.getTabs().add(defaultTab);

        if (categories.getStatus()) {
            for (Category category : categories.getResult()) {
                int categoryId = category.getId();
                Tab newTab = new Tab(category.getName());
                newTab.setContextMenu(getTabContextMenu(categoryId));

                newTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (Boolean.TRUE.equals(newValue)) {
                        setTabContent(newTab, categoryId);
                    }
                });
                mainTabPane.getTabs().add(newTab);
            }
        }
    }

    private void createDefaultTab() {
        ImageView imageView = new ImageView("icons/plus.png");
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        Label image = new Label();
        image.setGraphic(imageView);

        defaultTab = new Tab();
        defaultTab.setId("default-tab");
        defaultTab.setGraphic(image);

        setDefaultTabOnAction();
    }

    private ContextMenu getTabContextMenu(int categoryId) {
        CategoryController catController = new CategoryController();

        ContextMenu tabContextMenu = new ContextMenu();
        MenuItem removeCategoryMenuItem = new MenuItem();
        removeCategoryMenuItem.setText("Remove");
        removeCategoryMenuItem.setOnAction(event -> {
            OperationResult<Category> result = catController.delete(categoryId);
            if (result.getStatus()) {
                populateTasksView();
            }
        });
        tabContextMenu.getItems().add(removeCategoryMenuItem);
        return tabContextMenu;
    }

    private void setDefaultTabOnAction() {
        Label image = (Label) defaultTab.getGraphic();
        image.setOnMouseClicked(event -> {
            Result<Category> categoryResult = new Result<>(new Category());
            NewCategoryDialog dialog = new NewCategoryDialog(categoryResult);
            dialog.showStage();

            if (categoryResult.getResult() != null) {
                populateTasksView();
            }
            event.consume();
        });
    }

    private void setTabContent(Tab tab, int categoryId) {
        Accordion tasks = new Accordion();
        tasks.setId("tasks-accordion");

        TaskController taskController = new TaskController();
        OperationResult<Pair<Category, List<Task>>> taskResult = taskController.getTasksByCategory(categoryId);

        if (!taskResult.getStatus()) {
            BorderPane pane = new BorderPane();
            pane.setCenter(new Label("No tasks found. Create a new task to get started."));
            tab.setContent(pane);
            return;
        }

        boolean first = true;
        for (Task t : taskResult.getResult().getValue()) {
            TaskComponent comp = new TaskComponent();

            comp.setTaskId(t.getId());
            comp.setTaskName(t.getName());
            comp.setIsTaskDone(t.isDone(true));
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

        tab.setContent(mainPane);
    }
}
