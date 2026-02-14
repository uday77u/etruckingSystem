package utilities;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.function.BooleanSupplier;

public class UIActions {

    private final WebDriver driver;
    private final JavascriptExecutor js;
    private final WebDriverWait wait;

    private static final int MAX_RETRIES = 3;
    private static final Duration SHORT_WAIT = Duration.ofSeconds(2);
    private static final Duration LONG_WAIT  = Duration.ofSeconds(10);

    public UIActions(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, LONG_WAIT);
    }

    /* =========================================================
       PUBLIC API — USE THESE IN TESTS
       ========================================================= */

    public void click(WebElement element) {
        retry("CLICK", element,
            () -> nativeClick(element),
            () -> keyboardClick(element),
            () -> jsClick(element)
        );
    }

    public void type(WebElement element, String value) {
        retry("TYPE", element,
            () -> normalType(element, value),
            () -> keyboardPaste(element, value),
            () -> reactType(element, value),
            () -> contentEditableType(element, value)
        );
    }

    public void select(WebElement element, String value) {
        retry("SELECT", element,
            () -> nativeSelect(element, value),
            () -> customDropdownSelect(element, value) //,// ⭐ use ONLY this
           // () -> jsSelect(element, value)
        );


        waitForMuiBackdropToDisappear();   // 🔥 CRITICAL
    }

    public void pickDate(WebElement input, String value) {
        retry("DATE_PICKER", input,
            () -> {
                type(input, value);
                return verifyValue(input, value);
            },
            () -> calendarPopupPick(value)
        );
    }


    /* =========================================================
       CLICK STRATEGIES
       ========================================================= */

    private boolean nativeClick(WebElement el) {
        log("Native click");
        waitUntilClickable(el);
        el.click();
        waitForMuiBackdropToDisappear();   // 💎 stability boost
        waitForDomIdle();
        return true;
    }

    private boolean keyboardClick(WebElement el) {
        log("Keyboard ENTER click");
        waitUntilVisible(el);
        el.sendKeys(Keys.ENTER);
        waitForDomIdle();
        return true;
    }

    private boolean jsClick(WebElement el) {
        log("JS click");
        js.executeScript(
            "arguments[0].dispatchEvent(new MouseEvent('mousedown',{bubbles:true}));" +
            "arguments[0].dispatchEvent(new MouseEvent('mouseup',{bubbles:true}));" +
            "arguments[0].dispatchEvent(new MouseEvent('click',{bubbles:true}));",
            el
        );
        waitForDomIdle();
        return true;
    }

    
    /* =========================================================
       TYPE STRATEGIES
       ========================================================= */

    private boolean normalType(WebElement el, String value) {
        log("sendKeys typing");
        waitForMuiBackdropToDisappear();  // ⭐ add here
        waitUntilVisible(el);
        el.clear();
        el.sendKeys(value);
        return verifyValue(el, value);
    }

    private boolean keyboardPaste(WebElement el, String value) {
        log("Keyboard paste typing");
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        el.sendKeys(value);
        return verifyValue(el, value);
    }

    private boolean reactType(WebElement el, String value) {
        if (!isReactControlled(el)) return false;
        log("React-safe typing");

        js.executeScript(
            "const input = arguments[0];" +
            "const value = arguments[1];" +
            "const lastValue = input.value;" +
            "input.value = value;" +
            "const event = typeof InputEvent === 'function'" +
            " ? new InputEvent('input',{bubbles:true})" +
            " : new Event('input',{bubbles:true});" +
            "const tracker = input._valueTracker;" +
            "if (tracker) tracker.setValue(lastValue);" +
            "input.dispatchEvent(event);",
            el, value
        );
        waitForDomIdle();
        return verifyValue(el, value);
    }

    private boolean contentEditableType(WebElement el, String value) {
        Boolean editable = (Boolean) js.executeScript(
            "return arguments[0].isContentEditable === true;",
            el
        );
        if (!Boolean.TRUE.equals(editable)) return false;

        log("contenteditable typing");
        js.executeScript(
            "arguments[0].focus();" +
            "document.execCommand('selectAll', false, null);" +
            "document.execCommand('insertText', false, arguments[1]);" +
            "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));",
            el, value
        );
        waitForDomIdle();
        return true;
    }

    /* =========================================================
       DROPDOWN STRATEGIES
       ========================================================= */

    private boolean nativeSelect(WebElement el, String value) {
        if (!"select".equalsIgnoreCase(el.getTagName())) return false;
        log("Native <select>");
        new Select(el).selectByVisibleText(value);
        waitForDomIdle();
        return true;
    }
/*
    private boolean customDropdownSelect(WebElement el, String value) {
        log("Custom dropdown");
        click(el);
        el.sendKeys(value);
        el.sendKeys(Keys.ENTER);
        waitForDomIdle();
        return true;
    }*/
    private boolean customDropdownSelect(WebElement el, String value) {

        log("Custom dropdown");

        click(el);

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//li[@role='option' and normalize-space()='" + value + "']")
        ));

        option.click();

        // 👇 FORCE DROPDOWN CLOSE
       // new Actions(driver).sendKeys(Keys.ESCAPE).perform(); //closing entire form drawer
        driver.switchTo().activeElement().sendKeys(Keys.TAB);

        waitForMuiBackdropToDisappear();

        return true;
    }
    private boolean customDropdownSelectNew(WebElement el, String value) {

        click(el);

        // Wait dropdown portal open
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//li[@role='option' and normalize-space()='" + value + "']")
        ));

        scrollIntoView(option);

        option.click();

        waitForMuiBackdropToDisappear();

        return true;
    }
    private void scrollIntoView(WebElement el) {
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

/*
    private boolean customDropdownSelectNew(WebElement el, String value) {

        click(el);

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//li[@role='option' and normalize-space()='" + value + "']")
        ));

        option.click();

        waitForMuiBackdropToDisappear();

        return true;
    }*/

    private boolean jsSelect(WebElement el, String value) {
        log("JS dropdown select");
        js.executeScript(
            "arguments[0].value = arguments[1];" +
            "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));",
            el, value
        );
        waitForDomIdle();
        return true;
    }
/*
    private void waitForMuiBackdropToDisappear() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector(".MuiBackdrop-root")
                ));
        } catch (TimeoutException ignored) {}
    }*/
    private void waitForMuiBackdropToDisappear() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(driver -> {

                    List<WebElement> overlays = driver.findElements(
                        By.cssSelector(".MuiBackdrop-root")
                    );

                    for (WebElement overlay : overlays) {

                        String opacity = overlay.getCssValue("opacity");
                        String hidden  = overlay.getAttribute("aria-hidden");

                        if (!"0".equals(opacity) && !"true".equals(hidden)) {
                            return false; // overlay still blocking
                        }
                    }
                    return true;
                });

        } catch (TimeoutException ignored) {}
    }

    /* =========================================================
       CALENDAR STRATEGY
       ========================================================= */

    private boolean calendarPopupPick(String dayText) {
        log("Calendar popup pick");
        List<WebElement> days = driver.findElements(
            By.xpath("//td[normalize-space()='" + dayText + "']")
        );
        if (days.isEmpty()) return false;
        click(days.get(0));
        return true;
    }

    /* =========================================================
       RETRY ENGINE (ENTERPRISE CORE)
       ========================================================= */

    @SafeVarargs
    private final void retry(String action, WebElement el, BooleanSupplier... steps) {
        for (int i = 1; i <= MAX_RETRIES; i++) {
            log(action + " — attempt " + i);

            for (BooleanSupplier step : steps) {
                try {
                    if (step.getAsBoolean()) {
                        log(action + " succeeded");
                        return;
                    }
                } catch (Exception e) {
                    log(action + " step failed: " + e.getClass().getSimpleName());
                }
            }

            waitForDomIdle();
            waitUntilVisible(el);
        }
        throw new RuntimeException(action + " failed after retries");
    }

    /* =========================================================
       WAIT UTILITIES (NO SLEEP)
       ========================================================= */

    private void waitUntilClickable(WebElement el) {
        wait.until(ExpectedConditions.elementToBeClickable(el));
    }

    private void waitUntilVisible(WebElement el) {
        wait.until(ExpectedConditions.visibilityOf(el));
    }

    private void waitForDomIdle() {
        wait.until(driver ->
            (Boolean) js.executeScript(
                "return document.readyState === 'complete';"
            )
        );
    }

    /* =========================================================
       HELPERS
       ========================================================= */

    private boolean isReactControlled(WebElement el) {
       /* return (Boolean) js.executeScript(
            "return !!arguments[0]._valueTracker;",
            el*/
            return (Boolean) js.executeScript(
            	    "const acc = arguments[0].closest('.MuiAccordion-root');" +
            	    "if(!acc) return true;" +  // 🔥 prevents crash
            	    "const details = acc.querySelector('.MuiAccordionDetails-root');" +
            	    "return details && details.children.length > 0;",
            	    el
            	);

    }

    private boolean verifyValue(WebElement el, String expected) {
        String actual = el.getAttribute("value");
        log("Verify value → expected=" + expected + " actual=" + actual);
        return expected.equals(actual);
    }

    private void log(String msg) {
        System.out.println("[UIActions] " + msg);
        // Replace with SLF4J / Log4j in enterprise projects
    }
}