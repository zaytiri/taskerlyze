package personal.zaytiri.taskerlyze.ui.views.modifiables;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.CategoryEntity;
import personal.zaytiri.taskerlyze.ui.logic.loaders.CategoryLoader;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.KeyBindable;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.MessageType;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.UiGlobalMessage;

import java.io.IOException;

public class TabCreateUpdateCategory extends Tab {
    private final KeyBindable keyBinding;
    private CategoryEntity newCategory = new CategoryEntity();
    @FXML
    private TextField categoryName;
    private int categoryId;
    private boolean isSuccessful;

    public TabCreateUpdateCategory() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/tab-create-update-category.fxml"));
        keyBinding = new KeyBindable();
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
        keyBinding.addEnterKeyBinding(categoryName, evt -> create());
        keyBinding.addEscapeKeyBinding(categoryName, evt -> removePaneFromParent());

        Platform.runLater(() -> {
            populate();
            categoryName.selectAll();
            categoryName.requestFocus();
        });
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    private void create() {
        newCategory
                .setName(categoryName.getText());

        Pair<CategoryEntity, Pair<Boolean, String>> response = newCategory.createOrUpdate();
        boolean isSuccessfulFromApi = response.getValue().getKey();
        String messageFromApi = response.getValue().getValue();

        if (!isSuccessfulFromApi) {
            UiGlobalMessage.getUiGlobalMessage().setMessage(MessageType.ERROR, messageFromApi);
            return;
        }

        UiGlobalMessage.getUiGlobalMessage().setMessage(MessageType.NEUTRAL, messageFromApi);
        removePaneFromParent();


    }

    private void populate() {
        if (this.categoryId == 0) {
            return;
        }

        newCategory = new CategoryEntity(this.categoryId).get();
        categoryName.setText(newCategory.getName());
    }

    private void removePaneFromParent() {
        TabPane parent = this.getTabPane();
        parent.getTabs().remove(this);
        CategoryLoader.getCategoryLoader().load();
    }

}
