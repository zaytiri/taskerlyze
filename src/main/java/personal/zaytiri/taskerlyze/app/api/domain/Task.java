package personal.zaytiri.taskerlyze.app.api.domain;

import personal.zaytiri.taskerlyze.app.dependencyinjection.AppComponent;
import personal.zaytiri.taskerlyze.app.dependencyinjection.DaggerAppComponent;
import personal.zaytiri.taskerlyze.app.persistence.mappers.TaskMapper;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.ITaskRepository;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {
    private final ITaskRepository repository;
    private int id;
    private String name;
    private String description;
    private boolean done;
    private final TaskMapper mapper;

    @Inject
    public Task(ITaskRepository repository) {
        this.repository = repository;
        this.mapper = new TaskMapper();
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
        if (!exists()) {
            return false;
        }

        Response response = repository.delete(this);
        return response.isSuccess();
    }

    public boolean exists() {
        Response response = repository.exists(this); // check done by name

        return !response.getResult().isEmpty();
    }

    public List<Task> getAll(Map<String, Pair<String, Object>> filters) {
        Response response = repository.readFiltered(filters);

        return mapper.toEntity(response.getResult());
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
    }

    public String getName() {
        return name;
    }

    public Task setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isDone(boolean fromDb) {
        if (!fromDb) {
            return done;
        }
        Response response = repository.read(this);
        return Boolean.parseBoolean(response.getResult().get(0).get("done"));
    }

    public Task populate() {
        if (!exists()) {
            throw new IllegalArgumentException("No task exists with provided name.");
        }

        Response response = repository.read(this);

        return mapper.toEntity(response.getResult()).get(0);
    }

    public boolean setDone(boolean done) {
        Map<String, Object> sets = new HashMap<>();

        sets.put("done", done);

        Response response = repository.update(this, sets);
        this.done = done;

        return response.isSuccess();
    }

    public boolean setDone() {
        return setDone(!isDone(true));
    }
}
