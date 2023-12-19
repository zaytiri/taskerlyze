package personal.zaytiri.taskerlyze.app.persistence.repositories;

import jakarta.inject.Inject;
import personal.zaytiri.taskerlyze.app.api.domain.Question;
import personal.zaytiri.taskerlyze.app.persistence.mappers.QuestionMapper;
import personal.zaytiri.taskerlyze.app.persistence.models.QuestionModel;
import personal.zaytiri.taskerlyze.app.persistence.repositories.base.Repository;
import personal.zaytiri.taskerlyze.app.persistence.repositories.interfaces.IQuestionRepository;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query.Operators;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query.SelectQueryBuilder;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepository extends Repository<Question, QuestionModel, QuestionMapper> implements IQuestionRepository {

    @Inject
    public QuestionRepository() {
        super(new QuestionModel(), new QuestionMapper());
    }

    @Override
    public Response exists(Question task) {
        model = mapper.toModel(task);

        SelectQueryBuilder query = new SelectQueryBuilder(connection.open());

        Column name = model.getTable().getColumn("question");
        List<Column> columns = new ArrayList<>();
        columns.add(name);

        query.select(columns)
                .from(model.getTable())
                .where(name, Operators.EQUALS, model.getName());

        return query.execute();
    }
}
