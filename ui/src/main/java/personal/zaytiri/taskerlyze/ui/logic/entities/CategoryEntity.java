package personal.zaytiri.taskerlyze.ui.logic.entities;

import personal.zaytiri.taskerlyze.app.api.controllers.CategoryController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;

import java.util.ArrayList;
import java.util.List;

public class CategoryEntity extends Entity<Category, CategoryEntity, CategoryController> {
    private String name;

    public CategoryEntity(int id, String name) {
        this();
        this.id = id;
        this.name = name;
    }

    public CategoryEntity(Category category) {
        this(category.getId(), category.getName());
    }

    public CategoryEntity() {
        this.api = new CategoryController();
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

    public Category mapToApiObject() {
        return new Category().getInstance()
                .setId(id)
                .setName(name);
    }

    public CategoryEntity mapToUiObject(Category entity) {
        return new CategoryEntity(entity);
    }

    public CategoryEntity setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoryEntity setName(String name) {
        this.name = name;
        return this;
    }

    public Pair<Integer, String> getPair() {
        Pair<Integer, String> pair = new Pair<>();
        pair.setKey(this.id);
        pair.setValue(this.name);
        return pair;
    }

}
