package modelsRequest;

public class Admin_ForgotPasswordRequest {

    private String email;
    private String countryCode;
    private String phone;

    public Admin_ForgotPasswordRequest() {}

    public Admin_ForgotPasswordRequest(String email, String countryCode, String phone) {
        this.email = email;
        this.countryCode = countryCode;
        this.phone = phone;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
