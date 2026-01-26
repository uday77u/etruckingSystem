package utilities;

import java.time.Duration;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

public class WebDriverUtility {

    protected WebDriver driver;
    protected WebDriverWait wait;

    private static final Logger logger =
            LogManager.getLogger(WebDriverUtility.class);

    private static final int DEFAULT_TIMEOUT = 40;

    // =============================
    // Constructor
    // =============================
    public WebDriverUtility(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        PageFactory.initElements(driver, this);
    }

    // =============================
    // WAIT UTILITIES
    // =============================

    /* -------- By Locator -------- */
    public WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /* -------- WebElement (@FindBy) -------- */
    public WebElement waitForVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement fluentWait(By locator, int timeout, int polling) {
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(polling))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        return fluentWait.until(d -> d.findElement(locator));
    }

    // =============================
    // BASIC ACTIONS
    // =============================

    /* -------- Click -------- */
    public void click(By locator) {
        waitForClickable(locator).click();
    }

    public void click(WebElement element) {
        waitForClickable(element).click();
    }

    /* -------- Type -------- */
    public void type(By locator, String text) {
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    public void type(WebElement element, String text) {
        waitForVisible(element).clear();
        element.sendKeys(text);
    }

    public String getText(By locator) {
        return waitForVisible(locator).getText();
    }

    public String getText(WebElement element) {
        return waitForVisible(element).getText();
    }

    // =============================
    // SAFE ACTIONS (ANTI-FLAKY)
    // =============================

    public void safeClick(By locator) {
        safeClick(waitForClickable(locator));
    }

    public void safeClick(WebElement element) {
        try {
            scrollToElement(element);
            element.click();
        } catch (Exception e) {
            logger.warn("Standard click failed, falling back to JS click", e);
            jsClick(element);
        }
    }

    public void safeClick2(WebElement element) {
        try {
            scrollToElement(element);
            waitForClickable(element).click();
            return;
        } catch (Exception e1) {
            logger.warn("Standard click failed, trying keyboard ENTER", e1);
        }

        try {
            element.sendKeys(Keys.ENTER);
            return;
        } catch (Exception e2) {
            logger.warn("Keyboard ENTER failed, trying Actions click", e2);
        }

        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(element).click().perform();
            return;
        } catch (Exception e3) {
            logger.warn("Actions click failed, falling back to JS click", e3);
        }

        jsClick(element);
    }

    public void safeType(By locator, String text) {
        safeType(waitForVisible(locator), text);
    }

    public void safeType(WebElement element, String text) {
        waitForVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    // =============================
    // DROPDOWNS
    // =============================

    public void selectByVisibleText(By locator, String text) {
        new Select(waitForVisible(locator)).selectByVisibleText(text);
    }

    public void selectByVisibleText(WebElement element, String text) {
        new Select(waitForVisible(element)).selectByVisibleText(text);
    }

    public void selectByValue(By locator, String value) {
        new Select(waitForVisible(locator)).selectByValue(value);
    }

    public void selectByIndex(By locator, int index) {
        new Select(waitForVisible(locator)).selectByIndex(index);
    }

    // =============================
    // WINDOW HANDLING
    // =============================

    public void switchToWindowByTitle(String titlePartial) {
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            driver.switchTo().window(window);
            if (driver.getTitle().contains(titlePartial)) {
                return;
            }
        }
        logger.warn("Window not found with title containing: {}", titlePartial);
    }

    // =============================
    // FRAME HANDLING
    // =============================

    public void switchToFrame(int index) {
        driver.switchTo().frame(index);
    }

    public void switchToFrame(By locator) {
        driver.switchTo().frame(waitForVisible(locator));
    }

    public void switchToFrame(WebElement element) {
        driver.switchTo().frame(waitForVisible(element));
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    // =============================
    // ALERT HANDLING
    // =============================

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public void acceptAlert() {
        if (isAlertPresent()) {
            driver.switchTo().alert().accept();
        }
    }

    public void dismissAlert() {
        if (isAlertPresent()) {
            driver.switchTo().alert().dismiss();
        }
    }

    public String getAlertText() {
        return isAlertPresent() ? driver.switchTo().alert().getText() : null;
    }

    // =============================
    // ACTIONS CLASS
    // =============================

    public void mouseHover(By locator) {
        mouseHover(waitForVisible(locator));
    }

    public void mouseHover(WebElement element) {
        new Actions(driver).moveToElement(element).perform();
    }

    public void doubleClick(By locator) {
        doubleClick(waitForVisible(locator));
    }

    public void doubleClick(WebElement element) {
        new Actions(driver).doubleClick(element).perform();
    }

    public void dragAndDrop(By source, By target) {
        new Actions(driver)
                .dragAndDrop(waitForVisible(source), waitForVisible(target))
                .perform();
    }

    // =============================
    // JAVASCRIPT UTILITIES
    // =============================

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollByPixels(int x, int y) {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
    }

    public void jsClick(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }

    // =============================
    // PAGE VALIDATION
    // =============================

    public boolean isCurrentUrlContains(String segment) {
        return driver.getCurrentUrl().contains(segment);
    }

    public boolean isTitleContains(String segment) {
        return driver.getTitle().contains(segment);
    }
}