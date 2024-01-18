package personal.zaytiri.taskerlyze.ui.logic.loaders;

import java.util.List;

public interface Loader<T> {
    List<T> load();
}
