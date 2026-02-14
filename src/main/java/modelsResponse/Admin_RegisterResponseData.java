package modelsResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Admin_RegisterResponseData {

    private String id;
    private String userId;

    private String firstName;
    private String lastName;
    private String email;

    private String countryENCode;
    private String countryCode;
    private String phone;

    private String companyName;
    private String role;

    @JsonProperty("DOTNumber")
    private String dotNumber;

    private Boolean emailVerified;
    private Boolean phoneVerified;
    private Boolean isActive;
    private Boolean isDeleted;

    private String status;
    private String createdAt;
    private String updatedAt;
    
    private String verificationToken;

    // ---------- Getters & Setters ----------
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCountryENCode() { return countryENCode; }
    public void setCountryENCode(String countryENCode) { this.countryENCode = countryENCode; }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getDotNumber() { return dotNumber; }
    public void setDotNumber(String dotNumber) { this.dotNumber = dotNumber; }

    public Boolean getEmailVerified() { return emailVerified; }
    public void setEmailVerified(Boolean emailVerified) { this.emailVerified = emailVerified; }

    public Boolean getPhoneVerified() { return phoneVerified; }
    public void setPhoneVerified(Boolean phoneVerified) { this.phoneVerified = phoneVerified; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public String getVerificationToken() {
		return verificationToken;
	}
	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}
	@Override
	public String toString() {
		return "Admin_RegisterResponseData [id=" + id + ", userId=" + userId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", countryENCode=" + countryENCode + ", countryCode="
				+ countryCode + ", phone=" + phone + ", companyName=" + companyName + ", role=" + role + ", dotNumber="
				+ dotNumber + ", emailVerified=" + emailVerified + ", phoneVerified=" + phoneVerified + ", isActive="
				+ isActive + ", isDeleted=" + isDeleted + ", status=" + status + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", verificationToken=" + verificationToken + "]";
	}
}
