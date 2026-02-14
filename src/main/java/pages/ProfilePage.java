package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.WebDriverUtility;



public class ProfilePage extends BasePage{
	WebDriverUtility utility;
	public ProfilePage(WebDriver driver) {
		super(driver);
		utility=new WebDriverUtility(driver);

	}
	
	
	//-------------------------Locators---------------------------------------------
	@FindBy(xpath = "//input[@type='file']") private WebElement uploadProfilePhotoButton;
	@FindBy(xpath = "//p[contains(text(),'uploaded')]") private WebElement uploadProfilePhotoSuccessMessage;
	@FindBy(xpath = "//button[text()='Remove']") private WebElement removeProfilePhotoButton;
	@FindBy(xpath = "//button[text()='Confirm']") private WebElement removeProfilePhotoConfirmButton;
	@FindBy(xpath = "//p[contains(text(),'removed')]") private WebElement removeProfilePhotoSuccessMessage;
	
	@FindBy(xpath = "//img[@id='image']") private WebElement ProfileImage;
	@FindBy(xpath = "//img[contains(@class,'cover')]") private WebElement ProfilePhotoMaximizeDisplayed;
	
	@FindBy(xpath = "//span[text()='Dashboard']") private WebElement DashboardMenu;
	
	
	//By toastUploadMsg = By.xpath("//p[contains(text(),'uploaded')]");
	//By toastRemoveMsg =By.xpath("//button[text()='Remove']");

	
	//-------------------------Methods-----------------------------------------------
	public void clickUploadeProfilePhoto(String filePath) {
		uploadProfilePhotoButton.sendKeys(filePath);
		
	}
	
	public Boolean isUploadProfilePhotoSuccessMessage() {
		return uploadProfilePhotoSuccessMessage.isDisplayed();
	}
	
	public String getUploadProfilePhotoSuccessMessage() {
		return uploadProfilePhotoSuccessMessage.getText();
		
	}
	
	public String getRemoveProfilePhotoSuccessMessage() {
		return removeProfilePhotoSuccessMessage.getText();
		
	}
	
	public void clickRemoveProfilePhotoButton() {
		removeProfilePhotoButton.click();
	}
	
	public void clickRemoveProfilePhotoConfirm() {
		removeProfilePhotoConfirmButton.click();
	}
	

	
	public Boolean isRemoveProfilePhotoSuccessMessage() {
		return removeProfilePhotoSuccessMessage.isDisplayed();
		
	}
	
	public void clickProfileImage() {
		ProfileImage.click();
	}
	
	public Boolean isProfilePhotoMaximizeDisplayed() {	
		try {
			utility.waitForVisible(ProfilePhotoMaximizeDisplayed);
			
		} catch (Exception e) {
			return false;
		}
		
		return ProfilePhotoMaximizeDisplayed.isDisplayed();
		
	}
	
	public void clickDashboardMenu() {
		DashboardMenu.click();
	}


}
