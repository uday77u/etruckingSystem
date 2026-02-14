package ui_tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import actions.LoginActions;
import actions.NavigationActions;
import base.BaseUITest;
import pages.VehiclesPage;
import utilities.UI_ExtentReportManager;

@Listeners(UI_ExtentReportManager.class)
public class Vehicle_TC001_AddTruck extends BaseUITest{

	@Test(groups = {"Master","Vehicle"})
	public void Test_Vehicle_AddTruck() throws Exception{
		

		try {
			NavigationActions navigation=new NavigationActions(driver);
		    LoginActions loginActions=new LoginActions(driver);
		    VehiclesPage vehiclesPage=new VehiclesPage(driver);

			logger.debug("logging started");
		    logger.info("starting Vehicle_TC001_AddTruck");

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
		    
		    logger.info("STEP 8: Select 'Service Provider' radio button: No");
		    vehiclesPage.selectServiceProvider(false);
		    
		    logger.info("STEP 9: Enter 'Truck Number': {}",randomTruckNumber);
		    vehiclesPage.enterTruckNumberInput(randomTruckNumber);
		    
		    logger.info("STEP 10: Enter 'Model Name' : {}",randomModelName);
		    vehiclesPage.enterTruckModelNameInput(randomModelName);
		    Thread.sleep(10000);
		    
		    logger.info("STEP 11: Select 'Model Year': {}",randomModelYear);
		    vehiclesPage.selectModelYear(randomModelYear);
		    
		    logger.info("STEP 12: Enter 'VIN Number': {} ",randomVinNumber);
		    vehiclesPage.enterVINNumberInput(randomVinNumber);

		    logger.info("STEP 13: Select 'Registration State (USA)': California");
		    vehiclesPage.selectRegistrationState("California");
		    
		    
		    logger.info("STEP 14: Select 'License Plate' file");
		    vehiclesPage.uploadLicensePlateDocFile(DLpath);
		    Thread.sleep(10000);
		  
		    logger.info("STEP 15: Enter 'Truck License Plate': {}",randomLicenseNo);
		    vehiclesPage.enterTruckLicensePlateNumberInput(randomLicenseNo);

		    
		    logger.info("STEP 16: Select 'License Plate Expiry' date");
		    vehiclesPage.setLicensePlateExpiry("12/31/2026");

		    logger.info("STEP 17: Select 'Purchase Date': 01/15/2024");
		    vehiclesPage.setPurchaseDate("01/15/2024");

		    logger.info("STEP 18: Select 'Enroll Date'");
		    vehiclesPage.setEnrollDate("01/20/2024");
		    
		    logger.info("STEP 19: Select 'Insurance Policy Number' file");
		    vehiclesPage.uploadInsurancePolicyDocFile(SSNpath);
		    
		    logger.info("STEP 20: Enter 'Insurance Policy Number':{}",randomInsurancePolicy);
		    vehiclesPage.enterTruckInsurancePolicyNumberInput(randomInsurancePolicy);
		    
		    Thread.sleep(20000);
		    
		    logger.info("STEP 21: Select 'Insurance Expiry Date ' calender date: 12/31/2028");
		    vehiclesPage.setInsuranceExpiryDate("12/31/2028");
		    
		    logger.info("STEP 22: Select 'Condition' in dropdown");
		    vehiclesPage.selectCondition("Good");
		    
		    logger.info("STEP 23: Enter 'Gross Weight (in lbs)': {} ",randomGrossWeight);
		    vehiclesPage.enterGrossWeightInput(randomGrossWeight);
		    
		    logger.info("STEP 24: Select 'Annual Inspection Report' radio button: No");
		    vehiclesPage.selectAnnualInspectionReport(false);
		    
		    logger.info("STEP 25: Select 'IFTA Certification ' radio button: No");
		    vehiclesPage.selectIftaCertified(false);
		    
		    logger.info("STEP 26: Select 'CARB Certified ?' radio button: No");
		    vehiclesPage.selectCarbCertified(false);
		    
		    logger.info("STEP 27: Select 'IRP ' radio button - No");
		    vehiclesPage.selectIRP(false);
		    
		    logger.info("STEP 28: Select 'Mexico Permit ?' radio button: No");
		    vehiclesPage.selectMexicoPermit(false);
		    
		    logger.info("STEP 29: Select 'Assign Driver ? ' radio button: No");
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
	        logger.error("❌ Assertion failed in Vehicle_TC001_AddTruck: " + ae.getMessage(), ae);
	        throw ae; // rethrow so TestNG marks test as failed
	    } catch (Exception e) {
	        logger.error("❌ Unexpected exception in Vehicle_TC001_AddTruck: " + e.getMessage(), e);
	        throw e; // rethrow so TestNG marks test as failed
	    }
	    logger.debug("application logs end.......");
		logger.info("**** finished Vehicle_TC001_AddTruck  *****"); 
		
	}
}
