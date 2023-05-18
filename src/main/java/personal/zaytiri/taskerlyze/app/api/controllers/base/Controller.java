package personal.zaytiri.taskerlyze.app.api.controllers.base;


import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;

public interface Controller<T> {
    OperationResult<T> createOrUpdate(T request);

    OperationResult<T> delete(int id);

    OperationResult<T> get(int id);
}
