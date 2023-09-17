package personal.zaytiri.taskerlyze.ui.views;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import personal.zaytiri.taskerlyze.ui.components.LabelDay;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class CalendarView {
    private final VBox vboxYear;
    private final Label labelMonth;
    private final HBox hboxDaysOfTheWeek;
    private final Button previousWeekButton;
    private final Button nextWeekButton;
    private ToggleGroup daysView;
    private ObservableList<ToggleButton> daysToggleButtons;

    public CalendarView(VBox vboxYear, Label labelMonth, HBox hboxDaysOfTheWeek, Button previousWeekButton, Button nextWeekButton) {
        this.vboxYear = vboxYear;
        this.labelMonth = labelMonth;
        this.hboxDaysOfTheWeek = hboxDaysOfTheWeek;
        this.previousWeekButton = previousWeekButton;
        this.nextWeekButton = nextWeekButton;
        setPreviousNextWeekButtons();
    }

    public LabelDay getActiveDayInToggleGroup() {
        ToggleButton activeToggle = (ToggleButton) daysView.getSelectedToggle();
        return (LabelDay) activeToggle.getGraphic();
    }

    public ObservableList<ToggleButton> getDayToggleButtons() {
        return daysToggleButtons;
    }

    public LocalDate labelDayToLocalDate(LabelDay day) {
        return LocalDate.of(day.getYear(), day.getMonth(), day.getDay());
    }

    public void populateCalendar(LocalDate currentDate) {
        populateYear(currentDate);
        populateMonth(currentDate);

        daysView = new ToggleGroup();
        populateDaysOfTheWeek(currentDate);

        setOneToggleAlwaysSelected();
    }

    public void populateMonth(LocalDate currentDate) {
        String monthName = currentDate.getMonth().name().toLowerCase();
        labelMonth.setText(monthName.substring(0, 1).toUpperCase() + monthName.substring(1) + ",");
    }

    public void populateYear(LocalDate currentDate) {
        ObservableList<Node> year = vboxYear.getChildren();
        for (int i = 0; i < 4; i++) {
            Label digit = (Label) year.get(i);
            digit.setText(String.valueOf(currentDate.getYear()).substring(i, i + 1));
        }
    }

    private void populateDaysOfTheWeek(LocalDate currentDate) {
        int increment = 1;
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        daysToggleButtons = FXCollections.observableList(new ArrayList<>());
        ObservableList<Node> nodes = hboxDaysOfTheWeek.getChildren();
        for (Node node : nodes) {
            if (node instanceof Button) {
                continue;
            }

            ToggleButton tbtn = (ToggleButton) node;
            tbtn.setToggleGroup(daysView);
            LabelDay labelDay = (LabelDay) tbtn.getGraphic();

            LocalDate day = currentDate.minus(Period.ofDays(dayOfWeek.getValue() - increment++));
            labelDay.setText(String.valueOf(day.getDayOfMonth()));

            labelDay.setMonth(day.getMonthValue());
            labelDay.setDay(day.getDayOfMonth());
            labelDay.setYear(day.getYear());

            if (day.getDayOfMonth() == currentDate.getDayOfMonth()) {
                Platform.runLater(() -> tbtn.setSelected(true));
            }

            daysToggleButtons.add(tbtn);
        }
    }

    private void setOneToggleAlwaysSelected() {
        daysView.selectedToggleProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue == null) {
                        daysView.selectToggle(oldValue);
                    }
                });
    }

    private void setPreviousNextWeekButtons() {
        previousWeekButton.setOnAction(event -> {
            LocalDate date = labelDayToLocalDate(getActiveDayInToggleGroup());
            date = date.minusDays(7);
            populateCalendar(date);
        });

        nextWeekButton.setOnAction(event -> {
            LocalDate date = labelDayToLocalDate(getActiveDayInToggleGroup());
            date = date.plusDays(7);
            populateCalendar(date);
        });
    }
}
