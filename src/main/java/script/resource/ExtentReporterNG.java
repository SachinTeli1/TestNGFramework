package script.resource;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG 
{
	public static ExtentReports getReportObject() 
	{
		String path = System.getProperty("user.dir") + "/Reports/index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Web Automation Reports");
		reporter.config().setDocumentTitle("Extent Reports");
		
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Sachin");
		return extent;
	}
}