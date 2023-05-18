package personal.zaytiri.taskerlyze.app.persistence.models;

import personal.zaytiri.taskerlyze.app.persistence.models.base.Model;

import java.util.HashMap;
import java.util.Map;

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
    public Map<String, Object> getValues() {
        Map<String, Object> values = new HashMap<>();

        values.put("id", id);
        values.put("name", name);
        values.put("description", description);
        values.put("done", done);

        return values;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
