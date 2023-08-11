package personal.zaytiri.taskerlyze.app.persistence.models;

import personal.zaytiri.taskerlyze.app.persistence.models.base.Model;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;

import java.util.ArrayList;
import java.util.List;

public class TaskModel extends Model {
    private boolean done;
    private String description;

    public TaskModel() {
        super("tasks");
    }

    public String getDescription() {
        return description;
    }

    public TaskModel setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public List<Pair<String, Object>> getValues() {
        List<Pair<String, Object>> values = new ArrayList<>();

//        values.put("id", id); // its commented out because it does not need to be inserted, it will auto increment
        values.add(new Pair<>("name", name));
        values.add(new Pair<>("is_done", done));
        values.add(new Pair<>("description", description));
        values.add(new Pair<>("updated_at", updatedAt));
        values.add(new Pair<>("created_at", createdAt));

        return values;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
