package ui_tests;

import static org.testng.Assert.assertTrue;

import java.io.File;
import org.testng.annotations.Test;
import actions.LoginActions;
import actions.NavigationActions;
import base.BaseUITest;
import pages.DashBoardPage;
import pages.DriversPage;

public class TC003_Driver_AddDriver extends BaseUITest{

	@Test//(retryAnalyzer = RetryAnalyzer.class)
	public void TestAddDriver() throws Exception{
		String DLpath=System.getProperty("user.dir")+File.separator+"Photos"+File.separator+"DlTesting.jpeg";
		String SSNpath=System.getProperty("user.dir")+File.separator+"Photos"+File.separator+"Dl_ssn_test.jpeg";
		String photopath=System.getProperty("user.dir")+File.separator+"Photos"+File.separator+"Dl_ssn_test.jpeg";
		String TWICpath=System.getProperty("user.dir")+File.separator+"Photos"+File.separator+"Dl_ssn_test.jpeg";
		

		try {
			NavigationActions navigationActions=new NavigationActions(driver);
		    LoginActions loginActions=new LoginActions(driver);
		    DashBoardPage dashBoardPage=new DashBoardPage(driver);
		    DriversPage driversPage=new DriversPage(driver);
		    
		    
		logger.debug("logging started");
	    logger.info("starting TC003_Driver_AddDriver");

	    logger.info("STEP 1: Navigate to Admin Login Page");
		navigationActions.NavigateAuthToLoginAdmin();
		Thread.sleep(5000);
		
		logger.info("STEP 2: Login as Admin User");
		loginActions.LoginAdmin(USER_EMAIL, PASSWORD);
		Thread.sleep(5000);
		
	    logger.info("STEP 3: Verify 'dashboard' page is visible");
	    assertTrue(isCurrentUrlWithSegment("dashboard"),"Unable to navigate 'dashboard' page,current page url: "+driver.getCurrentUrl());
	    Thread.sleep(5000);
	    
	    logger.info("STEP 4: Click on 'Drivers' Menu");
	    dashBoardPage.clickDriversMenu();
	    
	    logger.info("STEP 5: Verify 'my driver' Page is visible");
	    assertTrue(isCurrentUrlWithSegment("my-drivers"),"Unable to navigate 'dashboard' page,current page url: "+driver.getCurrentUrl());
	    Thread.sleep(20000);
	    
	    logger.info("STEP 6: Click on 'Add Driver' button");
	    driversPage.clickAddDriverButton();
	    
	    logger.info("STEP 7: select DL file and upload");
	    driversPage.UploadDLfile(DLpath);
	    driversPage.verifyDLFileUploaded();

	    Thread.sleep(5000);
	    logger.info("STEP 8: enter/edit License Number"); //--verify license number or edit it
	    driversPage.enterDriverLicenseReactSafe("AN010130083278");

	    logger.info("STEP 9: click expiry date icon");
	    driversPage.clickCDLExpCalendarIcon();

	    logger.info("STEP 10: select expiry date in calendar");
	    driversPage.selectDateInCalender(2030, "Feb", 19);

	    Thread.sleep(5000);
	    logger.info("STEP 11: enter First Name");
	    driversPage.enterFirstNameText("DriverFirstName1");

	    logger.info("STEP 12: enter Last Name");
	    driversPage.enterLastNameText("DriverLastName1");
	    Thread.sleep(5000);
	    
	    logger.info("STEP 13: click Date of Birth  icon");
	    driversPage.clickDOBCalendarIcon();
	    Thread.sleep(5000);
	    
	    logger.info("STEP 14: select Date of Birth date in calendar");
	    driversPage.selectDateInCalender(1990, "Feb", 25);
	    Thread.sleep(5000);
	    
	    logger.info("STEP 15: enter email");
	    driversPage.enterEmail("Driver1@htk.com");

	    logger.info("STEP 16: enter phone"); 
	    driversPage.enterPhone("9876547210");
	    
	    logger.info("STEP 17: select SSN file and upload");
	    driversPage.UploadSSNfile(SSNpath);
	    
	    logger.info("STEP 18: enter SSN"); 
	    driversPage.enterSSN("987754321"); //edit ssn 
	    //driversPage.enterSSNnumberReactSafe("987754321");

	    logger.info("STEP 19: enter country"); 
	    driversPage.selectAddressCountry("India");
	    
	    logger.info("STEP 20: enter zipcode");
	    driversPage.enterZIPcode("560001");

	    logger.info("STEP 21: enter state"); //edit state
	    driversPage.enterState("Karnataka");

	    logger.info("STEP 22: enter city");  //edit state
	    driversPage.enterCity("Bangalore");

	    
	    logger.info("STEP 23: enter address");
	    driversPage.enterAddress("KR circle");
	    
	    
	    logger.info("STEP 24: enter uploadPhoto");
	    driversPage.UploadPhotofile(photopath);
	    Thread.sleep(5000);
	    logger.info("STEP 25: Select LicenseClass");
	    driversPage.selectLicenseClassDropDown("Class A");

	    Thread.sleep(5000);
	    logger.info("STEP 26: enter year of experience");
	    driversPage.selectYearsOfComExpDropDown("3-5 years");
	    Thread.sleep(5000);
	    logger.info("STEP 27: enter joiningDate");
	    driversPage.clickJoiningDateCalendarIcon();
	    
	    logger.info("STEP 28: select Joining date in calendar");
	    driversPage.selectDateInCalender(2025, "Feb", 19);
	    
	    logger.info("STEP 29: Select jobtype");
	    driversPage.selectJobTypeDropDown("Tanker hauler");
	    driversPage.closeOpenDropdown();
	    
	    logger.info("STEP 30: upload TWICCardNo");
	    driversPage.UploadTWICfile(TWICpath);
	    
	    logger.info("STEP 31: Enter TWICCardNo");
	    driversPage.enterTwicCardNo("4578512");
	    //driversPage.enterTwicCardNoJS("4578512");
	    
	    logger.info("STEP 32: click TwicExpDateCalendarIcon");
	    driversPage.clickTwicExpDateCalendarIcon();
	    Thread.sleep(5000);
	    
	    logger.info("STEP 33: select Twic Expiry date in calendar");
	    driversPage.selectDateInCalender(2029, "Feb", 19);
	   
	    logger.info("STEP 34: Select DriverType");
	    driversPage.selectDriverType("Solo");
	    
	    logger.info("STEP 35: Select Interestedin");
	    driversPage.selectInterests("Local");

	    logger.info("STEP 36: enter teamupwithRadioButton");
	    driversPage.clickTeamUpYesRadioButton();
	    
	    logger.info("STEP 37: Select LeadSource ");
	    driversPage.selectleadSourceDropDown("Facebook");

	    
	    logger.info("STEP 38: Click on save and next button");
	    driversPage.clickSubmitButton();
	    Thread.sleep(60000);
	    
	    
	    
		}
		catch (AssertionError ae) {
	        logger.error("❌ Assertion failed in TestAddDriver: " + ae.getMessage(), ae);
	        throw ae; // rethrow so TestNG marks test as failed
	    } catch (Exception e) {
	        logger.error("❌ Unexpected exception in TestAddDriver: " + e.getMessage(), e);
	        throw e; // rethrow so TestNG marks test as failed
	    }
	    logger.debug("application logs end.......");
		logger.info("**** finished TC002_AdminLogin  *****"); 
		
	}

}