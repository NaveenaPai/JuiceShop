package listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;

import base.TestBase;

public class TestNgListener extends TestBase implements ITestListener {

	ExtentReports reports = GetReportInstance();
	

	
	@Override
	public void onTestStart(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		logger.info("Test Started for -> " + methodName);
		test = reports.createTest(methodName);
		test.log(Status.INFO, "Test has started for " + methodName);
		threadSafeTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		logger.info("Test Successfully passed  -> " + methodName);
		threadSafeTest.get().log(Status.PASS, "Test has been successful for " + methodName);
	}

	@Override
	public void onTestFailure(ITestResult result) {

		String methodName = result.getMethod().getMethodName();
		logger.fatal("Test failed -> " + methodName);
		threadSafeTest.get().log(Status.FAIL, "Test has failed for " + methodName);

		WebDriver driver;
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver")
					.get(result.getInstance());
			String destnFile = TakeScreenshot(methodName, driver);
			threadSafeTest.get().addScreenCaptureFromPath(destnFile);
		} catch (Exception e) {
			logger.fatal("Fatal error occured for "+ methodName );
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		logger.info("Test skipped for-> " + methodName);
		threadSafeTest.get().log(Status.SKIP, "Test is skipped for " + methodName);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {

	}

	@Override
	public void onFinish(ITestContext context) {
		reports.flush();
	}

}
