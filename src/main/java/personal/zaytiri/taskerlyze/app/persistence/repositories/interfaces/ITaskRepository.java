package personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces;

import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

public interface ITaskRepository extends IRepository<Task> {

    Response done();

    Response exists(Task task);

    Response undone();
}
