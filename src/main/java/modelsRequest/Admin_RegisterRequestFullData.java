package modelsRequest;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class Admin_RegisterRequestFullData extends Admin_RegisterRequestReqData {

    private AddressDetails addressDetails;
    private String image;
    private String verificationToken;
    private Boolean emailVerified;
    private Boolean phoneVerified;
    private String theme;
    private Boolean isActive;
    private String loginToken;
    private ModifiedBy modifiedBy;
    private Integer blockedBy;
    private Integer deletedBy;
    private Boolean isDeleted;

    //default constructor
    public Admin_RegisterRequestFullData() {
        super();
    }
    
 // Constructor to wrap ReqData and keep other optional fields null
    public Admin_RegisterRequestFullData(Admin_RegisterRequestReqData reqData) {
        super(
            reqData.getFirstName(),
            reqData.getLastName(),
            reqData.getEmail(),
            reqData.getCountryENCode(),
            reqData.getCountryCode(),
            reqData.getPhone(),
            reqData.getDotNumber(),
            reqData.getCompanyName(),
            reqData.getPassword(),
            reqData.getRole()
        );
     // Optional fields are null by default
    }
    
    public Admin_RegisterRequestFullData(
            String firstName,
            String lastName,
            String email,
            String countryENCode,
            String countryCode,
            String phone,
            String DOTNumber,
            String companyName,
            String password,
            String role,
            AddressDetails addressDetails,
            String image,
            String verificationToken,
            Boolean emailVerified,
            Boolean phoneVerified,
            String theme,
            Boolean isActive,
            String loginToken,
            ModifiedBy modifiedBy,
            Integer blockedBy,
            Integer deletedBy,
            Boolean isDeleted
    ) {
        super(firstName, lastName, email, countryENCode, countryCode, phone, DOTNumber, companyName, password, role);
        this.addressDetails = addressDetails;
        this.image = image;
        this.verificationToken = verificationToken;
        this.emailVerified = emailVerified;
        this.phoneVerified = phoneVerified;
        this.theme = theme;
        this.isActive = isActive;
        this.loginToken = loginToken;
        this.modifiedBy = modifiedBy;
        this.blockedBy = blockedBy;
        this.deletedBy = deletedBy;
        this.isDeleted = isDeleted;
    }


    // Getters & Setters for all new fields
    public AddressDetails getAddressDetails() { return addressDetails; }
    public void setAddressDetails(AddressDetails addressDetails) { this.addressDetails = addressDetails; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getVerificationToken() { return verificationToken; }
    public void setVerificationToken(String verificationToken) { this.verificationToken = verificationToken; }

    public Boolean getEmailVerified() { return emailVerified; }
    public void setEmailVerified(Boolean emailVerified) { this.emailVerified = emailVerified; }

    public Boolean getPhoneVerified() { return phoneVerified; }
    public void setPhoneVerified(Boolean phoneVerified) { this.phoneVerified = phoneVerified; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public String getLoginToken() { return loginToken; }
    public void setLoginToken(String loginToken) { this.loginToken = loginToken; }

    public ModifiedBy getModifiedBy() { return modifiedBy; }
    public void setModifiedBy(ModifiedBy modifiedBy) { this.modifiedBy = modifiedBy; }

    public Integer getBlockedBy() { return blockedBy; }
    public void setBlockedBy(Integer blockedBy) { this.blockedBy = blockedBy; }

    public Integer getDeletedBy() { return deletedBy; }
    public void setDeletedBy(Integer deletedBy) { this.deletedBy = deletedBy; }

    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }

	@Override
	public String toString() {
		return "FullRegisterAdminRequest [addressDetails=" + addressDetails + ", image=" + image
				+ ", verificationToken=" + verificationToken + ", emailVerified=" + emailVerified + ", phoneVerified="
				+ phoneVerified + ", theme=" + theme + ", isActive=" + isActive + ", loginToken=" + loginToken
				+ ", modifiedBy=" + modifiedBy + ", blockedBy=" + blockedBy + ", deletedBy=" + deletedBy
				+ ", isDeleted=" + isDeleted + "]";
	}
    
    
}
