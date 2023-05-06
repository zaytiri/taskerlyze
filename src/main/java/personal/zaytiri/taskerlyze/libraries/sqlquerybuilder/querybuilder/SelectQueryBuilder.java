package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.Table;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectQueryBuilder extends QueryBuilder {

    private List<Table> tables;
    private List<Map<String, String>> results;

    public List<Map<String, String>> getResults() {
        return results;
    }

    public SelectQueryBuilder(Connection connection) {
        super(connection);
    }

    public SelectQueryBuilder select(List<Column> columns){
        query.append("select ");
        appendMultipleColumnsNameByComma(columns);
        query.append(" ");
        return this;
    }

    public SelectQueryBuilder select(){
        query.append("select * ");
        return this;
    }

    public SelectQueryBuilder from(Table table){
        addTableToQuery("from", table);
        return this;
    }

    public SelectQueryBuilder join(Table table){
        addTableToQuery("join", table);
        return this;
    }

    private void addTableToQuery(String clause, Table table){
        query.append(" ").append(clause).append(" ");
        query.append(table.getName()).append(" as ").append(getTableAbbreviation(table.getName()));
        tables.add(table);
    }

    public SelectQueryBuilder on(Column column1, Column column2){
        query.append(" on ");

        query.append(getColumnWithTableAbbreviation(column1));
        query.append("=");
        query.append(getColumnWithTableAbbreviation(column2));

        return this;
    }

    @Override
    public Response executeQuery() throws SQLException {
        List<Map<String, String>> resultsFromDb = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(query.toString());
        setValues(statement);

        ResultSet rs = statement.executeQuery();
        ResultSetMetaData md = rs.getMetaData();

        int numberOfCols = md.getColumnCount();

        while(rs.next())
        {
            Map<String, String> row = new HashMap<>();
            for (int i = 1; i <= numberOfCols; i++){
                String columnName = md.getColumnName(i);
                String value = rs.getString(columnName);
                row.put(columnName, value);
            }
            resultsFromDb.add(row);
        }
        statement.close();
        connection.close();

        results = resultsFromDb;

        return new Response().setResult(results);
    }

}
