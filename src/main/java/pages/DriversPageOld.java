package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.MuiActionsUtil;
import utilities.WebDriverUtility;

public class DriversPageOld extends BasePage {
	WebDriverUtility util;
	MuiActionsUtil util2;
	public DriversPageOld(WebDriver driver) {
		super(driver);
		util=new WebDriverUtility(driver);
		util2=new MuiActionsUtil(driver); 
	}

	//-------------------------Locators---------------------------------------------

	@FindBy(xpath = "//button[text()='Add Driver']") private WebElement addDriverButton;
	@FindBy(xpath = "(//i[contains(@class,'file-circle-plus')])[1]") private WebElement uploadDLButton;
	@FindBy(xpath = "(//input[@type='file'])[1]") private WebElement uploadDLfileInput;
	@FindBy(xpath = "(//input[@type='file'])[2]") private WebElement uploadSSNfileInput;
	@FindBy(xpath = "(//input[@type='file'])[3]") private WebElement uploadPhotofileInput;
	@FindBy(xpath = "(//input[@type='file'])[4]") private WebElement uploadTWICfileInput;
	
	@FindBy(xpath = "(//input[contains(@name,'drivingLicense')])[1]") private WebElement DriverLicenseText;
	@FindBy(xpath = "//input[contains(@name,'CDLExpDate')]") private WebElement CDLExpiryDate;
	@FindBy(xpath = "//input[contains(@name,'DOB')]") private WebElement DateOfBirthText;
	@FindBy(xpath = "//input[contains(@name,'JoiningDate')]") private WebElement JoiningDateText;
	@FindBy(xpath = "//input[contains(@placeholder,'First')]") private WebElement FirstNameText;
	
	@FindBy(xpath = "//input[contains(@placeholder,'Last')]") private WebElement LastNameText;
	@FindBy(xpath = "//input[contains(@placeholder,'Email')]") private WebElement EmailText;
	@FindBy(xpath = "//input[contains(@placeholder,'State')]") private WebElement StateText;
	@FindBy(xpath = "//input[contains(@placeholder,'Address')]") private WebElement AddressText;
	@FindBy(xpath = "//input[contains(@placeholder,'City')]") private WebElement CityText;
	@FindBy(xpath = "//input[contains(@placeholder,'ZIP')]") private WebElement ZIPText;
	@FindBy(xpath = "//input[contains(@placeholder,'Phone')]") private WebElement PhoneText;
	@FindBy(xpath = "//input[@name='SSN']") private WebElement SSNText;
	@FindBy(xpath = "//input[@name='twicCardNo']") private WebElement TwicCardNoText;
	@FindBy(xpath = "//button[@type='submit']") private WebElement submitButton;
	
	@FindBy(id = "country-select") private WebElement CountrySelectDropDown;
	@FindBy(xpath = "//input[contains(@name,'addressDetails.country')]") private WebElement AddressCountryDropDown;
	@FindBy(xpath = "//div[@id='mui-component-select-licenseClass']") private WebElement licenseClassDropDown;
	
	@FindBy(xpath = "//div[@id='mui-component-select-yearsOfComExp']") private WebElement yearsOfComExpDropDown;
	@FindBy(xpath = "//div[@id='mui-component-select-jobType']") private WebElement jobTypeDropDown;
	@FindBy(xpath = "//div[@id='mui-component-select-driverType']") private WebElement driverTypeDropDown;
	@FindBy(xpath = "//div[@id='mui-component-select-interests']") private WebElement interestsDropDown;
	@FindBy(xpath = "//div[@id='mui-component-select-leadSource']") private WebElement leadSourceDropDown;
	
	@FindBy(xpath = "(//*[name()='svg' and @data-testid='CalendarIcon'])[1]") private WebElement CDLExpCalendarIcon;
	@FindBy(xpath = "(//*[name()='svg' and @data-testid='CalendarIcon'])[2]") private WebElement DOBCalendarIcon;
	@FindBy(xpath = "(//*[name()='svg' and @data-testid='CalendarIcon'])[3]") private WebElement JoiningDateCalendarIcon;
	@FindBy(xpath = "(//*[name()='svg' and @data-testid='CalendarIcon'])[4]") private WebElement TwicExpDateCalendarIcon;
	
	@FindBy(xpath = "//input[@name='readyForTeamUp' and @value='true']") private WebElement TeamUpYesRadioButton;
	@FindBy(xpath = "//input[@name='readyForTeamUp' and @value='false']") private WebElement TeamUpNoRadioButton;

	@FindBy(xpath = "//*[name()='svg' and @data-testid='ArrowDropDownIcon' and contains(@class,'CalendarHeader')]") 
	private WebElement ArrowDownIconInCalender;

	//verify upload file
	By uploadedDLFileIcon = By.xpath("//button//i[contains(@class,'fa-file-image')]");
	
	public void verifyDLFileUploaded() {
	    util.waitForVisible(uploadedDLFileIcon);
	}
	
	
	//------------------------------Dynamic xpath locator---------------------------------------
	public WebElement getYearButton(int year) {
	    return driver.findElement(
	        By.xpath("//button[contains(@class,'yearButton') and normalize-space()='" + year + "']")
	    );
	}

	public WebElement getMonthButton(String month) {
	    return driver.findElement(
	        By.xpath("//button[contains(@class,'monthButton') and normalize-space()='" + month + "']")
	    );
	}

	public WebElement getDayButton(int day) {
	    return driver.findElement(
	        By.xpath("//button[contains(@class,'PickersDay') and normalize-space()='" + day + "']")
	    );
	}
	
	
	
	//-------------------------Methods-----------------------------------------------
	public void clickAddDriverButton() {
		//util2.muiClick(addDriverButton);
		//addDriverButton.click();
		util.click(addDriverButton);
	}
	
	public void clickUploadDLButton() {
		//uploadDLButton.click();

		util.safeClick(uploadDLButton);
		
	}
	
	public void UploadDLfile(String filePath) {
		//util.safeType(uploadDLfileInput, filePath);
		uploadDLfileInput.sendKeys(filePath);
	}

	public void enterDriverLicenseReactSafe(String licenseNo) {

	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    js.executeScript(
	        "const input = arguments[0];" +
	        "const lastValue = input.value;" +
	        "input.value = arguments[1];" +

	        // VERY IMPORTANT for React
	        "const event = new Event('input', { bubbles: true });" +
	        "event.simulated = true;" +

	        "const tracker = input._valueTracker;" +
	        "if (tracker) { tracker.setValue(lastValue); }" +

	        "input.dispatchEvent(event);",
	        DriverLicenseText,
	        licenseNo
	    );
	}


	public void enterSSNnumberReactSafe(String ssnNo) {

	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    js.executeScript(
	        "const input = arguments[0];" +
	        "const lastValue = input.value;" +
	        "input.value = arguments[1];" +

	        // VERY IMPORTANT for React
	        "const event = new Event('input', { bubbles: true });" +
	        "event.simulated = true;" +

	        "const tracker = input._valueTracker;" +
	        "if (tracker) { tracker.setValue(lastValue); }" +

	        "input.dispatchEvent(event);",
	        SSNText,
	        ssnNo
	    );
	}
	
	public void enterCDLExpiryDate(String expiryDate) {
		CDLExpiryDate.sendKeys(expiryDate);
	}
	
	public void enterDateOfBirth(String DateOfBirth) {
		DateOfBirthText.sendKeys(DateOfBirth);
	}
	
	public void enterJoiningDate(String JoiningDate) {
		JoiningDateText.sendKeys(JoiningDate);
	}
	
	public void enterFirstNameText(String FirstName) {
		FirstNameText.sendKeys(FirstName);
	}
	
	public void enterLastNameText(String LastName) {
		LastNameText.sendKeys(LastName);
	}
	
	public void enterEmail(String Email) {
		EmailText.sendKeys(Email);
	}
	public void enterState(String State) {
		StateText.clear();
		StateText.sendKeys(State);
	}
	public void enterAddress(String Address) {
		AddressText.sendKeys(Address);
	}
	public void enterCity(String City) {
		CityText.clear();
		CityText.sendKeys(City);
	}
	public void enterZIPcode(String ZIP) {
		ZIPText.sendKeys(ZIP);
	}
	public void enterPhone(String Phone) {
		PhoneText.sendKeys(Phone);
	}
	public void enterSSN(String SSN) {
		SSNText.sendKeys(SSN);
	}
	
	public void selectCountryCode(String countryCode ) {
		util2.muiSelectByText(CountrySelectDropDown, "+91");
		
	}
	
	public void clickCDLExpCalendarIcon() {
		CDLExpCalendarIcon.click();
	}
	
	public void clickArrowDownIconInCalender() {
		ArrowDownIconInCalender.click();
	}
	
	public void selectYearInCalendar(int year) {
		getYearButton(year).click();
	}
	
	public void selectMonthInCalendar(String Month) {
		getMonthButton(Month).click();		
	}

	public void selectDayInCalendar(int Day) {
		getDayButton(Day).click();		
	}
	
	public void selectDateInCalender(int Year, String Month ,int Day) {
		clickArrowDownIconInCalender();
		WebDriverUtility webDriverUtility=new WebDriverUtility(driver);
		webDriverUtility.waitForVisible(getYearButton(Year));
		getYearButton(Year).click();
		getMonthButton(Month).click();
		getDayButton(Day).click();
		
	}
	
	public void clickDOBCalendarIcon() {
		DOBCalendarIcon.click();
	}
	
	public void UploadSSNfile(String filePath) {
		uploadSSNfileInput.sendKeys(filePath);
	}
	
	public void selectAddressCountry(String countryName) {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	    // 1️⃣ Wait until enabled
	    wait.until(d -> AddressCountryDropDown.isEnabled());

	    // 2️⃣ Scroll & focus
	    ((JavascriptExecutor) driver).executeScript(
	        "arguments[0].scrollIntoView({block:'center'});" +
	        "arguments[0].focus();" +
	        "arguments[0].click();",
	        AddressCountryDropDown
	    );

	    // 3️⃣ Clear & type FULL value
	    AddressCountryDropDown.sendKeys(Keys.chord(Keys.CONTROL, "a"));
	    AddressCountryDropDown.sendKeys(Keys.DELETE);
	    AddressCountryDropDown.sendKeys(countryName); // "India"

	    // 4️⃣ Trigger blur (IMPORTANT)
	    AddressCountryDropDown.sendKeys(Keys.TAB);

	    // 5️⃣ Wait until value is set
	    wait.until(d ->
	        countryName.equals(AddressCountryDropDown.getAttribute("value"))
	    );
	}



	public void UploadPhotofile(String filePath) {
		uploadPhotofileInput.sendKeys(filePath);
	}
	/*
	public void selectLicenseClassDropDown(String licenseClass ) {
		Select select=new Select(licenseClassDropDown);
		select.deSelectByContainsVisibleText(licenseClass);
	}
	*/

	public void selectLicenseClassDropDown(String licenseClass) {
	    // Click the dropdown to open it
	    licenseClassDropDown.click();
	    
	    // Wait for options to appear and select the one matching 'licenseClass'
	    WebDriverUtility webDriverUtility = new WebDriverUtility(driver);
	    
	    // Assuming options are <li role="option"> elements inside the dropdown menu
	    WebElement option = driver.findElement(
	        By.xpath("//li[@role='option' and normalize-space()='" + licenseClass + "']")
	    );
	    
	    webDriverUtility.waitForVisible(option);
	    option.click();
	}
	
	public void selectYearsOfComExpDropDown(String yearsOfComExp) {
	    yearsOfComExpDropDown.click();

	    WebDriverUtility webDriverUtility = new WebDriverUtility(driver);

	    WebElement option = driver.findElement(
	        By.xpath("//li[@role='option' and normalize-space()='" + yearsOfComExp + "']")
	    );

	    webDriverUtility.waitForVisible(option);
	    option.click();
	}

	
	public void clickJoiningDateCalendarIcon() {
		JoiningDateCalendarIcon.click();
	}
/*
	public void selectJobTypeDropDown(String JobType ) {
		Select select=new Select(jobTypeDropDown);
		select.selectByContainsVisibleText(JobType);
		
	}
	*/
	
	public void selectJobTypeDropDown(String jobType) {
	    jobTypeDropDown.click();

	    WebDriverUtility webDriverUtility = new WebDriverUtility(driver);

	    WebElement option = driver.findElement(
	        By.xpath("//li[@role='option' and normalize-space()='" + jobType + "']")
	    );

	    webDriverUtility.waitForVisible(option);
	    option.click();
	}

	
	public void UploadTWICfile(String filePath) {
		uploadTWICfileInput.sendKeys(filePath);
	}

	
	public void enterTwicCardNo(String twic) {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    WebElement twicInput = wait.until(driver -> {
	        WebElement el = driver.findElement(By.xpath("//input[@name='twicCardNo']"));
	        return (el.isDisplayed() && el.isEnabled()) ? el : null;
	    });

	    // Scroll into view (important for MUI)
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].scrollIntoView({block:'center'});", twicInput);

	    // Use JS to remove readonly/disabled if UI is buggy
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].removeAttribute('readonly'); arguments[0].removeAttribute('disabled');", twicInput);

	    twicInput.clear();
	    twicInput.sendKeys(twic);
	}
	public void enterTwicCardNoJS(String twic) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript(
	        "document.querySelector(\"input[name='twicCardNo']\").value=arguments[0];",
	        twic
	    );
	}


	/*
	public void clickTwicExpDateCalendarIcon() {
		TwicExpDateCalendarIcon.click();
	}
	*/
	public void selectdriverTypeDropDown(String driverType ) {
		util.safeClick2(jobTypeDropDown);
	    selectMuiDropdownValue(jobTypeDropDown, driverType);
	}

	public void selectMuiDropdownValue(WebElement dropdown, String optionText) {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    // 1️⃣ Scroll & open dropdown safely
	    js.executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
	    js.executeScript("arguments[0].focus();", dropdown);
	    js.executeScript("arguments[0].click();", dropdown);

	    // 2️⃣ Wait for ANY visible listbox (do NOT store it)
	    wait.until(ExpectedConditions.presenceOfElementLocated(
	        By.xpath("//ul[@role='listbox']")
	    ));

	    // 3️⃣ Locate option FRESH from DOM (CRITICAL)
	    By optionLocator = By.xpath(
	        "//li[@role='option' and normalize-space()='" + optionText + "']"
	    );

	    WebElement option = wait.until(
	        ExpectedConditions.elementToBeClickable(optionLocator)
	    );

	    js.executeScript("arguments[0].click();", option);
	}

	
	public void selectinterestsDropDown(String interests ) {
		util.safeClick2(interestsDropDown);
		selectMuiDropdownValue(interestsDropDown, interests);
		
	}
	
	public void clickTeamUpYesRadioButton() {
		TeamUpYesRadioButton.click();
	}
	
	public void clickTeamUpNoRadioButton() {
		TeamUpNoRadioButton.click();
	}
	
	public void selectleadSourceDropDown(String leadSource ) {
		util.safeClick2(leadSourceDropDown);
		selectMuiDropdownValue(leadSourceDropDown, leadSource);
		
		
	}
	
	public void clickSubmitButton() {
		submitButton.click();
	}

	public void closeOpenDropdown() {
	    Actions actions = new Actions(driver);
	    actions.sendKeys(Keys.ESCAPE).perform();
	}
	@FindBy(xpath = "//input[@name='twicExpDate']")
	private WebElement TwicExpDateInput;

	
	public void clickTwicExpDateCalendarIcon() {
	    WebDriverUtility util = new WebDriverUtility(driver);

	    util.waitForVisible(TwicExpDateInput);

	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].scrollIntoView({block:'center'});", TwicExpDateInput);

	    TwicExpDateInput.click();
	}
//----------------------------------------------------------------------------
    @FindBy(id = "mui-component-select-driverType")
    private WebElement driverTypeDropdown;
	
    @FindBy(id = "mui-component-select-interests")
    private WebElement interestsDropdown;
   
    public void selectDriverType(String value) {
    	util.safeClick2(driverTypeDropDown);
        selectMuiDropdownValue(driverTypeDropdown, value);
    }

    public void selectInterests(String value) {
    	util.safeClick2(interestsDropDown);
        selectMuiDropdownValue(interestsDropdown, value);
    }
    public void selectMuiDropdownValue(By selectLocator, String value) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // 1. Click the MUI Select
        WebElement select = wait.until(ExpectedConditions.elementToBeClickable(selectLocator));
        select.click();

        // 2. Wait for MUI listbox (portal) to appear
        By listBox = By.xpath("//ul[@role='listbox']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(listBox));

        // 3. Locate option globally (NOT relative to select)
        By optionLocator = By.xpath(
            "//li[@role='option' and normalize-space()='" + value + "']"
        );

        WebElement option = wait.until(
            ExpectedConditions.elementToBeClickable(optionLocator)
        );

        // 4. Click using Actions (more reliable for MUI)
        new Actions(driver)
                .moveToElement(option)
                .click()
                .perform();

        // 5. Wait for dropdown to close (important)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(listBox));
    }




}