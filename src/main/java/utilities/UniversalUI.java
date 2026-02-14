package utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.*;
import java.util.function.BooleanSupplier;

public class UniversalUI {

    private final WebDriver driver;
    private final JavascriptExecutor js;
    private final WebDriverWait wait;

    private static final int MAX_RETRY = 3;

    // Stability tracking
    private static final Map<String,Integer> failureStats = new HashMap<>();

    public UniversalUI(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    /* ======================================================
       UNIVERSAL FIND (SELF HEALING)
       ====================================================== */

    public WebElement find(By locator) {

        try {
            return driver.findElement(locator);
        }
        catch (Exception e) {
            System.out.println("⚠ Locator failed → Healing");

            String text = locator.toString().replaceAll("[^a-zA-Z0-9 ]","");

            List<WebElement> matches =
                    driver.findElements(By.xpath("//*[contains(text(),'"+text+"')]"));

            if (!matches.isEmpty())
                return matches.get(0);

            throw new RuntimeException("Element not found: " + locator);
        }
    }

    /* ======================================================
       UNIVERSAL CLICK
       ====================================================== */

    public void click(By locator) {
        click(find(locator));
    }

    public void click(WebElement el) {

        retry("CLICK", el,

                () -> { waitClickable(el); el.click(); return true; },

                () -> { el.sendKeys(Keys.ENTER); return true; },

                () -> {
                    new Actions(driver).moveToElement(el).click().perform();
                    return true;
                },

                () -> {
                    if (!isMUI(el)) return false;
                    jsClick(el);
                    return true;
                },

                () -> { jsClick(el); return true; }
        );
    }

    /* ======================================================
       UNIVERSAL TYPE
       ====================================================== */

    public void type(By locator, String value) {
        type(find(locator), value);
    }

    public void type(WebElement el, String value) {

        retry("TYPE", el,

                () -> {
                    el.clear();
                    el.sendKeys(value);
                    return verifyValue(el,value);
                },

                () -> {
                    if (!isReactControlled(el)) return false;

                    js.executeScript(
                            "const input=arguments[0];" +
                            "const last=input.value;" +
                            "input.value=arguments[1];" +
                            "const tracker=input._valueTracker;" +
                            "if(tracker) tracker.setValue(last);" +
                            "input.dispatchEvent(new Event('input',{bubbles:true}));",
                            el,value
                    );
                    return verifyValue(el,value);
                },

                () -> {
                    js.executeScript(
                            "arguments[0].value=arguments[1];" +
                            "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));",
                            el,value
                    );
                    return verifyValue(el,value);
                }
        );
    }

    /* ======================================================
       UNIVERSAL DROPDOWN
       ====================================================== */

    public void select(By locator, String value) {
        select(find(locator), value);
    }

    public void select(WebElement el, String value) {

        retry("SELECT", el,

                // Native select
                () -> {
                    if (!el.getTagName().equalsIgnoreCase("select"))
                        return false;

                    new Select(el).selectByVisibleText(value);
                    return true;
                },

                // MUI dropdown
                () -> {
                    if (!isMUI(el)) return false;

                    click(el);
                    By option = By.xpath("//li[normalize-space()='"+value+"']");
                    click(find(option));
                    return true;
                },

                // Auto suggestion
                () -> {
                    click(el);
                    el.sendKeys(value);

                    By sug = By.xpath("//*[contains(text(),'"+value+"')]");
                    click(find(sug));
                    return true;
                }
        );
    }

    /* ======================================================
       UNIVERSAL DATE PICKER
       ====================================================== */

    public void pickDate(By locator, String day) {

        WebElement input = find(locator);

        retry("DATE", input,

                () -> {
                    type(input, day);
                    return true;
                },

                () -> {
                    By d = By.xpath("//td[normalize-space()='"+day+"']");
                    click(find(d));
                    return true;
                }
        );
    }

    /* ======================================================
       UNIVERSAL WAIT ENGINE
       ====================================================== */

    private void waitUIReady() {

        wait.until(d ->
                js.executeScript("return document.readyState").equals("complete")
        );

        waitOverlays();
        waitAngular();
    }

    private void waitOverlays() {

        List<String> overlays = List.of(
                ".MuiBackdrop-root",
                ".loading",
                ".spinner",
                ".cdk-overlay-backdrop"
        );

        for (String css : overlays) {
            try {
                wait.until(d ->
                        driver.findElements(By.cssSelector(css))
                                .stream()
                                .noneMatch(WebElement::isDisplayed));
            } catch (Exception ignored) {}
        }
    }

    private void waitAngular() {

        try {
            wait.until(d -> (Boolean) js.executeScript(
                    "if(window.getAllAngularTestabilities){" +
                            "return window.getAllAngularTestabilities()" +
                            ".every(x=>x.isStable());}" +
                            "return true;"
            ));
        } catch (Exception ignored) {}
    }

    /* ======================================================
       RETRY ENGINE
       ====================================================== */

    @SafeVarargs
    private final void retry(String action,
                             WebElement el,
                             BooleanSupplier... strategies) {

        for (int i=1;i<=MAX_RETRY;i++) {

            try {
                waitUIReady();
            } catch (Exception ignored){}

            for (BooleanSupplier step : strategies) {
                try {
                    if (step.getAsBoolean()) return;
                }
                catch (Exception e) {
                    failureStats.put(action,
                            failureStats.getOrDefault(action,0)+1);
                }
            }
        }

        throw new RuntimeException(action+" failed after retry");
    }

    /* ======================================================
       HELPERS
       ====================================================== */

    private void waitClickable(WebElement el) {
        wait.until(ExpectedConditions.elementToBeClickable(el));
    }

    private void jsClick(WebElement el) {
        js.executeScript(
                "arguments[0].dispatchEvent(new MouseEvent('click',{bubbles:true}));",
                el
        );
    }

    private boolean verifyValue(WebElement el,String expected) {
        return expected.equals(el.getAttribute("value"));
    }

    private boolean isMUI(WebElement el) {
        try {
            String cls = el.getAttribute("class");
            return cls != null && cls.contains("Mui");
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isReactControlled(WebElement el) {
        try {
            return (Boolean) js.executeScript(
                    "return !!arguments[0]._valueTracker;",
                    el
            );
        } catch (Exception e) {
            return false;
        }
    }

    /* ======================================================
       STABILITY REPORT
       ====================================================== */

    public static void printStabilityReport() {

        System.out.println("\n===== Stability Report =====");
        failureStats.forEach((k,v) ->
                System.out.println(k+" failures: "+v));
        System.out.println("============================\n");
    }
}
