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

    @Override
    public Response executeQuery() throws SQLException {
        List<Map<String, String>> resultsFromDb = new ArrayList<>();

        addSelectArgumentsToQuery();

        PreparedStatement statement = connection.prepareStatement(query.toString());
        setValues(statement);

        ResultSet rs = statement.executeQuery();
        ResultSetMetaData md = rs.getMetaData();

        int numberOfCols = md.getColumnCount();

        while (rs.next()) {
            Map<String, String> row = new HashMap<>();
            for (int i = 1; i <= numberOfCols; i++) {
                String columnName = md.getColumnName(i);
                String value = rs.getString(columnName);
                row.put(columnName, value);
            }
            resultsFromDb.add(row);
        }
        statement.close();
        connection.close();

        return new Response().setResult(resultsFromDb);
    }

    public SelectQueryBuilder from(Table table) {
        appendSelectArgumentsToQuery(table);
        addTableToQuery("from", table);
        return this;
    }

    public SelectQueryBuilder join(Table table) {
        appendSelectArgumentsToQuery(table);
        addTableToQuery("join", table);
        return this;
    }

    public SelectQueryBuilder joinByIds(Table table) {
        appendSelectArgumentsToQuery(table);
        addTableToQuery("join", table);

        // todo: join by primary keys or maybe add a foreign key property into the xml file to signify an association between two columns of different tables.

        //  on(column1, column2);
        return this;
    }

    public SelectQueryBuilder on(Column column1, Column column2) {
        query.append(" on ");

        query.append(getColumnWithTableAbbreviation(column1));
        query.append("=");
        query.append(getColumnWithTableAbbreviation(column2));

        return this;
    }

    public SelectQueryBuilder select(List<Column> columns) {
        query.append("select ");
        query.append(getMultipleColumnsNameByComma(columns));
        return this;
    }

    public SelectQueryBuilder select() {
        query.append("select *");
        return this;
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

    private void addTableToQuery(String clause, Table table) {
        query.append(" ").append(clause).append(" ");
        query.append(table.getName()).append(" as ").append(getTableAbbreviation(table.getName()));
        tables.add(table);
        // todo: it's necessary to keep track of all existent abbreviations so none is the same. if a abbr already exists then it must be found a different rule to get the abbr.
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
