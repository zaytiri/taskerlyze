package personal.zaytiri.taskerlyze.app.api.domain;

import jakarta.inject.Inject;
import personal.zaytiri.taskerlyze.app.api.domain.base.Entity;
import personal.zaytiri.taskerlyze.app.api.domain.base.IStorageOperations;
import personal.zaytiri.taskerlyze.app.dependencyinjection.AppComponent;
import personal.zaytiri.taskerlyze.app.persistence.mappers.SubTaskMapper;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.ISubTaskRepository;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubTask extends Entity<SubTask, ISubTaskRepository, SubTaskMapper> implements IStorageOperations<SubTask> {
    private boolean done;
    private int taskId;
    private LocalDate completedAt;

    @Inject
    public SubTask(ISubTaskRepository repository) {
        this.repository = repository;
        this.mapper = new SubTaskMapper();
        this.done = false;
    }

    public SubTask() {
    }

    public boolean create() {
        if (exists()) {
            return false;
        }

        Response response = repository.create(this);
        this.id = response.getLastInsertedId();

        return response.isSuccess();
    }

    @Override
    public boolean delete() {
        Response response = repository.delete(this);
        if (get() != null) {
            return false;
        }

        return response.isSuccess();
    }

    @Override
    public boolean exists() {
        Response response = repository.exists(this); // check done by name

        return !response.getResult().isEmpty();
    }

    public List<SubTask> findNameBySubString(String subString) {
        Map<String, Pair<String, Object>> filters = new HashMap<>();
        filters.put("name", new Pair<>("LIKE", subString));

        List<SubTask> results = get(filters, null);
        if (results.isEmpty()) {
            return new ArrayList<>();
        }

        return results;
    }

    @Override
    public List<SubTask> get(Map<String, Pair<String, Object>> filters, Pair<String, String> orderByColumn) {
        Response response = repository.read(filters, orderByColumn);

        return mapper.toEntity(response.getResult(), false);
    }

    @Override
    public SubTask get() {
        Map<String, Pair<String, Object>> filters = new HashMap<>();
        filters.put("id", new Pair<>("=", this.id));

        List<SubTask> results = get(filters, null);
        if (results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }

    public boolean update() {
        //todo: test what happens if i try to update but theres no entry to update because its not created yet.
        return repository.update(this).isSuccess();
    }

    public LocalDate getCompletedAt() {
        return completedAt;
    }

    public SubTask setCompletedAt(LocalDate completedAt) {
        this.completedAt = completedAt;
        return this;
    }

    public List<SubTask> getSubTasksByTask() {
        Response response = repository.getSubTasksByTask(this.taskId);

        return mapper.toEntity(response.getResult(), true);
    }

    public int getTaskId() {
        return taskId;
    }

    public SubTask setTaskId(int taskId) {
        this.taskId = taskId;
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

    public boolean isDone() {
        return done;
    }

    public SubTask setDone(boolean done) {
        this.done = done;
        return this;
    }

    public SubTask setId(int id) {
        this.id = id;
        return this;
    }

    public SubTask setName(String name) {
        this.name = name;
        return this;
    }

    public boolean setTaskStatus(boolean done) {
        List<Pair<String, Object>> sets = new ArrayList<>();

        LocalDate today = null;
        sets.add(new Pair<>("is_done", done));

        if (done) {
            today = LocalDate.now();
        }
        sets.add(new Pair<>("completed_at", today));

        Response response = repository.update(this, sets);
        if (response.isSuccess()) {
            this.done = done;
            this.completedAt = today;
        }

        return response.isSuccess();
    }

    @Override
    protected SubTask getInjectedComponent(AppComponent component) {
        return component.getSubTask();
    }
}
