package personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces;

import personal.zaytiri.taskerlyze.app.persistence.DbConnection;
import personal.zaytiri.taskerlyze.app.persistence.mappers.base.Mapper;
import personal.zaytiri.taskerlyze.app.persistence.models.base.Model;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query.*;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.util.List;
import java.util.Map;

public abstract class Repository<GEntity, GModel extends Model, GMapper extends Mapper<GEntity, GModel>> {

    protected final GMapper mapper;
    protected GModel model;
    protected DbConnection connection;

    protected Repository(GModel model, GMapper mapper) {
        this.model = model;
        this.mapper = mapper;
        this.connection = DbConnection.getInstance();
    }

    protected Repository(GModel model, GMapper mapper, DbConnection connection) {
        this.model = model;
        this.mapper = mapper;
        this.connection = connection;
    }

    public void create(GEntity entity) {
        model = mapper.toModel(entity);

        InsertQueryBuilder query = new InsertQueryBuilder(connection.open());

        query.insertInto(model.getTable(), model.getTable().getColumns())
                .values();

        query.execute();
    }

    public void delete(GEntity entity) {
        model = mapper.toModel(entity);

        DeleteQueryBuilder query = new DeleteQueryBuilder(connection.open());

        query.deleteFrom(model.getTable())
                .where(model.getTable().getColumn("id"), Operators.EQUALS, model.getId());

        query.execute();
    }

    public List<Map<String, String>> read(GEntity entity) {
        model = mapper.toModel(entity);

        SelectQueryBuilder query = new SelectQueryBuilder(connection.open());

        query.select()
                .from(model.getTable())
                .where(model.getTable().getColumn("id"), Operators.EQUALS, model.getId());

        Response response = query.execute();

        return response.getResult();
    }

    public List<Map<String, String>> readAll() {
        SelectQueryBuilder query = new SelectQueryBuilder(connection.open());

        query.select().from(model.getTable());

        Response response = query.execute();

        return response.getResult();
    }

    public void update(GEntity entity, Map<Column, Object> sets) {
        model = mapper.toModel(entity);

        UpdateQueryBuilder query = new UpdateQueryBuilder(connection.open());

        query.update(model.getTable())
                .values(sets).where(model.getTable().getColumn("id"), Operators.EQUALS, model.getId());

        query.execute();
    }
}
