package personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces;

import personal.zaytiri.makeitexplicitlyqueryable.sqlquerybuilder.response.Response;
import personal.zaytiri.taskerlyze.app.api.domain.Question;

import java.time.LocalDate;

public interface IQuestionRepository extends IRepository<Question> {
    Response exists(Question task);

    Response getQuestionsByCategoryAndAnsweredAtDate(int categoryId, LocalDate date);
}
