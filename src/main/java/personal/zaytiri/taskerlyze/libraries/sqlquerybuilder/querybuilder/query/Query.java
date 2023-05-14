package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder.query;

import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

        statement.close();
        if (closeConnection) {
            connection.close();
        }

        return new Response();
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
                resolvedQuery.append(values.get(i));
            }
        }
        return resolvedQuery.toString();
    }
}
