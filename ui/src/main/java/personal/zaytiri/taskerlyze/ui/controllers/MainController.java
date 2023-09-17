package personal.zaytiri.taskerlyze.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import personal.zaytiri.taskerlyze.ui.views.CalendarView;
import personal.zaytiri.taskerlyze.ui.views.CategoryView;
import personal.zaytiri.taskerlyze.ui.views.MenuView;
import personal.zaytiri.taskerlyze.ui.views.TasksView;


public class MainController {

    @FXML
    public TabPane mainTabPane;

    @FXML
    public BorderPane mainBorderPane;
    @FXML
    public BorderPane menuBorderPane;

    @FXML
    public Label labelMonth;

    @FXML
    public VBox vboxYear;

    @FXML
    public HBox hboxDaysOfTheWeek;

    public void createMainScene(Stage primaryStage) {
        CalendarView calView = new CalendarView();
        calView.populateCalendar(vboxYear, labelMonth, hboxDaysOfTheWeek);

        CategoryView catView = new CategoryView(mainTabPane);
        catView.populateCategoryView();
        catView.setPrimaryStage(primaryStage);

        TasksView tasksView = new TasksView(catView, calView);
        tasksView.populateTasksView();

        MenuView menu = new MenuView(tasksView);
        menu.setButtonsSetOnAction(menuBorderPane);
        menu.setPrimaryStage(primaryStage);
    }
}