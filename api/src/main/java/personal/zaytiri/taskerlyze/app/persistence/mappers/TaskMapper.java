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
    public List<Task> toEntity(List<Map<String, String>> rows, boolean mixedResult) {
        List<Task> tasks = new ArrayList<>();

        for (Map<String, String> row : rows) {
            Task task = new Task().getInstance();

            task.setId(Integer.parseInt(row.get(getFormattedName(mixedResult, "id"))));
            task.setName(row.get(getFormattedName(mixedResult, "name")));
            task.setDescription(row.get(getFormattedName(mixedResult, "description")));
            task.setCategoryId(Integer.parseInt(row.get(getFormattedName(mixedResult, "category_id"))));
            task.setDone(Integer.parseInt(row.get(getFormattedName(mixedResult, "is_done"))) != 0);

            tasks.add(task);
        }
        return tasks;
    }

    @Override
    public Task toEntity(TaskModel model) {
        Task entity = new Task().getInstance();

        if (model == null) return null;

        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        entity.setCategoryId(model.getCategoryId());
        entity.setDone(model.isDone());

        return entity;
    }

    @Override
    public TaskModel toModel(Task entity) {
        TaskModel model = new TaskModel();

        if (entity == null) return null;

        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setDescription(entity.getDescription());
        model.setCategoryId(entity.getCategoryId());
        model.setDone(entity.isDone(false));
        return model;
    }

    private String getFormattedName(boolean mixedResult, String columnName) {
        if (mixedResult) {
            return "tasks__" + columnName;
        }
        return columnName;
    }
}
