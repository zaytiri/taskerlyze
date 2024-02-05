package personal.zaytiri.taskerlyze.app.persistence.repositories;

import jakarta.inject.Inject;
import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.querybuilder.query.builders.SelectQueryBuilder;
import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.querybuilder.query.enums.Operators;
import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.response.Response;
import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.app.persistence.mappers.CategoryMapper;
import personal.zaytiri.taskerlyze.app.persistence.models.CategoryModel;
import personal.zaytiri.taskerlyze.app.persistence.repositories.base.Repository;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.ICategoryRepository;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository extends Repository<Category, CategoryModel, CategoryMapper> implements ICategoryRepository {
    @Inject
    public CategoryRepository() {
        super(new CategoryModel(), new CategoryMapper());
    }

    @Override
    public Response exists(Category category) {
        model = mapper.toModel(category);

        SelectQueryBuilder query = new SelectQueryBuilder(connection.open());

        Column profileId = model.getTable().getColumn("profile_id");

        Column name = model.getTable().getColumn("name");
        List<Column> columns = new ArrayList<>();
        columns.add(name);

        query.select(columns)
                .from(model.getTable())
                .where(name, Operators.EQUALS, model.getName())
                .and()
                .where(profileId, Operators.EQUALS, model.getProfileId());

        return query.execute();
    }
}
