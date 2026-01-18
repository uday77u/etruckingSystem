package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.WebDriverUtility;

public class HomePage extends BasePage {
WebDriverUtility webDriverUtility=new WebDriverUtility(driver);
public HomePage(WebDriver driver) {
		super(driver);
	}


//-------------------------Locators--------------------------------------------------------------------

@FindBy(xpath = "(//div[text()='Yes'])[1]") private WebElement AdminYesTextLink;
@FindBy(xpath = "(//div[text()='Yes'])[2]") private WebElement UserYesTextLink;
@FindBy(xpath = "//span[text()='SIGN UP']") private WebElement signupLink;

//---------------------------------------------Methods for Locators------------------------------------

public void clickAdminYesTextLink() {
	
	AdminYesTextLink.click();
}
public void clickUserYesTextLink() {
	
	UserYesTextLink.click();
}
public void clickSignup() {
	
	signupLink.click();
}

}
