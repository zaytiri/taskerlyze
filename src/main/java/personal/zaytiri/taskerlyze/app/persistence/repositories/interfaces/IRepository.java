package personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces;

import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.util.Map;

public interface IRepository<GEntity> {

    Response create(GEntity entity);

    Response delete(GEntity entity);

    Response read(GEntity entity);

    Response readAll();

    Response readFiltered(Map<String, Pair<String, Object>> filters);

    Response update(GEntity entity);

    Response update(GEntity entity, Map<String, Object> values);
}
