package ui_tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.MailinatorPage;
import testdata.TestContext;
import utilities.EmailUtil;

public class EmailVerificationTest {

    private WebDriver driver;
    private MailinatorPage mailinatorPage;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        mailinatorPage = new MailinatorPage(driver);
    }

    @Test(dependsOnGroups = "signup")
    public void verifyEmailAndReadOTP() {

        System.out.println("Verification Test Started");

        String email = TestContext.getGeneratedEmail();

        if (email == null) {
            throw new RuntimeException("Email is NULL — Signup probably failed");
        }

        String inbox = EmailUtil.extractInbox(email);

        mailinatorPage.openInbox(inbox);
        mailinatorPage.waitForEmailToArrive();

        String otp = mailinatorPage.fetchOTPFromUI();

        System.out.println("OTP FROM EMAIL: " + otp);
    }


    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
