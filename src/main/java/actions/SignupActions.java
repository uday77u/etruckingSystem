package actions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pages.HomePage;
import pages.LoginPage;
import pages.PasswordPage;
import pages.RegisterPage;
import pages.VerifyPhonePage;

public class SignupActions extends BaseAction{

    private WebDriver driver;

    private HomePage homePage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private PasswordPage passwordPage;
    private VerifyPhonePage verifyPhonePage;
    private Logger logger;

    public SignupActions(WebDriver driver) {
        this.driver = driver;
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        registerPage = new RegisterPage(driver);
        passwordPage = new PasswordPage(driver);
        verifyPhonePage = new VerifyPhonePage(driver);
        logger=LogManager.getLogger(getClass());
    }

    public void signupAdminUserWithLogs(
            String firstName,
            String lastName,
            String email,
            String phone,
            String dotNumber,
            String password
    ) {

    	logger.info("Started Create Signup Admin user Account");

        logger.info("STEP 1: Clicking Admin Yes link");
        homePage.clickAdminYesTextLink();

        logger.info("STEP 2: Verifying login page");
        assertEquals(driver.getCurrentUrl(), BASE_URL + "/login", "Login page URL mismatch");

        logger.info("STEP 3: Clicking signup link");
        loginPage.clickSignupLink();

        logger.info("STEP 4: Verifying register page");
        assertEquals(driver.getCurrentUrl(), BASE_URL + "/register", "Register page URL mismatch");

        logger.info("STEP 5: Entering First Name: " + firstName);
        registerPage.enterfirstNameText(firstName);

        logger.info("STEP 6: Entering Last Name: " + lastName);
        registerPage.enterlastNameText(lastName);

        logger.info("STEP 7: Entering Email: " + email);
        registerPage.enteremailText(email);

        logger.info("STEP 8: Entering Phone: " + phone);
        registerPage.enterphoneText(phone);

        logger.info("STEP 9: Entering DOT Number: " + dotNumber);
        registerPage.enterDOTNumberText(dotNumber);

        logger.info("STEP 10: Clicking submit button");
        registerPage.clickNextButton();
        
        logger.info("STEP 11: Verifying password page");
        assertTrue(isCurrentUrlWithSegment(driver,"password"),
                "Password page not displayed");

        logger.info("STEP 12: Entering password");
        passwordPage.enterPasswordText(password);

        logger.info("STEP 13: Entering confirm password");
        passwordPage.enterConfirmPasswordText(password);

        logger.info("STEP 14: Clicking signup button");
        passwordPage.clickSignupButton();

        logger.info("STEP 15: Verifying verify-phone page");
        assertTrue(isCurrentUrlWithSegment(driver, "verify-phone"),
                "Verify phone page not displayed");

        logger.info("STEP 16: Verifying email in link sent message");
        assertTrue(
                verifyPhonePage.getLinkSentMessage().toLowerCase().contains(email.toLowerCase()),
                "Verification email mismatch"
        );

        logger.info("STEP 17: Verifying phone in OTP message");
        assertTrue(
                verifyPhonePage.getOTPsentMessage().contains(phone),
                "OTP phone mismatch"
        );

        logger.info("STEP 18: Extracting OTP");
        String otp = verifyPhonePage.ExtractOTPcontainsMessage();
        logger.info("Extracted OTP: " + otp);

        logger.info("STEP 19: Entering OTP");
        verifyPhonePage.enterOTP(otp);

        logger.info("STEP 20: Clicking verify button");
        verifyPhonePage.clickVeriftButton();

        logger.info("STEP 21: Verifying navigation to login page");
        assertTrue(isCurrentUrlWithSegment(driver,"login"),
                "Login page not displayed after verification");
        
    	logger.info("Successfully Created Signup Admin user Account with name: "+firstName+"Phone: "+phone+"Email: "+email);
    }
    
    public void signupAdminUser(
            String firstName,
            String lastName,
            String email,
            String phone,
            String dotNumber,
            String password
    ) {

    	logger.info("Started Create Signup Admin user Account");


        homePage.clickAdminYesTextLink();


        assertEquals(driver.getCurrentUrl(), BASE_URL + "/login", "Login page URL mismatch");


        loginPage.clickSignupLink();


        assertEquals(driver.getCurrentUrl(), BASE_URL + "/register", "Register page URL mismatch");


        registerPage.enterfirstNameText(firstName);


        registerPage.enterlastNameText(lastName);


        registerPage.enteremailText(email);


        registerPage.enterphoneText(phone);


        registerPage.enterDOTNumberText(dotNumber);


        registerPage.clickNextButton();
        

        assertTrue(isCurrentUrlWithSegment(driver,"password"),
                "Password page not displayed");


        passwordPage.enterPasswordText(password);


        passwordPage.enterConfirmPasswordText(password);


        passwordPage.clickSignupButton();


        assertTrue(isCurrentUrlWithSegment(driver, "verify-phone"),
                "Verify phone page not displayed");

        assertTrue(
                verifyPhonePage.getLinkSentMessage().toLowerCase().contains(email.toLowerCase()),
                "Verification email mismatch"
        );

        assertTrue(
                verifyPhonePage.getOTPsentMessage().contains(phone),
                "OTP phone mismatch"
        );


        String otp = verifyPhonePage.ExtractOTPcontainsMessage();
        logger.info("Extracted OTP: " + otp);

        verifyPhonePage.enterOTP(otp);

        verifyPhonePage.clickVeriftButton();

        assertTrue(isCurrentUrlWithSegment(driver,"login"),
                "Login page not displayed after verification");
        
    	logger.info("Successfully Created Signup Admin user Account with name: "+firstName+"Phone: "+phone+"Email: "+email);
    }
    
    
}
