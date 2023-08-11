package personal.zaytiri.taskerlyze.app.api.domain.base;

import java.util.concurrent.ExecutionException;

public abstract class DbOperations{

    protected int id = 0;


    public int getId() {
        return id;
    }

    public DbOperations setId(int id) {
        this.id = id;
        return this;
    }

    public abstract boolean exists() throws ExecutionException, InterruptedException;
    public abstract void populate() throws ExecutionException, InterruptedException;
    public abstract void create() throws ExecutionException, InterruptedException;
    public abstract void update() throws ExecutionException, InterruptedException;
    public abstract void delete() throws ExecutionException, InterruptedException;

}
