package modelsResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Admin_RegisterResponse {

    private Boolean status;
    private String message;
    private String error;
    private String description;
    private Integer code;
    private Admin_RegisterResponseData data;
    
    private String otp; // present in 201 success response

    // ---------- Getters & Setters ----------
    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }

    public Admin_RegisterResponseData getData() { return data; }
    public void setData(Admin_RegisterResponseData data) { this.data = data; }

	public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }

    @Override
    public String toString() {
        return "Admin_RegisterResponse [status=" + status + ", message=" + message +
                ", error=" + error + ", description=" + description +
                ", code=" + code + ", otp=" + otp + ", data=" + data + "]";
    }
}
