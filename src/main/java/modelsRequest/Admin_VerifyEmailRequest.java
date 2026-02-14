package modelsRequest;

public class Admin_VerifyEmailRequest {

    private String token;

    // Constructor
    public Admin_VerifyEmailRequest(String token) {
        this.token = token;
    }

    // Getter
    public String getToken() {
        return token;
    }

    // Setter
    public void setToken(String token) {
        this.token = token;
    }
}
