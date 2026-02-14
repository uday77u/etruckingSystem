package utilities;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RandomUISelectorUtil {

    private WebDriver driver;
    private WebDriverWait wait;
    private Random random = new Random();

    public RandomUISelectorUtil(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(15));
    }

    // ======================================================
    // ✅ Random MUI Dropdown Selection
    // ======================================================
    public String selectRandomFromMuiDropdown(WebElement dropdown) {

        // Open dropdown
        wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();

        // Wait for listbox
        By optionsLocator = By.xpath("//li[@role='option' and not(@aria-disabled='true')]");
        List<WebElement> options = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(optionsLocator)
        );

        // Pick random option
        WebElement randomOption = options.get(random.nextInt(options.size()));
        String selectedText = randomOption.getText();

        new Actions(driver)
                .moveToElement(randomOption)
                .click()
                .perform();

        return selectedText; // helpful for assertions/logging
    }

    // ======================================================
    // ✅ Random Radio Button Selection
    // ======================================================
    public String selectRandomRadio(String radioNameAttribute) {

        By radiosLocator = By.xpath("//input[@name='" + radioNameAttribute + "']");
        List<WebElement> radios = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(radiosLocator)
        );

        WebElement randomRadio = radios.get(random.nextInt(radios.size()));
        String selectedValue = randomRadio.getAttribute("value");

        wait.until(ExpectedConditions.elementToBeClickable(
                randomRadio.findElement(By.xpath("./ancestor::label"))
        )).click();

        return selectedValue;
    }
}
