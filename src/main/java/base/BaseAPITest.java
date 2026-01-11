package base;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import utilities.ApiLoggingFilter;
import utilities.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class BaseAPITest implements ErrorMessageConstants {
	public static Logger logger;

	@BeforeClass
	public void setup() {
		RestAssured.filters(new ApiLoggingFilter());
	    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		logger=LogManager.getLogger(this.getClass());
		logger.debug("**** Logging test cases started ****");
	}
	
    public static void assertStatusCode(Response response, int expectedStatus) {
        int actualStatus = response.getStatusCode();
        logger.info("Status Code: " + actualStatus);
        assertEquals(actualStatus, expectedStatus,
                "Invalid HTTP status, Expected status code " + expectedStatus + " but got " + actualStatus);
    }

    public static void assertStatusLine(Response response) {
        String expected = ConfigManager.getProperty("expectedStatusLine");
        String actual = response.getStatusLine();
        logger.info("Status Line: " + actual);
        assertEquals(actual, expected, "Unexpected status line");
    }

    public static void assertResponseTime(Response response) {
        int warnThreshold = ConfigManager.getIntProperty("warnResponseTime");
        int failThreshold = ConfigManager.getIntProperty("failResponseTime");

        long responseTime = response.getTime();
        logger.info("Response Time: " + responseTime + " ms");

        if (responseTime > warnThreshold) {
            logger.warn("Response time is high: " + responseTime + " ms");
        }

        assertTrue(responseTime < failThreshold,
                "Response time exceeds " + failThreshold + " ms ==> " + responseTime + " ms");
    }

    public static void assertContentType(Response response, String expected) {
        String actualContentType = response.getContentType();
        logger.info("Content-Type: " + actualContentType);
        assertTrue(actualContentType.contains(expected),
                "Expected Content-Type containing '" + expected + "' but got: " + actualContentType);
    }

    public static void assertServerType(Response response, Logger logger) {
        String expected = ConfigManager.getProperty("expectedServer");
        String actual = response.getHeader("Server");
        logger.info("Server: " + actual);
        assertEquals(actual, expected, "Unexpected server type");
    }
}