package personal.zaytiri.taskerlyze.app.persistence.models;

import personal.zaytiri.taskerlyze.app.persistence.models.base.Model;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SubTaskModel extends Model {
    private boolean done;
    private int taskId;
    private LocalDate completedAt;

    public SubTaskModel() {
        super("subtasks");
    }

    public LocalDate getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDate completedAt) {
        this.completedAt = completedAt;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public List<Pair<String, Object>> getValues() {
        List<Pair<String, Object>> values = new ArrayList<>();

//        values.put("id", id); // its commented out because it does not need to be inserted, it will auto increment
        values.add(new Pair<>("name", name));
        values.add(new Pair<>("task_id", taskId));
        values.add(new Pair<>("is_done", done));
        values.add(new Pair<>("completed_at", completedAt));
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
