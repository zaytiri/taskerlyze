package personal.zaytiri.taskerlyze.ui.logic.entities;

import personal.zaytiri.taskerlyze.app.api.controllers.CategoryController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.mappers.CategoryMapper;

import java.util.ArrayList;
import java.util.List;

public class CategoryEntity extends Entity<Category, CategoryEntity, CategoryController> {
    private String name;

    public CategoryEntity(Category category) {
        this();
        mapper.mapToUiObject(category, this);
    }

    public CategoryEntity() {
        this(0);
    }

    public CategoryEntity(int id) {
        super(id);
        this.api = new CategoryController();
        mapper = new CategoryMapper();
    }

    @Override
    public List<Pair<Integer, String>> findBySubString(String subString) {
        OperationResult<List<Category>> result = api.findNameBySubString(subString);
        List<Pair<Integer, String>> categoriesToBeReturned = new ArrayList<>();
        for (Category cat : result.getResult()) {
            categoriesToBeReturned.add(new Pair<>(cat.getId(), cat.getName()));
        }
        return categoriesToBeReturned;
    }

    public CategoryEntity setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    protected CategoryEntity getObject() {
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoryEntity setName(String name) {
        this.name = name;
        return this;
    }
}
