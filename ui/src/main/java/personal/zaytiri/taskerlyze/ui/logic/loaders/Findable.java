package personal.zaytiri.taskerlyze.ui.logic.loaders;

import java.util.List;

public interface Findable<T> {

    List<T> find(String subString);
}
