package personal.zaytiri.taskerlyze.ui.logic.loaders;

import personal.zaytiri.taskerlyze.app.api.controllers.CategoryController;
import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.ui.logic.entities.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

public class CategoryLoader {

    public List<CategoryEntity> load() {
        List<CategoryEntity> categoriesToBeReturned = new ArrayList<>();

        List<Category> categories = new CategoryController().get(null, null).getResult();

        for (Category cat : categories) {
            categoriesToBeReturned.add(new CategoryEntity(cat));
        }

        return categoriesToBeReturned;
    }
}
