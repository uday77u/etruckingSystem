package ui_tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import base.BaseUITest;
import pages.HomePage;
import pages.LoginPage;
import pages.PasswordPage;
import pages.RegisterPage;
import pages.VerifyPhonePage;

public class TC002_AdminLogin extends BaseUITest{

	public void TestAdminLogin() {
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
	    
	    logger.info("STEP 5: Verify 'dashboard' page is visible");
	    assertTrue(isCurrentUrlWithSegment("dashboard"),"Unable to navigate 'dashboard' page,current page url: "+driver.getCurrentUrl());

	    
	    
	    
	    
	    
	  //input[@name='email']
	  //input[@name='password']
	  //button[@type='submit']
			
			
	    
		}
		catch (AssertionError ae) {
	        logger.error("❌ Assertion failed in verifySignupRegistration: " + ae.getMessage(), ae);
	        throw ae; // rethrow so TestNG marks test as failed
	    } catch (Exception e) {
	        logger.error("❌ Unexpected exception in verifySignupRegistration: " + e.getMessage(), e);
	        throw e; // rethrow so TestNG marks test as failed
	    }
	    logger.debug("application logs end.......");
		logger.info("**** finished TC002_AdminLogin  *****"); 
		
	}
}
