package personal.zaytiri.taskerlyze.app.persistence.models;

import personal.zaytiri.makeitexplicitlyqueryable.pairs.Pair;
import personal.zaytiri.taskerlyze.app.persistence.models.base.Model;

import java.util.ArrayList;
import java.util.List;

public class ProfileModel extends Model {
    public ProfileModel() {
        super("profiles");
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
