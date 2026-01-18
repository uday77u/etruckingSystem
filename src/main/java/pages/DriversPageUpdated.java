package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.WebDriverUtility;

public class DriversPageUpdated extends BasePage {

    private WebDriverUtility util;

    public DriversPageUpdated(WebDriver driver) {
        super(driver);
        this.util = new WebDriverUtility(driver);
    }

    /* ==========================================================
                          LOCATORS
       ========================================================== */

    // Buttons
    @FindBy(xpath = "//button[text()='Add Driver']")
    private WebElement addDriverButton;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    // File inputs
    @FindBy(xpath = "(//input[@type='file'])[1]")
    private WebElement dlFileInput;

    @FindBy(xpath = "(//input[@type='file'])[2]")
    private WebElement ssnFileInput;

    @FindBy(xpath = "(//input[@type='file'])[3]")
    private WebElement photoFileInput;

    @FindBy(xpath = "(//input[@type='file'])[4]")
    private WebElement twicFileInput;

    // Text fields
    @FindBy(name = "drivingLicense")
    private WebElement licenseNumberInput;

    @FindBy(xpath = "//*[name()='svg' and @data-testid='CalendarIcon']")
    private WebElement dobInput;

    @FindBy(name = "CDLExpDate")
    private WebElement cdlExpiryInput;

    @FindBy(name = "JoiningDate")
    private WebElement joiningDateInput;

    @FindBy(name = "SSN")
    private WebElement ssnInput;

    @FindBy(name = "twicCardNo")
    private WebElement twicCardNoInput;

    @FindBy(xpath = "//input[contains(@placeholder,'First')]")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[contains(@placeholder,'Last')]")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[contains(@placeholder,'Email')]")
    private WebElement emailInput;

    @FindBy(xpath = "//input[contains(@placeholder,'Phone')]")
    private WebElement phoneInput;

    @FindBy(xpath = "//input[contains(@placeholder,'ZIP')]")
    private WebElement zipInput;

    @FindBy(xpath = "//input[contains(@placeholder,'City')]")
    private WebElement cityInput;

    @FindBy(xpath = "//input[contains(@placeholder,'State')]")
    private WebElement stateInput;

    @FindBy(xpath = "//input[contains(@placeholder,'Address')]")
    private WebElement addressInput;

    @FindBy(xpath = "//input[contains(@name,'addressDetails.country')]")
    private WebElement countryInput;

    // Radio buttons
    @FindBy(xpath = "//input[@name='readyForTeamUp' and @value='true']")
    private WebElement teamUpYesRadio;

    @FindBy(xpath = "//input[@name='readyForTeamUp' and @value='false']")
    private WebElement teamUpNoRadio;

    // MUI Dropdown triggers
    @FindBy(id = "mui-component-select-licenseClass")
    private WebElement licenseClassDropdown;

    @FindBy(id = "mui-component-select-yearsOfComExp")
    private WebElement experienceDropdown;

    @FindBy(id = "mui-component-select-jobType")
    private WebElement jobTypeDropdown;

    @FindBy(id = "mui-component-select-driverType")
    private WebElement driverTypeDropdown;

    @FindBy(id = "mui-component-select-interests")
    private WebElement interestsDropdown;

    @FindBy(id = "mui-component-select-leadSource")
    private WebElement leadSourceDropdown;

    // Calendar icons
    @FindBy(xpath = "(//*[name()='svg' and @data-testid='CalendarIcon'])[1]")
    private WebElement cdlCalendarIcon;

    @FindBy(xpath = "(//*[name()='svg' and @data-testid='CalendarIcon'])[2]")
    private WebElement dobCalendarIcon;

    @FindBy(xpath = "(//*[name()='svg' and @data-testid='CalendarIcon'])[3]")
    private WebElement joiningDateCalendarIcon;

    @FindBy(xpath = "(//*[name()='svg' and @data-testid='CalendarIcon'])[4]")
    private WebElement twicCalendarIcon;

    // Calendar switch view button
   /* @FindBy(xpath = "//button[contains(@class,'MuiPickersCalendarHeader-switchViewButton')]")
    private WebElement calendarSwitchViewButton;
    
    @FindBy(xpath =
    		 "//div[@role='dialog']//button[contains(@class,'MuiPickersCalendarHeader-switchViewButton')]")
    		private WebElement calendarSwitchViewButton;*/
    private final By calendarSwitchViewBtn =
    	    By.xpath("//div[@role='dialog']//button[contains(@class,'MuiPickersCalendarHeader-switchViewButton')]");



    /* ==========================================================
                          DYNAMIC LOCATORS
       ========================================================== */

    private WebElement yearButton(int year) {
        return driver.findElement(By.xpath(
                "//button[contains(@class,'MuiPickersYear-yearButton') and text()='" + year + "']"));
    }

    private WebElement monthButton(String month) {
        return driver.findElement(By.xpath(
                "//button[contains(@class,'MuiPickersMonth-monthButton') and text()='" + month + "']"));
    }

    private WebElement dayButton(int day) {
        return driver.findElement(By.xpath(
                "//button[contains(@class,'MuiPickersDay-dayWithMargin') and text()='" + day + "']"));
    }

    private WebElement dropdownOption(String value) {
        return driver.findElement(By.xpath("//li[normalize-space()='" + value + "']"));
    }

    /* ==========================================================
                          ACTION METHODS
       ========================================================== */

    public void clickAddDriver() {
        util.waitForClickable(addDriverButton);
        addDriverButton.click();
    }

    public void uploadDL(String path) {
        dlFileInput.sendKeys(path);
    }

    public void uploadSSN(String path) {
        ssnFileInput.sendKeys(path);
    }

    public void uploadPhoto(String path) {
        photoFileInput.sendKeys(path);
    }

    public void uploadTWIC(String path) {
        twicFileInput.sendKeys(path);
    }

    public void enterFirstName(String value) {
        firstNameInput.sendKeys(value);
    }

    public void enterLastName(String value) {
        lastNameInput.sendKeys(value);
    }

    public void enterEmail(String value) {
        emailInput.sendKeys(value);
    }

    public void enterPhone(String value) {
        phoneInput.sendKeys(value);
    }

    public void enterSSN(String value) {
        ssnInput.sendKeys(value);
    }

    public void enterAddress(String value) {
        addressInput.sendKeys(value);
    }

    public void enterCity(String value) {
        cityInput.clear();
        cityInput.sendKeys(value);
    }

    public void enterState(String value) {
        stateInput.clear();
        stateInput.sendKeys(value);
    }

    public void enterZip(String value) {
        zipInput.sendKeys(value);
    }

    public void selectCountry(String country) {
        countryInput.sendKeys(country);
        util.waitForClickable(dropdownOption(country));
        dropdownOption(country).click();
    }

    /* ======================= DROPDOWNS ======================= */

   /* private void selectMuiDropdown(WebElement dropdown, String value) {
        util.waitForWebElementToBeClickable(dropdown);
        dropdown.click();
        util.waitForVisibilityOfWebelement(dropdownOption(value));
        dropdownOption(value).click();
    }*/
    
    public void selectMuiDropdown(WebElement dropdown, String optionText) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Scroll into view FIRST
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", dropdown
        );

        // Wait until visible (not clickable)
        wait.until(ExpectedConditions.visibilityOf(dropdown));

        // JS click to bypass label overlay
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].click();", dropdown
        );

        // Now wait for dropdown options
        By optionLocator = By.xpath(
            "//ul[@role='listbox']//li[normalize-space()='" + optionText + "']"
        );

        WebElement option = wait.until(
            ExpectedConditions.visibilityOfElementLocated(optionLocator)
        );

        option.click();
    }


    public void selectLicenseClass(String value) {
        selectMuiDropdown(licenseClassDropdown, value);
    }

    public void selectExperience(String value) {
        selectMuiDropdown(experienceDropdown, value);
    }

    public void selectJobType(String value) {
        selectMuiDropdown(jobTypeDropdown, value);
    }

    public void selectDriverType(String value) {
        selectMuiDropdown(driverTypeDropdown, value);
    }

    public void selectInterests(String value) {
        selectMuiDropdown(interestsDropdown, value);
    }

    public void selectLeadSource(String value) {
        selectMuiDropdown(leadSourceDropdown, value);
    }

    /* ======================= CALENDAR ======================= */

   /* public void selectDate(WebElement calendarIcon, int year, String month, int day) throws InterruptedException {
        calendarIcon.click();

        util.waitForWebElementToBeClickable(calendarSwitchViewButton);
        System.out.println("clicking on calendarSwitchViewButton");
        calendarSwitchViewButton.click();

        Thread.sleep(8000);
        util.waitForVisibilityOfWebelement(yearButton(year));
        System.out.println("clicking on yearButton");
        yearButton(year).click();

        util.waitForVisibilityOfWebelement(monthButton(month));
        System.out.println("clicking on monthButton");
        monthButton(month).click();

        util.waitForVisibilityOfWebelement(dayButton(day));
        System.out.println("clicking on dayButton");
        dayButton(day).click();
    }*/
    
    public void selectDate(WebElement input, int year, String month, int day) {

        util.waitForClickable(input);
        input.click();

        WebElement switchView = driver.findElement(By.xpath(
            "//div[@role='dialog']//button[contains(@class,'MuiPickersCalendarHeader-switchViewButton')]"));

        util.waitForClickable(switchView);
        switchView.click();

        selectYearByScroll(year);

        driver.findElement(By.xpath(
            "//div[@role='dialog']//button[text()='" + month + "']")).click();

        driver.findElement(By.xpath(
            "//div[@role='dialog']//button[text()='" + day + "']")).click();
    }
    /*
    public void selectDate(WebElement input, int year, String month, int day) {

        util.waitForWebElementToBeClickable(input);
        input.click();

        // ALWAYS re-find dialog elements
        util.waitForVisibility(calendarSwitchViewBtn,10);
        WebElement switchViewButton = driver.findElement(calendarSwitchViewBtn);

        util.waitForWebElementToBeClickable(switchViewButton);
        switchViewButton.click();

        selectYearByScroll(year);

        driver.findElement(By.xpath(
            "//div[@role='dialog']//button[text()='" + month + "']")).click();

        driver.findElement(By.xpath(
            "//div[@role='dialog']//button[text()='" + day + "']")).click();
    }*/
    /*
    public void selectDate(WebElement input, int year, String month, int day) {

        util.waitForWebElementToBeClickable(input);
        input.click();

        // Switch to year view
        WebElement switchBtn =
            util.waitForElementToBeVisible(calendarSwitchViewBtn);
        switchBtn.click();

        // Select year (CLICK ONLY)
        selectYearByScroll(year);

        // Select month
        driver.findElement(By.xpath(
            "//div[@role='dialog']//button[text()='" + month + "']")).click();

        // Select day
        driver.findElement(By.xpath(
            "//div[@role='dialog']//button[text()='" + day + "']")).click();
    }

*/



    public void selectDOB(int year, String month, int day) throws InterruptedException {
        selectDate(dobCalendarIcon, year, month, day);
    }

    public void selectCDLExpiry(int year, String month, int day) throws InterruptedException {
        selectDate(cdlCalendarIcon, year, month, day);
    }

    public void selectJoiningDate(int year, String month, int day) throws InterruptedException {
        selectDate(joiningDateCalendarIcon, year, month, day);
    }

    public void selectTwicExpiry(int year, String month, int day) throws InterruptedException {
        selectDate(twicCalendarIcon, year, month, day);
    }

    /* ======================= RADIO & SUBMIT ======================= */

    public void selectTeamUpYes() {
        teamUpYesRadio.click();
    }

    public void selectTeamUpNo() {
        teamUpNoRadio.click();
    }

    public void clickSaveAndNext() {
        submitButton.click();
    }
    
    //helper
    private void selectYearByScroll(int year) {

        By yearLocator = By.xpath(
            "//div[@role='dialog']//button[contains(@class,'MuiPickersYear-yearButton') and text()='" + year + "']"
        );

        WebElement yearList = driver.findElement(
            By.xpath("//div[@role='dialog']//div[contains(@class,'MuiPickersYear-root')]")
        );

        for (int i = 0; i < 30; i++) {
            try {
                WebElement yearButton = driver.findElement(yearLocator);
                util.waitForClickable(yearButton);
                yearButton.click();
                return;
            } catch (org.openqa.selenium.NoSuchElementException e) {
                yearList.sendKeys(Keys.PAGE_DOWN);
            }
        }

        throw new RuntimeException("❌ Year not found in DatePicker: " + year);
    }
    /*
    public void selectYearByScroll(int year) {

        By yearLocator = By.xpath(
            "//div[@role='dialog']//div[contains(@class,'MuiPickersYear-root') and text()='" + year + "']"
        );

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement yearElement = wait.until(
            ExpectedConditions.elementToBeClickable(yearLocator)
        );

        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", yearElement
        );

        yearElement.click();
    }*/
  /*  
    public void selectYearByScroll(int year) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By yearLocator = By.xpath(
            "//div[@role='dialog']//div[contains(@class,'MuiPickersYear-root') and text()='" + year + "']"
        );

        // Always locate FRESH element
        WebElement yearElement = wait.until(
            ExpectedConditions.presenceOfElementLocated(yearLocator)
        );

        // Scroll into view (MUI virtual list)
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", yearElement
        );

        // Re-locate AFTER scroll to avoid stale element
        yearElement = wait.until(
            ExpectedConditions.elementToBeClickable(yearLocator)
        );

        yearElement.click();
    }

*/
    

}
