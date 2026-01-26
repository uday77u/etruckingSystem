package utilities;

import java.io.File;
import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MuiActionsUtil {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final Logger logger =
            LogManager.getLogger(MuiActionsUtil.class);

    private static final int DEFAULT_TIMEOUT = 40;
    private static final int MAX_RETRIES = 3;

    // =============================
    // Constructor
    // =============================
    public MuiActionsUtil(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    // =============================
    // CORE MUI SYNCHRONIZATION
    // =============================

    /**
     * Waits until MUI backdrops and transitions finish
     */
    public void waitForMuiIdle() {
        wait.until(d -> (Boolean) ((JavascriptExecutor) d)
                .executeScript(
                        "return document.querySelectorAll('.MuiBackdrop-root').length === 0"));
    }

    /**
     * Waits until element is visible and stable
     */
    private WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement waitForVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    private void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    // =============================
    // MUI CLICK
    // =============================
    
    private WebElement waitForClickable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }
    
    public void muiClick(By locator) {
        muiClick1(waitForClickable(locator));
    }


    public void muiClick(WebElement element) {
        int attempts = 0;

        while (attempts < MAX_RETRIES) {
            try {
                waitForMuiIdle();
                scrollIntoView(element);
                wait.until(ExpectedConditions.elementToBeClickable(element));
                element.click();
                return;
            } catch (Exception e) {
                logger.warn("MUI click attempt {} failed", attempts + 1);
                attempts++;
            }
        }

        logger.warn("MUI click fallback to JS");
        jsClick(element);
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }
    //-------------------------
    private boolean isAccordion(WebElement element) {
        return element.getAttribute("aria-expanded") != null;
    }
    /*
    private void waitForAccordionToExpand(WebElement element) {
        wait.until(driver -> "true".equals(element.getAttribute("aria-expanded")));
    }
    */

    public void muiClick1(WebElement element) {
        int attempts = 0;

        while (attempts < MAX_RETRIES) {
            try {
                scrollIntoView(element);
                wait.until(ExpectedConditions.elementToBeClickable(element));
                element.click();

                // 🔍 AUTO accordion detection
                if (isAccordion(element)) {
                    logger.info("Detected MUI accordion, waiting for expansion");
                    waitForAccordionContent(element);
                }

                return;
            } catch (Exception e) {
                logger.warn("MUI click attempt {} failed", attempts + 1);
                attempts++;
            }
        }

        logger.warn("MUI click fallback to JS");
        jsClick(element);

        // JS click fallback also needs accordion wait
        if (isAccordion(element)) {
            waitForAccordionContent(element);
        }
    }

    // =============================
    // MUI TYPE (TEXTFIELD)
    // =============================
/*
    public void muiType(By inputLocator, String text) {
        muiType(waitForVisible(inputLocator), text);
    }
*/
    public void muiType(WebElement input, String text) {
        waitForMuiIdle();
        scrollIntoView(input);
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.DELETE);
        input.sendKeys(text);
    }
    
    public void muiType(By inputLocator, String value) {

        WebElement input = wait.until(
                ExpectedConditions.presenceOfElementLocated(inputLocator));

        // MUI TextField wrapper (click target)
        WebElement container =
                input.findElement(By.xpath("./ancestor::div[contains(@class,'MuiFormControl-root')]"));

        // Scroll container, not input
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", container);

        // Click container to focus input
        wait.until(ExpectedConditions.elementToBeClickable(container));
        container.click();

        // Clear safely
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.DELETE);

        // Type value
        input.sendKeys(value);
    }



    // =============================
    // MUI SELECT / DROPDOWN
    // =============================

    public void muiSelectByText(By dropdown, String optionText) {
        muiClick(dropdown);
        selectMuiOption(optionText);
    }

    public void muiSelectByText(WebElement dropdown, String optionText) {
        muiClick(dropdown);
        selectMuiOption(optionText);
    }

    private void selectMuiOption(String optionText) {
        By option =By.xpath("//li[@role='option' and normalize-space()='" + optionText + "']");  //By.xpath("//li[normalize-space()='" + optionText + "']");
        WebElement optionEl = waitForVisible(option);
        muiClick(optionEl);
    }

    // =============================
    // MUI AUTOCOMPLETE
    // =============================

    public void muiAutocomplete(By input, String value, String optionText) {
        muiType(input, value);
        selectMuiContainsOption(optionText);
    }

    public void muiAutocomplete(WebElement input, String value, String optionText) {
        muiType(input, value);
        selectMuiContainsOption(optionText);
    }

    private void selectMuiContainsOption(String optionText) {
        By option = By.xpath("//li[contains(.,'" + optionText + "')]");
        WebElement optionEl = waitForVisible(option);
        muiClick(optionEl);
    }

    // =============================
    // MUI CHECKBOX / SWITCH
    // =============================

    public void muiToggleByLabel(String labelText) {
        By label = By.xpath("//label[.//text()[normalize-space()='" + labelText + "']]");
        muiClick(label);
    }

    // =============================
    // MUI MENU / CONTEXT MENU
    // =============================

    public void muiMenuSelect(String menuItemText) {
        By item = By.xpath("//li[normalize-space()='" + menuItemText + "']");
        muiClick(item);
    }

    // =============================
    // MUI DIALOG / MODAL
    // =============================

    public void waitForMuiDialog() {
        By dialog = By.xpath("//div[@role='dialog']");
        waitForVisible(dialog);
    }

    public void closeMuiDialog() {
        By closeBtn = By.xpath("//button[@aria-label='Close']");
        muiClick(closeBtn);
    }

    // =============================
    // MUI CHIP (MULTI SELECT)
    // =============================

    public void removeMuiChip(String chipText) {
        By chipDelete = By.xpath(
                "//div[contains(@class,'MuiChip-root') and .//text()='" + chipText + "']" +
                "//button");
        muiClick(chipDelete);
    }

    // =============================
    // MUI TOOLTIP
    // =============================

    public String getMuiTooltipText(By trigger) {
        Actions actions = new Actions(driver);
        actions.moveToElement(waitForVisible(trigger)).perform();

        By tooltip = By.xpath("//div[contains(@class,'MuiTooltip-tooltip')]");
        return waitForVisible(tooltip).getText();
    }

    // =============================
    // RETRY GENERIC MUI ACTION
    // =============================

    public void retryMuiAction(Runnable action) {
        int retries = MAX_RETRIES;
        while (retries-- > 0) {
            try {
                action.run();
                return;
            } catch (Exception e) {
                logger.warn("Retrying MUI action...");
            }
        }
        throw new RuntimeException("MUI action failed after retries");
    }
    
//==============================Radio button=====================================
    /**
     * React/MUI-safe Radio Button selector
     *
     * @param name  radio group name attribute
     * @param value radio value attribute (true / false)
     */
    public void selectRadio(String name, String value) {

        By radioInput = By.xpath("//input[@type='radio' and @name='" + name + "' and @value='" + value + "']");
        By radioLabel = By.xpath("//label[.//input[@type='radio' and @name='" + name + "' and @value='" + value + "']]");
        logger.info("Selecting MUI radio [{} = {}]", name, value);

        // 1️⃣ Wait for label to be clickable
        WebElement label = wait.until(ExpectedConditions.elementToBeClickable(radioLabel));

        // 2️⃣ Scroll & click label (React event target)
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", label);
                

        try {
            label.click();
        } catch (Exception e) {
            logger.warn("Standard click failed, falling back to JS click");
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", label);
        }

        // 3️⃣ Wait for React state update (radio checked)
        wait.until(ExpectedConditions.elementToBeSelected(radioInput));
    }
    //------helper
    private void waitForAccordionContent(WebElement accordion) {
        wait.until(d -> {
            String expanded = accordion.getAttribute("aria-expanded");
            if (!"true".equals(expanded)) return false;

            return ((JavascriptExecutor) d)
                .executeScript(
                    "return arguments[0].closest('.MuiAccordion-root')" +
                    ".querySelectorAll('.MuiAccordionDetails-root *').length > 0;",
                    accordion
                ).equals(true);
        });
    }
//File upload utility
    public void uploadFile(By fileInput, String absolutePath) {
        WebElement input = waitForPresent(fileInput);

        // Make sure it's interactable
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].style.display='block';", input);
        File file = new File(absolutePath);

        if (!file.exists()) {
            throw new RuntimeException("❌ File not found: " + file.getAbsolutePath());
        }
        input.sendKeys(absolutePath);
    }
    public WebElement waitForPresent(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
 // =============================
 // MUI DATE PICKER
 // =============================

 /**
  * Sets MUI DatePicker by typing value (recommended)
  * Works for DesktopDatePicker / MobileDatePicker
  *
  * @param inputLocator date input field
  * @param dateValue    date string (e.g. 01/25/2026 or 2026-01-25)
  */
 public void muiDatePickerType(By inputLocator, String dateValue) {
     WebElement input = waitForVisible(inputLocator);

     waitForMuiIdle();
     scrollIntoView(input);

     // Focus input (important for MUI)
     input.click();

     // Clear existing value
     input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
     input.sendKeys(Keys.DELETE);

     // Type date
     input.sendKeys(dateValue);

     // Blur to trigger React state update
     input.sendKeys(Keys.TAB);
 }

 /**
  * Selects date from MUI calendar popup
  *
  * @param datePickerButton calendar icon button
  * @param day              day of month (1–31)
  */
 public void muiDatePickerSelectDay(By datePickerButton, int day) {
     // Open calendar
     muiClick(datePickerButton);

     // Wait for calendar dialog
     By calendar = By.xpath("//div[contains(@class,'MuiPickersPopper-root') or @role='dialog']");
     waitForVisible(calendar);

     // Select day
     By dayLocator = By.xpath(
         "//button[not(@disabled) and normalize-space()='" + day + "']"
     );

     WebElement dayBtn = waitForClickable(dayLocator);
     muiClick(dayBtn);

     waitForMuiIdle();
 }

  
}