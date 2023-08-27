package personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces;

import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

public interface ICategoryRepository extends IRepository<Category> {

    Response exists(Category category);
}
