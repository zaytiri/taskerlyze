package personal.zaytiri.taskerlyze.ui.views.components;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.ScrollEvent;
import personal.zaytiri.taskerlyze.ui.logic.entities.CategoryEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.CategoryLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.ICategorable;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MenuOptions;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.PopupAction;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

public class ComponentCategories extends TabPane implements PropertyChangeListener {
    private final MenuOptions contextMenu;
    private List<CategoryEntity> categories;
    @FXML
    private TabCategory defaultTab;
    @FXML
    private TabPane mainTabPane;
    private ICategorable currentView;

    public ComponentCategories() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/component-categories.fxml"));
        this.contextMenu = new MenuOptions();

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
        CategoryLoader.getCategoryLoader().addPropertyChangeListener(this);
        mainTabPane.setContextMenu(getTabContextMenu());
        reverseTabPaneScrollingDirection();
        CategoryLoader.getCategoryLoader().load();
    }

    public void setView(ICategorable currentView) {
        this.currentView = currentView;
    }

    public void populateCategoryView() {
        ObservableList<Tab> tabs = mainTabPane.getTabs();
        tabs.clear();

        defaultTab.setCategoryId(0);
        tabs.add(defaultTab);

        boolean selectFirst = true;
        for (CategoryEntity category : categories) {
            TabCategory newTab = new TabCategory();
            newTab.setCategoryId(category.getId());
            newTab.setCategoryName(category.getName());
            newTab.setView(this.currentView);
            newTab.setContextMenu();

            if (selectFirst) {
                Platform.runLater(() -> mainTabPane.getSelectionModel().select(newTab));
                selectFirst = false;
            }

            tabs.add(newTab);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.categories = FXCollections.observableList((List<CategoryEntity>) evt.getNewValue());
        populateCategoryView();
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

    private void addAddCategoryOptionForContextMenu() {
        this.contextMenu.addMenuItem("Add new category", event -> PopupAction.showDialogForAddingCategory(mainTabPane));
    }

    private ContextMenu getTabContextMenu() {
        addAddCategoryOptionForContextMenu();

        return contextMenu.buildContextMenu();
    }
}
