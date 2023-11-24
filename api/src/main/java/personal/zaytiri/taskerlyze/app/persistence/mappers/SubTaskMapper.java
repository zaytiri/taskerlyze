package personal.zaytiri.taskerlyze.app.persistence.mappers;

import personal.zaytiri.taskerlyze.app.api.domain.SubTask;
import personal.zaytiri.taskerlyze.app.persistence.mappers.base.Mapper;
import personal.zaytiri.taskerlyze.app.persistence.models.SubTaskModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubTaskMapper extends Mapper<SubTask, SubTaskModel> {
    public SubTaskMapper() {
        super("subtasks__");
    }

    @Override
    public List<SubTask> toEntity(List<Map<String, String>> rows, boolean mixedResult) {
        List<SubTask> tasks = new ArrayList<>();

        for (Map<String, String> row : rows) {
            SubTask task = new SubTask().getInstance();

            task.setId(getRowIntValue(row, mixedResult, "id"));
            task.setName(getRowStringValue(row, mixedResult, "name"));
            task.setDone(getRowBooleanValue(row, mixedResult, "is_done"));
            task.setCompletedAt(getRowDateValue(row, mixedResult, "completed_at"));
            task.setTaskId(getRowIntValue(row, mixedResult, "task_id"));

            tasks.add(task);
        }
        return tasks;
    }

    @Override
    public SubTask toEntity(SubTaskModel model) {
        SubTask entity = new SubTask().getInstance();

        if (model == null) return null;

        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setDone(model.isDone());
        entity.setCompletedAt(model.getCompletedAt());
        entity.setTaskId(model.getTaskId());

        return entity;
    }

    @Override
    public SubTaskModel toModel(SubTask entity) {
        SubTaskModel model = new SubTaskModel();

        if (entity == null) return null;

        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setDone(entity.isDone());
        model.setCompletedAt(entity.getCompletedAt());
        model.setTaskId(entity.getTaskId());

        return model;
    }
}
