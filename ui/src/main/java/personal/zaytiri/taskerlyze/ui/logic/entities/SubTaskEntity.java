package personal.zaytiri.taskerlyze.ui.logic.entities;

public class SubTaskEntity extends TaskEntity {
    private int taskId;

    public SubTaskEntity(int id, String taskName, boolean isTaskDone, int taskId) {
        super(id, taskName, isTaskDone);
        this.taskId = taskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
