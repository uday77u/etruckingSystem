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
public class Vehicle_TC002_AddTrailer extends BaseUITest{

	@Test(groups = {"Master","Vehicle"})
	public void Test_AddVehicle_trailer() throws Exception{
		

		try {
			NavigationActions navigation=new NavigationActions(driver);
		    LoginActions loginActions=new LoginActions(driver);
		    VehiclesPage vehiclesPage=new VehiclesPage(driver);

		    logger.debug("logging started");
		    logger.info("starting Vehicle_TC002_AddTrailer");

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
		    vehiclesPage.clickTrailerDetailsButton();

		    logger.info("STEP 8: Select 'Service Provider' radio button - No");
		    vehiclesPage.SelectIsServiceProviderTrailerRadioButton(false);
		    
		    logger.info("STEP 9: Enter 'Trailer Number': {}",randomTrailerNumber);
		    vehiclesPage.enterTrailerNumberInput(randomTrailerNumber);
		    
		    logger.info("STEP 10: Enter 'Model Name': {}",randomModelName);
		    vehiclesPage.enterTrailerModelNameInput(randomModelName);
		    
		    logger.info("STEP 11: Select 'Model Year': {} ",randomModelYear);
		    vehiclesPage.selectModelTrailerYear(randomModelYear);
		    
		    logger.info("STEP 12: Select 'Condition' in dropdown: Good =>options[Excellent, Good, Average, Critical]");
		    vehiclesPage.selectTrailerCondition("Good");

		    logger.info("STEP 13: Select 'License Plate' file");
		    vehiclesPage.uploadTrailerLicensePlateDocFile(DLpath);
		  
		    logger.info("STEP 14: Enter 'License Plate'");
		    vehiclesPage.enterTrailerLicensePlateNumberInput(randomLicenseNo);
		   
		    
		    logger.info("STEP 15: Select 'License Plate Expiry' calender date");
		    vehiclesPage.setTrailerLicensePlateExpiry("12/31/2026");
		    
		    logger.info("STEP 16: Select 'Purchase Date' calender date");
		    vehiclesPage.setTrailerPurchaseDate("01/15/2024");
		    
		    
		    logger.info("STEP 17: Select 'Enroll Date' calender date");
		    vehiclesPage.setTrailerEnrollDate("01/20/2024");
		    
		    
		    logger.info("STEP 18: Select 'Registration State (USA)' ");
		    vehiclesPage.selectTrailerRegistrationState("California");
		    
		    logger.info("STEP 19: Enter 'Engine Hours': {}",randomEngineHours);
		    vehiclesPage.enterEngineHours(randomEngineHours);
		    
		    
		    logger.info("STEP 20: Enter 'Max Capacity': {}",randomMaxCapacity);
		    vehiclesPage.enterMaxCapacity(randomMaxCapacity);
		    
		    logger.info("STEP 21: Select 'Trailer Size' in dropdown: 48' =>options[22' 48' 53']");
		    vehiclesPage.selectTrailerSize("48");
		    
		    logger.info("STEP 22: Select 'Trailer Type' in dropdown: Refrigerated (Reefer) =>options[Power Only, Power Only (Tow-Away), Rail, Reefer Pallet Exchange, Refrigerated (Reefer)]");
		    vehiclesPage.selectTrailerType("Refrigerated (Reefer)");
		    
		    logger.info("STEP 23: Select 'Insurance Policy Number' file");
		    vehiclesPage.uploadTrailerInsurancePolicyDocFile(SSNpath);
		    
		    
		    logger.info("STEP 24: Enter 'Insurance Policy Number': {}",randomInsurancePolicy);
		    vehiclesPage.enterTrailerInsurancePolicyNumberInput(randomInsurancePolicy);
		    
		    
		    logger.info("STEP 25: Select 'Insurance Expiry Date ' calender date: 12/31/2028");
		    vehiclesPage.setTrailerInsuranceExpiryDate("12/31/2028");

		    logger.info("STEP 26: Select 'Annual Inspection Report' radio button - No");
		    vehiclesPage.isAnnualInspectionReportTrailerRadioButton(false);
		    
		    logger.info("STEP 27: Select 'IFTA Certification ' radio button - No");
		    vehiclesPage.iftaCertifiedTrailerRadioButton(false);
		    
		    logger.info("STEP 28: Select 'CARB Certified ?' radio button - No");
		    vehiclesPage.isCARBTrailerRadioButton(false);
		    
		    logger.info("STEP 29: Click on Trailer 'Save' button");
		    vehiclesPage.clickTrailerDetailsSaveButton();
		    
		    Thread.sleep(10000);
		   
		    
		    
		    
		    
		}
		catch (AssertionError ae) {
	        logger.error("❌ Assertion failed in Vehicle_TC002_AddTrailer: " + ae.getMessage(), ae);
	        throw ae; // rethrow so TestNG marks test as failed
	    } catch (Exception e) {
	        logger.error("❌ Unexpected exception in Vehicle_TC002_AddTrailer: " + e.getMessage(), e);
	        throw e; // rethrow so TestNG marks test as failed
	    }
	    logger.debug("application logs end.......");
		logger.info("**** finished Vehicle_TC002_AddTrailer  *****"); 
		
	}
}
