package personal.zaytiri.taskerlyze.app.persistence.mappers;

import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.app.persistence.mappers.base.Mapper;
import personal.zaytiri.taskerlyze.app.persistence.models.TaskModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskMapper extends Mapper<Task, TaskModel> {
    public TaskMapper() {
        super("tasks__");
    }

    @Override
    public List<Task> toEntity(List<Map<String, String>> rows, boolean mixedResult) {
        List<Task> tasks = new ArrayList<>();

        for (Map<String, String> row : rows) {
            Task task = new Task().getInstance();

            task.setId(getRowIntValue(row, mixedResult, "id"));
            task.setName(getRowStringValue(row, mixedResult, "name"));
            task.setDescription(getRowStringValue(row, mixedResult, "description"));
            task.setCategoryId(getRowIntValue(row, mixedResult, "category_id"));
            task.setDone(getRowBooleanValue(row, mixedResult, "is_done"));
            task.setCompletedAt(getRowDateValue(row, mixedResult, "completed_at"));
            task.setAchieved(getRowStringValue(row, mixedResult, "achieved"));
            task.setPriority(getRowIntValue(row, mixedResult, "priority"));
            task.setUrl(getRowStringValue(row, mixedResult, "url"));

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
        entity.setCompletedAt(model.getCompletedAt());
        entity.setAchieved(model.getAchieved());
        entity.setPriority(model.getPriority());
        entity.setUrl(model.getUrl());

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
        model.setCompletedAt(entity.getCompletedAt());
        model.setAchieved(entity.getAchieved());
        model.setPriority(entity.getPriority());
        model.setUrl(entity.getUrl());

        return model;
    }
}
