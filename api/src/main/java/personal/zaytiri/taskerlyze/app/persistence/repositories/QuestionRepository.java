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

        Column categoryId = model.getTable().getColumn("category_id");

        Column name = model.getTable().getColumn("question");
        List<Column> columnsToSelect = new ArrayList<>();
        columnsToSelect.add(name);

        query.select(columnsToSelect)
                .from(model.getTable())
                .where(name, Operators.EQUALS, model.getName())
                .and()
                .where(categoryId, Operators.EQUALS, model.getCategoryId());

        return query.execute();
    }

    @Override
    public Response getQuestionsByCategoryAndAnsweredAtDate(int categoryId, LocalDate date) {
        model = new QuestionModel();

        SelectQueryBuilder query = new SelectQueryBuilder(connection.open());

        Table questions = model.getTable();

        query.select()
                .from(questions)
                .where(questions.getColumn("category_id"), Operators.EQUALS, categoryId);

        if (date.isEqual(LocalDate.now())) {
            query.and(2)
                    .where(questions.getColumn("is_answered"), Operators.EQUALS, false)
                    .or()
                    .where(questions.getColumn("answered_at"), Operators.EQUALS, date);
        } else {
            query.and()
                    .where(questions.getColumn("answered_at"), Operators.EQUALS, date);
        }

        return query.execute();
    }
}
