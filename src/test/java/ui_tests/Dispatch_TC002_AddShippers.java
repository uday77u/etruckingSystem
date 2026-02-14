package ui_tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import actions.LoginActions;
import actions.NavigationActions;
import base.BaseUITest;
import pages.DispatchBoardPage;
import pages.ShippersPage;
import utilities.UI_ExtentReportManager;
@Listeners(UI_ExtentReportManager.class)
public class Dispatch_TC002_AddShippers extends BaseUITest{
	@Test(groups = {"Master","Dispatch"})
	public void TestDispatch_AddShippers() throws Exception{
		try {
			
			NavigationActions navigation=new NavigationActions(driver);
		    LoginActions loginActions=new LoginActions(driver);
		    DispatchBoardPage dispatchBoardPage=new DispatchBoardPage(driver);
		    ShippersPage shippersPage=new ShippersPage(driver);

			logger.debug("logging started");
		    logger.info("starting Dispatch_TC002_AddShippers");

		    logger.info("STEP 1: Navigate to Admin Login Page");
			navigation.NavigateAuthToLoginAdmin();
			
			logger.info("STEP 2: Login as Admin User");
			loginActions.LoginAdmin(USER_EMAIL, PASSWORD);
			
		    logger.info("STEP 3: Verify 'dashboard' page is visible");
		    assertTrue(isCurrentUrlWithSegment("dashboard"),"Unable to navigate 'dashboard' page,current page url: "+driver.getCurrentUrl());
		    
		    logger.info("STEP 4: Click on 'Dispatch' Menu");
		    navigation.NavigateToDispatchBoardMenu();
		    
		    logger.info("STEP 5: Click on 'Shippers' ");
		    dispatchBoardPage.clickShippers();

		    logger.info("STEP 6: Verify 'Shippers' Page ");
		    assertTrue(isCurrentUrlWithSegment("shippers"), "Unable to navigate to 'shippers' page, current url: "+driver.getCurrentUrl());

		    logger.info("STEP 7: Click on 'Add shippers' ");
		    shippersPage.clickAddshipperButton();

		    Thread.sleep(8000);
		    logger.info("STEP 8: Verify 'Add Consignee form' is displayed");
		    assertTrue(shippersPage.isAddShipperFormisDisplayed(), "Unable to display 'Add consignee form'");
		    
		    
		    
		    
		    logger.info("STEP 9:Enter 'Name'");
		    shippersPage.enterName("shipper1");
		    
		    logger.info("STEP 10:Enter 'Email'");
		    shippersPage.enterEmail("Shipper1@mail.com");
		    
		    logger.info("STEP 11:Select 'Country code'");
		    shippersPage.selectCountryCode("+91");
		    
		    logger.info("STEP 12:Enter 'Phone'");
		    shippersPage.enterPhoneNumber("9898652380");
		    
		    logger.info("STEP 13:Select 'Country Name'");
		    shippersPage.selectCountryName("India");

		    logger.info("STEP 14:Enter 'ZIP Code':"+randomZipIndia);
		    shippersPage.enterZIPCode(randomZipIndia);
    
		    logger.info("STEP 15:Enter 'Address 1'");
		    shippersPage.enterAddress("Naagavara");

		    
			   //WAIT for ZIP auto-fill / re-render
		    shippersPage.waitForZipAutoFillToComplete();

		    // 🔁 RE-ENTER ADDRESS (CRITICAL)
		   // logger.info("STEP 16:Re-enter 'Address 1' after ZIP autofill");
		   // shippersPage.enterAddress("Naagavara");
		   // Thread.sleep(20000);
		    
		    /*
		     *Location capture from zip code no need city and state
		    logger.info("STEP 16:Enter 'City'");
		    shippersPage.enterCity("Bengaluru");
		    
		    logger.info("STEP 17:Enter 'State'");
		    shippersPage.enterState("Karnataka");
		    */
		    
		    logger.info("STEP 16:Enter 'Address 2'");
		    shippersPage.enterAddress2("address2 ");

		    logger.info("STEP 17:Click on 'Save'");
		    shippersPage.clickSaveButton();
		    
		    Thread.sleep(20000);
		    
		    
		    
		    
		}
		catch (AssertionError ae) {
	        logger.error("❌ Assertion failed in Dispatch_TC002_AddShippers: " + ae.getMessage(), ae);
	        throw ae; // rethrow so TestNG marks test as failed
	    } catch (Exception e) {
	        logger.error("❌ Unexpected exception in Dispatch_TC002_AddShippers: " + e.getMessage(), e);
	        throw e; // rethrow so TestNG marks test as failed
	    }
	    logger.debug("application logs end.......");
		logger.info("**** finished Dispatch_TC002_AddShippers  *****"); 
		
	}

}
