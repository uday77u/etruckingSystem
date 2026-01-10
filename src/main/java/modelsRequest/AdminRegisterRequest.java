package modelsRequest;

	 public class AdminRegisterRequest {

		    private String firstName;
		    private String lastName;
		    private String email;
		    private String countryENCode;
		    private String countryCode;
		    private String phone;
		    private String DOTNumber;
		    private String companyName;
		    private String password;
		    private AddressDetails addressDetails;
		    private String role;
		    private String image;
		    private String verificationToken;
		    private boolean emailVerified;
		    private boolean phoneVerified;
		    private String theme;
		    private boolean isActive;
		    private String loginToken;
		    private ModifiedBy modifiedBy;
		    private Integer blockedBy;
		    private Integer deletedBy;
		    private boolean isDeleted;

		    // Getters and Setters

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

		    public String getDOTNumber() {
		        return DOTNumber;
		    }

		    public void setDOTNumber(String DOTNumber) {
		        this.DOTNumber = DOTNumber;
		    }

		    public String getCompanyName() {
		        return companyName;
		    }
		    
		    public void setCompanyName(String companyName) {
		        this.companyName = companyName;
		    }

		    public String getPassword() {
		        return password;
		    }
		    
		    public void setPassword(String password) {
		        this.password = password;
		    }

		    public AddressDetails getAddressDetails() {
		        return addressDetails;
		    }

		    public void setAddressDetails(AddressDetails addressDetails) {
		        this.addressDetails = addressDetails;
		    }

		    public String getRole() {
		        return role;
		    }
		    
		    public void setRole(String role) {
		        this.role = role;
		    }

		    public String getImage() {
		        return image;
		    }
		    
		    public void setImage(String image) {
		        this.image = image;
		    }

		    public String getVerificationToken() {
		        return verificationToken;
		    }

		    public void setVerificationToken(String verificationToken) {
		        this.verificationToken = verificationToken;
		    }

		    public boolean isEmailVerified() {
		        return emailVerified;
		    }

		    public void setEmailVerified(boolean emailVerified) {
		        this.emailVerified = emailVerified;
		    }

		    public boolean isPhoneVerified() {
		        return phoneVerified;
		    }

		    public void setPhoneVerified(boolean phoneVerified) {
		        this.phoneVerified = phoneVerified;
		    }

		    public String getTheme() {
		        return theme;
		    }
		    
		    public void setTheme(String theme) {
		        this.theme = theme;
		    }

		    public boolean isActive() {
		        return isActive;
		    }
		    
		    public void setActive(boolean active) {
		        isActive = active;
		    }

		    public String getLoginToken() {
		        return loginToken;
		    }
		    
		    public void setLoginToken(String loginToken) {
		        this.loginToken = loginToken;
		    }

		    public ModifiedBy getModifiedBy() {
		        return modifiedBy;
		    }

		    public void setModifiedBy(ModifiedBy modifiedBy) {
		        this.modifiedBy = modifiedBy;
		    }

		    public Integer getBlockedBy() {
		        return blockedBy;
		    }

		    public void setBlockedBy(Integer blockedBy) {
		        this.blockedBy = blockedBy;
		    }

		    public Integer getDeletedBy() {
		        return deletedBy;
		    }

		    public void setDeletedBy(Integer deletedBy) {
		        this.deletedBy = deletedBy;
		    }

		    public boolean isDeleted() {
		        return isDeleted;
		    }

		    public void setDeleted(boolean deleted) {
		        isDeleted = deleted;
		    }
		}
