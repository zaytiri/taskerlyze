package personal.zaytiri.taskerlyze.app.persistence.repositories;

import jakarta.inject.Inject;
import personal.zaytiri.taskerlyze.app.api.domain.SubTask;
import personal.zaytiri.taskerlyze.app.persistence.mappers.SubTaskMapper;
import personal.zaytiri.taskerlyze.app.persistence.models.SubTaskModel;
import personal.zaytiri.taskerlyze.app.persistence.models.TaskModel;
import personal.zaytiri.taskerlyze.app.persistence.repositories.base.Repository;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.ISubTaskRepository;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query.Operators;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query.SelectQueryBuilder;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.util.ArrayList;
import java.util.List;

public class SubTaskRepository extends Repository<SubTask, SubTaskModel, SubTaskMapper> implements ISubTaskRepository {
    @Inject
    public SubTaskRepository() {
        super(new SubTaskModel(), new SubTaskMapper());
    }

    @Override
    public Response exists(SubTask subTask) {
        model = mapper.toModel(subTask);

        SelectQueryBuilder query = new SelectQueryBuilder(connection.open());

        Column taskId = model.getTable().getColumn("task_id");

        Column name = model.getTable().getColumn("name");
        List<Column> columns = new ArrayList<>();
        columns.add(name);

        query.select(columns)
                .from(model.getTable())
                .where(name, Operators.EQUALS, model.getName())
                .and()
                .where(taskId, Operators.EQUALS, model.getTaskId());

        return query.execute();
    }

    @Override
    public Response getSubTasksByTask(int taskId) {
        model = new SubTaskModel();
        TaskModel taskModel = new TaskModel();

        SelectQueryBuilder query = new SelectQueryBuilder(connection.open());

        query.select()
                .from(model.getTable())
                .join(taskModel.getTable())
                .on(model.getTable().getColumn("task_id"), taskModel.getTable().getColumn("id"))
                .where(taskModel.getTable().getColumn("id"), Operators.EQUALS, taskId);

        return query.execute();
    }
}
