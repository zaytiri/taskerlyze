package personal.zaytiri.taskerlyze.ui.logic.loaders;

import personal.zaytiri.taskerlyze.app.api.controllers.QuestionController;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Question;
import personal.zaytiri.taskerlyze.libraries.pairs.Pair;
import personal.zaytiri.taskerlyze.ui.logic.entities.QuestionEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QuestionLoader implements Loader<QuestionEntity>, Findable<Pair<Integer, String>> {
    private int categoryId;
    private LocalDate date;

    @Override
    public List<Pair<Integer, String>> find(String subString) {
        OperationResult<List<Question>> result = new QuestionController().findNameBySubString(subString);
        List<Pair<Integer, String>> questionsToBeReturned = new ArrayList<>();
        for (Question question : result.getResult()) {
            questionsToBeReturned.add(new Pair<>(question.getId(), question.getName()));
        }
        return questionsToBeReturned;
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

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
