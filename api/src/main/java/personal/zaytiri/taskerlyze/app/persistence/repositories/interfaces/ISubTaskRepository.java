package personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces;

import personal.zaytiri.taskerlyze.app.api.domain.SubTask;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

public interface ISubTaskRepository extends IRepository<SubTask> {
    Response exists(SubTask subTask);

    Response getSubTasksByTask(int taskId);

}
