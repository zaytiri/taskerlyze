package personal.zaytiri.taskerlyze.app.api.controllers.result;

public class MessageResult {

    private CodeResult code;
    private String message;

    public MessageResult() {
    }

    public CodeResult getCode() {
        return code;
    }

    public void setCode(CodeResult code) {
        this.code = code;
        getMessageResult();
    }

    public String getMessage() {
        return message;
    }

    public void setCode(CodeResult code, String customMessage) {
        this.code = code;
        this.message = customMessage;
    }

    private void getMessageResult() {

        switch (this.code) {
            case FOUND -> this.message = "Entity found with success.";
            case CREATED -> this.message = "New entity created with success.";
            case UPDATED -> this.message = "Entity updated with success.";
            case DELETED -> this.message = "Entity deleted with success. Can be recovered in the trash section.";
            case NOT_FOUND -> this.message = "Entity not found.";
            case NOT_CREATED -> this.message = "Entity not created.";
            case NOT_UPDATED -> this.message = "Entity not updated.";
            case NOT_DELETED -> this.message = "Entity not deleted.";
            case SUCCESS -> this.message = "The operation was successful.";
            case FAIL -> this.message = "The operation failed.";
        }
    }
}
