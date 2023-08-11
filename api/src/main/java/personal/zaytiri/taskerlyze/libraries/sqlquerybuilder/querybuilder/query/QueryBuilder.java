package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;

import java.sql.Connection;
import java.util.List;

public class QueryBuilder extends Query {
    public QueryBuilder(Connection connection) {
        super(connection);
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
