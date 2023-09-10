package personal.zaytiri.taskerlyze.ui.logic;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;

public class CalendarView {

    public void populateCalendar(VBox vboxYear, Label labelMonth, HBox hboxDaysOfTheWeek) {

        populateYear(vboxYear);
        populateMonth(labelMonth);

        ToggleGroup group = new ToggleGroup();
        populateDaysOfTheWeek(hboxDaysOfTheWeek, group);

        setOneToggleAlwaysSelected(group);
    }

    private void populateDaysOfTheWeek(HBox hboxDaysOfTheWeek, ToggleGroup group) {
        LocalDate now = LocalDate.now();

        int increment = 1;
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        for (Node node : hboxDaysOfTheWeek.getChildren()) {
            ToggleButton tbtn = (ToggleButton) node;
            tbtn.setToggleGroup(group);
            Label labelDay = (Label) tbtn.getGraphic();

            LocalDate day = now.minus(Period.ofDays(dayOfWeek.getValue() - increment++));
            labelDay.setText(String.valueOf(day.getDayOfMonth()));

            if (day.getDayOfMonth() == now.getDayOfMonth()) {
                Platform.runLater(() -> tbtn.setSelected(true));
            }
        }
    }

    private void populateMonth(Label labelMonth) {
        LocalDate now = LocalDate.now();
        String monthName = now.getMonth().name().toLowerCase();
        labelMonth.setText(monthName.substring(0, 1).toUpperCase() + monthName.substring(1) + ",");
    }

    private void populateYear(VBox vboxYear) {
        LocalDate now = LocalDate.now();

        ObservableList<Node> year = vboxYear.getChildren();
        for (int i = 0; i < 4; i++) {
            Label digit = (Label) year.get(i);
            digit.setText(String.valueOf(now.getYear()).substring(i, i + 1));
        }
    }

    private void setOneToggleAlwaysSelected(ToggleGroup group) {
        group.selectedToggleProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue == null) {
                        group.selectToggle(oldValue);
                    }
                });
    }
}
