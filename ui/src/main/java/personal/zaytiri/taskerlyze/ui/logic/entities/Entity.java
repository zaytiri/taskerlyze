package personal.zaytiri.taskerlyze.ui.logic.entities;

import personal.zaytiri.taskerlyze.app.api.controllers.base.IController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.mappers.IMapper;

import java.util.List;

public abstract class Entity<TModel, TEntity, TController extends IController<TModel>> {
    protected int id;
    protected TController api;
    protected IMapper<TModel, TEntity> mapper;

    protected Entity(int id) {
        this.id = id;
    }

    protected Entity() {
    }

    public Pair<TEntity, Pair<Boolean, String>> create() {
        OperationResult<TModel> result = api.create(mapper.mapToApiObject(getObject()));
        return new Pair<>(mapper.mapToUiObject(result.getResult(), getObject()), new Pair<>(result.getStatus(), result.getMessageResult().getMessage()));
    }

    public Pair<TEntity, Pair<Boolean, String>> createOrUpdate() {
        if (this.id == 0) {
            return create();
        } else {
            return update();
        }
    }

    public abstract List<Pair<Integer, String>> findBySubString(String subString);

    public TEntity get() {
        return get(this.id);
    }

    public TEntity get(int id) {
        OperationResult<TModel> taskResult = api.get(id);
        return mapper.mapToUiObject(taskResult.getResult(), getObject());
    }

    public int getId() {
        return id;
    }

    public boolean remove() {
        return api.delete(id).getStatus();
    }

    public abstract TEntity setId(int id);

    public Pair<TEntity, Pair<Boolean, String>> update() {
        OperationResult<TModel> result = api.update(mapper.mapToApiObject(getObject()));

        return new Pair<>(mapper.mapToUiObject(result.getResult(), getObject()), new Pair<>(result.getStatus(), result.getMessageResult().getMessage()));

    }

    protected abstract TEntity getObject();
}
