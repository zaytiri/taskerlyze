package personal.zaytiri.taskerlyze.app.api.domain;

import jakarta.inject.Inject;
import personal.zaytiri.taskerlyze.app.api.domain.base.Entity;
import personal.zaytiri.taskerlyze.app.api.domain.base.IStorageOperations;
import personal.zaytiri.taskerlyze.app.dependencyinjection.AppComponent;
import personal.zaytiri.taskerlyze.app.persistence.mappers.QuestionMapper;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.IQuestionRepository;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question extends Entity<Question, IQuestionRepository, QuestionMapper> implements IStorageOperations<Question> {
    private String answer;
    private int categoryId;
    private LocalDate answeredAt;
    private boolean isAnswered;

    @Inject
    public Question(IQuestionRepository repository) {
        this.repository = repository;
        this.mapper = new QuestionMapper();
    }

    public Question() {
    }

    public boolean create() {
        if (exists()) {
            return false;
        }

        Response response = repository.create(this);
        this.id = response.getLastInsertedId();

        return response.isSuccess();
    }

    public boolean delete() {
        Response response = repository.delete(this);
        if (get() != null) {
            return false;
        }

        return response.isSuccess();
    }

    public boolean exists() {
        Response response = repository.exists(this); // check done by name

        return !response.getResult().isEmpty();
    }

    public List<Question> get(Map<String, Pair<String, Object>> filters, Pair<String, String> orderByColumn) {
        Response response = repository.read(filters, orderByColumn);

        return mapper.toEntity(response.getResult(), false);
    }

    public Question get() {
        Map<String, Pair<String, Object>> filters = new HashMap<>();
        filters.put("id", new Pair<>("=", this.id));

        List<Question> results = get(filters, null);
        if (results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }

    public boolean update() {
        //todo: test what happens if i try to update but theres no entry to update because its not created yet.
        return repository.update(this).isSuccess();
    }

    public String getAnswer() {
        return answer;
    }

    public Question setAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    public LocalDate getAnsweredAt() {
        return answeredAt;
    }

    public Question setAnsweredAt(LocalDate answeredAt) {
        this.answeredAt = answeredAt;
        return this;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public Question setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public List<Question> getQuestionsByCategoryAndAnsweredAtDate() {
        Response response = repository.getQuestionsByCategoryAndAnsweredAtDate(this.categoryId, this.answeredAt);

        return mapper.toEntity(response.getResult(), false);
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public Question setAnswered(boolean answered) {
        isAnswered = answered;
        return this;
    }

    public Question setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public Question setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    protected Question getInjectedComponent(AppComponent component) {
        return component.getQuestion();
    }

    public boolean setQuestionStatus(boolean done) {
        List<Pair<String, Object>> sets = new ArrayList<>();

        LocalDate today = null;
        sets.add(new Pair<>("is_answered", done));

        if (done) {
            today = LocalDate.now();
        }
        sets.add(new Pair<>("answered_at", today));

        Response response = repository.update(this, sets);
        if (response.isSuccess()) {
            this.isAnswered = done;
            this.answeredAt = today;
        }

        return response.isSuccess();
    }
}
