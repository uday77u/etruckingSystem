package actions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pages.DashBoardPage;
import pages.HomePage;
import pages.LoginPage;


public class LoginActions extends BaseAction{
    private WebDriver driver;
    private Logger logger;
    
    private HomePage homePage;
    private LoginPage loginPage;
    private DashBoardPage dashBoardPage;
    

    public LoginActions(WebDriver driver) {
        this.driver = driver;
        logger=LogManager.getLogger(getClass());
        homePage = new HomePage(driver);
        
        loginPage = new LoginPage(driver);
        dashBoardPage= new DashBoardPage(driver);
        
    }

    public void LoginAdminWithLogs(String User_email, String Password) {
    //	 homePage.clickAdminYesTextLink();
 	  //  logger.info("STEP 1: Clicking on 'AdminYesTextLink'");

 	    logger.info("STEP 1: Verify login page is visible");
 	    assertEquals(driver.getCurrentUrl(), BASE_URL+"/login","Mis-match in url: ");

 	    logger.info("STEP 2: Entering the Email");
 	    loginPage.enterEmail(User_email);
 	    
 	    logger.info("STEP 3: Entering the password ");
 	    loginPage.enterPassword(Password);
 	    
 	    logger.info("STEP 4: Click on the submit button");
 	    loginPage.clickSubmitButton();
 	    
		logger.info("Account is active on other device Message displayed. Status: "+loginPage.isAccountActiveOnOtherDeviceMessage());
	    if(loginPage.isAccountActiveOnOtherDeviceMessage()) {
	    	loginPage.clickConfirmToLoginOnCurrentDevice();
	    	logger.info("Clicked on the 'confirm' button to end session in other device");
	    	
	    }
 	    
 	    logger.info("STEP 5: Verify 'dashboard' page is visible");
 	    assertTrue(isCurrentUrlWithSegment(driver,"dashboard"),"Unable to navigate 'dashboard' page,current page url: "+driver.getCurrentUrl());
 	    
 	   logger.info("Successful: Login  Admin user Account with Email: "+User_email);
   

	}
    
    public void LoginAdmin(String User_email, String Password) {
    //	 homePage.clickAdminYesTextLink();
 	  //  logger.info("STEP 1: Clicking on 'AdminYesTextLink'");

 	    assertEquals(driver.getCurrentUrl(), BASE_URL+"/login","Mis-match in url: ");


 	    loginPage.enterEmail(User_email);
 	    

 	    loginPage.enterPassword(Password);
 	    
 	    loginPage.clickSubmitButton();
 	    
		logger.info("Account is active on other device Message displayed. Status: "+loginPage.isAccountActiveOnOtherDeviceMessage());
	    if(loginPage.isAccountActiveOnOtherDeviceMessage()) {
	    	loginPage.clickConfirmToLoginOnCurrentDevice();
	    	logger.info("Clicked on the 'confirm' button to end session in other device");
	    	
	    }
 	    
 	    assertTrue(isCurrentUrlWithSegment(driver,"dashboard"),"Unable to navigate 'dashboard' page,current page url: "+driver.getCurrentUrl());
 	    
 	   logger.info("Successful: Login  Admin user Account with Email: "+User_email);
   

	}
    
    public void LogoutAdmin() {
	    logger.info("Click on the profile Icon");
	    dashBoardPage.clickProfileIcon();
	    
	    logger.info("Click on logout");
	    dashBoardPage.clickLogout();
	    
	    logger.info("Click on Logout confirm");
	    dashBoardPage.clickLogoutConfirm();
	    
	    assertTrue(isCurrentUrlWithSegment(driver,"auth"),"Unable to navigate 'Auth' page,current page url: "+driver.getCurrentUrl());
	    logger.info("Successful: Logout");
	    	    
	}
    
    
}
