package personal.zaytiri.taskerlyze.ui.logic.entities;

import personal.zaytiri.taskerlyze.app.api.controllers.TaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Task;

public class TaskEntity {
    private int id;
    private String name;
    private boolean isTaskDone;
    private String description;
    private int categoryId;
    private String url;
    private int priority;
    private final TaskController api;

    public TaskEntity(Task task) {
        this(task.getId(), task.getName(), task.isDone(false));
    }

    public TaskEntity(int id, String name, boolean isTaskDone) {
        this();
        this.id = id;
        this.name = name;
        this.isTaskDone = isTaskDone;
    }

    public TaskEntity() {
        api = new TaskController();
    }

    public boolean createOrUpdate() {
        Task newTask = new Task().getInstance();
        newTask
                .setName(name)
                .setDescription(description)
                .setCategoryId(categoryId)
                .setUrl(url)
                .setPriority(priority);

        OperationResult<Task> result = api.createOrUpdate(newTask);
        return result.getStatus();
    }

    public int getId() {
        return id;
    }

    public TaskEntity setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TaskEntity setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isTaskDone() {
        return isTaskDone;
    }

    public TaskEntity setTaskDone(boolean taskDone) {
        isTaskDone = taskDone;
        return this;
    }

    public TaskEntity setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public TaskEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public void setDone() {
        api.setDone(id, isTaskDone);
    }

    public TaskEntity setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public TaskEntity setUrl(String url) {
        this.url = url;
        return this;
    }
}
