package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

//Extent report 5.x...//version

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


public class ExtentReportManager implements ITestListener {
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	public Logger logger;

	private String repName;
	private String timeStamp;
	private int testNo=0;

	public void onStart(ITestContext testContext) {
		logger=LogManager.getLogger(getClass());
		
		/*
		SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String currentdatetimestamp=df.format(dt);
		*/
		
		timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
		repName = "Test-Report-" + timeStamp + ".html";
		
		logger.info("\n\t\t******* Test Case execution started "+timeStamp+"******");
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);// specify location of the report

		sparkReporter.config().setDocumentTitle("etruckingSystem"
				+ " Automation Report"); // Title of report
		sparkReporter.config().setReportName("etruckingSystem Functional Testing"); // name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "etruckingSystem");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environemnt", "QA");
		
		String os = testContext.getCurrentXmlTest().getParameter("os");
		if (os == null) {
		    os = System.getProperty("os.name");
		}
		extent.setSystemInfo("Operating System", os);
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()) {
		extent.setSystemInfo("Groups", includedGroups.toString());
		
		
		
		}
	}
	
	
	@Override
	public void onTestStart(ITestResult result) {
		testNo++;
		System.out.println("\nTest Case "+testNo+": "+result.getTestClass().getName());
		//test = extent.createTest(result.getMethod().getMethodName());
		test = extent.createTest(result.getTestClass().getName());
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println(" -->"+result.getMethod().getMethodName()+" got passed.");
		
		//test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.log(Status.PASS,result.getName()+" got successfully executed");
		
	}

	
	@Override
	public void onTestFailure(ITestResult result) {

	    System.out.println(" -->" + result.getMethod().getMethodName() + " got failed.");

	    test = extent.createTest(result.getTestClass().getName());
	    test.assignCategory(result.getMethod().getGroups());

	    test.log(Status.FAIL, result.getName() + " got failed");
	    test.log(Status.FAIL, result.getThrowable());

	    Object testClass = result.getInstance();

	    if (!(testClass instanceof base.BaseUITest)) {
	        test.warning("Test class does not extend BaseClass");
	        return;
	    }

	    base.BaseUITest base = (base.BaseUITest) testClass;

	    try {
	        String screenshotPath = base.getScreenshot(result.getName());

	        if (screenshotPath != null) {
	            test.addScreenCaptureFromPath(screenshotPath);
	        } else {
	            test.warning("Screenshot not captured (driver unavailable)");
	        }

	    } catch (IOException e) {
	        test.warning("Screenshot capture failed: " + e.getMessage());
	    }
	}
	
	public void onTestSkipped(ITestResult result) {
		System.out.println(" -->"+result.getMethod().getMethodName()+" got skipped.");
		
		//test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+" got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}

	@Override
	public void onFinish(ITestContext context) {

	    extent.flush();
	    logger.info("******* Test Case execution finished {} ******", timeStamp);

	    String reportPath = System.getProperty("user.dir")
	            + File.separator + "reports"
	            + File.separator + repName;

	    File reportFile = new File(reportPath);

	    if (reportFile.exists()) {
	        logger.info("Extent Report generated at:");
	        logger.info(reportFile.getAbsolutePath());
	    } else {
	        logger.error("Extent Report was NOT generated!");
	    }
	}

		//To send email with attachment
		//sendEmail(sender email,sender password(encrypted),recipient email);
		//sendEmail(xyz@gmail.com","encrypted password","abc@gmail.com");
	


}