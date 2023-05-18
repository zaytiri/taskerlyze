package personal.zaytiri.taskerlyze.app.api.domain;

import personal.zaytiri.taskerlyze.app.dependencyinjection.AppComponent;
import personal.zaytiri.taskerlyze.app.dependencyinjection.DaggerAppComponent;
import personal.zaytiri.taskerlyze.app.persistence.mappers.TaskMapper;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.ITaskRepository;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import javax.inject.Inject;

public class Task {
    private final ITaskRepository repository;
    private int id;
    private String name;
    private String description;
    private TaskMapper mapper;

    @Inject
    public Task(ITaskRepository repository) {
        this.repository = repository;
    }

    public boolean createOrUpdate() {
        Response response;

        if (!exists()) {
            response = repository.create(this);
            setId(response.getLastInsertedId());
        } else {
            response = repository.update(this);
        }

        populate();

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
        Response response = repository.exists(this);

        return !response.getResult().isEmpty();
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

    public Task populate() {
        if (!exists()) {
            return null;
        }

        Response response = repository.read(this);

        return mapper.toEntity(response.getResult()).get(0);
    }
}
