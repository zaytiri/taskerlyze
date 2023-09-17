package personal.zaytiri.taskerlyze.ui.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import personal.zaytiri.taskerlyze.app.api.controllers.CategoryController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.ui.components.DialogNewCategory;
import personal.zaytiri.taskerlyze.ui.components.TabCategory;
import personal.zaytiri.taskerlyze.ui.logic.entities.Result;

import java.util.ArrayList;
import java.util.List;

public class CategoryView {
    private final TabPane mainTabPane;
    private Stage primaryStage;
    private TabCategory defaultTab;
    private ObservableList<TabCategory> tabs;

    public CategoryView(TabPane mainTabPane) {
        this.mainTabPane = mainTabPane;
        createDefaultTab();
    }

    public TabCategory getActiveTabCategory() {
        return (TabCategory) mainTabPane.getSelectionModel().getSelectedItem();
    }

    public ObservableList<TabCategory> getTabs() {
        return this.tabs;
    }

    public void populateCategoryView() {
        mainTabPane.getTabs().clear();

        tabs = FXCollections.observableList(new ArrayList<>());

        CategoryController catController = new CategoryController();
        OperationResult<List<Category>> categories = catController.get(null, null);

        if (!categories.getStatus()) {
            BorderPane pane = new BorderPane();
            pane.setCenter(new Label("No categories found. Create a new category to get started."));
            defaultTab.setContent(pane);
        }

        mainTabPane.getTabs().add(defaultTab);
        tabs.add(defaultTab);

        if (categories.getStatus()) {
            for (Category category : categories.getResult()) {
                TabCategory newTab = new TabCategory();
                newTab.setCategoryId(category.getId());
                newTab.setCategoryName(category.getName());

                newTab.setContextMenu(getTabContextMenu(newTab.getCategoryId()));

                mainTabPane.getTabs().add(newTab);
                tabs.add(newTab);
            }
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void createDefaultTab() {
        ImageView imageView = new ImageView("icons/plus.png");
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        Label image = new Label();
        image.setGraphic(imageView);

        defaultTab = new TabCategory();
        defaultTab.setId("default-tab");
        defaultTab.setGraphic(image);

        setDefaultTabOnAction();
    }

    private ContextMenu getTabContextMenu(int categoryId) {
        CategoryController catController = new CategoryController();

        ContextMenu tabContextMenu = new ContextMenu();
        MenuItem removeCategoryMenuItem = new MenuItem();
        removeCategoryMenuItem.setText("Remove");
        removeCategoryMenuItem.setOnAction(event -> {
            OperationResult<Category> result = catController.delete(categoryId);
            if (result.getStatus()) {
                populateCategoryView();
            }
        });
        tabContextMenu.getItems().add(removeCategoryMenuItem);
        return tabContextMenu;
    }

    private void setDefaultTabOnAction() {
        Label image = (Label) defaultTab.getGraphic();
        image.setOnMouseClicked(event -> {
            Result<Category> categoryResult = new Result<>(new Category());
            DialogNewCategory dialog = new DialogNewCategory(categoryResult, primaryStage);
            dialog.showStage();

            if (categoryResult.getResult() != null) {
                populateCategoryView();
            }
            event.consume();
        });
    }
}
