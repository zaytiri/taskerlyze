package personal.zaytiri.taskerlyze.ui.components;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;

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


}
