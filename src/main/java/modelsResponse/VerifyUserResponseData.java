package modelsResponse;

public class VerifyUserResponseData {
	private boolean status;
	private String message;
	private Integer code;
	private String error;
	private String description;
	private String data;
	
	public VerifyUserResponseData() {
		
	}
	public VerifyUserResponseData(boolean status, String message, int code, String error, String description) {
		super();
		this.status = status;
		this.message = message;
		this.code = code;
		this.error = error;
		this.description = description;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
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
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "VerifyUserResponseData [status=" + status + ", message=" + message + ", code=" + code + ", error="
				+ error + ", description=" + description + "]";
	}
	
}