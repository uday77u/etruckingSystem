package modelsResponse;

public class Admin_SendVerificationLinkResponse {
	 private Boolean status;
	 private String message; 
	 private Integer code; 
	 private String error; 
	 private String description;
	 public Boolean getStatus() {
		 return status;
	 }
	 public void setStatus(Boolean status) {
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
	 
	 @Override
	 public String toString() {
		return "Admin_SendVerificationLinkResponse [status=" + status + ", message=" + message + ", code=" + code
				+ ", error=" + error + ", description=" + description + "]";
	 }
	 
	 
	 
	
}
