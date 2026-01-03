package ui_PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PasswordPage extends BasePage {

	public PasswordPage(WebDriver driver) {
		super(driver);
	}
	
	//-------------------------Locators--------------------------------------------------------------------
	
	@FindBy(xpath = "//input[@name='password']") private WebElement passwordText;
	@FindBy(xpath = "//input[@name='confirmPassword']") private WebElement confirmPasswordText;
	@FindBy(xpath = "//button[text()='SIGN UP']") private WebElement signupButton;
	
	
	//---------------------------------------------Methods for Locators------------------------------------
	public void enterPasswordText(String password) {
		passwordText.sendKeys(password);
	}
	
	public void enterConfirmPasswordText(String confirmPassword) {
		confirmPasswordText.sendKeys(confirmPassword);
	}
	
	public void clickSignupButton() {
		signupButton.click();
	}
}
