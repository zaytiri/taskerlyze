package personal.zaytiri.taskerlyze.app.api.domain.base;

import java.util.List;

public interface IFindable<T> extends IStorageOperations<T> {
    List<T> findNameBySubString(String subString, int profileId);
}
