package ui_PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VerifyPhonePage extends BasePage{

	public VerifyPhonePage(WebDriver driver) {
		super(driver);
	}
	//-------------------------Locators--------------------------------------------------------------------
	@FindBy(xpath = "//p[contains(text(),'OTP') and @class='text-sm']") private WebElement OTPsentMessage;
	@FindBy(xpath = "//p[contains(text(),'link') and @class='text-sm']") private WebElement linkSentMessage;
	@FindBy(xpath = "//p[contains(text(),'OTP is') and @class='text-sm']") private WebElement OTPcontainsMessage;
	
	
	
	//---------------------------------------------Methods for Locators------------------------------------
	public String getOTPsentMessage() {
		return OTPsentMessage.getText();
	}
	
	public String getLinkSentMessage() {
		return linkSentMessage.getText();
	}

	public String getOTPcontainsMessage() {
		return OTPcontainsMessage.getText();
	}
	
	public String ExtractOTPcontainsMessage() {
		return OTPcontainsMessage.getText().replaceAll("\\D+", "");
	}
}
