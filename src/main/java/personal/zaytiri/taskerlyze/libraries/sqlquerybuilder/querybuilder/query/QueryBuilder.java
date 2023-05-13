package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.schema.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {

    protected StringBuilder query;
    protected ArrayList<Object> values;
    protected Connection connection;
    protected boolean closeConnection;

    public QueryBuilder(Connection connection) {
        this.query = new StringBuilder();
        this.values = new ArrayList<>();
        this.connection = connection;
    }

    public QueryBuilder and() {
        return appendLogicOperator("and");
    }

    public QueryBuilder between() {
        return appendLogicOperator("between");
    }

    public Response execute() {
        Response response = new Response();

        try {
            response = executeQuery();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.err.println("Executed Query: " + query.toString());

            response.setSuccess(false)
                    .setMessage(e.getMessage());
        }

        return response.setQueryExecuted(query.toString());
    }

    public StringBuilder getQuery() {
        return query;
    }

    public QueryBuilder groupBy(List<Column> columns) {
        query.append(" group by ");
        appendMultipleColumnsNameByComma(columns);
        return this;
    }

    public boolean isCloseConnection() {
        return closeConnection;
    }

    public void setCloseConnection(boolean closeConnection) {
        this.closeConnection = closeConnection;
    }

    public QueryBuilder limit(int limit, int offset) {
        limit(limit);
        query.append(" offset ").append(offset);
        return this;
    }

    public QueryBuilder limit(int limit) {
        query.append(" limit ").append(limit);
        return this;
    }

    public QueryBuilder or() {
        return appendLogicOperator("or");
    }

    public QueryBuilder orderBy(Order order, List<Column> columns) {
        query.append(" order by ");
        appendMultipleColumnsNameByComma(columns);
        query.append(" ").append(order.value);
        return this;
    }

    public QueryBuilder where(Column leftColumn, Operators operator, Object rightColumn) {
        appendKeyword("where");
        query.append(getColumnWithTableAbbreviation(leftColumn)).append(operator.value).append("?");
        values.add(rightColumn);
        return this;
    }

    public QueryBuilder where(Column leftColumn, Operators operator, Column rightColumn) {
        appendKeyword("where");
        query.append(getColumnWithTableAbbreviation(leftColumn)).append(operator.value);
        query.append(getColumnWithTableAbbreviation(rightColumn));
        return this;
    }

    public QueryBuilder where(Column leftColumn, Operators operator) {
        appendKeyword("where");
        query.append(getColumnWithTableAbbreviation(leftColumn)).append(operator.value);
        return this;
    }

    protected void appendMultipleColumnsNameByComma(List<Column> values) {
        boolean first = true;
        for (Column val : values) {
            if (first) {
                query.append(getColumnWithTableAbbreviation(val));
                first = false;
                continue;
            }
            query.append(", ").append(getColumnWithTableAbbreviation(val));
        }
    }

    protected Response executeQuery() throws SQLException {
        PreparedStatement statement = null;

        statement = connection.prepareStatement(query.toString());
        setValues(statement);
        statement.executeUpdate();

        statement.close();
        if (closeConnection) {
            connection.close();
        }

        return new Response();
    }

    protected String getColumnWithTableAbbreviation(Column column) {
        return getTableAbbreviation(column.getTableName()) + "." + column.getName();
    }

    protected String getTableAbbreviation(String tableName) {
        return tableName.charAt(0) + String.valueOf(tableName.charAt(tableName.length() - 1));
    }

    protected void setValues(PreparedStatement preparedStatement) throws SQLException {
        for (int i = 0; i < values.size(); i++) {
            preparedStatement.setObject(i + 1, values.get(i));
        }
    }

    private void appendKeyword(String keyword) {
        if (!query.toString().contains(keyword)) {
            query.append(" ").append(keyword).append(" ");
        }
    }

    private QueryBuilder appendLogicOperator(String operator) {
        query.append(" ").append(operator).append(" ");
        return this;
    }
}
