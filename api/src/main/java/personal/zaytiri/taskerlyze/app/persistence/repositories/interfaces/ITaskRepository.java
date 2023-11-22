package personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces;

import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.time.LocalDate;

public interface ITaskRepository extends IRepository<Task> {
    Response exists(Task task);

    Response getTasksByCategoryAndCompletedAtDate(int categoryId, LocalDate date);

}
