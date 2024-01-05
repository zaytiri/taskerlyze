package personal.zaytiri.taskerlyze.ui.logic.uifuncionality;

import javafx.scene.layout.AnchorPane;

public abstract class Categorable extends AnchorPane {
    private int categoryId;

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public abstract Categorable getNewInstance();

    public abstract void loadView();
}
