package script.testcomponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import script.resource.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener 
{
	ExtentTest test;
	ExtentReports extent = ExtentReporterNG.getReportObject();
	
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); 	//Thread safe

	@Override
	public void onTestStart(ITestResult result) {
		//System.out.println("Test started: " + result.getName());
		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test); 	//This will assign unique thread id(ErrorValidation)- test [It will store as map]
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		//System.out.println("Test passed: " + result.getName());
		extentTest.get().log(Status.PASS, "Test passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		//System.out.println("Test failed: " + result.getName());
		extentTest.get().fail(result.getThrowable());		// To print the error massage

		//Screenshot:
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e1) 
		{
			e1.printStackTrace();
		}

		String filePath = null;
		try {
			filePath = getScreenShot(result.getMethod().getMethodName());
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());

	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}

}