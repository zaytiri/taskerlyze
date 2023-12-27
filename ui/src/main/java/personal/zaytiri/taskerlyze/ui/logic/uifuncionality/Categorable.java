package personal.zaytiri.taskerlyze.ui.logic.uifuncionality;

import javafx.scene.layout.AnchorPane;

public abstract class Categorable extends AnchorPane {
    private int categoryId;

    public abstract void loadView();

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
