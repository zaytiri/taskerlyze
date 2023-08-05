package personal.zaytiri.taskerlyze.app.persistence.repositories.base;

import personal.zaytiri.taskerlyze.app.persistence.DbConnection;
import personal.zaytiri.taskerlyze.app.persistence.mappers.base.Mapper;
import personal.zaytiri.taskerlyze.app.persistence.models.base.Model;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.IRepository;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query.*;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.util.HashMap;
import java.util.Map;

public abstract class Repository<GEntity, GModel extends Model, GMapper extends Mapper<GEntity, GModel>> implements IRepository<GEntity> {

    protected final GMapper mapper;
    protected GModel model;
    protected DbConnection connection;

    protected Repository(GModel model, GMapper mapper) {
        this.model = model;
        this.mapper = mapper;
        this.connection = DbConnection.getInstance();
    }

    @Override
    public Response create(GEntity entity) {
        model = mapper.toModel(entity);

        InsertQueryBuilder query = new InsertQueryBuilder(connection.open());

        query.insertInto(model.getTable(), model.getTable().getColumns())
                .values(model.getValues().values());

        return query.execute();
    }

    @Override
    public Response delete(GEntity entity) {
        model = mapper.toModel(entity);

        DeleteQueryBuilder query = new DeleteQueryBuilder(connection.open());

        query.deleteFrom(model.getTable())
                .where(model.getTable().getColumn("id"), Operators.EQUALS, model.getId());

        return query.execute();
    }

    @Override
    public Response read(Map<String, Pair<String, Object>> filters) {
        SelectQueryBuilder query = new SelectQueryBuilder(connection.open());

        query = query.select().from(model.getTable());

        if (filters.isEmpty()) {
            return query.execute();
        }

        boolean operator = false;
        for (Map.Entry<String, Pair<String, Object>> entry : filters.entrySet()) {
            if (operator) {
                query = query.and();
            }

            Column col = model.getTable().getColumn(entry.getKey());

            if (entry.getValue().value == null) {
                query = query.where(col, Operators.get(entry.getValue().key));
            } else {
                query = query.where(col, Operators.get(entry.getValue().key), entry.getValue().value);
            }

            operator = true;
        }

        return query.execute();
    }

    @Override
    public Response update(GEntity entity) {
        return update(entity, model.getValues());
    }

    @Override
    public Response update(GEntity entity, Map<String, Object> values) {
        model = mapper.toModel(entity);

        UpdateQueryBuilder query = new UpdateQueryBuilder(connection.open());

        Map<Column, Object> sets = new HashMap<>();

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            if (entry.getKey().equals("id")) {
                continue;
            }

            Column col = model.getTable().getColumn(entry.getKey());
            sets.put(col, entry.getValue());
        }

        query.update(model.getTable())
                .values(sets).where(model.getTable().getColumn("id"), Operators.EQUALS, model.getId());

        return query.execute();
    }
}