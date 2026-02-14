package utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MailinatorUtil {

    public static boolean verifyMailinatorEmail(
            WebDriver driver,
            String inboxName,
            String expectedSubject,
            String expectedContent) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        try {

            // Open Mailinator
            driver.get("https://www.mailinator.com/");

            // Enter Inbox Name
            WebElement inboxField = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("search")));
            inboxField.clear();
            inboxField.sendKeys(inboxName);

            // Click Go Button
            driver.findElement(By.xpath("//button[contains(text(),'GO')]")).click();

            // Wait for email list to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//table[contains(@class,'table')]")));

            // Click expected email subject
            WebElement email = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//td[contains(text(),'" + expectedSubject + "')]")));
            email.click();

            // Switch to email body iframe
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("html_msg_body"));

            // Verify email content
            WebElement mailBody = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

            return mailBody.getText().contains(expectedContent);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

