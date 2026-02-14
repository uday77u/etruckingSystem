package modelsResponse;

public class CommonErrorResponse {

    private Boolean status;
    private String message;
    private Integer code;
    private String error;
    private String description;

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }
}
