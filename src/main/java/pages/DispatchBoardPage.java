package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.MuiActionsUtil;

public class DispatchBoardPage extends BasePage{

	private MuiActionsUtil muiUtil;
	public DispatchBoardPage(WebDriver driver) {
		super(driver);
		muiUtil=new MuiActionsUtil(driver);
	}


	//======================================================================
	//   Locators
	//======================================================================
	
	//by locators
	By ConsigneesBy=By.xpath("//span[text()='Consignees']/ancestor::a");
	By ShippersBy=By.xpath("//span[text()='Shippers']/ancestor::a");
	By BrokersBy=By.xpath("//span[text()='Brokers']/ancestor::a");
	By DirectCustomersBy=By.xpath("//span[text()='Direct Customers']/ancestor::a");
	By ExternalCarriersBy=By.xpath("//span[text()='External Carriers']/ancestor::a");
	By FactoringCompaniesBy=By.xpath("//span[text()='Factoring Comp.']/ancestor::a");
	
	//web elements
	@FindBy(xpath = "//span[text()='Consignees']/ancestor::a") private WebElement ConsigneesButton;
	@FindBy(xpath = "//span[text()='Shippers']/ancestor::a") private WebElement ShippersButton;
	
	
	
	
	
	
	
	
	
	
	
	//======================================================================
	//   Methods
	//======================================================================
	public void clickConsignees() {
		muiUtil.muiClick(ConsigneesBy);
	}
	public void clickShippers() {
		muiUtil.muiClick(ShippersBy);
	}
	public void clickBrokers() {
		muiUtil.muiClick(BrokersBy);
	}
	public void clickDirectCustomers() {
		muiUtil.muiClick(DirectCustomersBy);
	}
	public void clickExternalCarriers() {
		muiUtil.muiClick(ExternalCarriersBy);
	}
	public void clickFactoringCompanies() {
		muiUtil.muiClick(FactoringCompaniesBy);
	}
	
	
	
}
