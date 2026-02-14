package base;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import utilities.ApiLoggingFilter;
import utilities.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import modelsResponse.Admin_RegisterResponse;

public class BaseAPITest implements ErrorMessageConstants,RandomTestData {
	public static Logger logger;

	@BeforeClass
	public void setup() {
		//RestAssured.filters(new ApiLoggingFilter());
	    //RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		logger=LogManager.getLogger(this.getClass());
		logger.debug("**** Logging test cases started ****");
	}
	
	
    @AfterMethod
    public void logTestResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            Throwable t = result.getThrowable();
            logger.error("Test '{}' failed: {}", result.getName(), t != null ? t.getMessage() : "No exception", t);
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.warn("Test '{}' skipped.", result.getName());
        } else {
            logger.info("Test '{}' passed.", result.getName());
        }
    }
	
	
	
	//Status code in both logs and report
	public static void assertStatusCode(Response response, int expectedStatus) {
	    int actualStatus = response.getStatusCode();
	    String responseBody = response.asString();

	    String message = "Invalid HTTP status | Expected: "
	            + expectedStatus + " | Actual: " + actualStatus
	            + " | Response body: " + responseBody;

	    logger.info("Status Code: " + actualStatus);

	    if (actualStatus != expectedStatus) {
	        logger.error(message);
	    }

	    assertEquals(actualStatus, expectedStatus, message);
	}


  //ResponseTimein both logs and report
    public static void assertResponseTime(Response response) {
        int failThreshold = ConfigManager.getIntProperty("failResponseTime");
        long responseTime = response.getTime();

        String message = "Response time exceeded limit. Threshold="
                + failThreshold + " ms, Actual=" + responseTime + " ms";

        logger.info("Response Time: " + responseTime + " ms");

        if (responseTime >= failThreshold) {
            logger.error(message);
        }

        assertTrue(responseTime < failThreshold, message);
    }

    //add error message in logs and report
    public static void assertEqualsWithLog(
            int actual,
            int expected,
            String failureContext
    ) {
        String message = failureContext +
                " | Expected: [" + expected + "]" +
                " but Found: [" + actual + "]";

        if (actual != expected) {
            logger.error(message);
        } else {
            logger.info("Assertion passed: " + message);
        }

        assertEquals(actual, expected, message);
    }
    
  //add message contains error in logs and report
    public static void assertContainsWithLog(
            Response response,
            String failureContext,
            String expectedContainsMessage
            
    ) {
        String actualMessage = null;

        try {
            actualMessage = response.jsonPath().getString("message");
        } catch (Exception e) {
            String error = "Unable to extract 'message' from response. Full response: "
                    + response.asString();
            logger.error(error, e);
            assertTrue(false, error);
        }

        String message = failureContext +
                " | Expected message to CONTAIN: [" + expectedContainsMessage + "]" +
                " | Actual message: [" + actualMessage + "]";

        boolean contains = actualMessage != null &&
                actualMessage.toLowerCase().contains(expectedContainsMessage.toLowerCase());

        if (!contains) {
            logger.error(message);
        } else {
            logger.info("Assertion passed: " + message);
        }

        assertTrue(contains, message);
    }

    public static String generateUniqueEmail(String testName) {
        return testName + "_" + System.currentTimeMillis() + "@testmail.com";
    }


 // ================= SUCCESS ASSERTIONS =================

    protected void assertOk(Response response, Admin_RegisterResponse res) {
        Assert.assertEquals(response.getStatusCode(), 200, "Expected HTTP 200");
        Assert.assertTrue(res.getStatus(), "Expected status=true");
        Assert.assertEquals(res.getCode(), Integer.valueOf(200), "Expected code=200");
    }

    protected void assertCreated(Response response, Admin_RegisterResponse res) {
        Assert.assertEquals(response.getStatusCode(),201,"Expected HTTP 201 Created");
        Assert.assertNotNull(res.getStatus(), "status must not be null");
        Assert.assertTrue(res.getStatus(), "status must be true");
    }


    // ================= CLIENT ERRORS =================

 /*   protected void assertBadRequest(Response response, Admin_RegisterResponse res) {
        Assert.assertEquals(response.getStatusCode(), 400, "Expected HTTP 400");
        Assert.assertFalse(res.getStatus(), "Expected status=false");
        Assert.assertEquals(res.getCode(), Integer.valueOf(400), "Expected code=400");
    }
*/
    protected void assertUnauthorized(Response response, Admin_RegisterResponse res) {
        Assert.assertEquals(response.getStatusCode(), 401, "Expected HTTP 401");
        Assert.assertFalse(res.getStatus(), "Expected status=false");
        Assert.assertEquals(res.getCode(), Integer.valueOf(401), "Expected code=401");
    }

    protected void assertNotFound(Response response, Admin_RegisterResponse res) {
        Assert.assertEquals(response.getStatusCode(), 404, "Expected HTTP 404");
        Assert.assertFalse(res.getStatus(), "Expected status=false");
        Assert.assertEquals(res.getCode(), Integer.valueOf(404), "Expected code=404");
    }

    protected void assertConflict(Response response, Admin_RegisterResponse res) {
        Assert.assertEquals(response.getStatusCode(), 409, "Expected HTTP 409");
        Assert.assertFalse(res.getStatus(), "Expected status=false");
        Assert.assertEquals(res.getCode(), Integer.valueOf(409), "Expected code=409");
    }

    // ================= SERVER ERRORS =================

    protected void assertServerError(Response response, Admin_RegisterResponse res) {
        Assert.assertEquals(response.getStatusCode(), 500, "Expected HTTP 500");
        Assert.assertFalse(res.getStatus(), "Expected status=false");
        Assert.assertEquals(res.getCode(), Integer.valueOf(500), "Expected code=500");
    }

    
    //not needed
    public static void assertStatusLine(Response response) {
        String expected = ConfigManager.getProperty("expectedStatusLine");
        String actual = response.getStatusLine();
        logger.info("Status Line: " + actual);
        assertEquals(actual, expected, "Unexpected status line");
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