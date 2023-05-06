package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.querybuilder;

import personal.zaytiri.taskerlyze.app.persistence.DbConnection;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.Column;
import personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.Table;
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

    public StringBuilder getQuery() {
        return query;
    }

    public QueryBuilder() {
        this.query = new StringBuilder();
        this.values = new ArrayList<>();
        this.connection = DbConnection.getInstance().open();
    }

    public QueryBuilder where(Column leftColumn, Operators operator, Object rightColumn){
        if (!query.toString().contains("where")){
            query.append(" where ");
        }
        query.append(leftColumn.getName()).append(operator.value).append("?");
        values.add(rightColumn);
        return this;
    }

    public QueryBuilder where(Column leftColumn, Operators operator){
        if (!query.toString().contains("where")){
            query.append(" where ");
        }
        query.append(leftColumn.getName()).append(operator.value);
        return this;
    }

    public QueryBuilder orderBy(Order order, List<Column> columns){
        query.append(" order by ");
        appendMultipleColumnsNameByComma(columns);
        query.append(" ").append(order.value);
        return this;
    }

    public QueryBuilder groupBy(List<Column> columns){
        query.append(" group by ");
        appendMultipleColumnsNameByComma(columns);
        return this;
    }

    public QueryBuilder limit(int limit, int offset){
        limit(limit);
        query.append(" offset ").append(offset);
        return this;
    }

    public QueryBuilder limit(int limit){
        query.append(" limit ").append(limit);
        return this;
    }

    private QueryBuilder appendLogicOperator(String operator){
        query.append(" ").append(operator).append(" ");
        return this;
    }

    public QueryBuilder and(){
        return appendLogicOperator("and");
    }

    public QueryBuilder or(){
        return appendLogicOperator("or");
    }

    public QueryBuilder between(){
        return appendLogicOperator("between");
    }

    protected void setValues(PreparedStatement preparedStatement) throws SQLException {
        for (int i = 0; i < values.size(); i++) {
            preparedStatement.setObject(i + 1, values.get(i));
        }
    }

    public Response execute(){
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

    protected Response executeQuery() throws SQLException {
        PreparedStatement statement = null;

        statement = connection.prepareStatement(query.toString());
        setValues(statement);
        statement.executeUpdate();

        connection.close();

        return new Response();
    }

    protected void appendMultipleColumnsNameByComma(List<Column> values) {
        boolean first = true;
        for (Column val : values) {
            if(first){
                query.append(val.getName());
                first = false;
                continue;
            }
            query.append(", ").append(val.getName());
        }
    }

    protected String getTableAbbreviation(Table table){
        String tableName = table.getName();
        return tableName.charAt(0) + String.valueOf(tableName.charAt(tableName.length() - 1));
    }
}
