package personal.zaytiri.taskerlyze.libraries.pairs;

public class Pair<X, Y> {
    public final X key;
    public final Y value;

    public Pair(X x, Y y) {
        this.key = x;
        this.value = y;
    }
}
