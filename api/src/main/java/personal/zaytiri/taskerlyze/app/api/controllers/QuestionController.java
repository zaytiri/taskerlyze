package personal.zaytiri.taskerlyze.app.api.controllers;

import personal.zaytiri.taskerlyze.app.api.controllers.base.Controller;
import personal.zaytiri.taskerlyze.app.api.controllers.result.CodeResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.MessageResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Question;

import java.time.LocalDate;
import java.util.List;

public class QuestionController extends Controller<Question> {
    /**
     * Gets all questions associated to a category.
     *
     * @param categoryId which is the identifier for a specific category.
     * @return OperationResult<Question>
     */
    public OperationResult<List<Question>> getQuestionsByCategoryAndAnsweredAtDate(int categoryId, LocalDate date) {
        Question question = getEntityInstance();
        question.setCategoryId(categoryId);
        question.setAnsweredAt(date);

        List<Question> tasks = question.getQuestionsByCategoryAndAnsweredAtDate();

        MessageResult message = new MessageResult();
        if (tasks.isEmpty()) {
            message.setCode(CodeResult.NOT_FOUND);
        } else {
            message.setCode(CodeResult.FOUND);
        }

        return new OperationResult<>(!tasks.isEmpty(), message, tasks);
    }
    @Override
    protected Question getEntityInstance(int id) {
        return new Question().getInstance().setId(id);
    }

    @Override
    protected Question getEntityInstance() {
        return new Question().getInstance();
    }
}
