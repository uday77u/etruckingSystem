package actions;

import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pages.DashBoardPage;
import pages.HomePage;
import pages.LoginPage;

public class NavigationActions extends BaseAction{
    private WebDriver driver;
    private Logger logger;
    
    private HomePage homePage;
    private LoginPage loginPage;
    private DashBoardPage dashBoardPage;
    

    public NavigationActions(WebDriver driver) {
        this.driver = driver;
        logger=LogManager.getLogger(getClass());
        homePage = new HomePage(driver);
        
        loginPage = new LoginPage(driver);
        dashBoardPage= new DashBoardPage(driver);
        
    }
	
	
	
	//before login navigations
	public void NavigateAuthToSignup() {
		homePage.clickSignup();
	    assertTrue(isCurrentUrlWithSegment(driver,"register"),"Unable to navigate 'Register'(Signup) page,current page url: "+driver.getCurrentUrl());
		logger.info("Successful: Navigated 'Auth' page to 'Signup' page");
	}
	
	public void NavigateAuthToLoginAdmin() {
		homePage.clickAdminYesTextLink();
	    assertTrue(isCurrentUrlWithSegment(driver,"login"),"Unable to navigate 'Admin Login' page,current page url: "+driver.getCurrentUrl());
		logger.info("Successful: Navigated 'Auth' page to 'Login Admin' page");
	}
	
	public void NavigateAuthToLoginUser() {
		homePage.clickUserYesTextLink();
	    assertTrue(isCurrentUrlWithSegment(driver,"user"),"Unable to navigate 'sign-in-user' page,current page url: "+driver.getCurrentUrl());
		logger.info("Successful: Navigated 'Auth' page to 'Login User' page");
	}
	/* Future Implementation
	public void NavigateLoginAdminToSignup() {
		
	}
	public void NavigateLoginUserToSignup() {
		
	}
	*/
	

	//Logged in Navigations
	public void NavigateToDashboardMenu() {
		dashBoardPage.clickDashboardMenu();
	    assertTrue(isCurrentUrlWithSegment(driver,"dashboard"),"Unable to navigate 'dashboard' page,current page url: "+driver.getCurrentUrl());
		logger.info("Successful: Navigated 'Auth' page to 'Login User' page");
	}
	
	public void NavigateToDispatchBoardMenu() {
		dashBoardPage.clickDispatchBoardMenu();
	    assertTrue(isCurrentUrlWithSegment(driver,"dispatch"),"Unable to navigate 'dispatch' page,current page url: "+driver.getCurrentUrl());
		logger.info("Successful: Navigated 'Auth' page to 'Login User' page");
	}
	
	public void NavigateToVehiclesMenu() {
		dashBoardPage.clickVehiclesMenu();
	    assertTrue(isCurrentUrlWithSegment(driver,"vehicle"),"Unable to navigate 'vehicle' page,current page url: "+driver.getCurrentUrl());
		logger.info("Successful: Navigated 'Auth' page to 'Login User' page");
	}
	
	public void NavigateToDriversMenu() {
		dashBoardPage.clickDriversMenu();
	    assertTrue(isCurrentUrlWithSegment(driver,"drivers"),"Unable to navigate 'drivers' page,current page url: "+driver.getCurrentUrl());
		logger.info("Successful: Navigated 'Auth' page to 'Login User' page");
	}
	
	public void NavigateToAssetMappingMenu() {
		dashBoardPage.clickVehiclesMenu();;
	    assertTrue(isCurrentUrlWithSegment(driver,"asset"),"Unable to navigate 'asset mapping' page,current page url: "+driver.getCurrentUrl());
		logger.info("Successful: Navigated 'Auth' page to 'Login User' page");
	}
	
	public void NavigateToAccountingMenu() {
		dashBoardPage.clickAccountingMenu();
	    assertTrue(isCurrentUrlWithSegment(driver,"accounting"),"Unable to navigate 'accounting' page,current page url: "+driver.getCurrentUrl());
		logger.info("Successful: Navigated 'Auth' page to 'Login User' page");
	}
	
	public void NavigateToComplianceMenu() {
		dashBoardPage.clickComplianceMenu();
	    assertTrue(isCurrentUrlWithSegment(driver,"compliance"),"Unable to navigate 'compliance' page,current page url: "+driver.getCurrentUrl());
		logger.info("Successful: Navigated 'Auth' page to 'Login User' page");
	}
	
	public void NavigateToInspectionMenu() {
		dashBoardPage.clickInspectionMenu();
	    assertTrue(isCurrentUrlWithSegment(driver,"inspection"),"Unable to navigate 'inspection' page,current page url: "+driver.getCurrentUrl());
		logger.info("Successful: Navigated 'Auth' page to 'Login User' page");
	}
	
	public void NavigateToManageUsersButton() {
		dashBoardPage.clickManageUsersButton();
	    assertTrue(isCurrentUrlWithSegment(driver,"manage"),"Unable to navigate 'manage-user' page,current page url: "+driver.getCurrentUrl());
		logger.info("Successful: Navigated 'Auth' page to 'Login User' page");
	}
	

}
