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

    // Constructor
    public MuiActionsUtil(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }



    
    //Waits until element is visible and stable
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
        muiClick(waitForClickable(locator));
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }
    
    //check accordion is expanded
    private boolean isAccordion(WebElement element) {
        return element.getAttribute("aria-expanded") != null;
    }
    
    //muiClick
    public void muiClick(WebElement element) {
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
 /*   
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
*/


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

    public void selectMuiOption(String optionText) {
        By option = By.xpath("//li[@role='option' and contains(normalize-space(),'" + optionText + "')]");

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
    
    //check box 
    public void setCheckbox(By checkboxInput, boolean shouldBeChecked) {

        wait.until(ExpectedConditions.presenceOfElementLocated(checkboxInput));

        boolean isChecked = driver.findElement(checkboxInput).isSelected();

        if (isChecked != shouldBeChecked) {
            muiClick(getCheckboxLabel(checkboxInput));

            if (shouldBeChecked) {
                wait.until(ExpectedConditions.elementToBeSelected(checkboxInput));
            } else {
                wait.until(ExpectedConditions.not(
                        ExpectedConditions.elementToBeSelected(checkboxInput)));
            }
        }
    }
    
    //check box helper method
    private By getCheckboxLabel(By checkboxInput) {

        WebElement input = wait.until(
                ExpectedConditions.presenceOfElementLocated(checkboxInput));

        WebElement label = input.findElement(By.xpath("./ancestor::label"));

        String forAttr = label.getAttribute("for");

        if (forAttr != null) {
            return By.cssSelector("label[for='" + forAttr + "']");
        }

        return By.xpath("//label[.//input[@type='checkbox']]");
    }
    //check box new
    public void setCheckboxNew(By checkboxInput, boolean shouldBeChecked) {

        WebElement input = wait.until(
                ExpectedConditions.presenceOfElementLocated(checkboxInput));

        scrollIntoView(input);

        boolean currentState = input.isSelected();

        if (currentState == shouldBeChecked) {
            logger.info("Checkbox already in desired state: {}", shouldBeChecked);
            return;
        }

        try {

            // ⭐ Click visual checkbox container instead of input
            WebElement clickable = input.findElement(
                    By.xpath("./ancestor::span[contains(@class,'MuiCheckbox-root')]"));

            muiClick(clickable);

        } catch (Exception e) {

            logger.warn("Clickable span not found, falling back to JS click");

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", input);
        }

        // ⭐ Wait React state commit
        wait.until(driver -> driver.findElement(checkboxInput).isSelected() == shouldBeChecked);
    }

    //===================================
    //error fields
    //===================================
    //
    public boolean isMuiFieldError(By inputLocator) {

        WebElement input = wait.until(
                ExpectedConditions.presenceOfElementLocated(inputLocator));

        WebElement wrapper = input.findElement(
                By.xpath("./ancestor::div[contains(@class,'MuiOutlinedInput-root')]"));

        return wrapper.getAttribute("class").contains("Mui-error");
    }
    
    //
    public WebElement goToFirstErrorField() {

        By errorFields = By.xpath("//*[contains(@class,'Mui-error')]");

        List<WebElement> errors = driver.findElements(errorFields);

        if (errors.isEmpty()) {
            logger.info("No error fields found");
            return null;
        }

        WebElement firstError = errors.get(0);

        // Scroll into view
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", firstError);

        // Highlight (visual debugging)
        highlightElement(firstError);

        logger.info("Navigated to first MUI error field");

        return firstError;
    }
    private void highlightElement(WebElement element) {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].style.border='3px solid red'; arguments[0].style.background='rgba(255,0,0,0.1)';",
                element
        );
    }
    public String goToFirstErrorAndGetMessage() {

        WebElement errorField = goToFirstErrorField();

        if (errorField == null)
            return null;

        try {
            WebElement message = errorField.findElement(
                    By.xpath(".//following::p[contains(@class,'MuiFormHelperText-root')][1]"));

            return message.getText();

        } catch (Exception e) {
            logger.warn("Error message not found");
            return null;
        }
    }
    public boolean hasMuiErrors() {
        return !driver.findElements(
                By.xpath("//*[contains(@class,'Mui-error')]")
        ).isEmpty();
    }
    public List<String> getAllMuiErrorMessages() {

        List<WebElement> messages = driver.findElements(
                By.xpath("//p[contains(@class,'MuiFormHelperText-root')]"));

        return messages.stream()
                .map(WebElement::getText)
                .filter(msg -> !msg.isBlank())
                .toList();
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
        File file=new File(absolutePath);

        input.sendKeys(file.getAbsolutePath());
        logger.info("absolutePath: "+absolutePath+" file.getAbsolutePath: "+file.getAbsolutePath());
    }
    public WebElement waitForPresent(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }
 // =============================
 // MUI DATE PICKER
 // =============================
 public void muiSetDate(By inputLocator, String date) {

     WebElement input = wait.until(
             ExpectedConditions.presenceOfElementLocated(inputLocator));

     // Scroll into view
     ((JavascriptExecutor) driver)
             .executeScript("arguments[0].scrollIntoView({block:'center'});", input);

     // Focus input
     wait.until(ExpectedConditions.elementToBeClickable(input));
     input.click();

     // Clear existing value
     input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
     input.sendKeys(Keys.DELETE);

     // Type date (MM/DD/YYYY)
     input.sendKeys(date);

     // Blur to trigger React validation
     input.sendKeys(Keys.TAB);
 }
 /*
 public void muiSetDate(By inputLocator, String date) {

	    WebElement input = wait.until(
	            ExpectedConditions.presenceOfElementLocated(inputLocator));

	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].scrollIntoView({block:'center'});", input);

	    input.click();
	    input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
	    input.sendKeys(Keys.DELETE);
	    input.sendKeys(date);

	    // CRITICAL for React state commit
	    input.sendKeys(Keys.TAB);

	    // Small wait for dependent fields to render
	    try {
	        Thread.sleep(300);
	    } catch (InterruptedException ignored) {}
	}
*/
 public boolean isElementPresent(By locator) {
	    return !driver.findElements(locator).isEmpty();
	}
 public void waitForElementToRender(By locator) {

	    wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	    wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

 /*
 public void openMuiAccordion(By accordionBtn, By innerField) {

	    WebElement accordion = wait.until(
	            ExpectedConditions.elementToBeClickable(accordionBtn));

	    accordion.click();

	    // HARD PROOF accordion is open
	    wait.until(ExpectedConditions.presenceOfElementLocated(innerField));

	    logger.info("MUI accordion fully expanded and content mounted");
	}*/
 public void openMuiAccordion(By accordionBtn, By innerField) {

	    WebElement accordion = wait.until(
	        ExpectedConditions.visibilityOfElementLocated(accordionBtn));

	    // Scroll to avoid overlay issues
	    ((JavascriptExecutor) driver)
	        .executeScript("arguments[0].scrollIntoView({block:'center'});", accordion);

	    accordion.click();

	    // PROOF accordion is open
	    wait.until(ExpectedConditions.presenceOfElementLocated(innerField));

	    logger.info("MUI accordion expanded and inner content rendered");
	}
 
 //vin number input not working
 /*public void muiType(By inputLocator, String value) {
	    wait.until(driver -> {
	        WebElement input = driver.findElement(inputLocator);
	        return input.isDisplayed() && input.isEnabled();
	    });

	    WebElement input = driver.findElement(inputLocator);
	    input.click();
	    input.clear();
	    input.sendKeys(value);
	}*/
 public void waitForInputEnabled(By inputLocator) {
	    wait.until(driver -> {
	        WebElement input = driver.findElement(inputLocator);
	        return input.isDisplayed() && input.isEnabled();
	    });
	}
//--model name not working
 public void muiType(By inputLocator, String value) {

	    WebElement input = wait.until(
	            ExpectedConditions.presenceOfElementLocated(inputLocator));

	    // MUI FormControl wrapper (THIS IS CRITICAL)
	    WebElement container =
	            input.findElement(By.xpath("./ancestor::div[contains(@class,'MuiFormControl-root')]"));

	    // Scroll container, not input
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].scrollIntoView({block:'center'});", container);

	    // Click container to trigger React focus
	    wait.until(ExpectedConditions.elementToBeClickable(container));
	    container.click();

	    // Now type
	    input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
	    input.sendKeys(Keys.DELETE);
	    input.sendKeys(value);
	}

 /*public void muiTypeDeferredInput(By inputLocator, String value) {

	    WebElement input = wait.until(
	            ExpectedConditions.presenceOfElementLocated(inputLocator));

	    // Scroll input itself (NOT FormControl)
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].scrollIntoView({block:'center'});", input);

	    // Force focus using JS (React-safe)
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].focus();", input);

	    // Small pause for React state settle
	    waitForMuiIdle();

	    // Clear & type
	    input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
	    input.sendKeys(Keys.DELETE);
	    input.sendKeys(value);

	    // Commit React state
	    input.sendKeys(Keys.TAB);
	}*/
 public void muiTypeDeferredInput(By inputLocator, String value) {

	    WebElement input = wait.until(
	            ExpectedConditions.presenceOfElementLocated(inputLocator));

	    // Scroll input
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].scrollIntoView({block:'center'});", input);

	    // Focus safely (React controlled input)
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].focus();", input);

	    // ⛔ REMOVE waitForMuiIdle() — THIS WAS BREAKING YOUR TEST

	    // Clear & type
	    input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
	    input.sendKeys(Keys.DELETE);
	    input.sendKeys(value);

	    // Commit React state
	    input.sendKeys(Keys.TAB);
	}

 public void waitForMuiIdle() {
	    try {
	        // Wait briefly for React microtasks, not UI disappearance
	        Thread.sleep(200);
	    } catch (InterruptedException ignored) {}
	}
//--not opening accordian it reaches vin input and failed
 public void expandMuiAccordion(By accordionRoot) {

	    // Wait for accordion root to be present
	    WebElement root = wait.until(
	            ExpectedConditions.presenceOfElementLocated(accordionRoot));

	    // Find Accordion Summary (this is the clickable part)
	    WebElement summary = root.findElement(
	            By.cssSelector(".MuiAccordionSummary-root"));

	    // Scroll into view (important for MUI)
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].scrollIntoView({block:'center'});", summary);

	    // Click when clickable
	    wait.until(ExpectedConditions.elementToBeClickable(summary));
	    summary.click();

	    // Wait until accordion is expanded (aria-expanded = true)
	    wait.until(d ->
	            "true".equals(summary.getAttribute("aria-expanded")));
	}

//address field not working 
 public void muiTypeAndBlur(By inputLocator, String value) {

	    WebElement input = wait.until(
	            ExpectedConditions.presenceOfElementLocated(inputLocator));

	    // Scroll
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].scrollIntoView({block:'center'});", input);

	    // Focus using JS (React-safe)
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].focus();", input);

	    // Clear + type
	    input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
	    input.sendKeys(Keys.DELETE);
	    input.sendKeys(value);

	    // 🔑 CRITICAL: trigger React onBlur / state commit
	    input.sendKeys(Keys.TAB);
	}


}