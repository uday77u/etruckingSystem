package modelsResponse;

import java.util.List;

public class Admin_ResetPasswordResponse {

    private Boolean status;
    private List<Object> data;
    private String message;
    private Integer code;
    private String error;
    private String description;

    public Boolean getStatus() {
        return status;
    }

    public List<Object> getData() {
        return data;
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
