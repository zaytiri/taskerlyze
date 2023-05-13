package personal.zaytiri.taskerlyze.libraries.sqlquerybuilder.response;

public class Response {

    private boolean success;
    private String message;
    private Object result;
    private String queryExecuted;

    public Response() {
        this.success = true;
        this.message = "Success.";
        this.queryExecuted = "";
        this.result = null;
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

    public <T> Object getResult(Class<T> tClass) {
        return tClass.cast(result);
    }

    public boolean isSuccess() {
        return success;
    }

    public Response setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Response setResult(Object result) {
        this.result = result;
        return this;
    }
}
