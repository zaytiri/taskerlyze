package personal.zaytiri.taskerlyze.app.persistence.mappers.base;

import java.util.List;
import java.util.Map;

public interface Mapper<E, M> {

    List<E> toEntity(List<Map<String, String>> rows, boolean mixedResult);

    E toEntity(M model);

    M toModel(E entity);
}
