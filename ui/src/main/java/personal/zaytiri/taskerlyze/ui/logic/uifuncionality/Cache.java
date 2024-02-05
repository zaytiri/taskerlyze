package personal.zaytiri.taskerlyze.ui.logic.uifuncionality;


import javafx.util.Pair;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Cache<UiElement> {
    // current category id, current date, if lst is to be reloaded from api, the list of elements to show.
    private final Map<Pair<Integer, LocalDate>, List<UiElement>> cache = new HashMap<>();

    public Map.Entry<Pair<Integer, LocalDate>, List<UiElement>> findByKey(Pair<Integer, LocalDate> keyToBeFound) {
        for (Map.Entry<Pair<Integer, LocalDate>, List<UiElement>> cacheEntry : cache.entrySet()) {
            if (Objects.equals(cacheEntry.getKey().getKey(), keyToBeFound.getKey())
                    && Objects.equals(cacheEntry.getKey().getValue(), keyToBeFound.getValue())) {
                return cacheEntry;
            }
        }
        return null;
    }

    public Map<Pair<Integer, LocalDate>, List<UiElement>> get() {
        return cache;
    }

    public void put(Pair<Integer, LocalDate> keyToReplace, List<UiElement> toBeReplaced) {
        cache.put(
                keyToReplace,
                toBeReplaced
        );
    }

    public boolean remove(Pair<Integer, LocalDate> keyToRemove) {
        return cache.keySet().removeIf(key ->
                Objects.equals(key.getKey(), keyToRemove.getKey())
                        && Objects.equals(key.getValue(), keyToRemove.getValue()));
    }

    public void replace(Pair<Integer, LocalDate> keyToReplace, List<UiElement> toBeReplaced) {
        remove(keyToReplace);
        put(keyToReplace, toBeReplaced);
    }
}
