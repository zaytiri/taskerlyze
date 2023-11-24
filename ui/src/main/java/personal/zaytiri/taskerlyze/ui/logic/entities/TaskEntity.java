package personal.zaytiri.taskerlyze.ui.logic.entities;

import personal.zaytiri.taskerlyze.app.api.controllers.TaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskEntity extends Entity<Task, TaskEntity, TaskController> {

    private String name;
    private boolean isTaskDone;
    private String description;
    private int categoryId;
    private String url;
    private int priority;

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

    public List<TaskEntity> findBySubString(String subString) {
        OperationResult<List<Task>> result = api.findBySubString(subString);
        List<TaskEntity> tasksToBeReturned = new ArrayList<>();
        for (Task task : result.getResult()) {
            tasksToBeReturned.add(mapToUiObject(task));
            System.out.println(task.getName());
        }
        return tasksToBeReturned;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public TaskEntity setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TaskEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return name;
    }

    public TaskEntity setName(String name) {
        this.name = name;
        return this;
    }

    public int getPriority() {
        return priority;
    }

    public TaskEntity setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public TaskEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public boolean isTaskDone() {
        return isTaskDone;
    }

    public TaskEntity setTaskDone(boolean taskDone) {
        isTaskDone = taskDone;
        return this;
    }

    public Task mapToApiObject() {
        return new Task().getInstance()
                .setId(id)
                .setName(name)
                .setDescription(description)
                .setCategoryId(categoryId)
                .setUrl(url)
                .setPriority(priority);
    }

    public TaskEntity mapToUiObject(Task entity) {
        return new TaskEntity(entity);
    }

    public TaskEntity setId(int id) {
        this.id = id;
        return this;
    }

    public void setDone() {
        api.setDone(id, isTaskDone);
    }


}
