package personal.zaytiri.taskerlyze.app.persistence.models;

import personal.zaytiri.taskerlyze.app.persistence.models.base.Model;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;

import java.util.ArrayList;
import java.util.List;

public class CategoryModel extends Model {
    public CategoryModel() {
        super("categories");
    }

    @Override
    public List<Pair<String, Object>> getValues() {
        List<Pair<String, Object>> values = new ArrayList<>();

//        values.put("id", id); // its commented out because it does not need to be inserted, it will auto increment
        values.add(new Pair<>("name", name));
        values.add(new Pair<>("updated_at", updatedAt));
        values.add(new Pair<>("created_at", createdAt));

        return values;
    }
}