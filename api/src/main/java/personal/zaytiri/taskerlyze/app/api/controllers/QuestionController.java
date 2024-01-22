package personal.zaytiri.taskerlyze.app.api.controllers;

import personal.zaytiri.taskerlyze.app.api.controllers.base.ControllerFindable;
import personal.zaytiri.taskerlyze.app.api.controllers.result.CodeResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.MessageResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Question;

import java.time.LocalDate;
import java.util.List;

public class QuestionController extends ControllerFindable<Question> {
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

    /**
     * Sets the status of a task to a given boolean with a specific given id.
     *
     * @param id     to set the status
     * @param isDone which is the boolean representing if a task is done or not
     * @return OperationResult<Task>
     */
    public OperationResult<Question> setAnswered(int id, boolean isDone) {
        Question question = getEntityInstance();
        question.setId(id);

        boolean isQuestionUpdated = question.setQuestionStatus(isDone);
        question = question.get();

        MessageResult message = new MessageResult();
        if (isQuestionUpdated) {
            message.setCode(CodeResult.SUCCESS);
        } else {
            message.setCode(CodeResult.FAIL);
        }

        return new OperationResult<>(isQuestionUpdated, message, question);
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
