package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.WebDriverUtility;

public class LoginPage extends BasePage {
	private WebDriverUtility util;
	
	public LoginPage(WebDriver driver) {
		super(driver);
		util=new WebDriverUtility(driver);
	}
	
	//-------------------------Locators--------------------------------------------------------------------
	@FindBy(xpath = "//a[text()='SIGN UP.']") private WebElement SignupLink;
	@FindBy(xpath = "//input[@name='email']") private WebElement emailText;
	@FindBy(xpath = "//input[@name='password']") private WebElement passwordText;
	@FindBy(xpath = "//button[@type='submit']") private WebElement submitButton;
	
	@FindBy(xpath = "//div[contains(text(),\"active on\")]") private WebElement AccountActiveOnOtherDeviceMessage;
	@FindBy(xpath = "//button[text()='Confirm']") private WebElement confirmButtonLoginOnDevice;
	

	//---------------------------------------------Methods for Locators------------------------------------
	public void clickSignupLink() {
		SignupLink.click();
	}

	public void enterEmail(String email) {
		emailText.sendKeys(email);
	}
	public void enterPassword(String password) {
		passwordText.sendKeys(password);
	}
	
	public void clickSubmitButton() {
		submitButton.click();
	}
	
	public boolean isAccountActiveOnOtherDeviceMessage() {
		try {
			util.waitForVisible(AccountActiveOnOtherDeviceMessage);
			return AccountActiveOnOtherDeviceMessage.isDisplayed();
		} catch (Exception e) {
			return false;
		}

	}
	public void clickConfirmToLoginOnCurrentDevice() {
		util.waitForVisible(confirmButtonLoginOnDevice);
		util.waitForClickable(confirmButtonLoginOnDevice);
		confirmButtonLoginOnDevice.click();
		
		//util.safeClick(confirmButtonLoginOnDevice);
		
	}
	
	
}