package ui_tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import actions.SignupActions;
import io.github.bonigarcia.wdm.WebDriverManager;
import testdata.TestContext;
import utilities.EmailUtil;

public class SignupTest {

    private WebDriver driver;
    private SignupActions signupActions;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        signupActions = new SignupActions(driver);
        driver.get("http://dev.etruckingsystem.com/auth");
    }

    @Test(groups = "signup")
    public void signupUser() {

        System.out.println("Signup Test Started");

        String email = EmailUtil.generateRandomMailinatorEmail();
        String phone = "9876543210";

        TestContext.setGeneratedEmail(email);
        TestContext.setPhone(phone);

        signupActions.signupAdminUser(
                "Test",
                "User",
                email,
                phone,
                "123456",
                "Password@123"
        );

        System.out.println("Signup Completed With Email: " + email);
    }


    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
