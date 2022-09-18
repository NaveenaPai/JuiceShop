package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.TestBase;

public class OrdersPage extends TestBase {

	WebDriver driver;
	
	@FindBy(id = "checkoutButton")
	WebElement checkout;
	
	@FindBy(id="navbarAccount")
	WebElement account;
	
	@FindBy(xpath="//button/span[contains(text(),'Orders')]")
	WebElement orders;
	
	@FindBy(xpath="//button/span[contains(text(),'Order History')]")
			WebElement orderHistory;
	@FindBy(xpath = "//h1[@class='confirmation']") WebElement title;
	public OrdersPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public String Checkout() {
		wait.until(ExpectedConditions.visibilityOf(checkout));
		scrollTo(checkout);
		checkout.click();
		wait.until(ExpectedConditions.visibilityOf(title));
		return title.getText();
		
	}

	public OrderHistoryPage ViewOrderSummary()
	{
		account.click();	
		wait.until(ExpectedConditions.visibilityOf(orders));
		orders.click();
		wait.until(ExpectedConditions.visibilityOf(orderHistory));
		orderHistory.click();
		
		return new OrderHistoryPage(driver);
	}
	
	
}
