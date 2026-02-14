package utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;

public class SmartElementActions {

    private final WebDriver driver;
    private final JavascriptExecutor js;
    private final Actions actions;
    private final WebDriverWait wait;

    private static final Logger log =
            Logger.getLogger(SmartElementActions.class.getName());

    private static final int MAX_RETRIES = 3;
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    public SmartElementActions(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    /* =========================================================
                            PUBLIC API
       ========================================================= */

    public void click(WebElement element) {
        retry("CLICK", () -> clickInternal(element));
    }

    public void type(WebElement element, String value) {
        retry("TYPE", () -> typeInternal(element, value));
    }

    public void select(WebElement dropdown, String optionText) {
        retry("SELECT", () -> {
            clickInternal(dropdown);
            WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//li[normalize-space()='" + optionText + "']")
                )
            );
            clickInternal(option);
        });
    }

    /**
     * Address / Autocomplete – must select option
     */
    public void typeAddress(
            WebElement input,
            String addressText,
            String expectedOptionText
    ) {
        retry("ADDRESS", () -> {
            typeMuiStable(input, addressText);

            WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//li[contains(.,'" + expectedOptionText + "')]")
                )
            );
            clickInternal(option);
        });
    }

    /**
     * DatePicker / Calendar
     */
    public void setDate(WebElement input, String dateValue) {
        retry("DATE", () -> {
            if (isMuiCalendarInput(input)) {
                typeMuiStable(input, dateValue);
                input.sendKeys(Keys.TAB);
            } else {
                input.clear();
                input.sendKeys(dateValue);
                input.sendKeys(Keys.TAB);
            }
        });
    }

    /* =========================================================
                        INTERNAL CORE
       ========================================================= */

    private void clickInternal(WebElement element) {
        waitUntilVisible(element);

        if (tryNormalClick(element)) return;
        if (tryKeyboardClick(element)) return;
        if (tryJsClick(element)) return;

        if (isMuiElement(element)) {
            log.info("Fallback → MUI click");
            muiClick(element);
            return;
        }

        throw failure("click", element);
    }

    private void typeInternal(WebElement element, String value) {
        waitUntilVisible(element);

        if (isMuiCalendarInput(element)) {
            typeMuiStable(element, value);
            element.sendKeys(Keys.TAB);
            return;
        }

        if (tryNormalType(element, value)) return;
        if (tryKeyboardType(element, value)) return;

        if (isMuiInput(element)) {
            log.info("Fallback → MUI stable input");
            typeMuiStable(element, value);
            return;
        }

        throw failure("type", element);
    }

    /* =========================================================
                        RETRY ENGINE
       ========================================================= */

    private void retry(String action, Runnable task) {
        int attempt = 1;

        while (attempt <= MAX_RETRIES) {
            try {
                log.info("🔁 " + action + " attempt " + attempt);
                task.run();
                log.info("✅ " + action + " success");
                return;
            } catch (Exception e) {
                log.warning("⚠️ " + action + " failed: " + e.getMessage());
            }
            attempt++;
        }
        throw new RuntimeException("❌ " + action + " failed after retries");
    }

    /* =========================================================
                        BASIC STRATEGIES
       ========================================================= */

    private boolean tryNormalClick(WebElement e) {
        try { e.click(); return true; }
        catch (Exception ignored) { return false; }
    }

    private boolean tryKeyboardClick(WebElement e) {
        try {
            actions.moveToElement(e).sendKeys(Keys.ENTER).perform();
            return true;
        } catch (Exception ignored) { return false; }
    }

    private boolean tryJsClick(WebElement e) {
        try {
            js.executeScript("arguments[0].click();", e);
            return true;
        } catch (Exception ignored) { return false; }
    }

    private boolean tryNormalType(WebElement e, String v) {
        try {
            e.clear();
            e.sendKeys(v);
            return true;
        } catch (Exception ignored) { return false; }
    }

    private boolean tryKeyboardType(WebElement e, String v) {
        try {
            e.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
            e.sendKeys(v);
            return true;
        } catch (Exception ignored) { return false; }
    }

    /* =========================================================
                        MUI / REACT CORE
       ========================================================= */

    /**
     * Stable React input:
     * input + change + blur safe
     */
    private void typeMuiStable(WebElement element, String value) {
        js.executeScript(
            "const i=arguments[0];" +
            "const last=i.value;" +
            "i.focus();" +
            "i.value=arguments[1];" +
            "const inputEv=new Event('input',{bubbles:true});" +
            "const changeEv=new Event('change',{bubbles:true});" +
            "const t=i._valueTracker;" +
            "if(t) t.setValue(last);" +
            "i.dispatchEvent(inputEv);" +
            "i.dispatchEvent(changeEv);",
            element, value
        );
    }

    private void muiClick(WebElement e) {
        js.executeScript(
            "arguments[0].focus();" +
            "arguments[0].dispatchEvent(new MouseEvent('mousedown',{bubbles:true}));" +
            "arguments[0].dispatchEvent(new MouseEvent('mouseup',{bubbles:true}));" +
            "arguments[0].dispatchEvent(new MouseEvent('click',{bubbles:true}));",
            e
        );
    }

    /* =========================================================
                        DETECTION
       ========================================================= */

    private boolean isMuiElement(WebElement e) {
        String cls = e.getAttribute("class");
        String role = e.getAttribute("role");

        return (cls != null && cls.contains("Mui")) ||
               (role != null && (
                   role.equalsIgnoreCase("button") ||
                   role.equalsIgnoreCase("checkbox") ||
                   role.equalsIgnoreCase("radio") ||
                   role.equalsIgnoreCase("combobox")
               ));
    }

    private boolean isMuiInput(WebElement e) {
        try {
            return Boolean.TRUE.equals(
                js.executeScript(
                    "return arguments[0]._valueTracker !== undefined;",
                    e
                )
            );
        } catch (Exception ex) {
            return false;
        }
    }

    private boolean isMuiCalendarInput(WebElement e) {
        return e.getAttribute("aria-haspopup") != null ||
               e.getAttribute("readonly") != null;
    }

    /* =========================================================
                        UTIL
       ========================================================= */

    private void waitUntilVisible(WebElement e) {
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    private RuntimeException failure(String action, WebElement e) {
        return new RuntimeException(
            "Failed to " + action + " element [" + describe(e) + "]"
        );
    }

    private String describe(WebElement e) {
        try {
            return e.getTagName() + " | " + e.getAttribute("class");
        } catch (Exception ex) {
            return "unknown element";
        }
    }
}
