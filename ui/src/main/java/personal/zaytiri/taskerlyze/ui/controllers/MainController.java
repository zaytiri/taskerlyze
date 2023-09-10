package personal.zaytiri.taskerlyze.ui.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import personal.zaytiri.taskerlyze.ui.logic.CalendarView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class MainController {

    @FXML
    public TabPane mainTabPane;

    @FXML
    public BorderPane mainBorderPane;

    @FXML
    public Label labelMonth;

    @FXML
    public VBox vboxYear;

    @FXML
    public HBox hboxDaysOfTheWeek;

    public void createMainScene() {
        mainTabPane.getStylesheets().add("css/tasks-tab-view.css");

        CalendarView calView = new CalendarView();
        calView.populateCalendar(vboxYear, labelMonth, hboxDaysOfTheWeek);


    }

    private void loadData(){

    }

}