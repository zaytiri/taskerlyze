package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;

import java.util.List;

public class GenericClauses {

    private final QueryBuilder builder;
    private int numberOfConditionsForClause = 0;
    private int definedNumberOfConditionsForClause = 0;

    public GenericClauses(QueryBuilder builder) {
        this.builder = builder;
    }

    public void and(int numberOfConditions) {
        builder.appendKeyword(Clause.AND.value);
        openNestedConditions(numberOfConditions);
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

    public void or(int numberOfConditions) {
        builder.appendKeyword(Clause.OR.value);
        openNestedConditions(numberOfConditions);
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

        closeNestedConditions();
    }

    public void where(StringBuilder query, Column leftColumn, Operators operator, Column rightColumn) {
        builder.tryAppendKeyword(Clause.WHERE.value);
        query.append(builder.getColumnName(leftColumn));
        builder.appendKeyword(operator.value);
        query.append(builder.getColumnName(rightColumn));

        closeNestedConditions();
    }

    public void where(StringBuilder query, Column leftColumn, Operators operator) {
        builder.tryAppendKeyword(Clause.WHERE.value);
        query.append(builder.getColumnName(leftColumn));
        builder.appendKeyword(operator.value);

        closeNestedConditions();
    }

    public void where(StringBuilder query, Column leftColumn) {
        builder.tryAppendKeyword(Clause.WHERE.value);
        query.append(builder.getColumnName(leftColumn));

        closeNestedConditions();
    }

    private void closeNestedConditions() {
        if (definedNumberOfConditionsForClause <= 1) {
            return;
        }
        numberOfConditionsForClause++;
        if (numberOfConditionsForClause == definedNumberOfConditionsForClause) {
            builder.query.append(") ");
        }
    }

    private void openNestedConditions(int numberOfConditions) {
        if (numberOfConditions <= 1) {
            return;
        }
        builder.query.append(" (");
        definedNumberOfConditionsForClause = numberOfConditions;
    }
}
