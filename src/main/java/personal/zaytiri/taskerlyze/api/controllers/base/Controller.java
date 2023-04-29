package personal.zaytiri.taskerlyze.api.controllers.base;


import personal.zaytiri.taskerlyze.api.controllers.result.OperationResult;

import java.util.concurrent.ExecutionException;

public interface Controller<T> {
    OperationResult<T> get(int id) throws ExecutionException, InterruptedException;

    OperationResult<T> create(T request) throws ExecutionException, InterruptedException;

    OperationResult<T> update(T request) throws ExecutionException, InterruptedException;

    OperationResult<T> delete(int id) throws ExecutionException, InterruptedException;

}
