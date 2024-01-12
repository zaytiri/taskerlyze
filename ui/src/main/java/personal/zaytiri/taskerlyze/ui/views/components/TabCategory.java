package personal.zaytiri.taskerlyze.ui.views.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import personal.zaytiri.taskerlyze.ui.logic.entities.CategoryEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.CategoryLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.Categorable;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MenuOptions;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.PopupAction;

import java.io.IOException;

public class TabCategory extends Tab {
    private final StringProperty categoryName = new SimpleStringProperty();
    private final MenuOptions contextMenu;
    private final Categorable currentView;
    private int categoryId;
    @FXML
    private ScrollPane mainScrollPane;

    public TabCategory(Categorable currentView) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/tab-category.fxml"));

        this.contextMenu = new MenuOptions();
        this.currentView = currentView;

        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        this.currentView.setCategoryId(categoryId);
    }

    @FXML
    public void initialize() {
        setOnAction();
        this.mainScrollPane.setContent(this.currentView);

    }

    public void setCategoryName(String categoryName) {
        this.categoryName.set(categoryName);
        setText(categoryName);
    }

    public void setContextMenu() {
        setContextMenu(getTabContextMenu());
    }

    public void setOnAction() {
        selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (Boolean.TRUE.equals(newValue)) {
                this.currentView.loadView();
            }
        });
    }

    private void addAddCategoryOptionForContextMenu() {
        this.contextMenu.addMenuItem("Add new category", event -> PopupAction.showDialogForAddingCategory(this.getTabPane()));
    }

    private void addEditCategoryOptionForContextMenu() {
        this.contextMenu.addMenuItem("Edit", event -> PopupAction.showDialogForEditingCategory(this.getTabPane(), this, getCategoryId()));
    }

    private void addRemoveCategoryOptionForContextMenu() {
        this.contextMenu.addMenuItem("Remove (no confirmation)", event -> {
            CategoryEntity category = new CategoryEntity(getCategoryId());
            if (category.remove()) {
                CategoryLoader.getCategoryLoader().load();
            }
        });
    }

    private ContextMenu getTabContextMenu() {
        addAddCategoryOptionForContextMenu();
        addEditCategoryOptionForContextMenu();
        addRemoveCategoryOptionForContextMenu();

        return contextMenu.buildContextMenu();
    }
}
