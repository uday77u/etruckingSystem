package ui_tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import actions.LoginActions;
import actions.NavigationActions;
import base.BaseUITest;
import pages.ConsigneesPage;
import pages.DispatchBoardPage;
import utilities.UI_ExtentReportManager;
@Listeners(UI_ExtentReportManager.class)
public class Dispatch_TC001_AddConsignee extends BaseUITest{
	@Test(groups = {"Master","Dispatch"})
	public void TestDispatch_AddConsignee() throws Exception{
		try {
			
			NavigationActions navigation=new NavigationActions(driver);
		    LoginActions loginActions=new LoginActions(driver);
		    DispatchBoardPage dispatchBoardPage=new DispatchBoardPage(driver);
		    ConsigneesPage consigneesPage=new ConsigneesPage(driver);

			logger.debug("logging started");
		    logger.info("starting Dispatch_TC001_AddConsignee");

		    logger.info("STEP 1: Navigate to Admin Login Page");
			navigation.NavigateAuthToLoginAdmin();
			
			logger.info("STEP 2: Login as Admin User");
			loginActions.LoginAdmin(USER_EMAIL, PASSWORD);
			
		    logger.info("STEP 3: Verify 'dashboard' page is visible");
		    assertTrue(isCurrentUrlWithSegment("dashboard"),"Unable to navigate 'dashboard' page,current page url: "+driver.getCurrentUrl());
		    
		    logger.info("STEP 4: Click on 'Dispatch' Menu");
		    navigation.NavigateToDispatchBoardMenu();
		    
		    logger.info("STEP 5: Click on 'Consignee' ");
		    dispatchBoardPage.clickConsignees();
		    
		    logger.info("STEP 6: Verify 'Consignee' Page ");
		    assertTrue(isCurrentUrlWithSegment("consignee"), "Unable to navigate to consignee page, current url: "+driver.getCurrentUrl());
		    
		    logger.info("STEP 7: Click on 'Add Consignee' ");
		    consigneesPage.clickAddConsigneeButton();
		    
		    Thread.sleep(8000);
		    logger.info("STEP 8: Verify 'Add Consignee form' is displayed");
		    assertTrue(consigneesPage.isAddConsigneeFormisDisplayed(), "Unable to display 'Add consignee form'");
		    
		    logger.info("STEP 9:Enter 'Name'");
		    consigneesPage.enterName("Consignee1");
		    
		    logger.info("STEP 10:Enter 'Email'");
		    consigneesPage.enterEmail("Consignee1@mail.com");
		    
		    logger.info("STEP 11:Select 'Country code'");
		    consigneesPage.selectCountryCode("+91");
		    
		    logger.info("STEP 12:Enter 'Phone'");
		    consigneesPage.enterPhoneNumber("9898652380");
		    
		    logger.info("STEP 13:Select 'Country Name'");
		    consigneesPage.selectCountryName("India");

		    logger.info("STEP 14:Enter 'ZIP Code':"+randomZipIndia);
		    consigneesPage.enterZIPCode(randomZipIndia);
    
		    logger.info("STEP 15:Enter 'Address 1'");
		    consigneesPage.enterAddress("Naagavara");

		    
			   //WAIT for ZIP auto-fill / re-render
		    consigneesPage.waitForZipAutoFillToComplete();

		    // 🔁 RE-ENTER ADDRESS (CRITICAL)
		   // logger.info("STEP 16:Re-enter 'Address 1' after ZIP autofill");
		   // consigneesPage.enterAddress("Naagavara");
		   // Thread.sleep(20000);
		    
		    /*
		     *Location capture from zip code no need city and state
		    logger.info("STEP 16:Enter 'City'");
		    consigneesPage.enterCity("Bengaluru");
		    
		    logger.info("STEP 17:Enter 'State'");
		    consigneesPage.enterState("Karnataka");
		    */
		    
		    logger.info("STEP 16:Enter 'Address 2'");
		    consigneesPage.enterAddress2("address2 ");

		    logger.info("STEP 17:Click on 'Save'");
		    consigneesPage.clickSaveButton();
		    
		    Thread.sleep(20000);
		    
		    
		    
		    
		}
		catch (AssertionError ae) {
	        logger.error("❌ Assertion failed in Dispatch_TC001_AddConsignee: " + ae.getMessage(), ae);
	        throw ae; // rethrow so TestNG marks test as failed
	    } catch (Exception e) {
	        logger.error("❌ Unexpected exception in Dispatch_TC001_AddConsignee: " + e.getMessage(), e);
	        throw e; // rethrow so TestNG marks test as failed
	    }
	    logger.debug("application logs end.......");
		logger.info("**** finished Dispatch_TC001_AddConsignee  *****"); 
		
	}

}
