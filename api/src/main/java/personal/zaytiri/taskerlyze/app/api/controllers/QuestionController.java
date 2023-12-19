package personal.zaytiri.taskerlyze.app.api.controllers;

import personal.zaytiri.taskerlyze.app.api.controllers.base.Controller;
import personal.zaytiri.taskerlyze.app.api.controllers.result.CodeResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.MessageResult;
import personal.zaytiri.taskerlyze.app.api.controllers.result.OperationResult;
import personal.zaytiri.taskerlyze.app.api.domain.Question;

import java.time.LocalDate;
import java.util.List;

public class QuestionController extends Controller<Question> {
    @Override
    protected Question getEntityInstance(int id) {
        return new Question().getInstance().setId(id);
    }

    @Override
    protected Question getEntityInstance() {
        return new Question().getInstance();
    }
}
