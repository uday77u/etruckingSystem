package modelsRequest;

public class Admin_SendVerificationLinkRequest {
	private String email;

	
	public Admin_SendVerificationLinkRequest(String email) {
		super();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Admin_SendVerificationLink [email=" + email + "]";
	}
	
	
}
