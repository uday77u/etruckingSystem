package ui_tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import actions.LoginActions;
import actions.NavigationActions;
import base.BaseUITest;
import pages.DashBoardPage;
import pages.DriversPage;

public class TC005_AddVehicle_trailer extends BaseUITest{

	@Test
	public void TestAddVehicle_trailer() throws Exception{
		String DLpath=System.getProperty("user.dir")+"/Photos/DlTesting.jpeg";
		String SSNpath=System.getProperty("user.dir")+"/Photos/Dl_ssn_test.jpeg";
		String photopath=System.getProperty("user.dir")+"/Photos/Dl_ssn_test.jpeg";
		String TWICpath=System.getProperty("user.dir")+"/Photos/Dl_ssn_test.jpeg";
		

		try {
			NavigationActions navigation=new NavigationActions(driver);
		    LoginActions loginActions=new LoginActions(driver);
		    VehiclesPage vehiclesPage=new VehiclesPage(driver);
		    DashBoardPage dashBoardPage=new DashBoardPage(driver);
		    DriversPage driversPage=new DriversPage(driver);
			logger.debug("logging started");
		    logger.info("starting TC004_AddVehicle");

		    logger.info("STEP 1: Navigate to Admin Login Page");
			navigation.NavigateAuthToLoginAdmin();
			
			logger.info("STEP 2: Login as Admin User");
			loginActions.LoginAdmin(USER_EMAIL, PASSWORD);
			
		    logger.info("STEP 3: Verify 'dashboard' page is visible");
		    assertTrue(isCurrentUrlWithSegment("dashboard"),"Unable to navigate 'dashboard' page,current page url: "+driver.getCurrentUrl());
		    
		    logger.info("STEP 4: Click on 'Vehicles' Menu");
		    navigation.NavigateToVehiclesMenu();
		    
		    logger.info("STEP 5: Verify 'Vehicle' Page is visible");
		    assertTrue(isCurrentUrlWithSegment("vehicle"),"Unable to navigate 'vehicle' page,current page url: "+driver.getCurrentUrl());
		    
		    logger.info("STEP 6: Click on 'Add Vehicle' button");
		    vehiclesPage.clickAddVehiclesButton();
		    
		    logger.info("STEP 7: Click on 'Trailer details' button");

		    logger.info("STEP 8: Select 'Service Provider' radio button - No");
		    logger.info("STEP 9: Enter 'Trailer Number' ");
		    
		    
		    logger.info("STEP 10: Enter 'Model Name' ");
		    
		    
		    logger.info("STEP 11: Select 'Model Year' ");
		    
		    logger.info("STEP 12: Select 'Condition' in dropdown");

		    
		    
		    
		    logger.info("STEP 13: Select 'License Plate' file");
		  
		    logger.info("STEP 14: Enter 'License Plate'");
		   
		    
		    logger.info("STEP 15: Select 'License Plate Expiry' calender date");
		    
		    logger.info("STEP 16: Select 'Purchase Date' calender date");
		    
		    logger.info("STEP 17: Select 'Enroll Date' calender date");
		    
		    logger.info("STEP 18: Select 'Registration State (USA)' ");
		    
		    logger.info("STEP 19: Enter 'Engine Hours'");
		    
		    logger.info("STEP 20: Enter 'Max Capacity'");
		    
		    logger.info("STEP 21: Select 'Trailer Size' in dropdown");
		    logger.info("STEP 22: Select 'Trailer Type' in dropdown");
		    
		    logger.info("STEP 23: Select 'Insurance Policy Number' file");
		    logger.info("STEP 24: Enter 'Insurance Policy Number'");
		    
		    logger.info("STEP 25: Select 'Insurance Expiry Date ' calender date");
		    

		    logger.info("STEP 26: Select 'Annual Inspection Report' radio button - No");

		    
		    logger.info("STEP 27: Select 'IFTA Certification ' radio button - No");

		    
		    logger.info("STEP 28: Select 'CARB Certified ?' radio button - No");

		    
		    logger.info("STEP 29: Click on 'Choose' button");

		    
		    Thread.sleep(10000);
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
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
