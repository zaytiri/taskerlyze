package personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces;

import personal.zaytiri.taskerlyze.app.api.domain.Question;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.time.LocalDate;

public interface IQuestionRepository extends IRepository<Question> {
    Response exists(Question task);

}
