package personal.zaytiri.taskerlyze.app.persistence.models;

import personal.zaytiri.taskerlyze.app.persistence.models.base.Model;

import java.util.ArrayList;
import java.util.List;

public class TaskModel extends Model {
    //    private Status status;

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
    public Object[] getValues() {
        List<Object> values = new ArrayList<>();

        values.add(id);
        values.add(name);
        values.add(description);

        return values.toArray();
    }
}
