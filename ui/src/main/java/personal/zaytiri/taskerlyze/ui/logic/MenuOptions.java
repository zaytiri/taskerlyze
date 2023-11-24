package personal.zaytiri.taskerlyze.ui.logic;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MenuOptions {
    private final ContextMenu contextMenu;
    private final List<MenuItem> menuItems;

    public MenuOptions() {
        this.contextMenu = new ContextMenu();
        this.menuItems = new ArrayList<>();
    }

    public void addMenuItem(String displayText, EventHandler<ActionEvent> menuAction) {
        MenuItem menuItem = new MenuItem();

        menuItem.setText(displayText);
        menuItem.setOnAction(menuAction);

        menuItems.add(menuItem);
    }

    public ContextMenu buildContextMenu() {
        this.contextMenu.getItems().addAll(menuItems);
        return this.contextMenu;
    }
}
