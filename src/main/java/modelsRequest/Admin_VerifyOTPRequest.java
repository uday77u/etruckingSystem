package modelsRequest;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Admin_VerifyOTPRequest {

    private String otp;
    private Double lat;
    private Double lng;
    private String location;

    public Admin_VerifyOTPRequest() {}

    public Admin_VerifyOTPRequest(String otp, Double lat, Double lng, String location) {
        this.otp = otp;
        this.lat = lat;
        this.lng = lng;
        this.location = location;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
