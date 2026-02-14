package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.MuiActionsUtil;
import utilities.UIActions;

public class ManageUsersPage extends BasePage {
	private MuiActionsUtil muiUtil;
	private UIActions uiUtil;

	public ManageUsersPage(WebDriver driver) {
		super(driver);
		muiUtil=new MuiActionsUtil(driver);
		uiUtil=new UIActions(driver);
	}
	//==========================================================
			//Locators
	//==========================================================
	
	//---------------------------------Button Locators-------------------------
	@FindBy(xpath = "(//button[text()='Add User'])[2]") private WebElement AddUserButton;
	@FindBy(xpath = "//button[@type='submit' and text()='Save']") private WebElement saveButton;
	
	
	
	//---------------------------------Input Locators---------------------------
	@FindBy(css = "input[name='firstName']") private WebElement firstNameInput;
	@FindBy(name = "lastName") private WebElement lastNameInput;
	@FindBy(name = "email") private WebElement emailInput;
	@FindBy(name = "phone") private WebElement phoneInput;
	@FindBy(name = "addressDetails.zipCode") private WebElement zipCodeInput;
	@FindBy(name = "addressDetails.state") private WebElement stateInput;
	@FindBy(name = "addressDetails.city") private WebElement cityInput;
	@FindBy(name = "addressDetails.address") private WebElement addressInput;
	
	
	//----------------------------------DropDown Locators--------------------------
	
	By countryCodeDropDown=By.id("country-select");
	By countryNameDropDown=By.id("mui-component-select-addressDetails.country");
	By roleDropDown=By.id("mui-component-select-role");
 
	@FindBy(id="country-select") private WebElement countryCodeDropDownWBE;
	@FindBy(id="mui-component-select-addressDetails.country") private WebElement countryNameDropDownWBE;
	@FindBy(id="mui-component-select-role") private WebElement roleDropDownWBE;
	
	//---------------------------------Radio Button--------------------------------
	@FindBy(name = "isActive") private WebElement isActiveRadioButton;
	@FindBy(name = "accessType") private WebElement accessTypeRadioButton;
	
	//---------------------------------Check box---------------------------------------
	By driversAllCheckBox=By.name("permissions.drivers.all");
	By driversDeleteCheckBox=By.name("permissions.drivers.delete");
	
	@FindBy(name = "permissions.drivers.all") private WebElement driversAllRadioButton;
	@FindBy(name = "permissions.drivers.delete") private WebElement driversDeleteRadioButton;
	
	
	//==========================================================
	//                        Methods
	//==========================================================
	
	//====================================Button Actions================================
	public void clickAddUser() {
		AddUserButton.click();
	}
	public void clickSave() {
		saveButton.click();
	}
	
	
	
	
	
	//=====================================Input Actions================================
	
	public void enterFirstName(String firstName) {
		firstNameInput.sendKeys(firstName);
	}
	public void enterlastName(String lastName) {
		lastNameInput.sendKeys(lastName);
	}
	public void enterEmail(String email) {
		//emailInput.sendKeys(email);
		//muiUtil.muiType(emailInput, email);
		uiUtil.type(emailInput, email);
		//emailInput.sendKeys(Keys.TAB);	
	}
	
	public void enterPhone(String phone) {
		phoneInput.sendKeys(phone);
	}
	
	public void enterZipCodeName(String zipCode) {
		//zipCodeInput.sendKeys(zipCode);
		//muiUtil.muiType(addressInput, zipCode);
		uiUtil.type(zipCodeInput, zipCode);
	}
	
	public void enterStateName(String state) {
		stateInput.sendKeys(state);
	}
	
	public void enterCityName(String city) {
		cityInput.sendKeys(city);
	}
	
	public void enterAddress(String address) {
		addressInput.sendKeys(address);
	}
	
	//=============================================DropDown=====================================
	public void selectCountryCode(String countryCode) {
		//muiUtil.muiSelectByText(countryCodeDropDown, countryCode);
		uiUtil.select(countryCodeDropDownWBE, countryCode);
		
	}
	public void selectCountryName(String countryName) {
		//muiUtil.muiSelectByText(countryNameDropDown, countryName);
		uiUtil.select(countryNameDropDownWBE, countryName);
	}
	public void selectRole(String role) {
		//muiUtil.muiSelectByText(roleDropDown, role);
		uiUtil.select(roleDropDownWBE, role);
	}
	
	//==============================================Radio Button=================================
	public void selectIsActive(String value) {
		muiUtil.selectRadio("isActive", value);
	}
	public void selectAccessType(String value) {
		muiUtil.selectRadio("accessType", value);
	}
	
	//==============================================Check box======================================
	public void selectDriversAll(Boolean value) {
		muiUtil.setCheckboxNew(driversAllCheckBox, value);
	}
	public void selectDriversDelete(boolean value) {
		muiUtil.setCheckboxNew(driversDeleteCheckBox, value);;
	}
	
	//================================================  Error================================

	public List<String> getFormValidationErrors() {

	    if (muiUtil.hasMuiErrors()) {

	        // Scroll to first error for visibility
	        muiUtil.goToFirstErrorField();

	        return muiUtil.getAllMuiErrorMessages();
	    }

	    return List.of(); // No errors
	}

		
	public boolean hasFormErrors() {
	    return muiUtil.hasMuiErrors();
	}



}
