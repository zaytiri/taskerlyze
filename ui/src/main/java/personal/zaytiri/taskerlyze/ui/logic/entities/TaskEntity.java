package personal.zaytiri.taskerlyze.ui.logic.entities;

import java.util.List;

public class TaskEntity {
    private int id;
    private String name;
    private boolean isTaskDone;
    private List<TaskEntity> subtasks;

    public TaskEntity(int id, String name, boolean isTaskDone) {
        this.id = id;
        this.name = name;
        this.isTaskDone = isTaskDone;
    }

    public TaskEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TaskEntity> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<TaskEntity> subtasks) {
        this.subtasks = subtasks;
    }

    public boolean isTaskDone() {
        return isTaskDone;
    }

    public void setTaskDone(boolean taskDone) {
        isTaskDone = taskDone;
    }
}
