package personal.zaytiri.taskerlyze.app.api.controllers.result;

public class MessageResult {

    private CodeResult code;
    private String message;

    public MessageResult() {
    }

    public void setCode(CodeResult code) {
        this.code = code;
        getMessageResult();
    }

    public void setCode(CodeResult code, String customMessage) {
        this.code = code;
        this.message= customMessage;
    }

    public CodeResult getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private void getMessageResult(){

        switch (this.code){
            case FOUND:
                this.message = "record found with success.";
                break;
            case CREATED:
                this.message = "record created with success.";
                break;
            case UPDATED:
                this.message = "record updated with success.";
                break;
            case DELETED:
                this.message = "record deleted with success. can be recovered in the trash section.";
                break;
            case NOTFOUND:
                this.message = "record not found.";
                break;
            case NOTCREATED:
                this.message = "record not created.";
                break;
            case NOTUPDATED:
                this.message = "record not updated.";
                break;
        }
    }
}
