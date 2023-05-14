package personal.zaytiri.taskerlyze.app.persistence.repositories;

import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.app.persistence.mappers.TaskMapper;
import personal.zaytiri.taskerlyze.app.persistence.models.TaskModel;
import personal.zaytiri.taskerlyze.app.persistence.repositories.base.Repository;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.ITaskRepository;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import javax.inject.Inject;

public class TaskRepository extends Repository<Task, TaskModel, TaskMapper> implements ITaskRepository {

    @Inject
    public TaskRepository() {
        super(new TaskModel(), new TaskMapper());
    }

    @Override
    public Response done() {
        return null;
    }

    @Override
    public Response undone() {
        return null;
    }
}
