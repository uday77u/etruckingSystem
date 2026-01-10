package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	//-------------------------Locators--------------------------------------------------------------------
	@FindBy(xpath = "//a[text()='SIGN UP.']") private WebElement SignupLink;
	
	
	
	
	
	
	//---------------------------------------------Methods for Locators------------------------------------
	public void clickSignupLink() {
		SignupLink.click();
	}

	
	
	
	
}