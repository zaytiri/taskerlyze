package personal.zaytiri.taskerlyze.app.persistence.models;

import personal.zaytiri.taskerlyze.app.persistence.models.base.Model;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QuestionModel extends Model {
    private String answer;
    private int categoryId;
    private LocalDate answeredAt;
    private boolean isAnswered;

    public QuestionModel() {
        super("questions");
    }

    public String getAnswer() {
        return answer;
    }

    public QuestionModel setAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    public LocalDate getAnsweredAt() {
        return answeredAt;
    }

    public QuestionModel setAnsweredAt(LocalDate answeredAt) {
        this.answeredAt = answeredAt;
        return this;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public QuestionModel setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    @Override
    public List<Pair<String, Object>> getValues() {
        List<Pair<String, Object>> values = new ArrayList<>();

//        values.put("id", id); // its commented out because it does not need to be inserted, it will auto increment
        values.add(new Pair<>("question", name));
        values.add(new Pair<>("answer", answer));
        values.add(new Pair<>("is_answered", isAnswered));
        values.add(new Pair<>("answered_at", answeredAt));
        values.add(new Pair<>("category_id", categoryId));
        values.add(new Pair<>("updated_at", updatedAt));
        values.add(new Pair<>("created_at", createdAt));

        return values;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public QuestionModel setAnswered(boolean answered) {
        isAnswered = answered;
        return this;
    }

}
