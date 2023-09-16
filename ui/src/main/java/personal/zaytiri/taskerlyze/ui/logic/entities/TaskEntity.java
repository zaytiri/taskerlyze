package personal.zaytiri.taskerlyze.ui.logic.entities;

import java.util.List;

public class TaskEntity {
    private int taskId;
    private String taskName;
    private boolean isTaskDone;
    private List<TaskEntity> subtasks;

    public TaskEntity(int taskId, String taskName, boolean isTaskDone) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.isTaskDone = isTaskDone;
    }

    public TaskEntity() {
    }

    public List<TaskEntity> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<TaskEntity> subtasks) {
        this.subtasks = subtasks;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isTaskDone() {
        return isTaskDone;
    }

    public void setTaskDone(boolean taskDone) {
        isTaskDone = taskDone;
    }
}
