package personal.zaytiri.taskerlyze.ui.logic.mappers;

import personal.zaytiri.taskerlyze.app.api.domain.Question;
import personal.zaytiri.taskerlyze.ui.logic.entities.QuestionEntity;

public class QuestionMapper implements IMapper<Question, QuestionEntity> {
    @Override
    public Question mapToApiObject(QuestionEntity questionEntity) {
        return new Question().getInstance()
                .setId(questionEntity.getId())
                .setName(questionEntity.getQuestion())
                .setAnswer(questionEntity.getAnswer())
                .setCategoryId(questionEntity.getCategoryId())
                .setAnsweredAt(questionEntity.getAnsweredAt())
                .setAnswered(questionEntity.isAnswered());
    }

    @Override
    public QuestionEntity mapToUiObject(Question question, QuestionEntity questionEntity) {
        return questionEntity
                .setId(question.getId())
                .setQuestion(question.getName())
                .setAnswer(question.getAnswer())
                .setCategoryId(question.getCategoryId())
                .setAnsweredAt(question.getAnsweredAt())
                .setAnswered(question.isAnswered());
    }
}