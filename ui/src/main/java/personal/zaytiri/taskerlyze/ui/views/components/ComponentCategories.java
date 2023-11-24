package personal.zaytiri.taskerlyze.ui.views.components;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import personal.zaytiri.taskerlyze.ui.logic.entities.CategoryEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.CategoryLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.PopupAction;

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
        reverseTabPaneScrollingDirection();
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

    public void reverseTabPaneScrollingDirection() {
        EventDispatcher originalDispatcher = mainTabPane.getEventDispatcher();
        mainTabPane.setEventDispatcher(new EventDispatcher() {
            @Override
            public Event dispatchEvent(Event event, EventDispatchChain tail) {
                if (event instanceof ScrollEvent e) {
                    ScrollEvent reversedEvent = new ScrollEvent(
                            e.getEventType(), e.getX(), e.getY(), e.getScreenX(),
                            e.getScreenY(), e.isShiftDown(), e.isControlDown(), e.isAltDown(),
                            e.isMetaDown(), e.isDirect(), e.isInertia(),
                            -e.getDeltaX(), // reverse it
                            e.getDeltaY(),
                            -e.getTotalDeltaX(), // reverse it
                            e.getTotalDeltaY(),
                            e.getMultiplierX(), e.getMultiplierY(), e.getTextDeltaXUnits(),
                            e.getTextDeltaX(), e.getTextDeltaYUnits(), e.getTextDeltaY(),
                            e.getTouchCount(), e.getPickResult()
                    );

                    return originalDispatcher.dispatchEvent(reversedEvent, tail);
                }

                return originalDispatcher.dispatchEvent(event, tail);
            }
        });
    }

    private void setDefaultTabOnAction() {
        Label image = (Label) defaultTab.getGraphic();
        image.setOnMouseClicked(event -> PopupAction.showDialogForAddingCategory(ifSuccessful -> populateCategoryView()));
    }
}
