package personal.zaytiri.taskerlyze.ui.logic.mappers;

public interface IMapper<TModel, TEntity> {
    TModel mapToApiObject(TEntity entity);

    TEntity mapToUiObject(TModel model, TEntity entity);
}
