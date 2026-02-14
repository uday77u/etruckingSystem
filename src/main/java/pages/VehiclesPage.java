package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import groovyjarjarantlr4.v4.runtime.tree.xpath.XPath;
import pages.BasePage;
import utilities.MuiActionsUtil;
import utilities.UIActions;
import utilities.WebDriverUtility;

public class VehiclesPage extends BasePage{
private WebDriverUtility util;
private MuiActionsUtil muiUtil;
private Logger logger;

	public VehiclesPage(WebDriver driver) {
		super(driver);
		util=new WebDriverUtility(driver);
		muiUtil=new MuiActionsUtil(driver);
		new UIActions(driver);
		logger=LogManager.getLogger(getClass());
	}

//=================================================================================================
	                                         //Locators
//=================================================================================================	
	
	//-------------------------------------------------------------------Buttons----------------------------------------------------------------------------
	
	//web Element Locators
	@FindBy(xpath = "//button[text()='Add Vehicles']") private WebElement addVehiclesButton;
	@FindBy(xpath = "//*[(@role='button' or self::button) and .//text()[normalize-space()='Truck Details']]") private WebElement TruckDetailsButton;
	@FindBy(xpath = "//button[text()='Choose']") private WebElement ChooseButton;	
	@FindBy(xpath = "(//button[text()='Save'])[1]") private WebElement truckDetailsSaveButton;
	
	@FindBy(xpath = "//*[(@role='button' or self::button) and .//text()[normalize-space()='Trailer Details']]") private WebElement trailerDetailsButton;
	@FindBy(xpath = "(//button[text()='Save'])[2]") private WebElement trailerDetailsSaveButton;
	
	
	
	//----------------------------------------------------------------------drop down -------------------------------------------------------------------------
	
	//By Locators
	private By registrationStateDropdown =By.id("mui-component-select-registrationState");
	private By modelYearDropdown =By.id("mui-component-select-modelYear");  
	private By conditionDropdown =By.id("mui-component-select-condition"); 
	
	private By modelYearTrailerDropdown =By.xpath("(//div[@id='mui-component-select-modelYear'])[2]"); 
	private By TrailerConditionDropdown =By.xpath("(//div[@id='mui-component-select-condition'])[2]");
	private By registrationStateTrailerDropdown =By.xpath("(//div[@aria-labelledby='mui-component-select-registrationState'])[2]");
	private By trailerSizeDropdown =By.id("mui-component-select-trailerSize");
	private By trailerTypeDropdown =By.id("mui-component-select-trailerTypeId");


	//--------------Dynamic Locators-----
	public By vehicleImage(int imageNo) {
		return By.xpath("//ul[.//p[text()='("+imageNo+")']]");
	}
	
	//----------------------------------------------------------------------Inputs------------------------------------------------------------------------------
	// ================= Inputs (React-safe) =================

	//By Locators
	private final By truckNumberByInput =By.cssSelector("input[name='truckNumber']");
	private final By truckModelNameByInput =By.cssSelector("input[name='modelName']");    
	private final By VINNumberByInput =By.cssSelector("input[name='VINNumber']");        
	private final By truckLicensePlateNumberByInput =By.cssSelector("input[name='licensePlateNumber']");
	private final By truckInsurancePolicyNumberByInput =By.cssSelector("input[name='insurancePolicyNumber']");
	private final By grossWeightByInput =By.cssSelector("input[name='grossWeight']");
	
	
	//WebElements
	@FindBy(css = "input[name='truckNumber']") private WebElement truckNumberInput;
	@FindBy(xpath = "(//input[@name='modelName'])[1]") private WebElement truckModelNameInput;
	@FindBy(css = "input[name='VINNumber']") private WebElement VINNumberInput;
	@FindBy(xpath = "(//input[@name='licensePlateNumber'])[1]") private WebElement truckLicensePlateNumberInput;
	@FindBy(xpath = "(//input[@name='insurancePolicyNumber'])[1]") private WebElement truckInsurancePolicyNumberInput;
	@FindBy(css = "input[name='grossWeight']") private WebElement grossWeightInput;
	
	@FindBy(css = "input[name='trailerNumber']") private WebElement trailerNumberInput;
	@FindBy(xpath = "(//input[@name='modelName'])[2]") private WebElement trailerModelNameInput;
	@FindBy(xpath = "(//input[@name='licensePlateNumber'])[2]") private WebElement trailerLicensePlateNumberInput;
	@FindBy(xpath = "(//input[@name='insurancePolicyNumber'])[2]") private WebElement trailerInsurancePolicyNumberInput;
	@FindBy(css = "input[name='engineHours']") private WebElement engineHoursInput;
	@FindBy(css = "input[name='maxCapacity']") private WebElement maxCapacityInput;
	
	
	//-------------------------------------------------------------------------file input locators-----------------------------------------------------------------------
	
	//Web elements Locators
	@FindBy(xpath = "//input[@type='file' and @id='licensePlateDoc']") private WebElement licensePlateDocInput;
	@FindBy(xpath = "//input[@type='file' and @id='insurancePolicyDoc']") private WebElement insurancePolicyDocInput;
	
	@FindBy(xpath = "//input[@type='file' and @id='trailerlicensePlateDoc']") private WebElement trailerLicensePlateDocInput;
	@FindBy(xpath = "//input[@type='file' and @id='trailerinsurancePolicyDoc']") private WebElement trailerInsurancePolicyDocInput;
	
	//By Locators
	private By licensePlateDocFile =By.cssSelector("input[type='file'][id='licensePlateDoc']");
	private By insurancePolicyDocFile =By.cssSelector("input[type='file'][id='insurancePolicyDoc']");
	


	//----------------------------------------------------------------------------Date (Calendar)) inputs-------------------------------------------------------------
	private final By licensePlateExpiryDate =By.cssSelector("input[name='licensePlateExpiry']");
	private final By purchaseDate =By.cssSelector("input[name='purchaseDate']");
	private final By enrollDate =By.cssSelector("input[name='enrolDate']");
	private final By insuranceExpiryDate =By.xpath("(//input[@name='insuranceExpiryDate'])[1]");
	        
	@FindBy(css = "input[name='enrolDate']") private WebElement enrollDateInput;
	private final By trailerLicensePlateExpiryDate =By.xpath("(//input[@name='licensePlateExpiry'])[2]");
	private final By trailerPurchaseDate =By.xpath("(//input[@name='purchaseDate'])[2]");
	private final By trailerEnrollDate =By.xpath("(//input[@name='enrolDate'])[2]");
	private final By trailerInsuranceExpiryDate =By.xpath("(//input[@name='insuranceExpiryDate'])[2]");
	
	//------------------------------------------------------------------------------Radio buttons-------------------------------------------------------------------------
	
	@FindBy(xpath = "(//input[@name='isServiceProvider' and @value='true'])[1]")
	private WebElement truckServiceProvider_YesRadioButton;                     
	
	@FindBy(xpath = "(//input[@name='isServiceProvider' and @value='false'])[1]")
	private WebElement truckServiceProvider_NoRadioButton;

	@FindBy(xpath = "(//input[@name='isAnnualInspectionReport' and @value='true'])[1]")
	private WebElement truckAnnualInspectionReport_YesRadioButton;
	
	@FindBy(xpath = "(//input[@name='isAnnualInspectionReport' and @value='false'])[1]")
	private WebElement truckAnnualInspectionReport_NoRadioButton;
	
	@FindBy(xpath = "(//input[@name='iftaCertified' and @value='true'])[1]")
	private WebElement truckIftaCertified_YesRadioButton;
	
	@FindBy(xpath = "(//input[@name='iftaCertified' and @value='false'])[1]")
	private WebElement truckIftaCertified_NoRadioButton;
	
	@FindBy(xpath = "(//input[@name='carbCertified' and @value='true'])")
	private WebElement carbCertified_YesRadioButton;
	
	@FindBy(xpath = "(//input[@name='carbCertified' and @value='false'])")
	private WebElement carbCertified_NoRadioButton;
	
	@FindBy(xpath = "(//input[@name='assignDriver' and @value='true'])")
	private WebElement assignDriver_YesRadioButton;
	
	@FindBy(xpath = "(//input[@name='assignDriver' and @value='false'])")
	private WebElement assignDriver_NoRadioButton;
	
	@FindBy(xpath = "(//input[@name='isMexicoPermit' and @value='true'])") 
	private WebElement MexicoPermit_YesRadioButton;
	
	@FindBy(xpath = "(//input[@name='isMexicoPermit' and @value='false'])")
	private WebElement MexicoPermit_NoRadioButton;
	
	@FindBy(xpath = "(//input[@name='isIRP' and @value='true'])")
	private WebElement IRP_YesRadioButton;
	
	@FindBy(xpath = "(//input[@name='isIRP' and @value='false'])")
	private WebElement IRP_NoRadioButton;
	
	
	//-------------------dynamic By Radio locator----------------------------------
	public By isServiceProviderTrailerRadioButton(Boolean value) {
		return By.xpath("(//label[.//input[@type='radio' and @name='isServiceProvider' and @value='"+value+"']])[2]");
	}
	public By isAnnualInspectionReportTrailerRadioButton(Boolean value) {
		return By.xpath("(//label[.//input[@type='radio' and @name='isAnnualInspectionReport' and @value='"+value+"']])[2]");
	}
	public By iftaCertifiedTrailerRadioButton(Boolean value) {
		return By.xpath("(//label[.//input[@type='radio' and @name='iftaCertified' and @value='"+value+"']])[2]");
	}
	public By isCARBTrailerRadioButton(Boolean value) {
		return By.xpath("//label[.//input[@type='radio' and @name='isArbNumber' and @value='"+value+"']]");
	}

	
					
//=======================================================================================
                                  //Methods
                                 
//=======================================================================================                              
                                  

	//===============================================================================Button Actions===================================================================
	
	public void clickTruckDetailsButton() {
		//util.click(TruckDetailsButton);
		muiUtil.muiClick(TruckDetailsButton);
	}
	public void clickChooseButton() {
		//util.click(ChooseButton);
		muiUtil.muiClick(ChooseButton);
	}
	public void clicktruckDetailsSaveButton() {
		//util.click(truckDetailsSaveButton);
		muiUtil.muiClick(truckDetailsSaveButton);
	}
	
	//Dynamic locators methods
	public void clickToChooseVehicleImage(int imageNo) {
		util.click(vehicleImage(imageNo));
	}
	
	
	public void clickTrailerDetailsButton() {
		//util.click(TruckDetailsButton);
		muiUtil.muiClick(trailerDetailsButton);
	}
	public void clickTrailerDetailsSaveButton() {
		//util.click(truckDetailsSaveButton);
		muiUtil.muiClick(trailerDetailsSaveButton);
	}
	
	
	//==================================================================================Radio button Actions====================================================================
	
	public void selectServiceProvider(boolean value) {
	    muiUtil.selectRadio("isServiceProvider", String.valueOf(value));
	}

	public void selectAnnualInspectionReport(boolean value) {
	    muiUtil.selectRadio("isAnnualInspectionReport", String.valueOf(value));
	}
	
	public void selectIftaCertified(boolean value) {
	    muiUtil.selectRadio("iftaCertified", String.valueOf(value));
	}
	public void selectCarbCertified(boolean value) {
	    muiUtil.selectRadio("carbCertified", String.valueOf(value));
	}
	public void selectAssignDriver(boolean value) {
	    muiUtil.selectRadio("assignDriver", String.valueOf(value));
	}
	public void selectMexicoPermit(boolean value) {
	    muiUtil.selectRadio("isMexicoPermit", String.valueOf(value));
	}
	public void selectIRP(boolean value) {
	    muiUtil.selectRadio("isIRP", String.valueOf(value));
	}
	
	public void SelectIsServiceProviderTrailerRadioButton(boolean value) {
		util.click(isServiceProviderTrailerRadioButton(value));
	}
	public void SelectIsAnnualInspectionReportTrailerRadioButton(boolean value) {
		util.click(isAnnualInspectionReportTrailerRadioButton(value));
	}
	public void SelectIftaCertifiedProviderTrailerRadioButton(boolean value) {
		util.click(iftaCertifiedTrailerRadioButton(value));
	}
	public void SelectIsCARBProviderTrailerRadioButton(boolean value) {
		util.click(isCARBTrailerRadioButton(value));
	}
	
	  
	
	
	//========================================================================================Input Actions==============================================================================
	//  
	public void enterTruckNumberInput(String value) {
		//util.type(truckNumberInput, value);
		muiUtil.muiType(truckNumberInput, value);
		//util.safeType(truckNumberInput, value);
	}
	public void enterTruckModelNameInput(String value) {
		muiUtil.muiType(truckModelNameInput, value);
	}
	/*public void enterVINNumberInput(String value) {
		//util.type(VINNumberInput, value);
		muiUtil.muiType(VINNumberInput, value);
	}*/
	public void enterTruckLicensePlateNumberInput(String value) {
		muiUtil.muiType(truckLicensePlateNumberInput, value);
	}
	public void enterTruckInsurancePolicyNumberInput(String value) {
		muiUtil.muiType(truckInsurancePolicyNumberInput, value);
	}
	public void enterGrossWeightInput(String value) {
		muiUtil.muiType(grossWeightInput, value);
	}
	
	
	
	public void enterTrailerNumberInput(String value) {
		//util.type(truckNumberInput, value);
		muiUtil.muiType(trailerNumberInput, value);
		//util.safeType(truckNumberInput, value);
	}
	public void enterTrailerModelNameInput(String value) {
		muiUtil.muiType(trailerModelNameInput, value);
	}
	public void enterTrailerLicensePlateNumberInput(String value) {
		muiUtil.muiType(trailerLicensePlateNumberInput, value);
	}
	public void enterTrailerInsurancePolicyNumberInput(String value) {
		muiUtil.muiType(trailerInsurancePolicyNumberInput, value);
	}
	public void enterEngineHours(String value) {
		muiUtil.muiType(engineHoursInput, value);
	}
	public void enterMaxCapacity(String value) {
		muiUtil.muiType(maxCapacityInput, value);
	}
	
	 
	//==========================================================================================Drop down Actions=============================================================================
	
	public void selectRegistrationState(String value) {
		muiUtil.muiSelectByText(registrationStateDropdown, value);
	}
	public void selectModelYear(String value) {
		muiUtil.muiSelectByText(modelYearDropdown, value);
	}
	
	public void selectCondition(String value) {
		muiUtil.muiSelectByText(conditionDropdown, value);
	}

	
	public void selectTrailerRegistrationState(String value) {
		muiUtil.muiSelectByText(registrationStateTrailerDropdown, value);
	}
	public void selectTrailerSize(String value) {
		muiUtil.muiSelectByText(trailerSizeDropdown, value);
	}
	public void selectTrailerType(String value) {
		muiUtil.muiSelectByText(trailerTypeDropdown, value);
	}
	public void selectTrailerCondition(String value) {
		muiUtil.muiSelectByText(TrailerConditionDropdown, value);
	}
	//===========================================================================================File Actions===============================================================================
	public void uploadLicensePlateDocFile(String filePath) {
		licensePlateDocInput.sendKeys(filePath);
		//uiActions.type(licensePlateDocInput, filePath);
	    //muiUtil.uploadFile(licensePlateDocFile, filePath);
		//muiUtil.muiType(licensePlateDocFile, filePath);
	}

	public void uploadInsurancePolicyDocFile(String filePath) {
		insurancePolicyDocInput.sendKeys(filePath);
	    //muiUtil.uploadFile(insurancePolicyDocFile, filePath);
		//muiUtil.muiType(insurancePolicyDocFile, filePath);
	}
	
	
	
	public void selectModelTrailerYear(String value) {
		muiUtil.muiSelectByText(modelYearTrailerDropdown, value);
	}
	public void uploadTrailerLicensePlateDocFile(String filePath) {
		trailerLicensePlateDocInput.sendKeys(filePath);
	}
	public void uploadTrailerInsurancePolicyDocFile(String filePath) {
		trailerInsurancePolicyDocInput.sendKeys(filePath);

	}
	
	//============================================================================================Calendar========================================================================================
	
	public void setLicensePlateExpiry(String date) {
	    muiUtil.muiSetDate(licensePlateExpiryDate, date);
	}

	public void setPurchaseDate(String date) {
	    muiUtil.muiSetDate(purchaseDate, date);
	}

	public void setEnrollDate(String date) {
	    muiUtil.muiSetDate(enrollDate, date);
	}

	public void setInsuranceExpiryDate(String date) {
	    muiUtil.muiSetDate(insuranceExpiryDate, date);
	}
	
	public void setEnrollDateIfVisible(String date) {
	    if (muiUtil.isElementPresent(enrollDate)) {
	        muiUtil.muiSetDate(enrollDate, date);
	    } else {
	        logger.info("Enroll Date not displayed – skipping as per UI rules");
	    }
	}
	

	
	
	public void setTrailerLicensePlateExpiry(String date) {
	    muiUtil.muiSetDate(trailerLicensePlateExpiryDate, date);
	}
	public void setTrailerPurchaseDate(String date) {
	    muiUtil.muiSetDate(trailerPurchaseDate, date);
	}
	public void setTrailerEnrollDate(String date) {
	    muiUtil.muiSetDate(trailerEnrollDate, date);
	}
	public void setTrailerInsuranceExpiryDate(String date) {
	    muiUtil.muiSetDate(trailerInsuranceExpiryDate, date);
	}
	
	//-----EnrollDate not working
	public void setEnrollDateIfPresent(String date) {
	    if (muiUtil.isElementPresent(enrollDate)) {
	        muiUtil.muiSetDate(enrollDate, date);
	    } else {
	        logger.info("Enroll Date not rendered – skipping as per UI rules");
	    }
	}

	public void assertEnrollDateIsVisible() {
	    if (!muiUtil.isElementPresent(enrollDate)) {
	        throw new AssertionError(
	                "Enroll Date should be visible but was not rendered after Purchase Date");
	    }
	}
	public void waitForEnrollDateToRender() {
	    muiUtil.waitForElementToRender(enrollDate);
	}

	private By truckDetailsAccordion =
			By.xpath("//*[(@role='button' or self::button) and .//text()[normalize-space()='Truck Details']]");

		private By truckNumber =
		    By.cssSelector("input[name='truckNumber']");

		
		public void openTruckDetails() {
		    //muiUtil.openMuiAccordion(truckDetailsAccordion, truckNumber);
			muiUtil.expandMuiAccordion(truckDetailsAccordion);
		}


		private By addVehicleFormRoot =
				By.xpath("//form"); // or heading, stepper, etc.
		public void clickAddVehiclesButton() {
		    muiUtil.muiClick(addVehiclesButton);
		    muiUtil.waitForElementToRender(addVehicleFormRoot);
		}

	//vin input is not working 
		
		private By vinNumberInput = By.cssSelector("input[name='VINNumber']");

		public void enterVINNumberInput(String vinNumber) {
		    muiUtil.muiTypeDeferredInput(vinNumberInput, vinNumber);
		}


	/*	public void waitForVINEnabled() {
		    muiUtil.waitForInputEnabled(vinNumberInput);
		}
*/



}
