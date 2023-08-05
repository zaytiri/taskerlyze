package personal.zaytiri.taskerlyze.app.api.controllers.base;


import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;

import java.util.List;
import java.util.Map;

public interface Controller<T> {
    OperationResult<T> createOrUpdate(T request);

    OperationResult<T> delete(int id);

    OperationResult<T> get(int id);

    OperationResult<List<T>> get(Map<String, Pair<String, Object>> filters);
}
