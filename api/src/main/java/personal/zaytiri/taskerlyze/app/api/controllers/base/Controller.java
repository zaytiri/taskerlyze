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
    public OperationResult<T> create(T request) {
        boolean isCreated = request.create();

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

    public OperationResult<List<T>> findNameBySubString(String subString) {
        T entity = getEntityInstance();

        List<T> tasksBySubString = entity.findNameBySubString(subString);

        MessageResult message = new MessageResult();
        if (tasksBySubString.isEmpty()) {
            message.setCode(CodeResult.NOT_FOUND);
        } else {
            message.setCode(CodeResult.FOUND);
        }

        return new OperationResult<>(!tasksBySubString.isEmpty(), message, tasksBySubString);
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
    public OperationResult<T> update(T request) {
        boolean isUpdated = request.update();

        MessageResult message = new MessageResult();
        if (isUpdated) {
            message.setCode(CodeResult.UPDATED);
        } else {
            message.setCode(CodeResult.NOT_UPDATED);
        }

        return new OperationResult<>(isUpdated, message, request);
    }

    protected abstract T getEntityInstance(int id);

    protected abstract T getEntityInstance();
}
