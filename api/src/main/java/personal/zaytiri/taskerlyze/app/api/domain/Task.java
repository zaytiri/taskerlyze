package personal.zaytiri.taskerlyze.app.api.domain;

import jakarta.inject.Inject;
import personal.zaytiri.taskerlyze.app.dependencyinjection.AppComponent;
import personal.zaytiri.taskerlyze.app.dependencyinjection.DaggerAppComponent;
import personal.zaytiri.taskerlyze.app.persistence.mappers.TaskMapper;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.ITaskRepository;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {
    private final ITaskRepository repository;
    private final TaskMapper mapper;
    private int id;
    private String name;
    private String description;
    private boolean done;

    @Inject
    public Task(ITaskRepository repository) {
        this.repository = repository;
        this.mapper = new TaskMapper();

        this.done = false;
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

    public List<Task> get(Map<String, Pair<String, Object>> filters, Pair<String, String> orderByColumn) {
        Response response = repository.read(filters, orderByColumn);

        return mapper.toEntity(response.getResult());
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

    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getId() {
        return id;
    }

    public Task setId(int id) {
        this.id = id;
        return this;
    }

    public static Task getInstance() {
        AppComponent component = DaggerAppComponent.create();

        return component.getTask();
//        return null;
    }

    public String getName() {
        return name;
    }

    public Task setName(String name) {
        this.name = name;
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

    public boolean setTaskStatus(boolean done) {
        List<Pair<String, Object>> sets = new ArrayList<>();

        sets.add(new Pair<>("is_done", done));

        Response response = repository.update(this, sets);
        this.done = done;

        return response.isSuccess();
    }

    public boolean setTaskStatus() {
        return setTaskStatus(!isDone(true));
    }
}
