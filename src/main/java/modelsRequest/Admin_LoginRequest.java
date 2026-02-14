package modelsRequest;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Admin_LoginRequest {

    private String email;
    private String password;
    private Boolean updateToken;
    private String timezone;
    private Double lat;
    private Double lng;
    private String location;

    public Admin_LoginRequest() {}

    public Admin_LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Admin_LoginRequest(String email, String password, Boolean updateToken,
                              String timezone, Double lat, Double lng, String location) {
        this.email = email;
        this.password = password;
        this.updateToken = updateToken;
        this.timezone = timezone;
        this.lat = lat;
        this.lng = lng;
        this.location = location;
    }

    // Getters & Setters

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Boolean getUpdateToken() { return updateToken; }
    public void setUpdateToken(Boolean updateToken) { this.updateToken = updateToken; }

    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }

    public Double getLat() { return lat; }
    public void setLat(Double lat) { this.lat = lat; }

    public Double getLng() { return lng; }
    public void setLng(Double lng) { this.lng = lng; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
