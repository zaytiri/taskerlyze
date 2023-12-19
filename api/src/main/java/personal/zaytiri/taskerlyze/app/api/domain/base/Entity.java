package personal.zaytiri.taskerlyze.app.api.domain.base;

import personal.zaytiri.taskerlyze.app.dependencyinjection.AppComponent;
import personal.zaytiri.taskerlyze.app.dependencyinjection.DaggerAppComponent;

public abstract class Entity<E, R, M> implements IDependencyInjection<E> {
    protected R repository;
    protected M mapper;

    protected int id;
    protected String name;

    public int getId() {
        return id;
    }

    public E getInstance() {
        AppComponent component = DaggerAppComponent.create();

        return getInjectedComponent(component);
    }

    public String getName() {
        return name;
    }

    public abstract E setId(int id);

    public abstract E setName(String name);

    protected abstract E getInjectedComponent(AppComponent component);
}
