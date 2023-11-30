package personal.zaytiri.taskerlyze.ui.logic.loaders;

import personal.zaytiri.taskerlyze.app.api.controllers.CategoryController;
import personal.zaytiri.taskerlyze.app.api.domain.Category;
import personal.zaytiri.taskerlyze.ui.logic.entities.CategoryEntity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class CategoryLoader {
    private static CategoryLoader INSTANCE;
    private final PropertyChangeSupport support;
    private final List<CategoryEntity> loadedCategories = new ArrayList<>();

    private CategoryLoader() {
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public static CategoryLoader getCategoryLoader() {
        if (INSTANCE == null) {
            INSTANCE = new CategoryLoader();
        }
        return INSTANCE;
    }

    public List<CategoryEntity> load() {
        List<CategoryEntity> categoriesToBeReturned = new ArrayList<>();

        List<Category> categories = new CategoryController().get(null, null).getResult();

        for (Category cat : categories) {
            categoriesToBeReturned.add(new CategoryEntity(cat));
        }
        support.firePropertyChange("loadedCategories", loadedCategories, categoriesToBeReturned);

        loadedCategories.clear();
        loadedCategories.addAll(categoriesToBeReturned);
        return categoriesToBeReturned;
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
}
