package modelsRequest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Admin_RegisterRequestReqDataRemoveNullFields {

    private String firstName;
    private String lastName;
    private String email;
    private String countryENCode;
    private String countryCode;
    private String phone;
    
    @JsonProperty("DOTNumber")
    private String dotNumber;  // Ensures JSON key is "DOTNumber"
    
    private String companyName;
    private String password;
    private String role;

    // ---------------- Constructor ----------------
    public Admin_RegisterRequestReqDataRemoveNullFields(
            String firstName,
            String lastName,
            String email,
            String countryENCode,
            String countryCode,
            String phone,
            String dotNumber,
            String password,
            String role) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.countryENCode = countryENCode;
        this.countryCode = countryCode;
        this.phone = phone;
        this.dotNumber = dotNumber;
        this.password = password;
        this.role = role;
    }

    // ---------------- Getters & Setters ----------------
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

    public String getDotNumber() { return dotNumber; }
    public void setDotNumber(String dotNumber) { this.dotNumber = dotNumber; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

	@Override
	public String toString() {
		return "RegisterAdminRequestRemoveNullFields [firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", countryENCode=" + countryENCode + ", countryCode=" + countryCode + ", phone=" + phone
				+ ", dotNumber=" + dotNumber + ", companyName=" + companyName + ", password=" + password + ", role="
				+ role + "]";
	}


    
    
}
