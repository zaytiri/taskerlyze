package personal.zaytiri.taskerlyze.app.api.controllers.base;


import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;

import java.util.List;
import java.util.Map;

public interface Controller<T> {
    /**
     * Creates or updates an object of type T.
     * The object needs to have all the necessary properties set to some value to create new data into the database.
     * If the data already exists, it will update every data that's different.
     *
     * @param request an object of type T.
     * @return OperationResult<T>
     */
    OperationResult<T> createOrUpdate(T request);

    /**
     * Deletes a given object of type T with a given id.
     *
     * @param id to delete from database.
     * @return OperationResult<T>
     */
    OperationResult<T> delete(int id);

    /**
     * Returns an object of type T with a given id.
     *
     * @param id to get from database.
     * @return OperationResult<T>
     */
    OperationResult<T> get(int id);

    /**
     * Returns a list of objects of type T considering a set of filters.
     * A filter should consist of the name of the column, an operator and the value.
     * The order by consists of a Pair containing the order (Pair's key) and the column's name (Pair's value).
     * If no order is necessary, input null.
     *
     * @param filters to get results from database.
     * @return OperationResult<T>
     */
    OperationResult<List<T>> get(Map<String, Pair<String, Object>> filters, Pair<String, String> orderByColumn);
}
