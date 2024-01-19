package personal.zaytiri.taskerlyze.ui.logic.entities;

import personal.zaytiri.taskerlyze.app.api.controllers.SubTaskController;
import personal.zaytiri.taskerlyze.app.api.domain.SubTask;
import personal.zaytiri.taskerlyze.ui.logic.mappers.SubTaskMapper;

public class SubTaskEntity extends Entity<SubTask, SubTaskEntity, SubTaskController> {
    private String name;
    private boolean isTaskDone;
    private int taskId;

    public SubTaskEntity(int id) {
        super(id);
        api = new SubTaskController();
        mapper = new SubTaskMapper();
    }

    public SubTaskEntity() {
        this(0);
    }

    public SubTaskEntity(SubTask subTask) {
        this();
        mapper.mapToUiObject(subTask, this);
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

    public SubTaskEntity setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    protected SubTaskEntity getObject() {
        return this;
    }
}
