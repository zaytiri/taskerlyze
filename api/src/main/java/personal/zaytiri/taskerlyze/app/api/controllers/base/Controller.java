package personal.zaytiri.taskerlyze.app.api.controllers.base;

import personal.zaytiri.taskerlyze.app.api.controllers.result.CodeResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.MessageResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.base.IStorageOperations;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;

import java.util.List;
import java.util.Map;

public abstract class Controller<T extends IStorageOperations<T>> implements IController<T> {

    @Override
    public OperationResult<T> createOrUpdate(T request) {
        boolean isCreated = request.createOrUpdate();

        MessageResult message = new MessageResult();
        if (isCreated) {
            message.setCode(CodeResult.CREATED);
        } else {
            message.setCode(CodeResult.NOT_CREATED);
        }

        return new OperationResult<>(isCreated, message, request);
    }

    @Override
    public OperationResult<T> delete(int id) {
        T toBeDeleted = getEntityInstance(id);

        boolean isDeleted = toBeDeleted.delete();

        MessageResult message = new MessageResult();
        if (isDeleted) {
            message.setCode(CodeResult.DELETED);
        } else {
            message.setCode(CodeResult.NOT_DELETED);
        }

        return new OperationResult<>(isDeleted, message, null);
    }

    @Override
    public OperationResult<T> get(int id) {
        T entity = getEntityInstance(id);

        T entityPopulated = entity.get();

        MessageResult message = new MessageResult();
        if (entityPopulated != null) {
            message.setCode(CodeResult.FOUND);
        } else {
            message.setCode(CodeResult.NOT_FOUND);
        }

        return new OperationResult<>(entityPopulated != null, message, entityPopulated);
    }

    @Override
    public OperationResult<List<T>> get(Map<String, Pair<String, Object>> filters, Pair<String, String> orderByColumn) {
        T entity = getEntityInstance();

        List<T> entities = entity.get(filters, orderByColumn);

        MessageResult message = new MessageResult();
        if (!entities.isEmpty()) {
            message.setCode(CodeResult.FOUND);
        } else {
            message.setCode(CodeResult.NOT_FOUND);
        }

        return new OperationResult<>(!entities.isEmpty(), message, entities);
    }

    protected abstract T getEntityInstance(int id);
    protected abstract T getEntityInstance();
}
