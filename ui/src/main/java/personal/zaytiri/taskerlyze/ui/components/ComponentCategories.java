package personal.zaytiri.taskerlyze.ui.components;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.ui.logic.CategoryLoader;
import personal.zaytiri.taskerlyze.ui.logic.entities.CategoryEntity;
import personal.zaytiri.taskerlyze.ui.logic.entities.Result;

import java.io.IOException;

public class ComponentCategories extends TabPane {
    @FXML
    private Tab defaultTab;

    @FXML
    private TabPane mainTabPane;
    @FXML
    private BorderPane categoriesNotFoundMessage;

    public ComponentCategories() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component-categories.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }

    }

    @FXML
    public void initialize() {
        setDefaultTabOnAction();
        populateCategoryView();
    }

    public void populateCategoryView() {
        CategoryLoader loader = new CategoryLoader();
        ObservableList<CategoryEntity> categories = FXCollections.observableList(loader.load());

        mainTabPane.getTabs().clear();

        if (categories.isEmpty()) {
            categoriesNotFoundMessage.setVisible(true);
        }

        mainTabPane.getTabs().add(defaultTab);

        boolean selectFirst = true;
        for (CategoryEntity category : categories) {
            TabCategory newTab = new TabCategory();
            newTab.setCategoryId(category.getId());
            newTab.setCategoryName(category.getName());
            newTab.setContextMenu(actionEvent -> populateCategoryView());

            if (selectFirst) {
                Platform.runLater(() -> mainTabPane.getSelectionModel().select(newTab));
                selectFirst = false;
            }

            mainTabPane.getTabs().add(newTab);
        }
    }

    private void setDefaultTabOnAction() {
        Label image = (Label) defaultTab.getGraphic();
        image.setOnMouseClicked(event -> {
            Result<Category> categoryResult = new Result<>(new Category());
            DialogNewCategory dialog = new DialogNewCategory(categoryResult);
            dialog.showStage();

            if (categoryResult.getResult() != null) {
                populateCategoryView();
            }
            event.consume();
        });
    }
}
