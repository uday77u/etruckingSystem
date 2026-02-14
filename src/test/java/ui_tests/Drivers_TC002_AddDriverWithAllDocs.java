package ui_tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import actions.LoginActions;
import actions.NavigationActions;
import base.BaseUITest;
import pages.DashBoardPage;
import pages.DriversPage;
import utilities.UI_ExtentReportManager;

@Listeners(UI_ExtentReportManager.class)
public class Drivers_TC002_AddDriverWithAllDocs extends BaseUITest{

	@Test(groups = {"Master","Drivers"})
	public void TestAddDriver() throws Exception{

		try {
			NavigationActions navigationActions=new NavigationActions(driver);
		    LoginActions loginActions=new LoginActions(driver);
		    DashBoardPage dashBoardPage=new DashBoardPage(driver);
		    DriversPage driversPage=new DriversPage(driver);
		    
		    
		logger.debug("logging started");
	    logger.info("starting Drivers_TC002_AddDriverWithAllDocs");

	    logger.info("STEP 1: Navigate to Admin Login Page");
		navigationActions.NavigateAuthToLoginAdmin();
		
		logger.info("STEP 2: Login as Admin User");
		loginActions.LoginAdmin(USER_EMAIL, PASSWORD);
		
	    logger.info("STEP 3: Verify 'dashboard' page is visible");
	    assertTrue(isCurrentUrlWithSegment("dashboard"),"Unable to navigate 'dashboard' page,current page url: "+driver.getCurrentUrl());
	    
	    logger.info("STEP 4: Click on 'Drivers' Menu");
	    dashBoardPage.clickDriversMenu();
	    
	    logger.info("STEP 5: Verify 'my driver' Page is visible");
	    assertTrue(isCurrentUrlWithSegment("my-drivers"),"Unable to navigate 'dashboard' page,current page url: "+driver.getCurrentUrl());
	    
	    logger.info("STEP 6: Click on 'Add Driver' button");
	    driversPage.clickAddDriverButton();
	    
	    logger.info("STEP 7: select DL file and upload");
	    driversPage.UploadDLfile(DLpath);
	    driversPage.verifyDLFileUploaded();

	    Thread.sleep(5000);
	    logger.info("STEP 8: Manually, enter/edit License Number: {}",randomLicenseNo); //--verify license number or edit it
	    driversPage.enterDriverLicenseReactSafe(randomLicenseNo);

	    logger.info("STEP 9: click expiry date icon");
	    driversPage.clickCDLExpCalendarIcon();

	    logger.info("STEP 10: select expiry date in calendar");
	    driversPage.selectDateInCalender(2030, "Feb", 19);

	    Thread.sleep(5000);
	    logger.info("STEP 11: enter First Name: {}",randomFirstName);
	    driversPage.enterFirstNameText(randomFirstName);

	    logger.info("STEP 12: enter Last Name: {}",randomLastName);
	    driversPage.enterLastNameText(randomLastName);
	    Thread.sleep(5000);
	    
	    logger.info("STEP 13: click Date of Birth  icon");
	    driversPage.clickDOBCalendarIcon();
	    Thread.sleep(5000);
	    
	    logger.info("STEP 14: select Date of Birth date in calendar: "+"1990, \"Feb\", 25");
	    driversPage.selectDateInCalender(1990, "Feb", 25);
	    Thread.sleep(5000);
	    
	    logger.info("STEP 15: enter email: {}",randomEmail);
	    driversPage.enterEmail(randomEmail);

	    logger.info("STEP 16: enter phone: {}",randomPhoneIndia); 
	    driversPage.enterPhone(randomPhoneIndia);
	    
	    logger.info("STEP 17: select SSN file and upload");
	    driversPage.UploadSSNfile(SSNpath);
	    driversPage.verifySSNFileUploaded();
	    
	    logger.info("STEP 18: enter SSN: {}",randomSSN); 
	    //driversPage.enterSSN("987754321"); //edit ssn 
	    driversPage.enterSSNnumberReactSafe(randomSSN);
	    Thread.sleep(5000);
	    
	    logger.info("STEP 19: enter country: "+"India"); 
	    driversPage.selectAddressCountry("India");
	    
	    logger.info("STEP 20: enter zipcode: {}",randomZipIndia);
	    driversPage.enterZIPcode(randomZipIndia);

	    logger.info("STEP 21: enter address: {}",randomAddress);
	    driversPage.enterAddress(randomAddress);
	    
	    
	    logger.info("STEP 22: enter uploadPhoto");
	    driversPage.UploadPhotofile(photopath);
	    Thread.sleep(5000);
	    
	    logger.info("STEP 23: Select LicenseClass: "+"Class A");
	    driversPage.selectLicenseClassDropDown("Class A");

	    Thread.sleep(5000);
	    logger.info("STEP 24: enter year of experience: "+"3-5 years");
	    driversPage.selectYearsOfComExpDropDown("3-5 years");
	    Thread.sleep(5000);
	    logger.info("STEP 25: enter joiningDate");
	    driversPage.clickJoiningDateCalendarIcon();
	    
	    logger.info("STEP 26: select Joining date in calendar: "+"2025, \"Feb\", 19");
	    driversPage.selectDateInCalender(2025, "Feb", 19);
	    
	    logger.info("STEP 27: Select jobtype: "+"Tanker hauler");
	    driversPage.selectJobTypeDropDown("Tanker hauler");
	    driversPage.closeOpenDropdown();
	    
	    logger.info("STEP 28:Upload 'TWIC Card' Doc"); 
	    driversPage.UploadTWICfile(DLpath);
	    
	    logger.info("STEP 29:Enter 'TWIC Card Number': {}",randomDotNumber);
	    driversPage.enterTwicCardNo(randomDotNumber);
	    
	    logger.info("STEP 30:Enter 'TWIC Card Expiration Date':"+"2030, \"Feb\", 25");
	    driversPage.clickTwicExpDateCalendarIcon();
	    driversPage.selectDateInCalender(2030, "Feb", 25);
	    
	    logger.info("STEP 31: Select DriverType: "+"Solo");
	    driversPage.selectDriverType("Solo");
	    
	    logger.info("STEP 32: Select Interestedin: "+"Local");
	    driversPage.selectInterests("Local");

	    logger.info("STEP 33: enter teamupwithRadioButton: "+"Yes");
	    driversPage.clickTeamUpYesRadioButton();
	    
	    logger.info("STEP 34: Select LeadSource ");
	    driversPage.selectleadSourceDropDown("Facebook");

	    logger.info("--Upload Additional Info and Documents--");
	    logger.info("STEP 35:Upload Medical Card Doc");
	    driversPage.uploadMedicalCard(DLpath);
	    
	    logger.info("STEP 36:Upload Copy of DMV(3-5 years) Doc ");
	    driversPage.uploadCopyOfDMVfile(DLpath);
	    driversPage.closeOpenDropdown();
	    
	    logger.info("STEP 37: Click on 'save and next' button");
	    driversPage.clickSaveAndNextButton();
	    Thread.sleep(20000);
	    
	    logger.info("STEP 38: verify that Pay/salary tab is activated ");
	    assertTrue(driversPage.isSalaryDetailsTabIsActive(),"Unable to navigate 'Pay/salary tab', ");
	    
	    logger.info("STEP 39:Select 'Employment type':Full-time,  Employment type =>Options['Full-time' or 'Part-time']"); 
	    driversPage.selectEmploymentType("Full-time");

	    logger.info("STEP 40: verify that 'Employment type' is 'Full-time' selected ");
	    assertTrue(driversPage.isEmploymentTypeSelected("Full-time"));
	    
	    logger.info("STEP 41:Select Pay type as 'Per Hours' =>Options['Per Hours' or 'Per Miles' or 'By Percentage']");
	    driversPage.selectPayType("Per Hours");

	    logger.info("STEP 42: verify that selected 'Pay type' as 'Per Hours' ");
	    assertTrue(driversPage.isPayTypeSelected("Per Hours"));
	    
	    logger.info("STEP 43: enter rate: 78");
	    driversPage.enterRate("78");
	    
	    logger.info("STEP 44:Enter 'Pay per Extra Stop': 5 ");
	    driversPage.enterpayPerExtraStop("5");
	    
	    logger.info("STEP 45:Enter 'Start After Pick(s)': 7 ");
	    driversPage.enterStartAfterPicks("7");
	    
	    logger.info("STEP 46:Enter 'Start After Drop(s)': 9");
	    driversPage.enterStartAfterDrops("9");
	    
	    logger.info("STEP 47:Enter 'Wait Time Pay (per hour)': 23 ");
	    driversPage.enterWaitTimePay("23");
	    
	    logger.info("STEP 48:Enter/Select 'Start After Hour(s)': 01");
	    driversPage.enterStartAfterHours("01");
	    
	    logger.info("STEP 49:Enter 'Layover Pay' : 7");
	    driversPage.enterLayoverPayInput("7");
	    
	    logger.info("STEP 50:Enter 'Detention Pay': 5");
	    driversPage.enterDetentionPayInput("5");   
	    
	    logger.info("STEP 51: Click on 'save' button");
	    driversPage.clickSaveButton();
	    Thread.sleep(20000);
	    
	   
		}
		catch (AssertionError ae) {
	        logger.error("❌ Assertion failed in Drivers_TC002_AddDriverWithAllDocs: " + ae.getMessage(), ae);
	        throw ae; // rethrow so TestNG marks test as failed
	    } catch (Exception e) {
	        logger.error("❌ Unexpected exception in Drivers_TC002_AddDriverWithAllDocs: " + e.getMessage(), e);
	        throw e; // rethrow so TestNG marks test as failed
	    }
	    logger.debug("application logs end.......");
		logger.info("**** finished Drivers_TC002_AddDriverWithAllDocs  *****"); 
		
	}

}