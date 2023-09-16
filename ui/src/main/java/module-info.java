module ui.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires api.main;
    requires java.desktop;

    opens personal.zaytiri.taskerlyze.ui.views to javafx.fxml;
    opens personal.zaytiri.taskerlyze.ui.controllers to javafx.fxml;
    opens personal.zaytiri.taskerlyze.ui.logic to javafx.fxml;
    opens personal.zaytiri.taskerlyze.ui.components to javafx.fxml;

    exports personal.zaytiri.taskerlyze.ui.views;
    exports personal.zaytiri.taskerlyze.ui.controllers;
    exports personal.zaytiri.taskerlyze.ui.logic;
    exports personal.zaytiri.taskerlyze.ui.components;
    exports personal.zaytiri.taskerlyze.ui.logic.entities;
    opens personal.zaytiri.taskerlyze.ui.logic.entities to javafx.fxml;
}