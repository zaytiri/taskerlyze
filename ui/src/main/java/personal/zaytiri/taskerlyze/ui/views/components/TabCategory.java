package personal.zaytiri.taskerlyze.ui.views.components;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;
import personal.zaytiri.taskerlyze.ui.logic.entities.CategoryEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.TaskLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MenuOptions;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.PopupAction;

import java.io.IOException;

public class TabCategory extends Tab {
    private final StringProperty categoryName = new SimpleStringProperty();
    private final IntegerProperty categoryId = new SimpleIntegerProperty();
    private final MenuOptions contextMenu;
    @FXML
    private ComponentTasks componentTasks;

    public TabCategory() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/tab-category.fxml"));
        this.contextMenu = new MenuOptions();

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
        this.componentTasks.setCategoryId(categoryId);
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

    private void addAddCategoryOptionForContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        this.contextMenu.addMenuItem("Add new category", event -> PopupAction.showDialogForAddingCategory(ifSuccessful));
    }

    private void addEditCategoryOptionForContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        this.contextMenu.addMenuItem("Edit", event -> PopupAction.showDialogForEditingCategory(getCategoryId(), ifSuccessful));
    }

    private void addRemoveCategoryOptionForContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        this.contextMenu.addMenuItem("Remove (no confirmation)", event -> {
            CategoryEntity category = new CategoryEntity(getCategoryId());
            if (category.remove()) {
                ifSuccessful.handle(event);
            }
        });
    }

    private ContextMenu getTabContextMenu(EventHandler<ActionEvent> ifSuccessful) {
        addAddCategoryOptionForContextMenu(ifSuccessful);
        addEditCategoryOptionForContextMenu(ifSuccessful);
        addRemoveCategoryOptionForContextMenu(ifSuccessful);

        return contextMenu.buildContextMenu();
    }


}
