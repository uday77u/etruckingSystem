package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OTPPage {

    private WebDriver driver;

    public OTPPage(WebDriver driver) {
        this.driver = driver;
    }

    private By otpField = By.id("otp");
    private By submitBtn = By.id("verifyOtp");

    public void enterOTP(String otp) {
        driver.findElement(otpField).sendKeys(otp);
    }

    public void submitOTP() {
        driver.findElement(submitBtn).click();
    }
}

