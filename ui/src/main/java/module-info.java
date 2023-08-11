module personal.zaytiri.taskerlyze.ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires api.main;


    opens personal.zaytiri.taskerlyze.ui to javafx.fxml;
    exports personal.zaytiri.taskerlyze.ui;
}