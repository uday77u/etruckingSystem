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
import utilities.UI_ExtentReportManager;

@Listeners(UI_ExtentReportManager.class)
public class Admin_TC002_SignupWithRegisteredEmail extends BaseUITest {
	

	@Test(  groups = {"Master","Admin"})
	public void verifySignupRegistration() throws Exception{
		String email="uday77u@gmail.com";//"RegisterdAdmin@mail.com";
		String phone=randomPhoneUSA;;
		
		try {
			HomePage homePage = new HomePage(driver);
		    LoginPage loginPage=new LoginPage(driver);
		    RegisterPage registerPage=new RegisterPage(driver);
		    new PasswordPage(driver);
		    new VerifyPhonePage(driver);
  
		logger.debug("\tlogging started");
	    logger.info("*** Starting Admin_TC002_SignupWithRegisteredEmail ***");
	    assertTrue(isCurrentTitleWithSegment("Transportation Company"),"Unable to open 'auth' Page,current page title: "+driver.getTitle());
	    
	    logger.info("STEP 1: Clicking on 'AdminYesTextLink'");
	    homePage.clickAdminYesTextLink();

	    logger.info("STEP 2: Verify login page is visible");
	    assertEquals(driver.getCurrentUrl(), BASE_URL+"/login","Mis-match in url: ");

	    logger.info("STEP 3: Clicked on signuplink");
	    loginPage.clickSignupLink();

	    logger.info("STEP 4: Verify register page is visible");
	    assertEquals(driver.getCurrentUrl(), BASE_URL+"/register","Mis-match in url: ");

	    logger.info("STEP 5: Enter FirstName: "+USER_NAME);
	    registerPage.enterfirstNameText(USER_NAME);

	    logger.info("STEP 6: Enter LastName");
	    registerPage.enterlastNameText("LastMyName");

	    logger.info("STEP 7: Enter email: "+email);
	    registerPage.enteremailText(email);

	    logger.info("STEP 8: Enter phone: {}",phone);
	    registerPage.enterphoneText(phone);
	    
	    logger.info("STEP 9: Enter DotNumber");
	    registerPage.enterDOTNumberText("867895");
	    Thread.sleep(10000);
	    
	    logger.info("STEP 10: Click 'Next' button");
	    registerPage.clickNextButton();
	    Thread.sleep(10000);
	    
	    logger.info("STEP 11: Verify password page is visible");
	    assertTrue(isCurrentUrlWithSegment("/register"),"Unable to navigate 'register' page,current page url: "+driver.getCurrentUrl());
	    
	    
	    logger.info("STEP 12:Once again click on 'Next' button");
	    registerPage.clickNextButton();
	    Thread.sleep(10000);
	    
	    logger.info("STEP 13: Verify password page is visible");
	    assertTrue(isCurrentUrlWithSegment("/register"),"Unable to navigate 'register' page,current page url: "+driver.getCurrentUrl());

  
		}
		catch (AssertionError ae) {
	        logger.error("❌ Assertion failed in Admin_TC002_SignupWithRegisteredEmail: " + ae.getMessage(), ae);
	        throw ae; // rethrow so TestNG marks test as failed
	    } catch (Exception e) {
	        logger.error("❌ Unexpected exception in Admin_TC002_SignupWithRegisteredEmail: " + e.getMessage(), e);
	        throw e; // rethrow so TestNG marks test as failed
	    }
	    logger.debug("application logs end.......");
		logger.info("**** finished Admin_TC002_SignupWithRegisteredEmail  *****"); 
}

}