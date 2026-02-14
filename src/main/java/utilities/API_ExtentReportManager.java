package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class API_ExtentReportManager implements ITestListener {

    private ExtentSparkReporter sparkReporter;
    private ExtentReports extent;
    private ExtentTest test;
    private Logger logger;

    private String repName;
    private String timeStamp;
    private int testCounter = 0;

    @Override
    public void onStart(ITestContext testContext) {

        logger = LogManager.getLogger(getClass());

        timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "API-Test-Report-" + timeStamp + ".html";

        logger.info("******* API Test Execution Started: {} *******", timeStamp);

        sparkReporter = new ExtentSparkReporter(
                System.getProperty("user.dir") + "/reports/" + repName);

        sparkReporter.config().setDocumentTitle("API Automation Report");
        sparkReporter.config().setReportName("API Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Application", "etruckingSystem");
        extent.setSystemInfo("Test Type", "API Automation");
        extent.setSystemInfo("Environment", "QA");  //Hard coded env
        
      
        //extent.setSystemInfo("Environment", System.getProperty("env", "qa").toUpperCase());                              //update env in configManager and update it
                 
        /*mvn test -Dci=true       //run ci with
        if (!Boolean.parseBoolean(System.getProperty("ci", "false"))
                && Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(reportFile.toURI());
        }
        */
        
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));

        List<String> groups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!groups.isEmpty()) {
            extent.setSystemInfo("Groups", groups.toString());
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        testCounter++;

        String className = result.getTestClass().getName()
                .substring(result.getTestClass().getName().lastIndexOf('.') + 1);

        String methodName = result.getMethod().getMethodName();

        test = extent.createTest(className + " :: " + methodName);
        test.assignCategory(result.getMethod().getGroups());

        logger.info("\n\n### Starting Test {} : {} ###", testCounter, methodName);
    }


    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test passed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.log(Status.FAIL, "Test failed");
        test.log(Status.FAIL, result.getThrowable());

        // Optional: attach API response if available
        Object response = result.getTestContext().getAttribute("API_RESPONSE");
        if (response != null) {
            test.log(Status.INFO, "API Response:");
            test.info("<pre>" + response.toString() + "</pre>");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, "Test skipped");
        if (result.getThrowable() != null) {
            test.log(Status.SKIP, result.getThrowable());
        }
    }

    @Override
    public void onFinish(ITestContext testContext) {

        extent.flush();
        logger.info("******* API Test Execution Finished: {} *******", timeStamp);

        // Auto-open report (local runs only)
        autoOpenReport();
    }
    
    
    
    
	 // Auto-open report (local runs only)
    public void autoOpenReport() {
        try {
            File reportFile = new File(
                    System.getProperty("user.dir") + "/reports/" + repName);

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(reportFile.toURI());
            }

        } catch (IOException e) {
            logger.error("Failed to open Extent Report", e);
        }
    }
    //-----------------Added for re usable method for assertion messages---------------
   /* private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    private API_ExtentReportManager() {}

    public static void setTest(ExtentTest test) {
        extentTest.set(test);
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    // ✅ INFO
    public static void logInfo(String message) {
        if (getTest() != null) {
            getTest().info(message);
        }
    }

    // ✅ PASS
    public static void logPass(String message) {
        if (getTest() != null) {
            getTest().pass(message);
        }
    }

    // ✅ FAIL
    public static void logFail(String message) {
        if (getTest() != null) {
            getTest().fail(message);
        }
    }*/
}
