package ui_tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
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
public class Admin_TC006_LoginVerifyProfilePhoto extends BaseUITest{

	@Test(groups = {"Master","Admin"})
	public void TestAdminLogin() throws Exception{

		try {
			HomePage homePage = new HomePage(driver);
		    LoginPage loginPage=new LoginPage(driver);
		    DashBoardPage dashBoardPage=new DashBoardPage(driver);
		    ProfilePage profilePage=new ProfilePage(driver);

		logger.debug("logging started");
	    logger.info("starting Admin_TC006_LoginVerifyProfilePhoto");
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
	    
	    logger.info("STEP 7: Click on the profile Icon");
	    dashBoardPage.clickProfileIcon();
	    
	    
	    logger.info("STEP 8: click on upload profile photo button");
	    profilePage.clickUploadeProfilePhoto(photopath);
	    Thread.sleep(5000);
	    
	    //System.out.println("Success message present: "+profilePage.isUploadProfilePhotoSuccessMessage());
	    //System.out.println("Success message: "+profilePage.getUploadProfilePhotoSuccessMessage());
	    
	    /*
	    logger.info("STEP 9: Verify 'upload success message' displayed");
	    assertTrue(profilePage.isUploadProfilePhotoSuccessMessage(), "'upload success message' is not displayed");
	    Thread.sleep(5000);
	    
	    logger.info("STEP 10: Verify 'upload success message' text");
	    assertEquals(profilePage.getUploadProfilePhotoSuccessMessage(), "upload success message","Mis-match in upload success message. ");
	    Thread.sleep(5000);
	    */
	    
	    logger.info("STEP 9: Click on profile photo 'Remove' button");
	    profilePage.clickRemoveProfilePhotoButton();
	    Thread.sleep(5000);
	    
	    logger.info("STEP 10: Click on profile photo remove 'confirm' button");
	    profilePage.clickRemoveProfilePhotoConfirm();
	    Thread.sleep(5000);
	    /*
	    logger.info("STEP 13: Verify 'remove success message' displayed");
	    assertTrue(profilePage.isRemoveProfilePhotoSuccessMessage(), "'remove success message' is not displayed");
	    Thread.sleep(5000);
	    
	    logger.info("STEP 14: Verify 'remove success message' text");
	    assertEquals(profilePage.getUploadProfilePhotoSuccessMessage(), "","Mis-match in remove success message. ");
	    Thread.sleep(5000);
	    
	    */
	    
	    logger.info("STEP 11: Click on 'Dashboard' menu");
	    profilePage.clickDashboardMenu();

	    
	    logger.info("STEP 12: Once again, click on the profile Icon");
	    dashBoardPage.clickProfileIcon();
	    Thread.sleep(10000);

	    logger.info("STEP 13: Click on company log Profile image");
	    profilePage.clickProfileImage();
	    Thread.sleep(5000);

	    logger.info("STEP 14: Verify profile Image is not available");
	    assertFalse(profilePage.isProfilePhotoMaximizeDisplayed(),"After removing profile photo, availability of profile photo: ");
	    Thread.sleep(5000);

	    
		}
		catch (AssertionError ae) {
	        logger.error("❌ Assertion failed in Admin_TC006_LoginVerifyProfilePhoto: " + ae.getMessage(), ae);
	        throw ae; // rethrow so TestNG marks test as failed
	    } catch (Exception e) {
	        logger.error("❌ Unexpected exception in Admin_TC006_LoginVerifyProfilePhoto: " + e.getMessage(), e);
	        throw e; // rethrow so TestNG marks test as failed
	    }
	    logger.debug("application logs end.......");
		logger.info("**** finished Admin_TC005_LoginVerifyProfilePhoto  *****"); 
		
	}
}