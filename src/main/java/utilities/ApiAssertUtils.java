package utilities;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class ApiAssertUtils {

    private ApiAssertUtils() {
        // prevent instantiation
    }

    public static void assertMessageContains(
            String actualMessage,
            String expectedContains,
            Logger logger
    ) {

        String logMessage =
                "Validating error message\n" +
                "Expected to contain : [" + expectedContains + "]\n" +
                "Actual message      : [" + actualMessage + "]";

        // ---- LOGGING ----
        logger.info(logMessage);

        // ---- EXTENT REPORT ----
        //API_ExtentReportManager.logInfo(logMessage);

        // ---- ASSERTION ----
        if (!actualMessage.contains(expectedContains)) {
            String failureMessage =
                    "❌ Error message validation failed\n" +
                    "Expected to contain : [" + expectedContains + "]\n" +
                    "Actual message      : [" + actualMessage + "]";

            logger.error(failureMessage);
           // API_ExtentReportManager.logFail(failureMessage);

            Assert.fail(failureMessage);
        }

        // ---- SUCCESS ----
        String successMessage = "✅ Error message validation passed";
        logger.info(successMessage);
       // API_ExtentReportManager.logPass(successMessage);
    }
}
