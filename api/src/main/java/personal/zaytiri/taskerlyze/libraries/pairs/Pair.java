package personal.zaytiri.taskerlyze.libraries.pairs;

public class Pair<X, Y> {
    private X key;
    private Y value;

    public Pair(X x, Y y) {
        this.key = x;
        this.value = y;
    }

    public Pair() {
    }

    public X getKey() {
        return key;
    }

    public void setKey(X key) {
        this.key = key;
    }

    public Y getValue() {
        return value;
    }

    public void setValue(Y value) {
        this.value = value;
    }
}
