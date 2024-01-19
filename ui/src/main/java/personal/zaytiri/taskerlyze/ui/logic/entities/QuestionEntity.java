package personal.zaytiri.taskerlyze.ui.logic.entities;

import personal.zaytiri.taskerlyze.app.api.controllers.QuestionController;
import personal.zaytiri.taskerlyze.app.api.domain.Question;
import personal.zaytiri.taskerlyze.ui.logic.mappers.QuestionMapper;

import java.time.LocalDate;

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

    public QuestionEntity setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    protected QuestionEntity getObject() {
        return this;
    }

    public void setQuestionAsAnswered() {
        api.setAnswered(this.id, isAnswered);
    }
}
