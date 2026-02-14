package modelsResponse;

import java.util.List;

public class Admin_ForgotPasswordResponse {

    private boolean status;
    private List<Object> data;
    
    // Error fields
    private String message;
    private Integer code;
    private String error;
    private String description;

    public Admin_ForgotPasswordResponse() {}

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Admin_ForgotPasswordResponse [status=" + status + ", data=" + data + ", message=" + message + ", code="
				+ code + ", error=" + error + ", description=" + description + "]";
	}
    
    
}
