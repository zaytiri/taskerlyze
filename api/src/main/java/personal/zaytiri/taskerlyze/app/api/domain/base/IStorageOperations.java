package personal.zaytiri.taskerlyze.app.api.domain.base;

import personal.zaytiri.taskerlyze.libraries.pairs.Pair;

import java.util.List;
import java.util.Map;

public interface IStorageOperations<T> {

    boolean create();

    boolean delete();

    boolean exists();

    T get();

    List<T> get(Map<String, Pair<String, Object>> filters, Pair<String, String> orderByColumn);

    boolean update();
}
