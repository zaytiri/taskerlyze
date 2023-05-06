package personal.zaytiri.taskerlyze.app.persistence.repositories;

import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.app.persistence.mappers.TaskMapper;
import personal.zaytiri.taskerlyze.app.persistence.models.TaskModel;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.Repository;

public class TaskRepository extends Repository<Task, TaskModel, TaskMapper> {

    public TaskRepository(){
        super(new TaskModel(), new TaskMapper());
    }

}
