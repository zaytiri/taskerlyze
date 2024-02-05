package personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces;

import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.response.Response;
import personal.zaytiri.taskerlyze.app.api.domain.Task;

import java.time.LocalDate;

public interface ITaskRepository extends IRepository<Task> {
    Response exists(Task task);

    Response getTasksByCategoryAndCompletedAtDate(int categoryId, LocalDate date);

}
