package personal.zaytiri.taskerlyze.ui.logic.entities;

import personal.zaytiri.taskerlyze.app.api.controllers.CategoryController;
import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.ui.logic.mappers.CategoryMapper;

public class CategoryEntity extends Entity<Category, CategoryEntity, CategoryController> {
    private String name;
    private int profileId;

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

    public String getName() {
        return name;
    }

    public CategoryEntity setName(String name) {
        this.name = name;
        return this;
    }

    public int getProfileId() {
        return profileId;
    }

    public CategoryEntity setProfileId(int profileId) {
        this.profileId = profileId;
        return this;
    }

    public CategoryEntity setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    protected CategoryEntity getObject() {
        return this;
    }
}
