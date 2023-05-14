package personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.util.Map;

public interface IRepository<GEntity> {

    Response create(GEntity entity);

    Response delete(GEntity entity);

    Response read(GEntity entity);

    Response readAll();

    Response update(GEntity entity, Map<Column, Object> sets);
}
