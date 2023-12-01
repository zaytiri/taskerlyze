package personal.zaytiri.taskerlyze.ui.logic.mappers;

import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.ui.logic.entities.TaskEntity;

public class TaskMapper implements IMapper<Task, TaskEntity> {
    @Override
    public Task mapToApiObject(TaskEntity taskEntity) {
        return new Task().getInstance()
                .setId(taskEntity.getId())
                .setName(taskEntity.getName())
                .setDone(taskEntity.isTaskDone())
                .setDescription(taskEntity.getDescription())
                .setCategoryId(taskEntity.getCategoryId())
                .setUrl(taskEntity.getUrl())
                .setPriority(taskEntity.getPriority())
                .setCompletedAt(taskEntity.getCompletedAt())
                .setAchieved(taskEntity.getAchieved());
    }

    @Override
    public TaskEntity mapToUiObject(Task task, TaskEntity taskEntity) {
        return taskEntity
                .setId(task.getId())
                .setName(task.getName())
                .setTaskDone(task.isDone(false))
                .setDescription(task.getDescription())
                .setCategoryId(task.getCategoryId())
                .setUrl(task.getUrl())
                .setPriority(task.getPriority())
                .setCompletedAt(task.getCompletedAt())
                .setAchieved(task.getAchieved());
    }
}