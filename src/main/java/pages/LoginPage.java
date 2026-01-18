package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.MuiActionsUtil;
import utilities.WebDriverUtility;

public class LoginPage extends BasePage {
	private WebDriverUtility util;
	private MuiActionsUtil util2;
	
	public LoginPage(WebDriver driver) {
		super(driver);
		util=new WebDriverUtility(driver);
		util2=new MuiActionsUtil(driver);
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
		util.click(SignupLink);
		//util2.muiClick(SignupLink);
		//SignupLink.click();
	}

	public void enterEmail(String email) {
		util.type(emailText,email);
		//emailText.sendKeys(email);
		//util2.muiType(emailText,email);
	}
	public void enterPassword(String password) {
		util.type(passwordText,password);
		//passwordText.sendKeys(password);
		//util2.muiType(passwordText,password);
	}
	
	public void clickSubmitButton() {
		util.click(submitButton);
		//submitButton.click();
		//util2.muiClick(submitButton);
	}
	
	public boolean isAccountActiveOnOtherDeviceMessage() {
		try {
			return util.waitForVisible(AccountActiveOnOtherDeviceMessage).isDisplayed();
			//return AccountActiveOnOtherDeviceMessage.isDisplayed();
		} catch (Exception e) {
			return false;
		}

	}
	public void clickConfirmToLoginOnCurrentDevice() {
		//util.waitForVisible(confirmButtonLoginOnDevice);
		//util.waitForClickable(confirmButtonLoginOnDevice);
		//confirmButtonLoginOnDevice.click();
		//util2.muiClick(confirmButtonLoginOnDevice);
		
		util.safeClick(confirmButtonLoginOnDevice);
		
	}
	
	
}