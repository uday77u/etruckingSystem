package ui_tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import actions.LoginActions;
import actions.NavigationActions;
import base.BaseUITest;
import pages.DispatchBoardPage;
import pages.FactoringCompPage;
import utilities.UI_ExtentReportManager;

@Listeners(UI_ExtentReportManager.class)
public class Dispatch_TC006_AddFactoringCompanies extends BaseUITest{
	@Test(groups = {"Master","Dispatch"})
	public void TestDispatch_AddShippers() throws Exception{
		try {
			
			NavigationActions navigation=new NavigationActions(driver);
		    LoginActions loginActions=new LoginActions(driver);
		    DispatchBoardPage dispatchBoardPage=new DispatchBoardPage(driver);
		    FactoringCompPage factoringCompPage=new FactoringCompPage(driver);

			logger.debug("logging started");
		    logger.info("starting Dispatch_TC006_AddFactoringCompanies");

		    logger.info("STEP 1: Navigate to Admin Login Page");
			navigation.NavigateAuthToLoginAdmin();
			
			logger.info("STEP 2: Login as Admin User");
			loginActions.LoginAdmin(USER_EMAIL, PASSWORD);
			
		    logger.info("STEP 3: Verify 'dashboard' page is visible");
		    assertTrue(isCurrentUrlWithSegment("dashboard"),"Unable to navigate 'dashboard' page,current page url: "+driver.getCurrentUrl());
		    
		    logger.info("STEP 4: Click on 'Dispatch' Menu");
		    navigation.NavigateToDispatchBoardMenu();
		    
		    logger.info("STEP 5: Click on 'Factoring Companies' ");
		    dispatchBoardPage.clickFactoringCompanies();

		    logger.info("STEP 6: Verify 'factoring-comp' Page ");
		    assertTrue(isCurrentUrlWithSegment("factoring-comp"), "Unable to navigate to 'factoring-comp' page, current url: "+driver.getCurrentUrl());

		    logger.info("STEP 7: Click on 'Add Factoring' ");
		    factoringCompPage.clickAddFactoringButton();

		    Thread.sleep(8000);
		    logger.info("STEP 8: Verify 'Add Factoring form' is displayed");
		    assertTrue(factoringCompPage.isAddFactoringFormisDisplayed(), "Unable to display 'Add Factoring form'");
		    
		    
		    
		    
		    logger.info("STEP 9:Enter 'Company Name'");
		    factoringCompPage.enterCompanyName("CompanyName1");
		    
		    logger.info("STEP 10:Enter 'Email'");
		    factoringCompPage.enterEmail("CompanyName1@mail.com");
		    
		    logger.info("STEP 11:Select 'Country code'");
		    factoringCompPage.selectCountryCode("+91");
		    
		    logger.info("STEP 12:Enter 'Phone'");
		    factoringCompPage.enterPhoneNumber("9898652380");
		    
		    logger.info("STEP 13:Select 'Country Name'");
		    factoringCompPage.selectCountryName("India");

		    logger.info("STEP 14:Enter 'ZIP Code':"+randomZipIndia);
		    factoringCompPage.enterZIPCode(randomZipIndia);
    
		    logger.info("STEP 15:Enter 'Address 1'");
		    factoringCompPage.enterAddress("Naagavara");

		    
			   //WAIT for ZIP auto-fill / re-render
		    factoringCompPage.waitForZipAutoFillToComplete();

		    // 🔁 RE-ENTER ADDRESS (CRITICAL)
		   // logger.info("STEP 16:Re-enter 'Address 1' after ZIP autofill");
		   // factoringCompPage.enterAddress("Naagavara");
		   // Thread.sleep(20000);
		    
		    /*
		     *Location capture from zip code no need city and state
		    logger.info("STEP 16:Enter 'City'");
		    factoringCompPage.enterCity("Bengaluru");
		    
		    logger.info("STEP 17:Enter 'State'");
		    factoringCompPage.enterState("Karnataka");
		    */
		    
		   // logger.info("STEP 16:Enter 'Address 2'");
		   // factoringCompPage.enterAddress2("address2 ");

		    logger.info("STEP 17:Click on 'Save'");
		    factoringCompPage.clickSaveButton();
		    
		    Thread.sleep(20000);
		    
		    
		    
		    
		}
		catch (AssertionError ae) {
	        logger.error("❌ Assertion failed in Dispatch_TC006_AddFactoringCompanies: " + ae.getMessage(), ae);
	        throw ae; // rethrow so TestNG marks test as failed
	    } catch (Exception e) {
	        logger.error("❌ Unexpected exception in Dispatch_TC006_AddFactoringCompanies: " + e.getMessage(), e);
	        throw e; // rethrow so TestNG marks test as failed
	    }
	    logger.debug("application logs end.......");
		logger.info("**** finished Dispatch_TC006_AddFactoringCompanies  *****"); 
		
	}

}
