package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;

import java.util.List;

public class GenericClauses {

    private final QueryBuilder builder;

    public GenericClauses(QueryBuilder builder) {
        this.builder = builder;
    }

    public void and() {
        builder.appendKeyword(Clause.AND.value);
    }

    public void and(StringBuilder query, List<Object> values, Object value) {
        builder.appendKeyword(Clause.AND.value);
        query.append(" ?");
        values.add(value);
    }

    public void between(StringBuilder query, List<Object> values, Object value) {
        builder.appendKeyword(Operators.BETWEEN.value);
        query.append("?");
        values.add(value);
    }


    public void or() {
        builder.appendKeyword(Clause.OR.value);
    }


    public void where(StringBuilder query, List<Object> values, Column leftColumn, Operators operator, Object rightColumn) {
        builder.tryAppendKeyword(Clause.WHERE.value);
        query.append(builder.getColumnName(leftColumn));
        builder.appendKeyword(operator.value);
        query.append("?");

        if (operator.equals(Operators.LIKE)) {
            rightColumn = "%" + rightColumn + "%";
        }

        values.add(rightColumn);
    }

    public void where(StringBuilder query, Column leftColumn, Operators operator, Column rightColumn) {
        builder.tryAppendKeyword(Clause.WHERE.value);
        query.append(builder.getColumnName(leftColumn));
        builder.appendKeyword(operator.value);
        query.append(builder.getColumnName(rightColumn));
    }

    public void where(StringBuilder query, Column leftColumn, Operators operator) {
        builder.tryAppendKeyword(Clause.WHERE.value);
        query.append(builder.getColumnName(leftColumn));
        builder.appendKeyword(operator.value);
    }

    public void where(StringBuilder query, Column leftColumn) {
        builder.tryAppendKeyword(Clause.WHERE.value);
        query.append(builder.getColumnName(leftColumn));
    }
}
