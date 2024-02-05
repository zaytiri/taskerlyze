package personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces;

import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.response.Response;
import personal.zaytiri.taskerlyze.app.api.domain.SubTask;

public interface ISubTaskRepository extends IRepository<SubTask> {
    Response exists(SubTask subTask);

    Response getSubTasksByTask(int taskId);

}
