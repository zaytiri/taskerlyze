package personal.zaytiri.taskerlyze.app.api.domain;

import jakarta.inject.Inject;
import personal.zaytiri.taskerlyze.app.api.domain.base.Entity;
import personal.zaytiri.taskerlyze.app.api.domain.base.IStorageOperations;
import personal.zaytiri.taskerlyze.app.dependencyinjection.AppComponent;
import personal.zaytiri.taskerlyze.app.persistence.mappers.CategoryMapper;
import personal.zaytiri.taskerlyze.app.persistence.mappers.TaskMapper;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.ITaskRepository;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.util.*;

public class Task extends Entity<Task, ITaskRepository, TaskMapper> implements IStorageOperations<Task> {
    private String description;
    private boolean done;
    private int categoryId;
    private Date completedAt;
    private String achieved;
    private String url;
    private int priority;

    @Inject
    public Task(ITaskRepository repository) {
        this.repository = repository;
        this.mapper = new TaskMapper();

        this.done = false;
    }

    public Task() {
    }

    public boolean createOrUpdate() {
        Response response;

        if (!exists()) {
            response = repository.create(this);
            this.id = response.getLastInsertedId();
        } else {
            response = repository.update(this);
        }
        return response.isSuccess();
    }

    public boolean delete() {
        Response response = repository.delete(this);
        if (get() != null) {
            return false;
        }

        return response.isSuccess();
    }

    public boolean exists() {
        Response response = repository.exists(this); // check done by name

        return !response.getResult().isEmpty();
    }

    public Task get() {
        Map<String, Pair<String, Object>> filters = new HashMap<>();
        filters.put("id", new Pair<>("=", this.id));

        List<Task> results = get(filters, null);
        if (results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }

    public List<Task> get(Map<String, Pair<String, Object>> filters, Pair<String, String> orderByColumn) {
        Response response = repository.read(filters, orderByColumn);

        return mapper.toEntity(response.getResult(), false);
    }

    public String getAchieved() {
        return achieved;
    }

    public void setAchieved(String achieved) {
        this.achieved = achieved;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public Task setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getPriority() {
        return priority;
    }

    public Task setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public Pair<Category, List<Task>> getTasksByCategory() {
        Response response = repository.getTasksByCategory(this.categoryId);

        Pair<Category, List<Task>> result = new Pair<>();

        Category category = new Category();
        if (!response.getResult().isEmpty()) {
            category = new CategoryMapper().toEntity(response.getResult(), true).get(0);
        }
        result.setKey(category);

        result.setValue(mapper.toEntity(response.getResult(), true));

        return result;
    }

    public String getUrl() {
        return url;
    }

    public Task setUrl(String url) {
        this.url = url;
        return this;
    }

    public boolean isDone(boolean getFromDb) {
        if (!getFromDb) {
            return done;
        }
        Map<String, Pair<String, Object>> filters = new HashMap<>();
        filters.put("id", new Pair<>("=", this.id));

        Response response = repository.read(filters, null);
        return Boolean.parseBoolean(response.getResult().get(0).get("is_done"));
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Task setId(int id) {
        this.id = id;
        return this;
    }

    public Task setName(String name) {
        this.name = name;
        return this;
    }

    public boolean setTaskStatus(boolean done) {
        List<Pair<String, Object>> sets = new ArrayList<>();

        Date today = null;
        sets.add(new Pair<>("is_done", done));

        if (done) {
            today = new Date();
        }
        sets.add(new Pair<>("completed_at", today));

        Response response = repository.update(this, sets);
        if (response.isSuccess()) {
            this.done = done;
            this.completedAt = today;
        }

        return response.isSuccess();
    }

    public boolean setTaskStatus() {
        return setTaskStatus(!isDone(true));
    }

    @Override
    protected Task getInjectedComponent(AppComponent component) {
        return component.getTask();
    }
}
