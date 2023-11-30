package personal.zaytiri.taskerlyze.ui.views.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.CategoryEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.CategoryLoader;

import java.io.IOException;

public class TabCreateUpdateCategory extends Tab {
    private final CategoryEntity newCategory = new CategoryEntity();
    @FXML
    private TextField categoryName;

    public TabCreateUpdateCategory() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/tab-create-update-category.fxml"));

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
        categoryName.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                create();
                ev.consume();
            }
        });
    }

    private void create() {
        newCategory
                .setName(categoryName.getText());

        Pair<CategoryEntity, Pair<Boolean, String>> response = newCategory.createOrUpdate();
        boolean isSuccessfulFromApi = response.getValue().getKey();
        String errorMessageFromApi = response.getValue().getValue();

        if (!isSuccessfulFromApi) {
            System.out.println(errorMessageFromApi);
            return;
        }

        TabPane parent = this.getTabPane();
        parent.getTabs().remove(this);

        CategoryLoader.getCategoryLoader().load();
    }

}
