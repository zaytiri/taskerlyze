package personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces;

import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.util.Map;

public interface IRepository<GEntity> {

    /**
     * Creates a new entity in the database.
     *
     * @param entity to be created
     * @return type Response object
     */
    Response create(GEntity entity);

    /**
     * Deletes an existent entity from the database.
     *
     * @param entity to be deleted
     * @return type Response object
     */
    Response delete(GEntity entity);

    /**
     * Returns any results that match the given filters.
     * <p>
     * If no filters are given, all found results are returned.
     * A filter consists of a column's name, the desired operator and a value respectively.
     * The Map's key must be the column's name while the Pair object consists of the operator (Pair's key) and the value (Pair's value).
     * If the value is null, it means the condition does not need the value, e.g to use the IS NULL operator, this does not need a second value.
     *
     * @param filters a set of 3 values, the column's name, the operator and a value.
     * @return type Response object
     */
    Response read(Map<String, Pair<String, Object>> filters);

    /**
     * Updates an entity with all values from that entity.
     *
     * @param entity to be updated
     * @return type Response object
     */
    Response update(GEntity entity);

    /**
     * Updates an entity with given values, meaning it will only update those values and not all values, opposite of the Update(GEntity entity) method.
     *
     * @param entity to be updated
     * @param values to be updated
     * @return type Response object
     */
    Response update(GEntity entity, Map<String, Object> values);
}
