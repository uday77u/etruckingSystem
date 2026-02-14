package ui_tests;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import actions.LoginActions;
import actions.NavigationActions;
import base.BaseUITest;
import pages.ManageUsersPage;
import utilities.UI_ExtentReportManager;

@Listeners(UI_ExtentReportManager.class)
public class ManageUsers_TC001_AddUser extends BaseUITest{
	@Test(groups = {"Master","ManageUsers"})
	public void ManageUsers_AddUser() throws Exception{
		try {
			NavigationActions navigation=new NavigationActions(driver);
		    LoginActions loginActions=new LoginActions(driver);
		    ManageUsersPage manageUsersPage=new ManageUsersPage(driver);

		    logger.debug("logging started");
		    logger.info("starting ManageUsers_TC001_AddUser");

		    logger.info("STEP 1: Navigate to Admin Login Page");
			navigation.NavigateAuthToLoginAdmin();
			
			logger.info("STEP 2: Login as Admin User");
			loginActions.LoginAdmin(USER_EMAIL, PASSWORD);
			
		    logger.info("STEP 3: Verify 'dashboard' page is visible");
		    assertTrue(isCurrentUrlWithSegment("dashboard"),"Unable to navigate 'dashboard' page,current page url: "+driver.getCurrentUrl());
		    
		    logger.info("STEP 4: Click on 'Vehicles' Menu");
		    navigation.NavigateToManageUsersButton();
		    
		    logger.info("STEP 5: Verify 'manage-users' Page is visible");
		    assertTrue(isCurrentUrlWithSegment("manage-users"),"Unable to navigate 'manage-users' page,current page url: "+driver.getCurrentUrl());
		    
		    logger.info("STEP 6: Click on 'Add user' button");
		    manageUsersPage.clickAddUser();

		    logger.info("STEP 7: Enter 'First Name': {}",randomFirstName);
		    manageUsersPage.enterFirstName(randomFirstName);

		    logger.info("STEP 8: Enter 'Last Name': {}",randomLastName);
		    manageUsersPage.enterlastName(randomLastName);

		    logger.info("STEP 9: Enter 'Email Id': {}",randomEmail);
		    manageUsersPage.enterEmail(randomEmail);
		    Thread.sleep(10000);
		    logger.info("STEP 10: Select 'PhCountryCode': +91 options=>[+1,+91,+61]");
		    manageUsersPage.selectCountryCode("+91");

		    logger.info("STEP 11: Enter 'Phone': {}",randomPhoneUSA);
		    manageUsersPage.enterPhone(randomPhoneAUS);

		    logger.info("STEP 10: Select 'CountryName': +91 options=>[United States, Australia, India]");
		    manageUsersPage.selectCountryName("India");

		    logger.info("STEP 12: Enter 'ZIP Code': 560001");
		    manageUsersPage.enterZipCodeName("560001");
		    Thread.sleep(10000);
		    logger.info("STEP 13: Enter 'State': ManuallyEnteredStateName");
		    manageUsersPage.enterStateName("ManuallyEnteredStateName");

		    logger.info("STEP 14: Enter 'City': ManuallyEnteredCityName");
		    manageUsersPage.enterCityName("ManuallyEnteredCityName");

		    logger.info("STEP 15: Enter 'Address': {}",randomAddress);
		    manageUsersPage.enterAddress(randomAddress);
		    Thread.sleep(10000);
		    logger.info("STEP 16: Select 'Role': Safety Personnel options=>[Co-Admin, Dispatcher, Sales Person, Account Manager, Safety Personnel]");
		    manageUsersPage.selectRole("Safety Personnel");
		    Thread.sleep(10000);
		    logger.info("STEP 17: Select 'Is user Active': false options=>[true, false]");
		    manageUsersPage.selectIsActive("false");
		    Thread.sleep(10000);
		    logger.info("STEP 18: Select 'Access Type': false options=>[Full Access, Partial Access]");
		    manageUsersPage.selectAccessType("false");
		    Thread.sleep(10000);
		    logger.info("STEP 19: Select 'DriversAllModule': true options=>[true, false]");
		    manageUsersPage.selectDriversAll(true);
		    Thread.sleep(10000);
		    logger.info("STEP 20: Select 'DriversDeleteModule': true options=>[true, false]");
		    manageUsersPage.selectDriversDelete(false);
		    Thread.sleep(10000);
		    logger.info("STEP 21: Click on 'Save'");
		    manageUsersPage.clickSave();
		    
		    logger.info("STEP 21: Verify that no 'error messages' are present");
		    List<String> errors = manageUsersPage.getFormValidationErrors();
		    if (!errors.isEmpty()) {

		        logger.error("Form validation errors found:");
		        errors.forEach(err -> logger.error(" - " + err));

		        Assert.fail("Form submission failed due to validation errors: " + errors);
		    }

		    Thread.sleep(30000);
   
		}
		catch (AssertionError ae) {
	        logger.error("❌ Assertion failed in ManageUsers_TC001_AddUser: " + ae.getMessage(), ae);
	        throw ae; // rethrow so TestNG marks test as failed
	    } catch (Exception e) {
	        logger.error("❌ Unexpected exception in ManageUsers_TC001_AddUser: " + e.getMessage(), e);
	        throw e; // rethrow so TestNG marks test as failed
	    }
	    logger.debug("application logs end.......");
		logger.info("**** finished ManageUsers_TC001_AddUser  *****"); 
		
	}
	
}
