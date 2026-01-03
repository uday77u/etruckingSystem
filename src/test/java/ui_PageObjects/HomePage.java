package ui_PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui_Utilities.WebDriverUtility;

public class HomePage extends BasePage {
WebDriverUtility webDriverUtility=new WebDriverUtility(driver);
public HomePage(WebDriver driver) {
		super(driver);
	}


//-------------------------Locators--------------------------------------------------------------------

@FindBy(xpath = "(//div[text()='Yes'])[1]") private WebElement AdminYesTextLink;



//---------------------------------------------Methods for Locators------------------------------------

public void clickAdminYesTextLink() {
	AdminYesTextLink.click();
}



}
