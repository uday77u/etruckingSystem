package modelsRequest;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Admin_ResetPasswordRequest {

    private String password;

    public Admin_ResetPasswordRequest() {}

    public Admin_ResetPasswordRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
