package personal.zaytiri.taskerlyze.ui.views.elements;

import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;
import javafx.util.StringConverter;
import personal.zaytiri.taskerlyze.ui.logic.loaders.Findable;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.IdentifiableItem;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class MoveEntity extends AnchorPane {
    private final Findable<Pair<Integer, String>> findableEntity;
    @FXML
    private MFXListView<IdentifiableItem<String>> itemsList;
    @FXML
    private MFXTextField searchText;
    @FXML
    private Label label;

    public MoveEntity(Findable<Pair<Integer, String>> findableEntity) {
        this.findableEntity = findableEntity;

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/move-entity.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load fxml file", ex);
        }
    }

    public IdentifiableItem<String> getSelected() {
        return itemsList.getSelectionModel().getSelectedValue();
    }

    @FXML
    public void initialize() {
        overrideIdentifiableItemDisplayInListView();

        // search while the user is typing to show a realtime list
        searchText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Objects.equals(oldValue, newValue) || newValue.isEmpty()) {
                search();
            }
        });
    }

    public void setDescription(String text) {
        this.label.setText(text);
    }

    private void overrideIdentifiableItemDisplayInListView() {
        StringConverter<IdentifiableItem<String>> converter = FunctionalStringConverter.to(profile -> (profile == null) ? "" : profile.getItemDisplay());
        itemsList.setConverter(converter);
    }

    private void search() {
        String textToSearch = searchText.getText();
        if (textToSearch.isEmpty()) {
            return;
        }

        List<Pair<Integer, String>> foundResults = findableEntity.find(textToSearch);
        itemsList.getItems().clear();

        for (Pair<Integer, String> result : foundResults) {
            IdentifiableItem<String> item = new IdentifiableItem<>();
            item.setItemId(result.getKey());
            item.setItemDisplay(result.getValue());

            itemsList.getItems().add(item);
        }
    }
}
