package personal.zaytiri.taskerlyze.ui.logic.mappers;

import personal.zaytiri.taskerlyze.app.api.domain.SubTask;
import personal.zaytiri.taskerlyze.ui.logic.entities.SubTaskEntity;

public class SubTaskMapper implements IMapper<SubTask, SubTaskEntity> {
    @Override
    public SubTask mapToApiObject(SubTaskEntity subTaskEntity) {
        return new SubTask().getInstance()
                .setId(subTaskEntity.getId())
                .setName(subTaskEntity.getName())
                .setDone(subTaskEntity.isTaskDone())
                .setTaskId(subTaskEntity.getTaskId());
    }

    @Override
    public SubTaskEntity mapToUiObject(SubTask subTask, SubTaskEntity subTaskEntity) {
        return subTaskEntity
                .setId(subTask.getId())
                .setName(subTask.getName())
                .setTaskDone(subTask.isDone())
                .setTaskId(subTask.getTaskId());
    }
}
