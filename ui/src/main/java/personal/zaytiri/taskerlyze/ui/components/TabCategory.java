package personal.zaytiri.taskerlyze.ui.components;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import personal.zaytiri.taskerlyze.ui.logic.TaskLoader;
import personal.zaytiri.taskerlyze.ui.logic.entities.CategoryEntity;

import java.io.IOException;

public class TabCategory extends Tab {
    private final StringProperty categoryName = new SimpleStringProperty();
    private final IntegerProperty categoryId = new SimpleIntegerProperty();

    public TabCategory() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/tab-category.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public int getCategoryId() {
        return categoryId.get();
    }

    public void setCategoryId(int categoryId) {
        this.categoryId.set(categoryId);
    }

    @FXML
    public void initialize() {
        setOnAction();
    }

    public void setCategoryName(String categoryName) {
        this.categoryName.set(categoryName);
        setText(categoryName);
    }

    public void setContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        setContextMenu(getTabContextMenu(ifSuccessful));
    }

    public void setOnAction() {
        selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                TaskLoader.getTaskLoader().setActiveCategoryId(getCategoryId());
            }
        });
    }

    private ContextMenu getTabContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        ContextMenu tabContextMenu = new ContextMenu();
        MenuItem removeCategoryMenuItem = new MenuItem();

        removeCategoryMenuItem.setText("Remove");
        removeCategoryMenuItem.setOnAction(event -> {
            CategoryEntity category = new CategoryEntity().setId(getCategoryId());

            if (category.remove()) {
                ifSuccessful.handle(event);
            }
        });
        tabContextMenu.getItems().add(removeCategoryMenuItem);
        return tabContextMenu;
    }
}
