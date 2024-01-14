package personal.zaytiri.taskerlyze.ui.views.popups.interfaces;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.Entity;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.IdentifiableItem;

import java.util.List;
import java.util.Objects;


public abstract class DialogMoveEntity<ESearch extends Entity, T> extends Dialog<T> {

    private final ESearch entityToBeFound;
    protected int entityIdToBeMoved;
    @FXML
    protected Button moveButton;
    @FXML
    protected ListView<IdentifiableItem<String>> itemsList;
    @FXML
    private TextField searchText;
    @FXML
    private Button searchButton;
    @FXML
    private Label errorMessage;

    public DialogMoveEntity(ESearch entityToBeFound) {
        super("dialog-move", "Search:");
        this.entityToBeFound = entityToBeFound;
    }

    @FXML
    public void initialize() {
        overrideIdentifiableItemDisplayInListView();
        setActionOnMoveButton();

        // search while the user is typing to show a realtime list
        searchText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Objects.equals(oldValue, newValue) || newValue.isEmpty()) {
                search();
            }
        });
    }

    public void setEntityToBeMoved(int entityIdToBeMoved) {
        this.entityIdToBeMoved = entityIdToBeMoved;
    }

    @Override
    public void showDialog() {

        show();
    }

    protected abstract void setActionOnMoveButton();

    private void overrideIdentifiableItemDisplayInListView() {
        itemsList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(IdentifiableItem<String> item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getItemDisplay() == null) {
                    setText(null);
                } else {
                    setText(item.getItemDisplay());
                }
            }
        });
    }

    private void search() {
        String textToSearch = searchText.getText();
        if (textToSearch.isEmpty()) {
            return;
        }

        List<Pair<Integer, String>> foundResults = entityToBeFound.findBySubString(textToSearch);
        itemsList.getItems().clear();

        for (Pair<Integer, String> result : foundResults) {
            IdentifiableItem<String> item = new IdentifiableItem<>();
            item.setItemId(result.getKey());
            item.setItemDisplay(result.getValue());

            itemsList.getItems().add(item);
        }
    }
}
