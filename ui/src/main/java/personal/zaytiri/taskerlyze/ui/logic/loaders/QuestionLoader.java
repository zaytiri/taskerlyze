package personal.zaytiri.taskerlyze.ui.logic.loaders;

import personal.zaytiri.taskerlyze.app.api.controllers.QuestionController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Question;
import personal.zaytiri.taskerlyze.ui.logic.entities.QuestionEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QuestionLoader implements Loadable<QuestionEntity> {
    private int categoryId;
    private LocalDate date;

    @Override
    public List<QuestionEntity> load() {
        OperationResult<List<Question>> questionResult = new QuestionController().getQuestionsByCategoryAndAnsweredAtDate(categoryId, date);

        List<QuestionEntity> questionToBeReturned = new ArrayList<>();
        for (Question question : questionResult.getResult()) {
            questionToBeReturned.add(new QuestionEntity(question));
        }
        return questionToBeReturned;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
