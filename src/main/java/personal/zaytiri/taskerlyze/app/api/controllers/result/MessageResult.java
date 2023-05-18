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
            case FOUND -> this.message = "record found with success.";
            case CREATED -> this.message = "record created with success.";
            case UPDATED -> this.message = "record updated with success.";
            case DELETED -> this.message = "record deleted with success. can be recovered in the trash section.";
            case NOT_FOUND -> this.message = "record not found.";
            case NOT_CREATED -> this.message = "record not created.";
            case NOT_UPDATED -> this.message = "record not updated.";
            case NOT_DELETED -> this.message = "record not deleted.";
        }
    }
}
