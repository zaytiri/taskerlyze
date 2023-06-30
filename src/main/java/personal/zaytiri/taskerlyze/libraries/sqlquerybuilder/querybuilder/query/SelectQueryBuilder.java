package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Table;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectQueryBuilder extends QueryBuilder {
    private final StringBuilder selectQuery;
    private final List<Table> tables;

    public SelectQueryBuilder(Connection connection) {
        super(connection);
        selectQuery = new StringBuilder();
        tables = new ArrayList<>();
    }

    /**
     * Generates a partial SQL query to select data from a given table.
     *
     * @param table to be queried.
     * @returns SelectQueryBuilder
     */
    public SelectQueryBuilder from(Table table) {
        appendSelectArgumentsToQuery(table);
        appendKeyword(Clause.FROM.value);
        addTableToQuery(table);
        return this;
    }

    public SelectQueryBuilder groupBy(List<Column> columns) {
        if (!tryAppendKeyword(Clause.GROUP_BY.value)) {
            return this;
        }
        query.append(getMultipleColumnsNameByComma(columns));
        return this;
    }

    public SelectQueryBuilder join(Table table) {
        appendSelectArgumentsToQuery(table);
        appendKeyword(Clause.JOIN.value);
        addTableToQuery(table);
        return this;
    }

    public SelectQueryBuilder limit(int limit, int offset) {
        limit(limit);
        if (!tryAppendKeyword(Clause.OFFSET.value)) {
            return this;
        }
        query.append(offset);
        return this;
    }

    public SelectQueryBuilder limit(int limit) {
        if (!tryAppendKeyword(Clause.LIMIT.value)) {
            return this;
        }
        query.append(limit);
        return this;
    }

    public SelectQueryBuilder on(Column column1, Column column2) {
        appendKeyword(Clause.ON.value);

        query.append(getColumnName(column1));
        query.append(Operators.EQUALS.value);
        query.append(getColumnName(column2));

        return this;
    }

    public SelectQueryBuilder orderBy(Order order, List<Column> columns) {
        if (!tryAppendKeyword(Clause.ORDER_BY.value)) {
            return this;
        }
        query.append(getMultipleColumnsNameByComma(columns));
        appendKeyword(order.value);
        return this;
    }

    /**
     * Generates a partial SQL query to select given column from a table.
     * It will only select the columns which are contained in a given list.
     * It should be followed up by from() method.
     *
     * @param columns to be selected.
     * @returns SelectQueryBuilder
     */
    public SelectQueryBuilder select(List<Column> columns) {
        tryAppendKeyword(Clause.SELECT.value);
        query.append(getMultipleColumnsNameByComma(columns, true));
        return this;
    }

    /**
     * Generates a partial SQL query to select every column from a table.
     * It should be followed up by from() method.
     *
     * @returns SelectQueryBuilder
     */
    public SelectQueryBuilder select() {
        tryAppendKeyword(Clause.SELECT.value + " *");
        return this;
    }

    @Override
    protected Response executeQuery() throws SQLException {
        List<Map<String, String>> resultsFromDb = new ArrayList<>();

        addSelectArgumentsToQuery();

        PreparedStatement statement = connection.prepareStatement(query.toString());
        setValues(statement);

        ResultSet rs = statement.executeQuery();
        ResultSetMetaData md = rs.getMetaData();

        int numberOfCols = md.getColumnCount();
        int numberOfRows = 0;

        while (rs.next()) {
            Map<String, String> row = new HashMap<>();
            for (int i = 1; i <= numberOfCols; i++) {
                String columnName = md.getColumnName(i);
                String value = rs.getString(columnName);
                row.put(columnName, value);
            }
            resultsFromDb.add(row);
            numberOfRows++;
        }
        statement.close();
        if (closeConnection) {
            connection.close();
        }

        return new Response()
                .setResult(resultsFromDb)
                .setNumberOfRows(numberOfRows);
    }

    @Override
    protected String getColumnName(Column column) {
        StringBuilder col = new StringBuilder();
        String columnName = getTableAbbreviation(column.getTableName()) + "." + column.getName();
        col.append(columnName);
        return col.toString();
    }

    protected String getTableAbbreviation(String tableName) {
//        return tableName.charAt(0) + String.valueOf(tableName.charAt(tableName.length() - 1));
        return tableName;
    }

    protected String getTableName(Table table) {
        return table.getName() + " as " + getTableAbbreviation(table.getName());
    }

    private void addSelectArgumentsToQuery() {
        if (selectQuery.isEmpty())
            return;

        if (tables.size() == 1)
            return;

        if (!query.toString().contains("*"))
            return;

        query.replace(0, query.length(), query.toString().replace("*", selectQuery.toString()));
    }

    private void addTableToQuery(Table table) {
        query.append(getTableName(table));
        tables.add(table);
    }

    private void appendSelectArgumentsToQuery(Table table) {
        if (!query.toString().contains("*"))
            return;

        if (!selectQuery.isEmpty()) {
            selectQuery.append(", ");
        }
        selectQuery.append(getMultipleColumnsNameByComma(table.getColumns(), true));
    }
}
