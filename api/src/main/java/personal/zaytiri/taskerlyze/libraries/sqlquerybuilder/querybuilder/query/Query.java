package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.sql.*;
import java.util.ArrayList;

public class Query {
    protected Connection connection;
    protected boolean closeConnection;
    protected StringBuilder query;
    protected ArrayList<Object> values;

    public Query(Connection connection) {
        this.connection = connection;
        closeConnection = true;
        this.query = new StringBuilder();
        this.values = new ArrayList<>();
    }

    public Response execute() {
        Response response = new Response();

        try {
            response = executeQuery();

        } catch (SQLException e) {
            System.err.println(e.getMessage());

            response.setSuccess(false)
                    .setMessage(e.getMessage());
        }

        return response.setQueryExecuted(resolveQueryParameters());
    }

    public String getQuery() {
        return query.toString();
    }

    public boolean isCloseConnection() {
        return closeConnection;
    }

    public void setCloseConnection(boolean closeConnection) {
        this.closeConnection = closeConnection;
    }

    protected Response executeQuery() throws SQLException {
        PreparedStatement statement = null;

        statement = connection.prepareStatement(query.toString());
        setValues(statement);
        statement.executeUpdate();

        // get last inserted id from database
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()");
        int id = rs.getInt("last_insert_rowid()");
        stmt.close();

        statement.close();
        if (closeConnection) {
            connection.close();
        }

        return new Response().setLastInsertedId(id);
    }

    protected void resetQuery() {
        this.query = new StringBuilder();
        this.values = new ArrayList<>();
    }

    protected void setValues(PreparedStatement preparedStatement) throws SQLException {
        for (int i = 0; i < values.size(); i++) {
            preparedStatement.setObject(i + 1, values.get(i));
        }
    }

    private String resolveQueryParameters() {
        String[] splittedQueryBy = query.toString().split("\\?");

        StringBuilder resolvedQuery = new StringBuilder();
        for (int i = 0; i < splittedQueryBy.length; i++) {
            resolvedQuery.append(splittedQueryBy[i]);
            if (i < values.size()) {
                Object value = values.get(i);
                if (value instanceof java.util.Date) {
                    value = ((java.util.Date) value).getTime();
                }
                resolvedQuery.append(value);
            }
        }
        return resolvedQuery.toString();
    }
}
