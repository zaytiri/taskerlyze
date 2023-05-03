package personal.zaytiri.taskerlyze.app.api.controllers.result;

public class OperationResult<T> {

    private boolean status;

    private MessageResult messageResult;

    private T result;

    public OperationResult(boolean status, MessageResult message, T result) {
        this.status = status;
        this.messageResult = message;
        this.result = result;
    }

    public OperationResult() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public MessageResult getMessageResult() {
        return messageResult;
    }

    public void setMessageResult(MessageResult messageResult) {
        this.messageResult = messageResult;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
