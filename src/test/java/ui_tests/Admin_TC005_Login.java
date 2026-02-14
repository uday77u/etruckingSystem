package ui_tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseUITest;
import pages.DashBoardPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;
import utilities.UI_ExtentReportManager;
@Listeners(UI_ExtentReportManager.class)
public class Admin_TC005_Login extends BaseUITest{

	@Test(groups = {"Master","Admin"})
	public void TestAdminLogin() throws Exception{

		try {
			HomePage homePage = new HomePage(driver);
		    LoginPage loginPage=new LoginPage(driver);
		    new DashBoardPage(driver);
		    new ProfilePage(driver);

		logger.debug("logging started");
	    logger.info("starting Admin_TC005_Login");
	    assertTrue(isCurrentTitleWithSegment("Transportation Company"),"Unable to open 'auth' Page,current page title: "+driver.getTitle());
	    
	    homePage.clickAdminYesTextLink();
	    logger.info("STEP 1: Clicking on 'AdminYesTextLink'");

	    logger.info("STEP 2: Verify login page is visible");
	    assertEquals(driver.getCurrentUrl(), BASE_URL+"/login","Mis-match in url: ");

	    logger.info("STEP 3: Entering the Email");
	    loginPage.enterEmail(USER_EMAIL);
	    
	    logger.info("STEP 4: Entering the password ");
	    loginPage.enterPassword(PASSWORD);
	    
	    logger.info("STEP 5: Click on the submit button");
	    loginPage.clickSubmitButton();
	    
		logger.info("Account is active on other device Message displayed. Status: "+loginPage.isAccountActiveOnOtherDeviceMessage());
	    if(loginPage.isAccountActiveOnOtherDeviceMessage()) {
	    	loginPage.clickConfirmToLoginOnCurrentDevice();
	    	logger.info("Clicked on the 'confirm' button to end session in other device");
	    }
	    
	    logger.info("STEP 6: Verify 'dashboard' page is visible");
	    assertTrue(isCurrentUrlWithSegment("dashboard"),"Unable to navigate 'dashboard' page,current page url: "+driver.getCurrentUrl());
	    

	    

		}
		catch (AssertionError ae) {
	        logger.error("❌ Assertion failed in Admin_TC005_Login: " + ae.getMessage(), ae);
	        throw ae; // rethrow so TestNG marks test as failed
	    } catch (Exception e) {
	        logger.error("❌ Unexpected exception in Admin_TC005_Login: " + e.getMessage(), e);
	        throw e; // rethrow so TestNG marks test as failed
	    }
	    logger.debug("application logs end.......");
		logger.info("**** finished Admin_TC005_Login  *****"); 
		
	}
}