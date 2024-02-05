package personal.zaytiri.taskerlyze.app.persistence.models;

import personal.zaytiri.makeitexplicitlyqueryable.pairs.Pair;
import personal.zaytiri.taskerlyze.app.persistence.models.base.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskModel extends Model {
    private boolean done;
    private String description;
    private int categoryId;
    private LocalDate completedAt;
    private String achieved;
    private String url;
    private int priority;

    public TaskModel() {
        super("tasks");
    }

    public String getAchieved() {
        return achieved;
    }

    public TaskModel setAchieved(String achieved) {
        this.achieved = achieved;
        return this;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public LocalDate getCompletedAt() {
        return completedAt;
    }

    public TaskModel setCompletedAt(LocalDate completedAt) {
        this.completedAt = completedAt;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TaskModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getPriority() {
        return priority;
    }

    public TaskModel setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public TaskModel setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public List<Pair<String, Object>> getValues() {
        List<Pair<String, Object>> values = new ArrayList<>();

//        values.put("id", id); // its commented out because it does not need to be inserted, it will auto increment
        values.add(new Pair<>("name", name));
        values.add(new Pair<>("is_done", done));
        values.add(new Pair<>("description", description));
        values.add(new Pair<>("completed_at", completedAt));
        values.add(new Pair<>("priority", priority));
        values.add(new Pair<>("achieved", achieved));
        values.add(new Pair<>("url", url));
        values.add(new Pair<>("category_id", categoryId));
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
