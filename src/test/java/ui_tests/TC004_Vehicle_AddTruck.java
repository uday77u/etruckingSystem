package ui_tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import actions.LoginActions;
import actions.NavigationActions;
import base.BaseUITest;
import pages.DashBoardPage;
import pages.DriversPage;
import utilities.RetryAnalyzer;
import utilities.RetryListener;

public class TC004_Vehicle_AddTruck extends BaseUITest{

	@Test//(retryAnalyzer = RetryAnalyzer.class)
	public void TestAddVehicle_truck() throws Exception{
		String DLpath=System.getProperty("user.dir")+"/Photos/DlTesting.jpeg";
		String SSNpath=System.getProperty("user.dir")+"/Photos/Dl_ssn_test.jpeg";
		String photopath=System.getProperty("user.dir")+"/Photos/Dl_ssn_test.jpeg";
		String TWICpath=System.getProperty("user.dir")+"/Photos/Dl_ssn_test.jpeg";
		

		try {
			NavigationActions navigation=new NavigationActions(driver);
		    LoginActions loginActions=new LoginActions(driver);
		    VehiclesPage vehiclesPage=new VehiclesPage(driver);

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
		    
		    logger.info("STEP 7: Click on 'Truck details' button");
		    vehiclesPage.clickTruckDetailsButton();
		    
		    
		    
		    logger.info("STEP 8: Select 'Service Provider' radio button - No");
		    vehiclesPage.selectServiceProvider(false);
		    
		    logger.info("STEP 9: Enter 'Truck Number' ");
		    vehiclesPage.enterTruckNumberInput("KA98BG9878");
		    
		    logger.info("STEP 10: Enter 'Model Name' ");
		    vehiclesPage.enterTruckModelNameInput("Hero");
		    
		    logger.info("STEP 11: Select 'Model Year' ");
		    vehiclesPage.selectModelYear("2024");
		    
		    logger.info("STEP 12: Enter 'VIN Number' ");
		    vehiclesPage.enterVINNumberInput("9876584215");

		   // logger.info("STEP 13: Select 'Registration State (USA)' ");
		   // vehiclesPage.selectRegistrationState("California");
		    
		    
		    logger.info("STEP 14: Select 'License Plate' file");
		    vehiclesPage.uploadLicensePlateDocFile(DLpath.toString());
		  
		    logger.info("STEP 15: Enter 'License Plate'");
		    vehiclesPage.enterTruckLicensePlateNumberInput("KA98BG9878");
		    
		    //logger.info("STEP 16: Select 'License Plate Expiry' calender date");
		    
		    //logger.info("STEP 17: Select 'Purchase Date' calender date");
		    
		    //logger.info("STEP 18: Select 'Enroll Date' calender date");
		    
		    logger.info("STEP 16: Select 'License Plate Expiry' calendar date");
		    vehiclesPage.selectLicensePlateExpiryDate("01/25/2026");

		    logger.info("STEP 17: Select 'Purchase Date' calendar date");
		    vehiclesPage.selectPurchaseDate("01/20/2026");

		    logger.info("STEP 18: Select 'Enroll Date' calendar date");
		    vehiclesPage.selectEnrollDate("01/21/2026");

		    
		    logger.info("STEP 19: Select 'Insurance Policy Number' file");
		    vehiclesPage.uploadInsurancePolicyDocFile(SSNpath);
		    
		    logger.info("STEP 20: Enter 'Insurance Policy Number'");
		    vehiclesPage.enterTruckInsurancePolicyNumberInput("iisure4571424");
		    
		    //logger.info("STEP 21: Select 'Insurance Expiry Date ' calender date");
		    
		    logger.info("STEP 22: Select 'Condition' in dropdown");
		    vehiclesPage.selectCondition("Good");
		    
		    logger.info("STEP 23: Enter 'Gross Weight (in lbs)' ");
		    vehiclesPage.enterGrossWeightInput("12");
		    
		    logger.info("STEP 24: Select 'Annual Inspection Report' radio button - No");
		    vehiclesPage.selectAnnualInspectionReport(false);
		    
		    logger.info("STEP 25: Select 'IFTA Certification ' radio button - No");
		    vehiclesPage.selectIftaCertified(false);
		    
		    logger.info("STEP 26: Select 'CARB Certified ?' radio button - No");
		    vehiclesPage.selectCarbCertified(false);
		    
		    logger.info("STEP 27: Select 'IRP ' radio button - No");
		    vehiclesPage.selectIRP(false);
		    
		    logger.info("STEP 28: Select 'Mexico Permit ?' radio button - No");
		    vehiclesPage.selectMexicoPermit(false);
		    
		    logger.info("STEP 29: Select 'Assign Driver ? ' radio button - No");
		    vehiclesPage.selectAssignDriver(false);
		    
		    logger.info("STEP 30: Click on 'Choose' button");
		    vehiclesPage.clickChooseButton();
		    
		    logger.info("STEP 31: Select 'Vehicle Image' on displayed images");
		    vehiclesPage.clickToChooseVehicleImage(3);
		    
		    logger.info("STEP 32: Click on 'Choose' button");
		    vehiclesPage.clicktruckDetailsSaveButton();
		    
		    Thread.sleep(10000);
		    
		    
		    
		    
		    
		    
		    
		    
		}
		catch (AssertionError ae) {
	        logger.error("❌ Assertion failed in TestAddVehicle_truck: " + ae.getMessage(), ae);
	        throw ae; // rethrow so TestNG marks test as failed
	    } catch (Exception e) {
	        logger.error("❌ Unexpected exception in TestAddVehicle_truck: " + e.getMessage(), e);
	        throw e; // rethrow so TestNG marks test as failed
	    }
	    logger.debug("application logs end.......");
		logger.info("**** finished TC004_AddVehicle_truck  *****"); 
		
	}
}
