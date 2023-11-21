package personal.zaytiri.taskerlyze.ui.logic.entities;

import personal.zaytiri.taskerlyze.app.api.controllers.CategoryController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;

public class CategoryEntity {
    private final CategoryController api;
    private int id;
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
        api = new CategoryController();
    }

    public Pair<Boolean, String> create() {
        Category newCat = new Category().getInstance().setName(name);
        OperationResult<Category> result = api.create(newCat);

        return new Pair<>(result.getStatus(), result.getMessageResult().getMessage());
    }

    public int getId() {
        return id;
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

    public boolean remove() {
        return api.delete(id).getStatus();
    }
}
