package personal.zaytiri.taskerlyze.ui.logic.loaders;

import personal.zaytiri.taskerlyze.app.api.controllers.QuestionController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Question;
import personal.zaytiri.taskerlyze.ui.logic.entities.QuestionEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QuestionLoader implements Loader<QuestionEntity> {
    private final int categoryId;
    private final LocalDate date;

    public QuestionLoader(int categoryId, LocalDate date) {
        this.categoryId = categoryId;
        this.date = date;
    }

    @Override
    public List<QuestionEntity> load() {
        OperationResult<List<Question>> questionResult = new QuestionController().getQuestionsByCategoryAndAnsweredAtDate(categoryId, date);

        List<QuestionEntity> questionToBeReturned = new ArrayList<>();
        for (Question question : questionResult.getResult()) {
            questionToBeReturned.add(new QuestionEntity(question));
        }
        return questionToBeReturned;
    }
}
