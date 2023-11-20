package personal.zaytiri.taskerlyze.ui.logic.entities;

import personal.zaytiri.taskerlyze.app.api.controllers.SubTaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.SubTask;

public class SubTaskEntity {
    private final SubTaskController api;
    private int id;
    private String name;
    private boolean isTaskDone;
    private int taskId;

    public SubTaskEntity() {
        api = new SubTaskController();
    }

    public SubTaskEntity(int id, String name, boolean isTaskDone, int taskId) {
        this();
        this.id = id;
        this.name = name;
        this.isTaskDone = isTaskDone;
        this.taskId = taskId;
    }

    public SubTaskEntity(SubTask subTask) {
        this(subTask.getId(), subTask.getName(), subTask.isDone(false), subTask.getTaskId());
    }

    public boolean createOrUpdate() {
        SubTask newTask = new SubTask().getInstance();
        newTask
                .setName(name)
                .setTaskId(taskId);

        OperationResult<SubTask> result = api.createOrUpdate(newTask);
        return result.getStatus();
    }

    public int getId() {
        return id;
    }

    public SubTaskEntity setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SubTaskEntity setName(String name) {
        this.name = name;
        return this;
    }

    public int getTaskId() {
        return taskId;
    }

    public SubTaskEntity setTaskId(int taskId) {
        this.taskId = taskId;
        return this;
    }

    public boolean isTaskDone() {
        return isTaskDone;
    }

    public SubTaskEntity setTaskDone(boolean taskDone) {
        isTaskDone = taskDone;
        return this;
    }

    public void setDone() {
        api.setDone(id, isTaskDone);
    }
}
