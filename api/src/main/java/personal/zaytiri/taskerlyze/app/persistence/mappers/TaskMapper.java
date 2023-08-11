package personal.zaytiri.taskerlyze.app.persistence.mappers;

import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.app.persistence.mappers.base.Mapper;
import personal.zaytiri.taskerlyze.app.persistence.models.TaskModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskMapper implements Mapper<Task, TaskModel> {
    public TaskMapper() {
    }

    @Override
    public Task toEntity(TaskModel model) {
        Task entity = Task.getInstance();

        if (model == null) return null;

        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        entity.setDone(model.isDone());

        return entity;
    }

    @Override
    public List<Task> toEntity(List<Map<String, String>> rows) {
        List<Task> tasks = new ArrayList<>();

        for (Map<String, String> row : rows) {
            Task task = Task.getInstance();

            task.setId(Integer.parseInt(row.get("id")));
            task.setName(row.get("name"));
            task.setDescription(row.get("description"));
            task.setDone(Integer.parseInt(row.get("is_done")) != 0);

            tasks.add(task);
        }
        return tasks;
    }

    @Override
    public TaskModel toModel(Task entity) {
        TaskModel model = new TaskModel();

        if (entity == null) return null;

        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setDescription(entity.getDescription());
        model.setDone(entity.isDone(false));
        return model;
    }
}
