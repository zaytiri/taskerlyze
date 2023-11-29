package personal.zaytiri.taskerlyze.ui.logic.mappers;

import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.ui.logic.entities.CategoryEntity;

public class CategoryMapper implements IMapper<Category, CategoryEntity> {
    @Override
    public Category mapToApiObject(CategoryEntity categoryEntity) {
        return new Category().getInstance()
                .setId(categoryEntity.getId())
                .setName(categoryEntity.getName());
    }

    @Override
    public CategoryEntity mapToUiObject(Category category, CategoryEntity categoryEntity) {
        return categoryEntity
                .setId(category.getId())
                .setName(category.getName());
    }
}
