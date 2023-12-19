package personal.zaytiri.taskerlyze.app.persistence.mappers;

import personal.zaytiri.taskerlyze.app.api.domain.Question;
import personal.zaytiri.taskerlyze.app.persistence.mappers.base.Mapper;
import personal.zaytiri.taskerlyze.app.persistence.models.QuestionModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestionMapper extends Mapper<Question, QuestionModel> {
    public QuestionMapper() {
        super("questions__");
    }

    @Override
    public List<Question> toEntity(List<Map<String, String>> rows, boolean mixedResult) {
        List<Question> questions = new ArrayList<>();

        for (Map<String, String> row : rows) {
            Question question = new Question().getInstance();

            question.setId(getRowIntValue(row, mixedResult, "id"));
            question.setName(getRowStringValue(row, mixedResult, "question"));
            question.setAnswer(getRowStringValue(row, mixedResult, "answer"));
            question.setCategoryId(getRowIntValue(row, mixedResult, "category_id"));
            question.setAnswered(getRowBooleanValue(row, mixedResult, "is_answered"));
            question.setAnsweredAt(getRowDateValue(row, mixedResult, "answered_at"));

            questions.add(question);
        }
        return questions;
    }

    @Override
    public Question toEntity(QuestionModel model) {
        Question entity = new Question().getInstance();

        if (model == null) return null;

        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setAnswer(model.getAnswer());
        entity.setCategoryId(model.getCategoryId());
        entity.setAnswered(model.isAnswered());
        entity.setAnsweredAt(model.getAnsweredAt());

        return entity;
    }

    @Override
    public QuestionModel toModel(Question entity) {
        QuestionModel model = new QuestionModel();

        if (entity == null) return null;

        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setAnswer(entity.getAnswer());
        model.setCategoryId(entity.getCategoryId());
        model.setAnswered(entity.isAnswered());
        model.setAnsweredAt(entity.getAnsweredAt());

        return model;
    }
}
