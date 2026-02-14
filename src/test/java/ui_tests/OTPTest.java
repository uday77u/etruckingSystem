package ui_tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.MailinatorPage;
import pages.OTPPage;
import utilities.EmailUtil;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class OTPTest {

    public static void main(String[] args) {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        String email = EmailUtil.generateRandomMailinatorEmail();
        String inbox = EmailUtil.extractInbox(email);

        // 👉 Use this email in application signup
        System.out.println("Generated Email: " + email);

        // --- Fetch OTP using UI ---
        MailinatorPage mailPage = new MailinatorPage(driver);
        mailPage.openInbox(inbox);
        
        mailPage.waitForEmailToArrive();
        String otp = mailPage.fetchOTPFromUI();

        // OR 👉 Fetch OTP using API
        // String otp = MailinatorAPIUtil.getOTPFromInbox(inbox);

        System.out.println("OTP: " + otp);

        // --- Enter OTP in App ---
        OTPPage otpPage = new OTPPage(driver);
        otpPage.enterOTP(otp);
        otpPage.submitOTP();

        driver.quit();
    }
}

