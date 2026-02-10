package base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import utilities.ConfigManager;
import utilities.geoLocatorUtil;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class BaseUITest implements FrameWorkConstants{

	public WebDriver driver;
	public Logger logger;
	
	@BeforeSuite(alwaysRun = true)
	@Parameters("env")
	public void beforeSuite(@Optional("") String xmlEnv) {

	    String env = !xmlEnv.isEmpty()
	            ? xmlEnv
	            : System.getProperty("env", "dev");

	    ConfigManager.loadConfig(env);
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({"browser"})
	public void setup(@Optional("chrome") String Browser)//
	{
		logger=LogManager.getLogger(this.getClass());
		
		try {
			driver=BrowserFactory.initBrowser(Browser,HEADLESS_EXECUTION);
			if (driver == null) {
	            throw new RuntimeException("Driver initialization failed");
	        }
			
			if(HEADLESS_EXECUTION)
				logger.info(Browser+" launched in HEADLESS mode.");
				else
					logger.info(Browser+" launched in NORMAL mode.");
				
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				logger.info("Browser Window is maximized.");
				//driver.get("https://etruckingsystem.com/auth");
				
				String country="USA";
				geoLocatorUtil.setGeoLocation(driver, country);
				driver.navigate().refresh();
				
			    logger.info("* Launching the browser");
			    logger.info("* Navigating to baseURL: "+BASE_URL);
			    driver.get(BASE_URL);
			
		} catch (Exception e) {
			logger.error("Setup failed", e);
	        throw e;
		}
		
		
		
	}
	
	@AfterMethod (alwaysRun = true)
	public void tearDown() {
		if(driver!=null) {
		driver.quit();
		logger.info("Browser is closed.\n");
		}
	}
	
	public String getScreenshot(String testName) throws IOException {

		 // ✅ Ensure logger is always available
	    if (logger == null) {
	        logger = LogManager.getLogger(this.getClass());
	    }
	    
	    if (driver == null) {
	        logger.error("Driver is null. Screenshot skipped.");
	        return null;
	    }

	    TakesScreenshot ts = (TakesScreenshot) driver;
	    File source = ts.getScreenshotAs(OutputType.FILE);

	    String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	    String targetPath = System.getProperty("user.dir")
	            + File.separator + "Screenshots"
	            + File.separator + testName + "_" + timeStamp + ".png";

	    File targetFile = new File(targetPath);
	    targetFile.getParentFile().mkdirs();
	    FileUtils.copyFile(source, targetFile);

	    return targetPath;
	}

	//utility method to verify the current page URL contains segment
	public boolean isCurrentUrlWithSegment(String segment) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.urlContains(segment));
		return driver.getCurrentUrl().toLowerCase().contains(segment.toLowerCase());
	}
	
	//utility method to verify the current page Title contains segment
	public boolean isCurrentTitleWithSegment(String segment) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
		wait.until(ExpectedConditions.titleIs(segment));
		return driver.getTitle().toLowerCase().contains(segment.toLowerCase());
	}
	
	//utility method to explicit wait for web element to be click able
	public void waitForWebElementToBeClickable(WebElement webElement) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
		wait.until(ExpectedConditions.elementToBeClickable(webElement));
	}
	
	//utility method to drag the element to view
	public void scrollToViewWebElement(WebDriver driver,WebElement webelement) {
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", webelement);
	}

	//utility wait for visibility of an element
		public void waitForVisibilityOfWebelement(WebElement webelement) {
			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
			wait.until(ExpectedConditions.visibilityOf(webelement));
		}
}