package ui_tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import pages.BasePage;
import utilities.MuiActionsUtil;
import utilities.WebDriverUtility;

public class VehiclesPage extends BasePage{
private WebDriverUtility util;
private MuiActionsUtil util2;

	public VehiclesPage(WebDriver driver) {
		super(driver);
		util=new WebDriverUtility(driver);
		util2=new MuiActionsUtil(driver);
	}

//=====================================================/============================================
	                                         //Locators
//=================================================================================================	
	//buttons
	@FindBy(xpath = "//button[text()='Add Vehicles']")
	private WebElement addVehiclesButton;
	
	@FindBy(xpath = "//*[(@role='button' or self::button) and .//text()[normalize-space()='Truck Details']]")
	private WebElement TruckDetailsButton;
	
	@FindBy(xpath = "//button[text()='Choose']")
	private WebElement ChooseButton;
	
	@FindBy(xpath = "(//button[text()='Save'])[1]")
	private WebElement truckDetailsSaveButton;
	
	//dropdown 
	private By registrationStateDropdown =By.id("mui-component-select-registrationState");
	private By modelYearDropdown =By.id("mui-component-select-modelYear");  
	private By conditionDropdown =By.id("mui-component-select-condition"); 
	
	//--------------Dynamic Locators-----
	public By vehicleImage(int imageNo) {
		return By.xpath("//ul[.//p[text()='("+imageNo+")']]");
	}
	
	//file locators
	private By licensePlateDocFile =By.cssSelector("input[type='file'][id='licensePlateDoc']");
	private By insurancePolicyDocFile =By.cssSelector("input[type='file'][id='insurancePolicyDoc']");
	
	
	// ================= Inputs (React-safe) =================

	private final By truckNumberInput =
	        By.cssSelector("input[name='truckNumber']");

	private final By truckModelNameInput =
	        By.cssSelector("input[name='modelName']");

	private final By VINNumberInput =
	        By.cssSelector("input[name='VINNumber']");

	private final By truckLicensePlateNumberInput =
	        By.cssSelector("input[name='licensePlateNumber']");

	private final By truckInsurancePolicyNumberInput =
	        By.cssSelector("input[name='insurancePolicyNumber']");

	private final By grossWeightInput =
	        By.cssSelector("input[name='grossWeight']");

	
//=======================================================================================
                                  //Methods
                                 
//=======================================================================================                              
                                  
	
	//butons methods
	public void clickAddVehiclesButton() {
		//util.click(addVehiclesButton);
		util2.muiClick1(addVehiclesButton);
	} 
	public void clickTruckDetailsButton() {
		//util.click(TruckDetailsButton);
		util2.muiClick1(TruckDetailsButton);
	}
	public void clickChooseButton() {
		//util.click(ChooseButton);
		util2.muiClick1(ChooseButton);
	}
	public void clicktruckDetailsSaveButton() {
		//util.click(truckDetailsSaveButton);
		util2.muiClick1(truckDetailsSaveButton);
	}
	
	//Dynamic locators methods
	public void clickToChooseVehicleImage(int imageNo) {
		//util.click(vehicleImage(imageNo));
		util.click(vehicleImage(imageNo));
	}
	
	
	
	
	
	
	//----------Radio button methods-------
	public void selectServiceProvider(boolean value) {
	    util2.selectRadio("isServiceProvider", String.valueOf(value));
	}

	public void selectAnnualInspectionReport(boolean value) {
	    util2.selectRadio("isAnnualInspectionReport", String.valueOf(value));
	}
	
	public void selectIftaCertified(boolean value) {
	    util2.selectRadio("iftaCertified", String.valueOf(value));
	}
	public void selectCarbCertified(boolean value) {
	    util2.selectRadio("carbCertified", String.valueOf(value));
	}
	public void selectAssignDriver(boolean value) {
	    util2.selectRadio("assignDriver", String.valueOf(value));
	}
	public void selectMexicoPermit(boolean value) {
	    util2.selectRadio("isMexicoPermit", String.valueOf(value));
	}
	public void selectIRP(boolean value) {
	    util2.selectRadio("isIRP", String.valueOf(value));
	}
	
	

	
	//Input methods
	//  
	public void enterTruckNumberInput(String value) {
		//util.type(truckNumberInput, value);
		util2.muiType(truckNumberInput, value);
		//util.safeType(truckNumberInput, value);
	}
	public void enterTruckModelNameInput(String value) {
		util2.muiType(truckModelNameInput, value);
	}
	public void enterVINNumberInput(String value) {
		//util.type(VINNumberInput, value);
		util2.muiType(VINNumberInput, value);
	}
	public void enterTruckLicensePlateNumberInput(String value) {
		util2.muiType(truckLicensePlateNumberInput, value);
	}
	public void enterTruckInsurancePolicyNumberInput(String value) {
		util2.muiType(truckInsurancePolicyNumberInput, value);
	}
	public void enterGrossWeightInput(String value) {
		util2.muiType(grossWeightInput, value);
	}
	
	
	//dropdown methods
	public void selectRegistrationState(String value) {
		util2.muiSelectByText(registrationStateDropdown, value);
	}
	public void selectModelYear(String value) {
		util2.muiSelectByText(modelYearDropdown, value);
	}
	
	public void selectCondition(String value) {
		util2.muiSelectByText(conditionDropdown, value);
	}

	//file
	public void uploadLicensePlateDocFile(String filePath) {
	    util2.uploadFile(licensePlateDocFile, filePath);
		//util2.muiType(licensePlateDocFile, filePath);
	}

	public void uploadInsurancePolicyDocFile(String filePath) {
	    util2.uploadFile(insurancePolicyDocFile, filePath);
		//util2.muiType(insurancePolicyDocFile, filePath);
	}
	
	//----calendar---
	// Date input fields
	private By licensePlateExpiryInput = By.name("licensePlateExpiry");
	private By purchaseDateInput      = By.name("purchaseDate");
	private By enrollDateInput        = By.name("enrollDate");

	// Calendar icon buttons (if input is readonly)
	private By licensePlateCalendarBtn =
	        By.xpath("//input[@name='licensePlateExpiry']/ancestor::div//button");
//---
	public void selectLicensePlateExpiryDate(String date) {
	    util2.muiDatePickerType(licensePlateExpiryInput, date);
	}

	public void selectPurchaseDate(String date) {
		util2.muiDatePickerType(purchaseDateInput, date);
	}

	public void selectEnrollDate(String date) {
		util2.muiDatePickerType(enrollDateInput, date);
	}

}
