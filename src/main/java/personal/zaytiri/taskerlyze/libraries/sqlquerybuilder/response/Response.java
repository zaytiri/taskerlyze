package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Response {

    private boolean success;
    private String message;
    private List<Map<String, String>> result;
    private String queryExecuted;
    private int numberOfRows;
    private int lastInsertedId;

    public Response() {
        this.success = true;
        this.message = "Success.";
        this.queryExecuted = "";
        this.result = new ArrayList<>();
        this.numberOfRows = 0;
        this.lastInsertedId = 0;
    }

    /**
     * Gets the last ID that was inserted in the database.
     *
     * @returns the last inserted ID of a row.
     */
    public int getLastInsertedId() {
        return lastInsertedId;
    }

    public Response setLastInsertedId(int lastInsertedId) {
        this.lastInsertedId = lastInsertedId;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Response setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Gets the number of rows available in a SQL result.
     *
     * @returns the number of rows in the result.
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    public Response setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
        return this;
    }

    /**
     * Gets the SQL query executed in plain text and with all unknown parameters resolved.
     *
     * @returns the SQL query executed.
     */
    public String getQueryExecuted() {
        return queryExecuted;
    }

    public Response setQueryExecuted(String queryExecuted) {
        this.queryExecuted = queryExecuted;
        return this;
    }

    /**
     * Gets the result of a SQL query. This result consists of a List of Map object.
     * Each entry of the map corresponds to a column pair, which the key is the column name and the value is the column's value.
     * The list corresponds to all rows while each entry map corresponds to a column in a row.
     *
     * @returns a list of rows where each index will have entries of columns.
     */
    public List<Map<String, String>> getResult() {
        return result;
    }

    public Response setResult(List<Map<String, String>> result) {
        this.result = result;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public Response setSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
