package modelsRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VerifyUserRequestData {
	private String firstName;
	private String lastName; 
	private String email; // user@example.com
	private String countryENCode; 
	private String countryCode; 
	private String phone; 
	
	@JsonProperty("DOTNumber")
	private String dotNumber;
	
	
	public VerifyUserRequestData(String firstName, String lastName, String email, String countryENCode,
			String countryCode, String phone, String DOTNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.countryENCode = countryENCode;
		this.countryCode = countryCode;
		this.phone = phone;
		
		this.dotNumber = DOTNumber;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountryENCode() {
		return countryENCode;
	}

	public void setCountryENCode(String countryENCode) {
		this.countryENCode = countryENCode;
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

	public String getDotNumber() {
		return dotNumber;
	}

	public void setDotNumber(String dotNumber) {
		this.dotNumber = dotNumber;
	}

	@Override
	public String toString() {
		return "VerifyUserRequestData [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", countryENCode=" + countryENCode + ", countryCode=" + countryCode + ", phone=" + phone
				+ ", dotNumber=" + dotNumber + "]";
	}





	 

}
