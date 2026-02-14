package ui_tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import actions.LoginActions;
import actions.NavigationActions;
import base.BaseUITest;
import pages.DispatchBoardPage;
import pages.ExternalCarriersPage;
import utilities.UI_ExtentReportManager;

@Listeners(UI_ExtentReportManager.class)
public class Dispatch_TC005_AddExternalCarriers extends BaseUITest{
	@Test(groups = {"Master","Dispatch"})
	public void TestDispatch_AddCarrier() throws Exception{
		try {
			
			NavigationActions navigation=new NavigationActions(driver);
		    LoginActions loginActions=new LoginActions(driver);
		    DispatchBoardPage dispatchBoardPage=new DispatchBoardPage(driver);
		    ExternalCarriersPage externalCarriersPage=new ExternalCarriersPage(driver);

			logger.debug("logging started");
		    logger.info("starting Dispatch_TC005_AddExternalCarriers");

		    logger.info("STEP 1: Navigate to Admin Login Page");
			navigation.NavigateAuthToLoginAdmin();
			
			logger.info("STEP 2: Login as Admin User");
			loginActions.LoginAdmin(USER_EMAIL, PASSWORD);
			
		    logger.info("STEP 3: Verify 'dashboard' page is visible");
		    assertTrue(isCurrentUrlWithSegment("dashboard"),"Unable to navigate 'dashboard' page,current page url: "+driver.getCurrentUrl());
		    
		    logger.info("STEP 4: Click on 'Dispatch' Menu");
		    navigation.NavigateToDispatchBoardMenu();
		    
		    logger.info("STEP 5: Click on 'External Carriers' ");
		    dispatchBoardPage.clickExternalCarriers();

		    logger.info("STEP 6: Verify 'Carrier' Page ");
		    assertTrue(isCurrentUrlWithSegment("external-carriers"), "Unable to navigate to 'external-carriers' page, current url: "+driver.getCurrentUrl());

		    logger.info("STEP 7: Click on 'Add Carrier' ");
		    externalCarriersPage.clickAddCarrierButton();

		    Thread.sleep(8000);
		    logger.info("STEP 8: Verify 'Add Carrier form' is displayed");
		    assertTrue(externalCarriersPage.isAddCarrierFormisDisplayed(), "Unable to display 'Add Carrier form'");
		    
		    
		    
		    
		    logger.info("STEP 9:Enter 'Name'");
		    externalCarriersPage.enterName("Carrier1");
		    
		    logger.info("STEP 10:Enter 'Email'");
		    externalCarriersPage.enterEmail("Carrier1@mail.com");
		    
		    logger.info("STEP 11:Select 'Country code'");
		    externalCarriersPage.selectCountryCode("+91");
		    
		    logger.info("STEP 12:Enter 'Phone'");
		    externalCarriersPage.enterPhoneNumber("9898652380");
		    
		    logger.info("STEP 13:Enter 'MC Number'");
		    externalCarriersPage.enterMCNumber("9897455");
		    
		    logger.info("STEP 14:Enter 'Dot Number'");
		    externalCarriersPage.enterDOTNumber("12345");
		    
		    logger.info("STEP 15:Select 'Country Name'");
		    externalCarriersPage.selectCountryName("India");

		    logger.info("STEP 16:Enter 'ZIP Code':"+randomZipIndia);
		    externalCarriersPage.enterZIPCode(randomZipIndia);
    
		    logger.info("STEP 17:Enter 'Address 1'");
		    externalCarriersPage.enterAddress("Naagavara");

		    
			   //WAIT for ZIP auto-fill / re-render
		    externalCarriersPage.waitForZipAutoFillToComplete();

		    // 🔁 RE-ENTER ADDRESS (CRITICAL)
		   // logger.info("STEP 16:Re-enter 'Address 1' after ZIP autofill");
		   // externalCarriersPage.enterAddress("Naagavara");
		   // Thread.sleep(20000);
		    
		    /*
		     *Location capture from zip code no need city and state
		    logger.info("STEP 16:Enter 'City'");
		    externalCarriersPage.enterCity("Bengaluru");
		    
		    logger.info("STEP 17:Enter 'State'");
		    externalCarriersPage.enterState("Karnataka");
		    */
		    
		    logger.info("STEP 18:Enter 'Address 2'");
		    externalCarriersPage.enterAddress2("address2 ");

		    logger.info("STEP 19:Click on 'Save'");
		    externalCarriersPage.clickSaveButton();
		    
		    Thread.sleep(20000);
		    
		    
		    
		    
		}
		catch (AssertionError ae) {
	        logger.error("❌ Assertion failed in Dispatch_TC005_AddExternalCarriers: " + ae.getMessage(), ae);
	        throw ae; // rethrow so TestNG marks test as failed
	    } catch (Exception e) {
	        logger.error("❌ Unexpected exception in Dispatch_TC005_AddExternalCarriers: " + e.getMessage(), e);
	        throw e; // rethrow so TestNG marks test as failed
	    }
	    logger.debug("application logs end.......");
		logger.info("**** finished Dispatch_TC005_AddExternalCarriers  *****"); 
		
	}

}
