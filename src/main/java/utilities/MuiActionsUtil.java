package utilities;

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

    private static final int DEFAULT_TIMEOUT = 20;
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

    public void muiClick(By locator) {
        muiClick(waitForVisible(locator));
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

    // =============================
    // MUI TYPE (TEXTFIELD)
    // =============================

    public void muiType(By inputLocator, String text) {
        muiType(waitForVisible(inputLocator), text);
    }

    public void muiType(WebElement input, String text) {
        waitForMuiIdle();
        scrollIntoView(input);
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.DELETE);
        input.sendKeys(text);
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
        By option = By.xpath("//li[normalize-space()='" + optionText + "']");
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
}
