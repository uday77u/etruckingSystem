package actions;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.FrameWorkConstants;

public class BaseAction implements FrameWorkConstants{
	

	//utility method to verify the current page URL contains segment
	public boolean isCurrentUrlWithSegment(WebDriver driver, String segment) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
		wait.until(ExpectedConditions.urlContains(segment));
		return driver.getCurrentUrl().toLowerCase().contains(segment.toLowerCase());
	}
	
	//utility method to verify the current page Title contains segment
	public boolean isCurrentTitleWithSegment(WebDriver driver,String segment) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
		wait.until(ExpectedConditions.titleIs(segment));
		return driver.getTitle().toLowerCase().contains(segment.toLowerCase());
	}
	
	//utility method to explicit wait for web element to be click able
	public void waitForWebElementToBeClickable(WebDriver driver,WebElement webElement) {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
		wait.until(ExpectedConditions.elementToBeClickable(webElement));
	}
	
	//utility method to drag the element to view
	public void scrollToViewWebElement(WebDriver driver,WebElement webelement) {
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", webelement);
	}

	//utility wait for visibility of an element
		public void waitForVisibilityOfWebelement(WebDriver driver,WebElement webelement) {
			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
			wait.until(ExpectedConditions.visibilityOf(webelement));
		}

}
