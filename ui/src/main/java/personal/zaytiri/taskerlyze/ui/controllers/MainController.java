package personal.zaytiri.taskerlyze.ui.controllers;

import javafx.fxml.FXML;
import personal.zaytiri.taskerlyze.ui.views.components.ComponentCalendar;
import personal.zaytiri.taskerlyze.ui.views.components.ComponentSideMenu;


public class MainController {
    @FXML
    private ComponentSideMenu componentSideMenu;
    @FXML
    private ComponentCalendar componentCalendar;

    public void setAppPreConfigurations() {
        componentSideMenu.addPropertyChangeListener(componentCalendar);
    }
}