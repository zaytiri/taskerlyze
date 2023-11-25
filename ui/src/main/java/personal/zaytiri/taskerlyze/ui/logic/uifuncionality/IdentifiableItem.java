package personal.zaytiri.taskerlyze.ui.logic.uifuncionality;

public class IdentifiableItem<T> {
    private int itemId;
    private T itemDisplay;

    public IdentifiableItem() {
    }

    public T getItemDisplay() {
        return itemDisplay;
    }

    public void setItemDisplay(T itemDisplay) {
        this.itemDisplay = itemDisplay;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

}
