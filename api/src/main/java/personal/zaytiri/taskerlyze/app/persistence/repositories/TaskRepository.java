package personal.zaytiri.taskerlyze.app.persistence.repositories;

import jakarta.inject.Inject;
import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.app.persistence.mappers.TaskMapper;
import personal.zaytiri.taskerlyze.app.persistence.models.TaskModel;
import personal.zaytiri.taskerlyze.app.persistence.repositories.base.Repository;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.ITaskRepository;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query.Operators;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query.SelectQueryBuilder;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository extends Repository<Task, TaskModel, TaskMapper> implements ITaskRepository {

    @Inject
    public TaskRepository() {
        super(new TaskModel(), new TaskMapper());
    }

    @Override
    public Response exists(Task task) {
        model = mapper.toModel(task);

        SelectQueryBuilder query = new SelectQueryBuilder(connection.open());

        Column name = model.getTable().getColumn("name");
        List<Column> columns = new ArrayList<>();
        columns.add(name);

        query.select(columns)
                .from(model.getTable())
                .where(name, Operators.EQUALS, model.getName());

        return query.execute();
    }

    @Override
    public Response getTasksByCategoryAndCompletedAtDate(int categoryId, LocalDate date) {
        model = new TaskModel();

        SelectQueryBuilder query = new SelectQueryBuilder(connection.open());

        Table taskTable = model.getTable();

        query.select()
                .from(taskTable)
                .where(taskTable.getColumn("category_id"), Operators.EQUALS, categoryId);

        if (date.isEqual(LocalDate.now())) {
            query.and(2)
                    .where(taskTable.getColumn("is_done"), Operators.EQUALS, false)
                    .or()
                    .where(taskTable.getColumn("completed_at"), Operators.EQUALS, date);
        } else {
            query.and()
                    .where(taskTable.getColumn("completed_at"), Operators.EQUALS, date);
        }

        return query.execute();
    }
}
