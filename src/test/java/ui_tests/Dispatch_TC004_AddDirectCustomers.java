package ui_tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import actions.LoginActions;
import actions.NavigationActions;
import base.BaseUITest;
import pages.CustomersPage;
import pages.DispatchBoardPage;
import utilities.UI_ExtentReportManager;
@Listeners(UI_ExtentReportManager.class)
public class Dispatch_TC004_AddDirectCustomers extends BaseUITest{
	@Test(groups = {"Master","Dispatch"})
	public void TestDispatch_AddCustomers() throws Exception{
		try {
			
			NavigationActions navigation=new NavigationActions(driver);
		    LoginActions loginActions=new LoginActions(driver);
		    DispatchBoardPage dispatchBoardPage=new DispatchBoardPage(driver);
		    CustomersPage customersPage=new CustomersPage(driver);

			logger.debug("logging started");
		    logger.info("starting Dispatch_TC004_AddDirectCustomers");

		    logger.info("STEP 1: Navigate to Admin Login Page");
			navigation.NavigateAuthToLoginAdmin();
			
			logger.info("STEP 2: Login as Admin User");
			loginActions.LoginAdmin(USER_EMAIL, PASSWORD);
			
		    logger.info("STEP 3: Verify 'dashboard' page is visible");
		    assertTrue(isCurrentUrlWithSegment("dashboard"),"Unable to navigate 'dashboard' page,current page url: "+driver.getCurrentUrl());
		    
		    logger.info("STEP 4: Click on 'Dispatch' Menu");
		    navigation.NavigateToDispatchBoardMenu();
		    
		    logger.info("STEP 5: Click on 'Direct Customers' ");
		    dispatchBoardPage.clickDirectCustomers();

		    logger.info("STEP 6: Verify 'DirectCustomers' Page ");
		    assertTrue(isCurrentUrlWithSegment("customer"), "Unable to navigate to 'customer' page, current url: "+driver.getCurrentUrl());

		    logger.info("STEP 7: Click on 'Add Customer' ");
		    customersPage.clickAddCustomerButton();

		    Thread.sleep(8000);
		    logger.info("STEP 8: Verify 'Add Consignee form' is displayed");
		    assertTrue(customersPage.isAddCustomerFormisDisplayed(), "Unable to display 'Add consignee form'");
		    
		    
		    
		    
		    logger.info("STEP 9:Enter 'Name'");
		    customersPage.enterName("Customer1");
		    
		    logger.info("STEP 10:Enter 'Email'");
		    customersPage.enterEmail("Customer1@mail.com");
		    
		    logger.info("STEP 11:Select 'Country code'");
		    customersPage.selectCountryCode("+91");
		    
		    logger.info("STEP 12:Enter 'Phone'");
		    customersPage.enterPhoneNumber("9898652380");
		    
		    logger.info("STEP 13:Select 'Country Name'");
		    customersPage.selectCountryName("India");

		    logger.info("STEP 14:Enter 'ZIP Code':"+randomZipIndia);
		    customersPage.enterZIPCode(randomZipIndia);
    
		    logger.info("STEP 15:Enter 'Address 1'");
		    customersPage.enterAddress("Naagavara");

		    
			   //WAIT for ZIP auto-fill / re-render
		    customersPage.waitForZipAutoFillToComplete();

		    // 🔁 RE-ENTER ADDRESS (CRITICAL)
		   // logger.info("STEP 16:Re-enter 'Address 1' after ZIP autofill");
		   // customersPage.enterAddress("Naagavara");
		   // Thread.sleep(20000);
		    
		    /*
		     *Location capture from zip code no need city and state
		    logger.info("STEP 16:Enter 'City'");
		    customersPage.enterCity("Bengaluru");
		    
		    logger.info("STEP 17:Enter 'State'");
		    customersPage.enterState("Karnataka");
		    */
		    
		    logger.info("STEP 16:Enter 'Address 2'");
		    customersPage.enterAddress2("address2 ");

		    logger.info("STEP 17:Click on 'Save'");
		    customersPage.clickSaveButton();
		    
		    Thread.sleep(20000);
		    
		    
		    
		    
		}
		catch (AssertionError ae) {
	        logger.error("❌ Assertion failed in Dispatch_TC004_AddDirectCustomers: " + ae.getMessage(), ae);
	        throw ae; // rethrow so TestNG marks test as failed
	    } catch (Exception e) {
	        logger.error("❌ Unexpected exception in Dispatch_TC004_AddDirectCustomers: " + e.getMessage(), e);
	        throw e; // rethrow so TestNG marks test as failed
	    }
	    logger.debug("application logs end.......");
		logger.info("**** finished Dispatch_TC004_AddDirectCustomers  *****"); 
		
	}

}
