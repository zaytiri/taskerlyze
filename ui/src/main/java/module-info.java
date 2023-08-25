module personal.zaytiri.taskerlyze.ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires api.main;
    requires java.desktop;


    opens personal.zaytiri.taskerlyze.ui to javafx.fxml;
    exports personal.zaytiri.taskerlyze.ui;
    exports personal.zaytiri.taskerlyze.ui.views;
    opens personal.zaytiri.taskerlyze.ui.views to javafx.fxml;
    exports personal.zaytiri.taskerlyze.ui.controllers;
    opens personal.zaytiri.taskerlyze.ui.controllers to javafx.fxml;
}