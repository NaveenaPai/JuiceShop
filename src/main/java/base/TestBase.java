package base;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.github.javafaker.Faker;

public class TestBase {

	static WebDriver driver;
	public static WebDriverWait wait;
	static ThreadLocal<WebDriver> threadsafeDriver = new ThreadLocal<>();
	static Properties prop;

	static ExtentSparkReporter reporter;
	public static ExtentTest test;
	public static ThreadLocal<ExtentTest> threadSafeTest = new ThreadLocal<ExtentTest>();

	public static final Logger logger = LogManager.getLogger();
	public Faker faker = new Faker();

	public static String GetConfigValue(String key) {
		String filepath = System.getProperty("user.dir") + "/src/resources/config.properties";
		String value = "";
		try {
			FileInputStream propFile = new FileInputStream(new File(filepath));
			prop = new Properties();
			prop.load(propFile);

			value = prop.getProperty(key, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/*
	 * public static void SetConfigValue(String key, String value) { String filepath
	 * = System.getProperty("user.dir") + "/src/resources/config.properties";
	 * 
	 * try { FileOutputStream propFile = new FileOutputStream(new File(filepath));
	 * prop = new Properties(); prop.setProperty(key, value); prop.store(propFile,
	 * "Updated value " + value + " for key " + key);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 */
	public static WebDriver Initialise_Driver() {
		String driverPath;
		String browser = GetConfigValue("browser");
		switch (browser) {
		case "chrome":
			driverPath = System.getProperty("user.dir") + "/src/resources/drivers/chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", driverPath);

			String downloadPath = System.getProperty("user.dir") + "\\target\\PDFs";
			ChromeOptions chromeOptions = new ChromeOptions();

			HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
			chromeOptionsMap.put("download.default_directory", downloadPath); //setting custom project path to place the pdf
			chromeOptionsMap.put("download.prompt_for_download", false); 
			chromeOptionsMap.put("plugins.always_open_pdf_externally", true);
			chromeOptions.setExperimentalOption("prefs", chromeOptionsMap);

			driver = new ChromeDriver(chromeOptions);

			break;
		default:
			System.out.println("Please pass the correct browser value: " + browser);
			break;
		}

		wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		threadsafeDriver.set(driver);

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		return getDriver();
	}

	public static synchronized WebDriver getDriver() {
		return threadsafeDriver.get();
	}

	public static ExtentReports GetReportInstance() {
		String filepath = System.getProperty("user.dir") + "/Reports/ExtentReport.html";
		File reportFile = new File(filepath);
		reporter = new ExtentSparkReporter(reportFile);

		ExtentReports reports = new ExtentReports();
		reports.attachReporter(reporter);

		return reports;
	}

	public static String TakeScreenshot(String methodName, WebDriver driver) {
		TakesScreenshot screenShot = (TakesScreenshot) driver;
		File src = screenShot.getScreenshotAs(OutputType.FILE);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String time = timestamp.toString().replace(":", "_").replace(" ", "_");
		String dir = System.getProperty("user.dir");
		String destFilePath = dir + "/target/ScreenShots/" + methodName + "_Screenshot_" + time + ".png";
		try {
			FileUtils.copyFile(src, new File(destFilePath));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return destFilePath;
	}

	public static void scrollTo(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public static String GenerateRandomEmail() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder builder = new StringBuilder();
		Random rnd = new Random();
		while (builder.length() < 10) { // length of the random string.
			int index = (int) (rnd.nextFloat() * chars.length());
			builder.append(chars.charAt(index));
		}
		String email = builder.toString() + "@gmail.com";
		return email;

	}

	public static void SetCustomDownloadPath() {
		ChromeOptions options = new ChromeOptions();
		String downloadPath = System.getProperty("user.dir") + "/Downloads";

		Map<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadPath);
		chromePrefs.put("download.prompt_for_download", false);
		chromePrefs.put("plugins.plugins_disabled", "Chrome PDF Viewer");

		options.setExperimentalOption("prefs", chromePrefs);

		driver = new ChromeDriver(options);
	}
}
