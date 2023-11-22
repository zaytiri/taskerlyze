package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;

import java.sql.Connection;

public class DeleteQueryBuilder extends QueryBuilder implements IGenericClauses<DeleteQueryBuilder> {
    private final GenericClauses genericClauses;

    public DeleteQueryBuilder(Connection connection) {
        super(connection);
        genericClauses = new GenericClauses(this);
    }

    @Override
    public DeleteQueryBuilder and(int numberOfConditions) {
        genericClauses.and(numberOfConditions);
        return this;
    }

    @Override
    public DeleteQueryBuilder and() {
        genericClauses.and(0);
        return this;
    }

    @Override
    public DeleteQueryBuilder and(Object value) {
        genericClauses.and(query, values, value);
        return this;
    }

    @Override
    public DeleteQueryBuilder between(Object value) {
        genericClauses.between(query, values, value);
        return this;
    }

    @Override
    public DeleteQueryBuilder or(int numberOfConditions) {
        genericClauses.or(numberOfConditions);
        return this;
    }

    @Override
    public DeleteQueryBuilder or() {
        genericClauses.or(0);
        return this;
    }

    @Override
    public DeleteQueryBuilder where(Column leftColumn, Operators operator, Object rightColumn) {
        genericClauses.where(query, values, leftColumn, operator, rightColumn);
        return this;
    }

    @Override
    public DeleteQueryBuilder where(Column leftColumn, Operators operator, Column rightColumn) {
        genericClauses.where(query, leftColumn, operator, rightColumn);
        return this;
    }

    @Override
    public DeleteQueryBuilder where(Column leftColumn, Operators operator) {
        genericClauses.where(query, leftColumn, operator);
        return this;
    }

    @Override
    public DeleteQueryBuilder where(Column leftColumn) {
        genericClauses.where(query, leftColumn);
        return this;
    }

    /**
     * Generates an SQL query to delete data from a given table only needing the table's name.
     * This method could be used alone (without a subsequent where clause) but doing so will delete everything from the given table.
     *
     * @param table to delete data.
     * @returns DeleteQueryBuilder.
     */
    public DeleteQueryBuilder deleteFrom(Table table) {
        resetQuery();
        tryAppendKeyword(Clause.DELETE.value);
        tryAppendKeyword(Clause.FROM.value);
        query.append(table.getName());
        return this;
    }
}
