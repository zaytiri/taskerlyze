package personal.zaytiri.taskerlyze.ui.components;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class LabelDay extends Label {
    private final IntegerProperty month = new SimpleIntegerProperty();
    private final IntegerProperty year = new SimpleIntegerProperty();
    private final IntegerProperty day = new SimpleIntegerProperty();
    @FXML
    public Label label;

    public LabelDay() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/label-day.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public IntegerProperty dayProperty() {
        return day;
    }

    public int getDay() {
        return day.get();
    }

    public void setDay(int day) {
        this.day.set(day);
    }

    public int getMonth() {
        return month.get();
    }

    public void setMonth(int value) {
        month.set(value);
    }

    public int getYear() {
        return year.get();
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public IntegerProperty monthProperty() {
        return month;
    }

    public IntegerProperty yearProperty() {
        return year;
    }
}
