package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;

import java.sql.Connection;

public class DeleteQueryBuilder extends QueryBuilder {

    public DeleteQueryBuilder(Connection connection) {
        super(connection);
    }

    public DeleteQueryBuilder deleteFrom(Table table) {
        tryAppendKeyword(Clause.DELETE.value);
        tryAppendKeyword(Clause.FROM.value);
        query.append(table.getName());
        return this;
    }
}
