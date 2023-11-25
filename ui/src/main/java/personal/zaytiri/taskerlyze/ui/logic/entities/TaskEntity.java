package personal.zaytiri.taskerlyze.ui.logic.entities;

import personal.zaytiri.taskerlyze.app.api.controllers.TaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;

import java.util.ArrayList;
import java.util.List;

public class TaskEntity extends Entity<Task, TaskEntity, TaskController> {

    private String name;
    private boolean isTaskDone;
    private String description;
    private int categoryId;
    private String url;
    private int priority;

    public TaskEntity(int id, String name, boolean isTaskDone, String description, int categoryId, String url, int priority) {
        this(id);
        this.name = name;
        this.isTaskDone = isTaskDone;
        this.description = description;
        this.categoryId = categoryId;
        this.url = url;
        this.priority = priority;
    }

    public TaskEntity() {
        this(0);
    }

    public TaskEntity(Task task) {
        this(
                task.getId(),
                task.getName(),
                task.isDone(false),
                task.getDescription(),
                task.getCategoryId(),
                task.getUrl(),
                task.getPriority());
    }

    public TaskEntity(int id) {
        super(id);
        api = new TaskController();
    }

    public List<Pair<Integer, String>> findBySubString(String subString) {
        OperationResult<List<Task>> result = api.findNameBySubString(subString);
        List<Pair<Integer, String>> tasksToBeReturned = new ArrayList<>();
        for (Task task : result.getResult()) {
            tasksToBeReturned.add(new Pair<>(task.getId(), task.getName()));
        }
        return tasksToBeReturned;
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

    public void setDone() {
        api.setDone(id, isTaskDone);
    }


}
