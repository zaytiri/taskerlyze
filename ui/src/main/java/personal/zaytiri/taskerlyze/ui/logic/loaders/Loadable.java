package personal.zaytiri.taskerlyze.ui.logic.loaders;

import java.util.List;

public interface Loadable<T> {
    List<T> load();
}
