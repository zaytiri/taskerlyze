package personal.zaytiri.taskerlyze.ui.components;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class TabCategory extends Tab {
    private final StringProperty categoryName = new SimpleStringProperty();
    private final IntegerProperty categoryId = new SimpleIntegerProperty();

    @FXML
    private BorderPane tasksNotFoundBorderPane;

    @FXML
    private Accordion tasksAccordion;

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

    public IntegerProperty categoryIdProperty() {
        return categoryId;
    }

    public StringProperty categoryNameProperty() {
        return categoryName;
    }

    public int getCategoryId() {
        return categoryId.get();
    }

    public void setCategoryId(int categoryId) {
        this.categoryId.set(categoryId);
    }

    public String getCategoryName() {
        return categoryName.get();
    }

    public void setCategoryName(String categoryName) {
        this.categoryName.set(categoryName);
        setText(categoryName);
    }

    public Accordion getTasksAccordion() {
        return tasksAccordion;
    }

    public void showNotFoundMessage() {
        tasksNotFoundBorderPane.setVisible(true);
    }
}
