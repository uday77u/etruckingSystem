package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashBoardPage extends BasePage {

	public DashBoardPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	//-------------------------Locators---------------------------------------------
	
	//menu locators
	@FindBy(xpath = "//span[text()='Dashboard']/ancestor::a") private WebElement dashboardMenu;
	@FindBy(xpath = "//span[text()='Dispatch Board']/ancestor::a") private WebElement dispatchBoardMenu;
	@FindBy(xpath = "//span[text()='Vehicles']/ancestor::a") private WebElement VehiclesMenu;
	@FindBy(xpath = "//span[text()='Drivers']/ancestor::a") private WebElement driversMenu;
	@FindBy(xpath = "//span[text()='Asset Mapping']/ancestor::a") private WebElement AssetMappingMenu;
	@FindBy(xpath = "//span[text()='Accounting']/ancestor::a") private WebElement accountingMenu;
	@FindBy(xpath = "//span[text()='Compliance']/ancestor::a") private WebElement complianceMenu;
	@FindBy(xpath = "//span[text()='Inspection']/ancestor::a") private WebElement InspectionMenu;
	@FindBy(xpath = "//button[text()='Manage Users']") private WebElement manageUsersButton;
	
	
	@FindBy(xpath = "//img[contains(@class,\"MuiAvatar\")]") private WebElement profileIcon;	
	@FindBy(xpath = "//button[text()='Logout']") private WebElement logoutButton;
	@FindBy(xpath = "//button[text()='Confirm']") private WebElement logoutConfirmButton;
	
	//-------------------------Methods-----------------------------------------------
	
	//menu click actions 
	public void clickDashboardMenu() {
		dashboardMenu.click();
	}
	
	public void clickDispatchBoardMenu() {
		dispatchBoardMenu.click();
	}
	
	public void clickVehiclesMenu() {
		VehiclesMenu.click();
	}
	
	public void clickDriversMenu() {
		driversMenu.click();
	}
	
	public void clickAssetMappingMenu() {
		AssetMappingMenu.click();
	}
	
	public void clickAccountingMenu() {
		accountingMenu.click();
	}
	
	public void clickComplianceMenu() {
		complianceMenu.click();
	}
	
	public void clickInspectionMenu() {
		InspectionMenu.click();
	}
	
	public void clickManageUsersButton() {
		manageUsersButton.click();
	}
	
	
	
	//
	public void clickProfileIcon() {
		profileIcon.click();
	}

	
	public void clickLogout() {
		logoutButton.click();
	}
	
	public void clickLogoutConfirm() {
		logoutConfirmButton.click();
	}
	
	
}
