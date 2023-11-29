package personal.zaytiri.taskerlyze.ui.logic.entities;

import personal.zaytiri.taskerlyze.app.api.controllers.TaskController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Task;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.mappers.TaskMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskEntity extends Entity<Task, TaskEntity, TaskController> {

    private String name;
    private boolean isTaskDone;
    private String description;
    private int categoryId;
    private LocalDate completedAt;
    private String url;
    private int priority;
    private String achieved;

    public TaskEntity() {
        this(0);
    }

    public TaskEntity(Task task) {
        this();
        mapper.mapToUiObject(task, this);
    }

    public TaskEntity(int id) {
        super(id);
        api = new TaskController();
        mapper = new TaskMapper();
    }

    public List<Pair<Integer, String>> findBySubString(String subString) {
        OperationResult<List<Task>> result = api.findNameBySubString(subString);
        List<Pair<Integer, String>> tasksToBeReturned = new ArrayList<>();
        for (Task task : result.getResult()) {
            tasksToBeReturned.add(new Pair<>(task.getId(), task.getName()));
        }
        return tasksToBeReturned;
    }

    public TaskEntity setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    protected TaskEntity getObject() {
        return this;
    }

    public String getAchieved() {
        return achieved;
    }

    public TaskEntity setAchieved(String achieved) {
        this.achieved = achieved;
        return this;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public TaskEntity setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public LocalDate getCompletedAt() {
        return completedAt;
    }

    public TaskEntity setCompletedAt(LocalDate completedAt) {
        this.completedAt = completedAt;
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
