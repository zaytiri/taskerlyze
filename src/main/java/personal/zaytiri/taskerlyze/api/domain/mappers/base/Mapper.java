package personal.zaytiri.taskerlyze.api.domain.mappers.base;

public interface Mapper<E, M> {

    M toModel(E entity, M model);
    M toModel(E entity);
    E toEntity(M model, E entity);
    E toEntity(M model);
}
