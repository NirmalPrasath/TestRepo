package StartBasic1.ExtentReport;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.concurrent.ThresholdCircuitBreaker;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

//utils.Listener
 public class ListinerClass implements ITestListener {

	
	static ExtentReports extent=ListinerClass.extentReport();
	//static ExtentReports extent=ExtentReportUtil.extentReport();
	
	//static ExtentReports extent=new ExtentReports();;
	static ExtentTest test;
	
	public static final ThreadLocal<ExtentTest> extentTest=new ThreadLocal<>();	
	public static ExtentReports extentReport()
	{
		  // Generate a unique report name with timestamp
       // String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String timeStamp = new SimpleDateFormat("yyyy-MMM-dd.HH.mm.ss").format(new Date());
        String reportName="Extend_Report"+timeStamp+".html";
        		
		//ExtentSparkReporter reporter=new ExtentSparkReporter(".\\reports\\extentReport"+System.currentTimeMillis()+".html");
		
		ExtentSparkReporter reporter=new ExtentSparkReporter("C:\\SeleniumAutomation\\myworkspace-1\\SeleniumPractices-1\\src\\test\\java\\StartBasic1\\ExtentReport\\report.html");
		
		reporter.config().setDocumentTitle("Online Store API Automation");
		reporter.config().setReportName("API Testing");
		reporter.config().setTheme(Theme.STANDARD);
		
		extent=new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Host Name", "Localhost");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", System.getProperty("user.name"));
		return extent;
		
	}
	
	
	
	
	@Override
	public void onTestStart(ITestResult result) {
		
		test=extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
		extentTest.get().log(Status.INFO, result.getMethod().getMethodName()+" execution started");
		
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		extentTest.get().log(Status.PASS, result.getMethod().getMethodName()+" Successful");
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		extentTest.get().log(Status.FAIL, result.getMethod().getMethodName()+" - Fail");
		
		test.fail(result.getThrowable());
		extentTest.set(test);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
	
		extentTest.get().log(Status.SKIP, result.getMethod().getMethodName() +"Skipped");
		test.skip(result.getThrowable());
		extentTest.set(test);
	}


	@Override
	public void onFinish(ITestContext context) {
		extentTest.get().log(Status.INFO, context.getName()+" execution finished");
		extent.flush();
	}
	
	

}



