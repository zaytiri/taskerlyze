package personal.zaytiri.taskerlyze.app.api.domain;

public class Task {
import personal.zaytiri.taskerlyze.app.dependencyinjection.AppComponent;
import personal.zaytiri.taskerlyze.app.dependencyinjection.DaggerAppComponent;

    private final ITaskRepository repository;
    private int id;
    private String name;
    private String description;

    public Task() {}
    @Inject
    public Task(ITaskRepository repository) {
        this.repository = repository;
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

    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }
}
