package modelsRequest;

public class Admin_GenerateOTPRequest {

    private String countryCode;
    private String phone;

    public Admin_GenerateOTPRequest() {}

    public Admin_GenerateOTPRequest(String countryCode, String phone) {
        this.countryCode = countryCode;
        this.phone = phone;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
