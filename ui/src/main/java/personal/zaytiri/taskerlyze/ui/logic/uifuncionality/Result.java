package personal.zaytiri.taskerlyze.ui.logic.uifuncionality;

public class Result<T> {
    private T result;
    private boolean status;

    public Result(T result) {
        this.result = result;
    }

    public Result() {
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccessful() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
