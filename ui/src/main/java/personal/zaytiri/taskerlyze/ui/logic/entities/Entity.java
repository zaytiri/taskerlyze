package personal.zaytiri.taskerlyze.ui.logic.entities;

import personal.zaytiri.taskerlyze.app.api.controllers.base.IController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;

public abstract class Entity<TModel, TEntity, TController extends IController<TModel>> {
    protected int id;
    protected TController api;

    protected Entity(int id) {
        this.id = id;
    }

    protected Entity() {
    }

    public Pair<TEntity, Pair<Boolean, String>> create() {
        OperationResult<TModel> result = api.create(mapToApiObject());
        return new Pair<>(mapToUiObject(result.getResult()), new Pair<>(result.getStatus(), result.getMessageResult().getMessage()));
    }

    public Pair<TEntity, Pair<Boolean, String>> createOrUpdate() {
        if (this.id == 0) {
            return create();
        } else {
            return update();
        }
    }

    public TEntity get() {
        return get(this.id);
    }

    public TEntity get(int id) {
        OperationResult<TModel> taskResult = api.get(id);
        return mapToUiObject(taskResult.getResult());
    }

    public int getId() {
        return id;
    }

    public abstract TModel mapToApiObject();

    public abstract TEntity mapToUiObject(TModel model);

    public boolean remove() {
        return api.delete(id).getStatus();
    }

    public abstract TEntity setId(int id);

    public Pair<TEntity, Pair<Boolean, String>> update() {
        OperationResult<TModel> result = api.update(mapToApiObject());

        return new Pair<>(mapToUiObject(result.getResult()), new Pair<>(result.getStatus(), result.getMessageResult().getMessage()));

    }
}
