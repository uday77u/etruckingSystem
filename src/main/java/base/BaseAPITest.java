package base;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import utilities.ApiLoggingFilter;
import io.restassured.RestAssured;

public class BaseAPITest implements ErrorMessageConstants {
	public static Logger logger;

	@BeforeClass
	public void setup() {
		RestAssured.filters(new ApiLoggingFilter());
	    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		logger=LogManager.getLogger(this.getClass());
		logger.debug("**** Logging test cases started ****");
	}
	
	
}