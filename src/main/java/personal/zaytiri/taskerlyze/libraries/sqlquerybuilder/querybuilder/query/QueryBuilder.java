package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;

import java.sql.Connection;
import java.util.List;

public class QueryBuilder extends Query {
    public QueryBuilder(Connection connection) {
        super(connection);
    }

    public QueryBuilder and() {
        return appendKeyword(Clause.AND.value);
    }

    public QueryBuilder and(Object value) {
        appendKeyword(Clause.AND.value);
        query.append(" ?");
        values.add(value);
        return this;
    }

    public QueryBuilder between(Object value) {
        appendKeyword(Operators.BETWEEN.value);
        query.append("?");
        values.add(value);
        return this;
    }


    public QueryBuilder or() {
        return appendKeyword(Clause.OR.value);
    }


    public QueryBuilder where(Column leftColumn, Operators operator, Object rightColumn) {
        tryAppendKeyword(Clause.WHERE.value);
        query.append(getColumnName(leftColumn));
        appendKeyword(operator.value);
        query.append("?");
        values.add(rightColumn);
        return this;
    }

    public QueryBuilder where(Column leftColumn, Operators operator, Column rightColumn) {
        tryAppendKeyword(Clause.WHERE.value);
        query.append(getColumnName(leftColumn));
        appendKeyword(operator.value);
        query.append(getColumnName(rightColumn));
        return this;
    }

    public QueryBuilder where(Column leftColumn, Operators operator) {
        tryAppendKeyword(Clause.WHERE.value);
        query.append(getColumnName(leftColumn));
        appendKeyword(operator.value);
        return this;
    }

    public QueryBuilder where(Column leftColumn) {
        tryAppendKeyword(Clause.WHERE.value);
        query.append(getColumnName(leftColumn));
        return this;
    }

    protected QueryBuilder appendKeyword(String keyword) {
        query.append(" ").append(keyword).append(" ");
        return this;
    }

    protected String getColumnName(Column column) {
        return column.getName();
    }

    protected String getMultipleColumnsNameByComma(List<Column> values) {
        return appendColumnsByComma(values, false);
    }

    protected String getMultipleColumnsNameByComma(List<Column> values, boolean as) {
        return appendColumnsByComma(values, as);
    }

    protected boolean tryAppendKeyword(String keyword) {
        if (!query.toString().contains(keyword)) {
            appendKeyword(keyword);
            return true;
        }
        return false;
    }

    private String appendColumnsByComma(List<Column> values, boolean as) {
        StringBuilder columns = new StringBuilder();

        boolean comma = false;
        for (Column val : values) {
            if (comma) {
                columns.append(", ");
            }
            String columnName = getColumnName(val);
            columns.append(columnName);

            if (as) {
                String columnWithAbbrModified = columnName.replace(".", "__");
                columns.append(" as ").append(columnWithAbbrModified);
            }

            comma = true;
        }
        return columns.toString();
    }
}
