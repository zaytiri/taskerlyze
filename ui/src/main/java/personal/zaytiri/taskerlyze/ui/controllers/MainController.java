package personal.zaytiri.taskerlyze.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

import java.time.LocalDate;


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
    @FXML
    private Button previousWeekButton;
    @FXML
    private Button nextWeekButton;

    public void createMainScene(Stage primaryStage) {
        CalendarView calView = new CalendarView(vboxYear, labelMonth, hboxDaysOfTheWeek, previousWeekButton, nextWeekButton);
        calView.populateCalendar(LocalDate.now());

        CategoryView catView = new CategoryView(mainTabPane);
        catView.setPrimaryStage(primaryStage);
        catView.populateCategoryView();

        TasksView tasksView = new TasksView(catView, calView);
        tasksView.populateTasksView();

        MenuView menu = new MenuView(tasksView, calView);
        menu.setPrimaryStage(primaryStage);
        menu.setButtonsSetOnAction(menuBorderPane);
    }
}