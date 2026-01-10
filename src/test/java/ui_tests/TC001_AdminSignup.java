package ui_tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import base.BaseUITest;
import pages.HomePage;
import pages.LoginPage;
import pages.PasswordPage;
import pages.RegisterPage;
import pages.VerifyPhonePage;
import utilities.ExtentNew;

//@Listeners(ExtentNew.class)
public class TC001_AdminSignup extends BaseUITest {
	

	@Test
	public void verifySignupRegistration() {
		String email="User."+System.currentTimeMillis()+"@gmail.com";
		String phone="9876543210";
		
		try {
			HomePage homePage = new HomePage(driver);
		    LoginPage loginPage=new LoginPage(driver);
		    RegisterPage registerPage=new RegisterPage(driver);
		    PasswordPage passwordPage=new PasswordPage(driver);
		    VerifyPhonePage verifyPhonePage=new VerifyPhonePage(driver);

		logger.debug("logging started");
	    logger.info("starting TC001_RegisterUser");

	    logger.info("STEP 1: Launching the browser");
	    logger.info("STEP 2: Navigating to baseURL: "+BASE_URL);
	    driver.get(BASE_URL);
	    assertTrue(isCurrentTitleWithSegment("Transportation Company"),"Unable to open 'auth' Page,current page title: "+driver.getTitle());
	    
	    homePage.clickAdminYesTextLink();
	    logger.info("STEP 3: Clicking on 'AdminYesTextLink'");

	    logger.info("STEP 4: Verify login page is visible");
	    assertEquals(driver.getCurrentUrl(), BASE_URL+"/login","Mis-match in url: ");

	    logger.info("STEP 5: Clicked on signuplink");
	    loginPage.clickSignupLink();

	    logger.info("STEP 6: Verify register page is visible");
	    assertEquals(driver.getCurrentUrl(), BASE_URL+"/register","Mis-match in url: ");

	    logger.info("STEP 7: Enter FirstName: "+USER_NAME);
	    registerPage.enterfirstNameText(USER_NAME);

	    logger.info("STEP 8: Enter LastName");
	    registerPage.enterlastNameText("LastMyName");

	    logger.info("STEP 9: Enter email: "+email);
	    registerPage.enteremailText(email);

	    logger.info("STEP 10: Enter phone");
	    registerPage.enterphoneText(phone);
	    
	    logger.info("STEP 11: Enter DotNumber");
	    registerPage.enterDOTNumberText("8695");

	    logger.info("STEP 12: Click 'submitButton' button");
	    registerPage.clicksubmitButton();
	
	    logger.info("STEP 13: Verify password page is visible");
	    assertTrue(isCurrentUrlWithSegment("/password"),"Unable to navigate 'password' page,current page url: "+driver.getCurrentUrl());

	    logger.info("STEP 14: Enter Password");
	    passwordPage.enterPasswordText(PASSWORD);

	    logger.info("STEP 15: Enter ConfirmPassword");
	    passwordPage.enterConfirmPasswordText(PASSWORD);

	    logger.info("STEP 16: click SignupButton");
	    passwordPage.clickSignupButton();

	    logger.info("STEP 17: Verify 'verifyPhone' page is visible");
	    assertTrue(isCurrentUrlWithSegment("verify-phone"),"Unable to navigate 'verify-phone' page,current page url: "+driver.getCurrentUrl());

	    logger.info("STEP 18:Verifying verification link Message contains email. Message: "+verifyPhonePage.getLinkSentMessage());
	    assertTrue(verifyPhonePage.getLinkSentMessage().toLowerCase().contains(email.toLowerCase()),"Verification link message, sent to email is mis-matching.Registered email:"+email);

	    logger.info("STEP 19:Verifying OTP request Message contains phone number. Message: "+verifyPhonePage.getOTPsentMessage());
	    assertTrue(verifyPhonePage.getOTPsentMessage().contains(phone),"OTP request message, sent to phone number is mis-matching.Registered phone:"+phone);
	    
	    logger.info("OTP Message: "+verifyPhonePage.getOTPcontainsMessage());
	    
	    logger.info("STEP 20: ExtractOTPcontainsMessage: "+verifyPhonePage.ExtractOTPcontainsMessage());
	    verifyPhonePage.enterOTP(verifyPhonePage.ExtractOTPcontainsMessage());

	    logger.info("STEP 21: Clicked on 'verify' button");
	    verifyPhonePage.clickVeriftButton();

	    logger.info("STEP 22: Verify 'login' page is visible");
	    assertTrue(isCurrentUrlWithSegment("login"),"Unable to navigate 'login' page,current page url: "+driver.getCurrentUrl());

	    

	    
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
