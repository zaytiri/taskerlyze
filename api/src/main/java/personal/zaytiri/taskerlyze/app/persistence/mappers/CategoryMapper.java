package personal.zaytiri.taskerlyze.app.persistence.mappers;

import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.app.persistence.mappers.base.Mapper;
import personal.zaytiri.taskerlyze.app.persistence.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryMapper extends Mapper<Category, CategoryModel> {
    public CategoryMapper() {
        super("categories__");
    }

    @Override
    public List<Category> toEntity(List<Map<String, String>> rows, boolean mixedResult) {
        List<Category> categories = new ArrayList<>();

        for (Map<String, String> row : rows) {
            Category category = new Category().getInstance();

            category.setId(getRowIntValue(row, mixedResult, "id"));
            category.setName(getRowStringValue(row, mixedResult, "name"));

            categories.add(category);
        }
        return categories;
    }

    @Override
    public Category toEntity(CategoryModel model) {
        Category entity = new Category().getInstance();

        if (model == null) return null;

        entity.setId(model.getId());
        entity.setName(model.getName());

        return entity;
    }

    @Override
    public CategoryModel toModel(Category entity) {
        CategoryModel model = new CategoryModel();

        if (entity == null) return null;

        model.setId(entity.getId());
        model.setName(entity.getName());

        return model;
    }
}
