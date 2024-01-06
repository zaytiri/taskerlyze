package personal.zaytiri.taskerlyze.ui.logic.entities;

import personal.zaytiri.taskerlyze.app.api.controllers.QuestionController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Question;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.mappers.QuestionMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QuestionEntity extends Entity<Question, QuestionEntity, QuestionController> {
    private String question;
    private String answer;
    private int categoryId;
    private LocalDate answeredAt;
    private boolean isAnswered;

    public QuestionEntity() {
        this(0);
    }

    public QuestionEntity(Question question) {
        this();
        mapper.mapToUiObject(question, this);
    }

    public QuestionEntity(int id) {
        super(id);
        api = new QuestionController();
        mapper = new QuestionMapper();
    }

    public List<Pair<Integer, String>> findBySubString(String subString) {
        OperationResult<List<Question>> result = api.findNameBySubString(subString);
        List<Pair<Integer, String>> questionsToBeReturned = new ArrayList<>();
        for (Question question : result.getResult()) {
            questionsToBeReturned.add(new Pair<>(question.getId(), question.getName()));
        }
        return questionsToBeReturned;
    }

    public QuestionEntity setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    protected QuestionEntity getObject() {
        return this;
    }

    public String getAnswer() {
        return answer;
    }

    public QuestionEntity setAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    public LocalDate getAnsweredAt() {
        return answeredAt;
    }

    public QuestionEntity setAnsweredAt(LocalDate answeredAt) {
        this.answeredAt = answeredAt;
        return this;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public QuestionEntity setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getQuestion() {
        return question;
    }

    public QuestionEntity setQuestion(String question) {
        this.question = question;
        return this;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public QuestionEntity setAnswered(boolean answered) {
        isAnswered = answered;
        return this;
    }

    public void setQuestionAsAnswered() {
        api.setAnswered(this.id, isAnswered);
    }
}
