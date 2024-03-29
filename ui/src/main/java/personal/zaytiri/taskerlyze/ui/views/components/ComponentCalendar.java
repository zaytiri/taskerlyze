package personal.zaytiri.taskerlyze.ui.views.components;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import personal.zaytiri.taskerlyze.ui.logic.globals.Configuration;
import personal.zaytiri.taskerlyze.ui.logic.globals.UiGlobalFilter;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.DateConversion;
import personal.zaytiri.taskerlyze.ui.views.elements.LabelDay;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;

public class ComponentCalendar extends BorderPane {
    private final ToggleGroup daysToggleGroup = new ToggleGroup();
    @FXML
    private VBox vboxYear;
    @FXML
    private Label labelMonth;
    @FXML
    private HBox hboxDaysOfTheWeek;
    @FXML
    private Button previousWeekButton;
    @FXML
    private Button nextWeekButton;
    @FXML
    private Button exit;
    @FXML
    private Button today;
    @FXML
    private MFXDatePicker datePicker;

    public ComponentCalendar() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component-calendar.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public LabelDay getActiveDayInToggleGroup() {
        ToggleButton activeToggle = (ToggleButton) daysToggleGroup.getSelectedToggle();
        return (LabelDay) activeToggle.getGraphic();
    }

    @FXML
    public void initialize() {
        setOnlyOneDayToggleAlwaysSelected();
        setPreviousNextWeekButtons();
        populateCalendar(LocalDate.now());
        setButtonsSetOnAction();

        datePicker.setStartingYearMonth(YearMonth.now());
        datePicker.valueProperty().addListener((observableValue, localDate, newLocalDate) -> populateCalendar(newLocalDate));
    }

    public void populateCalendar(LocalDate currentDate) {
        populateYear(currentDate);
        populateMonth(currentDate);
        populateDaysOfTheWeek(currentDate);
    }

    public void populateMonth(LocalDate currentDate) {
        labelMonth.setText(DateConversion.getNameOfMonthCapitalized(currentDate) + ",");
    }

    public void populateYear(LocalDate currentDate) {
        ObservableList<Node> year = vboxYear.getChildren();
        for (int i = 0; i < 4; i++) {
            Label digit = (Label) year.get(i);
            digit.setText(String.valueOf(currentDate.getYear()).substring(i, i + 1));
        }
    }


    public void setButtonsSetOnAction() {
        setExitButtonOnAction(exit);
        setTodayButtonOnAction(today);
    }

    private LocalDate getConvertedDate(LabelDay labelDay) {
        return DateConversion.dateToLocalDate(
                labelDay.getYear(),
                labelDay.getMonth(),
                labelDay.getDay());
    }

    private void populateDaysOfTheWeek(LocalDate currentDate) {
        int increment = 1;
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        ObservableList<Node> nodes = hboxDaysOfTheWeek.getChildren();
        for (Node node : nodes) {
            if (node instanceof Button) {
                continue;
            }

            ToggleButton tbtn = (ToggleButton) node;
            tbtn.setToggleGroup(daysToggleGroup);
            LabelDay labelDay = (LabelDay) tbtn.getGraphic();

            LocalDate day = currentDate.minus(Period.ofDays(dayOfWeek.getValue() - increment++));
            labelDay.setText(String.valueOf(day.getDayOfMonth()));

            labelDay.setMonth(day.getMonthValue());
            labelDay.setDay(day.getDayOfMonth());
            labelDay.setYear(day.getYear());

            tbtn.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (Boolean.TRUE.equals(newValue)) {
                    LocalDate date = getConvertedDate(labelDay);
                    populateMonth(date);
                    populateYear(date);

                    UiGlobalFilter.getUiGlobalFilter().setActiveDay(date);
                    datePicker.setValue(date);
                    datePicker.updateCurrentDate();
                }
            });

            if (day.getDayOfMonth() == currentDate.getDayOfMonth()) {
                Platform.runLater(() -> tbtn.setSelected(true));
            }
        }
    }

    private void setExitButtonOnAction(Button exit) {
        Stage primaryStage = Configuration.getInstance().getPrimaryStage();
        exit.setOnAction(event -> primaryStage.close());
        primaryStage.setOnCloseRequest(event -> primaryStage.close());
    }

    private void setOnlyOneDayToggleAlwaysSelected() {
        daysToggleGroup.selectedToggleProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue == null) {
                        daysToggleGroup.selectToggle(oldValue);
                    }
                });
    }

    private void setPreviousNextWeekButtons() {
        previousWeekButton.setOnAction(event -> {
            LocalDate date = getConvertedDate(getActiveDayInToggleGroup());
            date = date.minusDays(7);
            populateCalendar(date);
        });

        nextWeekButton.setOnAction(event -> {
            LocalDate date = getConvertedDate(getActiveDayInToggleGroup());
            date = date.plusDays(7);
            populateCalendar(date);
        });
    }

    private void setTodayButtonOnAction(Button today) {
        today.setOnAction(event -> populateCalendar(LocalDate.now()));
    }
}
