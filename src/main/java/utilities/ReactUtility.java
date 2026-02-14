package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ReactUtility {
	protected WebDriver driver;

	public void setReactInputValue(WebElement input, String value) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    js.executeScript(
	        "const input = arguments[0];" +
	        "const value = arguments[1];" +
	        "const lastValue = input.value;" +

	        "input.value = value;" +

	        "const event = new Event('input', { bubbles: true });" +
	        "event.simulated = true;" +

	        "const tracker = input._valueTracker;" +
	        "if (tracker) { tracker.setValue(lastValue); }" +

	        "input.dispatchEvent(event);",
	        input,
	        value
	    );
	}

	public void setReactInputValue(By locator, String value) {
	    WebElement input = driver.findElement(locator);
	    setReactInputValue(input, value);
	}

	/*   Some teams prefer InputEvent
	public void setReactInputValue(WebElement input, String value) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    js.executeScript(
	        "const input = arguments[0];" +
	        "const value = arguments[1];" +
	        "const lastValue = input.value;" +

	        "input.value = value;" +

	        "const event = typeof InputEvent === 'function' " +
	        "  ? new InputEvent('input', { bubbles: true }) " +
	        "  : new Event('input', { bubbles: true });" +

	        "const tracker = input._valueTracker;" +
	        "if (tracker) { tracker.setValue(lastValue); }" +

	        "input.dispatchEvent(event);",
	        input,
	        value
	    );
	}
*/
	
	public void setReactInputValueWithRetry(WebElement input, String value) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    final int MAX_RETRIES = 3;
	    final long RETRY_DELAY_MS = 300;

	    for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
	        try {
	            System.out.println(String.format(
	                "[ReactInput] Attempt %d/%d → Setting value: '%s'",
	                attempt, MAX_RETRIES, value
	            ));

	            js.executeScript(
	                "const input = arguments[0];" +
	                "const value = arguments[1];" +
	                "const lastValue = input.value;" +

	                "input.value = value;" +

	                "const event = typeof InputEvent === 'function' " +
	                "  ? new InputEvent('input', { bubbles: true }) " +
	                "  : new Event('input', { bubbles: true });" +

	                "event.simulated = true;" +

	                "const tracker = input._valueTracker;" +
	                "if (tracker) { tracker.setValue(lastValue); }" +

	                "input.dispatchEvent(event);",
	                input,
	                value
	            );

	            // ✅ Verify value actually stuck
	            String actualValue = input.getAttribute("value");

	            if (value.equals(actualValue)) {
	                System.out.println("[ReactInput] ✅ Success. Value confirmed: " + actualValue);
	                return;
	            } else {
	                System.out.println(String.format(
	                    "[ReactInput] ⚠ Verification failed. Expected='%s' Actual='%s'",
	                    value, actualValue
	                ));
	            }

	        } catch (Exception e) {
	            System.out.println(String.format(
	                "[ReactInput] ❌ Error on attempt %d: %s",
	                attempt, e.getMessage()
	            ));
	        }

	        // Retry delay
	        try {
	            Thread.sleep(RETRY_DELAY_MS);
	        } catch (InterruptedException ignored) {}
	    }

	    throw new RuntimeException(
	        "[ReactInput] Failed to set value after " + MAX_RETRIES + " attempts: " + value
	    );
	}

	public void setReactInputValueWithRetry(By locator, String value) {
	    WebElement input = driver.findElement(locator);
	    setReactInputValueWithRetry(input, value);
	}

	public boolean isReactControlled(WebElement input) {
	    return (Boolean) ((JavascriptExecutor) driver)
	        .executeScript("return !!arguments[0]._valueTracker;", input);
	}

	public void smartSetInputValue(WebElement input, String value) {
	    if (isReactControlled(input)) {
	        System.out.println("[Input] React-controlled detected");
	        setReactInputValueWithRetry(input, value);
	    } else {
	        System.out.println("[Input] Standard input detected");
	        input.clear();
	        input.sendKeys(value);
	    }
	}

	public void setInputValueUniversal(WebElement element, String value) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    try {
	        // 1️⃣ contenteditable (divs pretending to be inputs)
	        Boolean isContentEditable = (Boolean) js.executeScript(
	            "return arguments[0].isContentEditable === true;",
	            element
	        );

	        if (Boolean.TRUE.equals(isContentEditable)) {
	            System.out.println("[UniversalInput] contenteditable detected");
	            setContentEditableValue(element, value);
	            return;
	        }

	        // 2️⃣ React-controlled input
	        Boolean isReactControlled = (Boolean) js.executeScript(
	            "return !!arguments[0]._valueTracker;",
	            element
	        );

	        if (Boolean.TRUE.equals(isReactControlled)) {
	            System.out.println("[UniversalInput] React-controlled input detected");
	            setReactInputValueWithRetry(element, value);
	            return;
	        }

	        // 3️⃣ Standard input / textarea
	        System.out.println("[UniversalInput] Standard input detected");
	        element.clear();
	        element.sendKeys(value);

	    } catch (Exception e) {
	        throw new RuntimeException(
	            "[UniversalInput] Failed to set value: " + value,
	            e
	        );
	    }
	}

	
	private void setContentEditableValue(WebElement element, String value) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    js.executeScript(
	        "const el = arguments[0];" +
	        "const text = arguments[1];" +

	        "el.focus();" +
	        "document.execCommand('selectAll', false, null);" +
	        "document.execCommand('insertText', false, text);" +

	        "el.dispatchEvent(new Event('input', { bubbles: true }));",
	        element,
	        value
	    );
	}

	
	
	
	
}