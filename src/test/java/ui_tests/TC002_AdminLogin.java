package ui_tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import base.BaseUITest;
import pages.DashBoardPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;

public class TC002_AdminLogin extends BaseUITest{

	@Test
	public void TestAdminLogin() throws Exception{
		String uploadPhotoPath=System.getProperty("user.dir")+"/Photos/companyProfilePhoto.jpeg";
		try {
			HomePage homePage = new HomePage(driver);
		    LoginPage loginPage=new LoginPage(driver);
		    DashBoardPage dashBoardPage=new DashBoardPage(driver);
		    ProfilePage profilePage=new ProfilePage(driver);

		logger.debug("logging started");
	    logger.info("starting TC001_RegisterUser");
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
	    
	    logger.info("STEP 6: Verify 'dashboard' page is visible");
	    assertTrue(isCurrentUrlWithSegment("dashboard"),"Unable to navigate 'dashboard' page,current page url: "+driver.getCurrentUrl());
	    
	    logger.info("STEP 7: Click on the profile Icon");
	    dashBoardPage.clickProfileIcon();
	    
	    
	    logger.info("STEP 8: click on upload profile photo button");
	    profilePage.clickUploadeProfilePhoto(uploadPhotoPath);
	    Thread.sleep(5000);
	    
	    System.out.println("Success message present: "+profilePage.isUploadProfilePhotoSuccessMessage());
	    System.out.println("Success message: "+profilePage.getUploadProfilePhotoSuccessMessage());
	    
	    /*
	    logger.info("STEP 9: Verify 'upload success message' displayed");
	    assertTrue(profilePage.isUploadProfilePhotoSuccessMessage(), "'upload success message' is not displayed");
	    Thread.sleep(5000);
	    
	    logger.info("STEP 10: Verify 'upload success message' text");
	    assertEquals(profilePage.getUploadProfilePhotoSuccessMessage(), "upload success message","Mis-match in upload success message. ");
	    Thread.sleep(5000);
	    */
	    
	    logger.info("STEP 11: Click on profile photo 'Remove' button");
	    profilePage.clickRemoveProfilePhotoButton();
	    Thread.sleep(5000);
	    
	    logger.info("STEP 12: Click on profile photo remove 'confirm' button");
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
	    
	    logger.info("STEP 15: Click on 'Dashboard' menu");
	    profilePage.clickDashboardMenu();

	    
	    logger.info("STEP 16: Once again, click on the profile Icon");
	    dashBoardPage.clickProfileIcon();

	    
	    logger.info("STEP 17: Click on company log Profile image");
	    profilePage.clickProfileImage();
	    
	    logger.info("STEP 18: Verify profile Image is not available");
	    assertFalse(profilePage.isProfilePhotoMaximizeDisplayed(),"After removing profile photo, availability of profile photo: ");

	    
	    
	    
	    
	    

			
			
	    
		}
		catch (AssertionError ae) {
	        logger.error("❌ Assertion failed in TestAdminLogin: " + ae.getMessage(), ae);
	        throw ae; // rethrow so TestNG marks test as failed
	    } catch (Exception e) {
	        logger.error("❌ Unexpected exception in TestAdminLogin: " + e.getMessage(), e);
	        throw e; // rethrow so TestNG marks test as failed
	    }
	    logger.debug("application logs end.......");
		logger.info("**** finished TC002_AdminLogin  *****"); 
		
	}
}