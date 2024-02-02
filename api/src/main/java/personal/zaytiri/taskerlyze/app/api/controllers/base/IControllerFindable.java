package personal.zaytiri.taskerlyze.app.api.controllers.base;

import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;

import java.util.List;

public interface IControllerFindable<T> {
    /**
     * Finds entities which the name contains a given substring.
     * If nothing is found, it returns an empty list.
     *
     * @param subString to search for.
     * @return OperationResult<List < T>>
     */
    OperationResult<List<T>> findNameBySubString(String subString, int profileId);
}
