package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.MuiActionsUtil;
import utilities.SmartElementActions;
import utilities.UIActions;
import utilities.WebDriverUtility;

public class ConsigneesPage extends BasePage{
	private MuiActionsUtil muiAct;
	private WebDriverUtility wbdAct;
	private SmartElementActions smartActions;
	private UIActions uiAct;

	public ConsigneesPage(WebDriver driver) {
		super(driver);
		muiAct=new MuiActionsUtil(driver);
		wbdAct=new WebDriverUtility(driver);
		uiAct=new UIActions(driver);
	}
	
	//======================================================================
	//   Locators
	//======================================================================
	
	
	//------------------buttons--------------------------------
	By AddConsigneeButtonBy=By.xpath("//button[text()='Add Consignee']");
	By SaveButtonBy=By.xpath("//button[text()='Save']");
	By CancelButtonBy=By.xpath("//button[text()='Cancel']");
	
	@FindBy(xpath = "//button[text()='Add Consignee']") private WebElement AddConsigneeButton;
	@FindBy(xpath = "//button[text()='Save']") private WebElement SaveButton;
	@FindBy(xpath = "//button[text()='Cancel']") private WebElement CancelButton;
	
	
	
	//----------------inputs-----------------------------------

	//input web elements
	@FindBy(xpath = "//input[@placeholder='Name']") private WebElement NameInput;
	@FindBy(xpath = "//input[@placeholder='Email']") private WebElement EmailInput;
	@FindBy(xpath = "//input[@placeholder='Phone Number']") private WebElement PhoneNumberInput;
	@FindBy(xpath = "//input[@placeholder='Address']") private WebElement AddressInput;
	@FindBy(xpath = "//input[@placeholder='ZIP Code']") private WebElement ZIPCodeInput;
	@FindBy(xpath = "//input[@placeholder='City']") private WebElement CityInput;
	@FindBy(xpath = "//input[@placeholder='State']") private WebElement StateInput;
	@FindBy(xpath = "//input[@placeholder='Address 2']") private WebElement Address2Input;
	
	

	//----------------drop downs by locators--------------------
	By countryCodeSelectBy=By.id("country-select");
	By countryNameSelectBy=By.id("mui-component-select-country");
	By countryCodeByxpath=By.xpath("//div[@aria-labelledby='country-select-label country-select']");
	
	
	@FindBy(xpath ="//div[@aria-labelledby='country-select-label country-select']") private WebElement countryCodeDropDown;	
	@FindBy(id  ="mui-component-select-country") private WebElement countryNameSelect;
	//dynamic by locator
	public By countryCodeDropDownOptions(String contryCode) {
		return By.xpath("//li[@role='option' and @data-value='"+contryCode+"']");
	}
	
	
	//----------------input by locators------------------------
	By NameInputBy=By.xpath("//input[@placeholder='Name']");
	By EmailInputBy=By.xpath("//input[@placeholder='Email']");
	By PhoneNumberInputBy=By.xpath("//input[@placeholder='Phone Number']");
	By AddressInputBy=By.xpath("//input[@placeholder='Address']");
	By ZIPCodeInputBy=By.xpath("//input[@placeholder='ZIP Code']");
	By CityInputBy=By.xpath("//input[@placeholder='City']");
	By StateInputBy=By.xpath("//input[@placeholder='State']");
	By Address2InputBy=By.xpath("//input[@placeholder='Address 2']");

	


	

	
	
	
	//Displayed by locators
	By AddConsigneeFormBy=By.xpath("//div[contains(@class,'paperAnchorRight')]");
	//displayed web elements
	@FindBy(xpath = "//div[contains(@class,'paperAnchorRight')]") private WebElement AddConsigneeForm;
	
	
	
	
	//======================================================================
	//   Methods
	//======================================================================
	
	
	//=======================button actions============================
	public void clickAddConsigneeButton() {
		muiAct.muiClick(AddConsigneeButtonBy);
	}
	
	public void clickSaveButton() {
		muiAct.muiClick(SaveButtonBy);
	}
	
	public void clickCancelButton() {
		muiAct.muiClick(CancelButtonBy);
	}

	
	//==========================input actions=============================
	public void enterName(String value) {
		muiAct.muiType(NameInputBy, value);
	}
	public void enterEmail(String value) {
		muiAct.muiType(EmailInputBy, value);
	}
	public void enterPhoneNumber(String value) {
		muiAct.muiType(PhoneNumberInputBy, value);
	}
	public void enterAddress(String value) {
		//uiAct.type(AddressInput, value);
		muiAct.muiType(AddressInput, value);
		//AddressInput.sendKeys(Keys.TAB);
		//utility.type(AddressInput, value);
		//muiAct.muiTypeAndBlur(AddressInputBy, value);
		//System.out.println("AddressInput value: " + AddressInput.getAttribute("value"));
	}
	public void enterZIPCode(String value) {
		muiAct.muiType(ZIPCodeInputBy, value);
		ZIPCodeInput.sendKeys(Keys.TAB);
	}
	public void enterCity(String value) {
		muiAct.muiType(CityInputBy, value);
	}
	public void enterState(String value) {
		muiAct.muiType(StateInputBy, value);
	}
	public void enterAddress2(String value) {
		muiAct.muiType(Address2InputBy, value);
	}
	
	//=============================Drop down methods================================
	public void selectCountryCode(String phoneCode) {
		//ui.select(countryCodeSelectBy, phoneCode);
		uiAct.select(countryCodeDropDown, phoneCode);
	}
	
	public void selectCountryName(String CoutryName) {
		//muiAct.muiSelectByText(countryNameSelectBy, CoutryName);
		uiAct.select(countryNameSelect, CoutryName);
	}
	
	//=============================visibility of elements action=====================
	public Boolean isAddConsigneeFormisDisplayed() {
		try {
			wbdAct.waitForVisible(AddConsigneeForm);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	
	//================================helper confirm=================================
	public boolean isAddressFilled() {
	    WebElement input = driver.findElement(AddressInputBy);
	    return !input.getAttribute("value").isEmpty();
	}

	public void waitForZipAutoFillToComplete() {
	    try {
	        Thread.sleep(1500); // controlled, justified wait
	    } catch (InterruptedException ignored) {}
	}

	public void enterAddressWithZipStabilization(String address, String zip) {

	    // Initial entry
	    smartActions.type(AddressInput, address);

	    // Enter ZIP (triggers React re-render)
	    muiAct.muiType(ZIPCodeInputBy, zip);

	    // Wait for auto city/state update
	    wbdAct.waitUntil(() ->
	        !CityInput.getAttribute("value").isEmpty() &&
	        !StateInput.getAttribute("value").isEmpty(),
	        5
	    );

	    // Re-apply address AFTER re-render (CRITICAL)
	    smartActions.type(AddressInput, address);
	}

	@FindBy(id = "country-select") private WebElement CountrySelectDropDown;
	public void selectCountryCode1(String countryCode ) {
		muiAct.muiSelectByText(CountrySelectDropDown, "+91");
	}

	public void waitForPacSuggestionsToDisappear() {

	    new WebDriverWait(driver, Duration.ofSeconds(5))
	        .until(d -> d.findElements(By.cssSelector(".pac-item")).isEmpty());
	}

}
