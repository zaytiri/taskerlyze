package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;

import java.sql.Connection;

public class DeleteQueryBuilder extends QueryBuilder {

    public DeleteQueryBuilder(Connection connection) {
        super(connection);
    }

    /**
     * Generates an SQL query to delete data from a given table only needing the table's name.
     * This method could be used alone (without a subsequent where clause) but doing so will delete everything from the given table.
     *
     * @param table to delete data.
     * @returns DeleteQueryBuilder.
     */
    public DeleteQueryBuilder deleteFrom(Table table) {
        tryAppendKeyword(Clause.DELETE.value);
        tryAppendKeyword(Clause.FROM.value);
        query.append(table.getName());
        return this;
    }
}
