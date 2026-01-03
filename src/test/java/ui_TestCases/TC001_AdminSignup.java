package ui_TestCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import ui_BaseClass.BaseClass;
import ui_PageObjects.HomePage;
import ui_PageObjects.LoginPage;
import ui_PageObjects.PasswordPage;
import ui_PageObjects.RegisterPage;
import ui_PageObjects.VerifyPhonePage;

public class TC001_AdminSignup extends BaseClass {
	

	@Test
	public void verifySignupRegistration() throws Exception {
		
		try {
			HomePage homePage = new HomePage(driver);
		    LoginPage loginPage=new LoginPage(driver);
		    RegisterPage registerPage=new RegisterPage(driver);
		    PasswordPage passwordPage=new PasswordPage(driver);
		    VerifyPhonePage verifyPhonePage=new VerifyPhonePage(driver);

		logger.debug("logging started");
	    logger.info("starting TC001_RegisterUser");

	    logger.info("STEP 1: Launching the browser");
	    logger.info("STEP 2: Navigating to baseURL:  https://etruckingsystem.com/auth ");
	    driver.get("https://etruckingsystem.com/auth");
	    Thread.sleep(5000);
	    
	    homePage.clickAdminYesTextLink();
	    logger.info("STEP 3: Clicking on 'AdminYesTextLink'");
	    Thread.sleep(5000);
	                   
	    logger.info("STEP 4: Verify login page is visible");
	    assertEquals(driver.getCurrentUrl(), "https://etruckingsystem.com/auth/login","mis match in url");
	    Thread.sleep(5000);
	    
	    logger.info("STEP 5: Clicked on signuplink");
	    loginPage.clickSignupLink();
	    Thread.sleep(5000);
	    
	    logger.info("STEP 6: Verify register page is visible");
	    assertEquals(driver.getCurrentUrl(), "https://etruckingsystem.com/auth/register","mis match in url");
	    Thread.sleep(5000);
	    
	    logger.info("STEP 7: Enter FirstName");
	    registerPage.enterfirstNameText(USER_NAME);
	    Thread.sleep(5000);
	    
	    logger.info("STEP 8: Enter LastName");
	    registerPage.enterlastNameText("LastMyName");
	    Thread.sleep(5000);
	    

	    logger.info("STEP 9: Enter email");
	    registerPage.enteremailText("User."+System.currentTimeMillis()+"@gmail.com");
	    Thread.sleep(5000);
	    
	    logger.info("STEP 10: Enter phone");
	    registerPage.enterphoneText("9874563210");
	    
	    logger.info("STEP 11: Enter DotNumber");
	    registerPage.enterDOTNumberText("8695");

	    logger.info("STEP 12: Click 'submitButton' button");
	    registerPage.clicksubmitButton();
	    Thread.sleep(5000);
	    
	    logger.info("STEP 13: Verify password page is visible");
	    assertEquals(driver.getCurrentUrl(), "https://etruckingsystem.com/auth/password","mis match in url");
	    Thread.sleep(5000);
	    
	    logger.info("STEP 14: Enter Password");
	    passwordPage.enterPasswordText(PASSWORD);
	    Thread.sleep(5000);
	    
	    logger.info("STEP 15: Enter ConfirmPassword");
	    passwordPage.enterConfirmPasswordText(PASSWORD);
	    Thread.sleep(5000);
	    
	    logger.info("STEP 16: click SignupButton");
	    passwordPage.clickSignupButton();
	    Thread.sleep(5000);
	    
	    logger.info("STEP 17: Verify 'verifyPhone' page is visible");
	    assertEquals(driver.getCurrentUrl(), "https://etruckingsystem.com/auth/verify-phone","mis match in url");
	    Thread.sleep(5000);
	    
	    logger.info("getOTPcontainsMessage: "+verifyPhonePage.getOTPcontainsMessage());
	    logger.info("getLinkSentMessage: "+verifyPhonePage.getLinkSentMessage());
	    logger.info("getOTPsentMessage: "+verifyPhonePage.getOTPsentMessage());
	    logger.info("ExtractOTPcontainsMessage: "+verifyPhonePage.ExtractOTPcontainsMessage());


	    
		}
		catch (AssertionError ae) {
	        logger.error("❌ Assertion failed in verifySignupRegistration: " + ae.getMessage(), ae);
	        throw ae; // rethrow so TestNG marks test as failed
	    } catch (Exception e) {
	        logger.error("❌ Unexpected exception in verifySignupRegistration: " + e.getMessage(), e);
	        throw e; // rethrow so TestNG marks test as failed
	    }
	    logger.debug("application logs end.......");
		logger.info("**** finished TC001a_verifySignupRegistration  *****"); 
}

}
