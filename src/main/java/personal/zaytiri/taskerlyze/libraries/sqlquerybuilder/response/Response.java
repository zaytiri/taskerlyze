package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Response {

    private boolean success;
    private String message;
    private List<Map<String, String>> result;
    private String queryExecuted;

    public Response() {
        this.success = true;
        this.message = "Success.";
        this.queryExecuted = "";
        this.result = new ArrayList<>();
    }

    public String getMessage() {
        return message;
    }

    public Response setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getQueryExecuted() {
        return queryExecuted;
    }

    public Response setQueryExecuted(String queryExecuted) {
        this.queryExecuted = queryExecuted;
        return this;
    }

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
