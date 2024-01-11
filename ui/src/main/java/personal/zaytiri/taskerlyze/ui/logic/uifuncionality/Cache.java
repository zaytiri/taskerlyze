package personal.zaytiri.taskerlyze.ui.logic.uifuncionality;

import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Cache {
    // current category id, current date, if lst is to be reloaded from api, the list of elements to show.
    private final Map<Pair<Integer, LocalDate>, Pair<Boolean, List<TaskEntity>>> cache = new HashMap();

    public Map.Entry<Pair<Integer, LocalDate>, Pair<Boolean, List<TaskEntity>>> findByKey(Pair<Integer, LocalDate> keyToBeFound) {
        for (Map.Entry<Pair<Integer, LocalDate>, Pair<Boolean, List<TaskEntity>>> cacheEntry : cache.entrySet()) {
            if (Objects.equals(cacheEntry.getKey().getKey(), keyToBeFound.getKey())
                    && Objects.equals(cacheEntry.getKey().getValue(), keyToBeFound.getValue())) {
                return cacheEntry;
            }
        }
        return null;
    }

    public Map<Pair<Integer, LocalDate>, Pair<Boolean, List<TaskEntity>>> get() {
        return cache;
    }

    public void put(Pair<Integer, LocalDate> keyToReplace, Pair<Boolean, List<TaskEntity>> toBeReplaced) {
        cache.put(
                keyToReplace,
                new Pair<>(
                        toBeReplaced.getKey(),
                        toBeReplaced.getValue()
                ));
    }

    public boolean remove(Pair<Integer, LocalDate> keyToRemove) {
        return cache.keySet().removeIf(key ->
                Objects.equals(key.getKey(), keyToRemove.getKey())
                        && Objects.equals(key.getValue(), keyToRemove.getValue()));
    }

    public void replace(Pair<Integer, LocalDate> keyToReplace, Pair<Boolean, List<TaskEntity>> toBeReplaced) {
        remove(keyToReplace);
        put(keyToReplace, toBeReplaced);
    }
}
