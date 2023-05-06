package personal.zaytiri.taskerlyze.app.persistence.mappers;

import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.app.persistence.mappers.base.Mapper;
import personal.zaytiri.taskerlyze.app.persistence.models.TaskModel;

public class TaskMapper implements Mapper<Task, TaskModel> {
    public TaskMapper() {}

    @Override
    public TaskModel toModel(Task entity) {
        TaskModel model = new TaskModel();

        if (entity == null) return null;

        model.setId(entity.getId());
        model.setName(entity.getName());
        return model;
    }

    @Override
    public Task toEntity(TaskModel model) {
        Task entity = new Task();

        if (model == null) return null;

        entity.setId(model.getId());
        entity.setName(model.getName());

        return entity;
    }
}
