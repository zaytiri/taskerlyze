package personal.zaytiri.taskerlyze.app.persistence.mappers.base;

public interface Mapper<E, M> {

    M toModel(E entity);
    E toEntity(M model);
}
