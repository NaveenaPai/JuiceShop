package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;

public class OrderHistoryPage extends TestBase {

	WebDriver driver;

	@FindBy(xpath = "//button[@aria-label='Track Your Order']")
	WebElement trackOrder;

	@FindBy(xpath = "//button[@aria-label='Print order confirmation']")
	WebElement printOrder;

	@FindBy (xpath="//h1//code") WebElement trackingCode;
	public OrderHistoryPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void TrackOrder() {
		trackOrder.click();
		String destnPath=TakeScreenshot("OrderSummary", driver);
		logger.info("Screenshot of Order Summary successfully saved at : "+destnPath);
		
	}

	public void PrintOrder() {
		printOrder.click();
	}

}
