package personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces;

import personal.zaytiri.taskerlyze.app.persistence.models.base.Model;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.*;

import java.util.List;
import java.util.Map;

public abstract class Repository<GEntity, GModel extends Model> {

    private GModel model;

    protected Repository(GModel model){
        this.model = model;
    }
    public List<Map<String, String>> read(GEntity entity){
        model = mapModelFrom(entity);

        SelectQueryBuilder query = new SelectQueryBuilder();

        query.select()
                .from(model.getTable())
                .where(model.getTable().getColumn("id"), Operators.EQUALS, model.getId());

        query.execute();

        return query.getResults();
    }

    public List<Map<String, String>> readAll(){
        SelectQueryBuilder query = new SelectQueryBuilder();

        query.select().from(model.getTable());

        query.execute();

        return query.getResults();
    }

    public void create(GEntity entity){
        model = mapModelFrom(entity);

        InsertQueryBuilder query = new InsertQueryBuilder();

        query.insertInto(model.getTable(), model.getTable().getColumns())
                .values();

        query.execute();
    }

    public void update(GEntity entity, Map<Column, Object> sets){
        model = mapModelFrom(entity);

        UpdateQueryBuilder query = new UpdateQueryBuilder();

        query.update(model.getTable())
                .values(sets).where(model.getTable().getColumn("id"), Operators.EQUALS, model.getId());

        query.execute();
    }

    public void delete(GEntity entity){
        model = mapModelFrom(entity);

        DeleteQueryBuilder query = new DeleteQueryBuilder();

        query.deleteFrom(model.getTable())
                .where(model.getTable().getColumn("id"), Operators.EQUALS, model.getId());

        query.execute();
    }

    // todo this could maybe be a mapper which will be included as generics
    protected abstract GModel mapModelFrom(GEntity entity);
}
