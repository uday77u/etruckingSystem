package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class MailinatorPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public MailinatorPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    private By inboxField = By.id("search");
    private By goButton = By.xpath("//button[contains(text(),'GO')]");
    private By firstMail = By.xpath("(//tr[contains(@class,'ng-scope')])[1]");
    private By mailFrame = By.id("html_msg_body");

  /*  public void openInbox(String inboxName) {
        driver.get("https://www.mailinator.com/");

        wait.until(ExpectedConditions.visibilityOfElementLocated(inboxField))
                .sendKeys(inboxName);

        driver.findElement(goButton).click();
    }*/
    public void openInbox(String inbox) {

        driver.get("https://www.mailinator.com/v4/public/inboxes.jsp?to=" + inbox);
    }

/*
    public String fetchOTPFromUI() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(firstMail)).click();

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(mailFrame));

        String bodyText = driver.findElement(By.tagName("body")).getText();

        driver.switchTo().defaultContent();

        return extractOTP(bodyText);
    }*/

    private String extractOTP(String text) {
        return text.replaceAll("[^0-9]", "").substring(0,6);
    }
    
    public String fetchOTPFromUI() {

        // Wait for email rows
        By emailRows = By.cssSelector("tr[data-message-id]");

        wait.until(ExpectedConditions.visibilityOfElementLocated(emailRows));

        driver.findElements(emailRows).get(0).click();

        // Switch to iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("html_msg_body"));

        String body = driver.findElement(By.tagName("body")).getText();

        driver.switchTo().defaultContent();

        return extractOTP(body);
    }

    public void waitForEmailToArrive() {

        By emailRows = By.cssSelector("tr[data-message-id]");

        new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(emailRows, 0));
    }

}
