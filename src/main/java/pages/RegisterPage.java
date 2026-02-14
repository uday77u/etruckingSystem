package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.MuiActionsUtil;
import utilities.WebDriverUtility;

public class RegisterPage extends BasePage{
	private WebDriverUtility wbUtil;
	public RegisterPage(WebDriver driver) {
		super(driver);
		wbUtil=new WebDriverUtility(driver);
		new MuiActionsUtil(driver);
	}
	
	//-------------------------Locators--------------------------------------------------------------------
	@FindBy(xpath = "//input[@name='firstName']") private WebElement firstNameText;
	@FindBy(xpath = "//input[@name='lastName']") private WebElement lastNameText;
	@FindBy(xpath = "//input[@name='email']") private WebElement emailText;
	@FindBy(xpath = "//input[@name='phone']") private WebElement phoneText;
	@FindBy(xpath = "//input[@name='DOTNumber']") private WebElement DOTNumberText;
	@FindBy(xpath = "//button[@type='submit' and text()='NEXT']") private WebElement NextButton;
	
	
	
	//---------------------------------------------Methods for Locators------------------------------------
	public void enterfirstNameText(String firstName) {
		firstNameText.sendKeys(firstName);
	}

	public void enterlastNameText(String lastName) {
		lastNameText.sendKeys(lastName);
	}

	public void enteremailText(String email) {
		emailText.sendKeys(email);
	}

	public void enterDOTNumberText(String DOTNumber) {
		DOTNumberText.sendKeys(DOTNumber);
	}
	public void enterphoneText(String phone) {
		phoneText.sendKeys(phone);
	}


	public void clickNextButton() {
		//submitButton.click();
		wbUtil.safeClick(NextButton);
	}

	
	
	

}