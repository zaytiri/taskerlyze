package personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces;

import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.response.Response;
import personal.zaytiri.taskerlyze.app.api.domain.Category;

public interface ICategoryRepository extends IRepository<Category> {

    Response exists(Category category);
}
