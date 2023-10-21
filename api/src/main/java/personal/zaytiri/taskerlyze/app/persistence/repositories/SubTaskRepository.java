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
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

public class SubTaskRepository extends Repository<SubTask, SubTaskModel, SubTaskMapper> implements ISubTaskRepository {
    @Inject
    public SubTaskRepository() {
        super(new SubTaskModel(), new SubTaskMapper());
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
