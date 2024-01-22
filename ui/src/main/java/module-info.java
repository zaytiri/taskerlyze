module ui.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires api.main;
    requires java.desktop;
    requires com.google.common;
    requires MaterialFX;

    opens personal.zaytiri.taskerlyze.ui.views to javafx.fxml;
    opens personal.zaytiri.taskerlyze.ui.controllers to javafx.fxml;
    opens personal.zaytiri.taskerlyze.ui.logic to javafx.fxml;
    opens personal.zaytiri.taskerlyze.ui.views.components to javafx.fxml;

    exports personal.zaytiri.taskerlyze.ui.views;
    exports personal.zaytiri.taskerlyze.ui.controllers;
    exports personal.zaytiri.taskerlyze.ui.logic;
    exports personal.zaytiri.taskerlyze.ui.views.components;
    exports personal.zaytiri.taskerlyze.ui.logic.entities;
    opens personal.zaytiri.taskerlyze.ui.logic.entities to javafx.fxml;
    exports personal.zaytiri.taskerlyze.ui.views.popups;
    opens personal.zaytiri.taskerlyze.ui.views.popups to javafx.fxml;
    exports personal.zaytiri.taskerlyze.ui.logic.loaders;
    opens personal.zaytiri.taskerlyze.ui.logic.loaders to javafx.fxml;
    exports personal.zaytiri.taskerlyze.ui.views.elements;
    opens personal.zaytiri.taskerlyze.ui.views.elements to javafx.fxml;
    exports personal.zaytiri.taskerlyze.ui.logic.uifuncionality;
    opens personal.zaytiri.taskerlyze.ui.logic.uifuncionality to javafx.fxml;
    exports personal.zaytiri.taskerlyze.ui.views.popups.interfaces;
    opens personal.zaytiri.taskerlyze.ui.views.popups.interfaces to javafx.fxml;
    exports personal.zaytiri.taskerlyze.ui.views.modifiables;
    opens personal.zaytiri.taskerlyze.ui.views.modifiables to javafx.fxml;
}