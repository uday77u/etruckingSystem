package utilities;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UI_ExtentReportManager implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private Logger logger;

    private String repName;
    private String timeStamp;
    private int testNo = 0;

    @Override
    public void onStart(ITestContext context) {

        logger = LogManager.getLogger(getClass());

        timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "UI-Test-Report-" + timeStamp + ".html";

        ExtentSparkReporter sparkReporter =
                new ExtentSparkReporter(
                        System.getProperty("user.dir")
                                + File.separator + "reports"
                                + File.separator + repName);

        sparkReporter.config().setDocumentTitle("UI Automation Report");
        sparkReporter.config().setReportName("UI Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Application", "etruckingSystem");
        extent.setSystemInfo("Environment",
                System.getProperty("env", "qa").toUpperCase());
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
    }

    @Override
    public void onTestStart(ITestResult result) {

        testNo++;

        String testName = result.getTestClass().getName()
                + " :: " + result.getMethod().getMethodName();

        ExtentTest test = extent.createTest(testName);
        test.assignCategory(result.getMethod().getGroups());

        extentTest.set(test);
        logger.info("Starting Test {} : {}", testNo, testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        ExtentTest test = extentTest.get();
        test.log(Status.FAIL, result.getThrowable());

        Object instance = result.getInstance();
        if (instance instanceof base.BaseUITest) {
            try {
                String screenshotPath =
                        ((base.BaseUITest) instance)
                                .getScreenshot(result.getMethod().getMethodName());
                test.addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                test.warning("Screenshot failed: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = extentTest.get();
        test.log(Status.SKIP, "Test skipped");
        if (result.getThrowable() != null) {
            test.log(Status.SKIP, result.getThrowable());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        logger.info("Execution finished. Report: {}", repName);
        
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

}
