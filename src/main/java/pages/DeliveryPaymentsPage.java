	package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.TestBase;

public class DeliveryPaymentsPage extends TestBase {

	WebDriver driver;
	@FindBy(xpath = "//span[text()='Continue']")
	WebElement continueBtn;
	@FindBy(xpath = "//mat-row")
	WebElement deliveryRows;
	

	@FindBy(id="mat-expansion-panel-header-0")
	WebElement addNewCard;
	
	@FindBy(xpath = "//input/following-sibling::span//mat-label[text()='Name']")
	WebElement name;
	@FindBy(xpath = "//input/following-sibling::span//mat-label[text()='Card Number']")
	WebElement cardNumber;
	
	@FindBy(xpath = "//mat-form-field//select/following-sibling::span//mat-label[text()='Expiry Month']")
	WebElement expiryMonth;
	
	@FindBy(xpath = "//mat-form-field//select/following-sibling::span//mat-label[text()='Expiry Year']")
	WebElement expiryYear;
	
	@FindBy(id = "submitButton")
	WebElement submitButton;
	
	@FindBy(css=".mat-radio-button.mat-accent")
	WebElement card;
	
	@FindBy(id="collapseCouponElement") WebElement couponPanel;
	@FindBy(id="coupon") WebElement coupon;
	@FindBy(css=".error.ng-star-inserted") WebElement couponError;
	@FindBy(id="applyCouponButton") WebElement couponRedeem;
	@FindBy(css=".nextButton") WebElement next;
	
	Actions action;
	public DeliveryPaymentsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action=new Actions(driver);
	}

	public void ChooseDeliveryMethod(String deliveryType) {
		String deliveryXPath = "//mat-cell[contains(text(),'" + deliveryType + "')]";
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(deliveryXPath)));
		WebElement delivery=deliveryRows.findElement(By.xpath(deliveryXPath));
		scrollTo(delivery);
		delivery.click();
		wait.until(ExpectedConditions.visibilityOfAllElements(continueBtn));
		scrollTo(continueBtn);
		continueBtn.click();
	}

	public void AddNewCard() {
		
		addNewCard.click();
		wait.until(ExpectedConditions.visibilityOf(name));
		action.moveToElement(name).click().sendKeys(faker.name().name()).build().perform();
		action.moveToElement(cardNumber).click().sendKeys(faker.number().digits(16)).build().perform();
		//action.moveToElement(expiryMonth).click().sendKeys(faker.number().digits(16)).build().perform();
		action.moveToElement(expiryMonth).click().sendKeys("2").build().perform();
		action.moveToElement(expiryYear).click().sendKeys("2022").build().perform();
		//Select month=new Select(expiryMonth);
		//month.selectByVisibleText("2");
		//Select year=new Select(expiryYear);
		//year.selectByVisibleText("2022");
		submitButton.click();
		
}
	
	public String VerifyCoupon()
	{
		String errorMsg="";
		scrollTo(coupon);
		action.moveToElement(couponPanel).click().build().perform();
		
		couponPanel.click();
		wait.until(ExpectedConditions.visibilityOf(coupon));
		action.moveToElement(coupon).sendKeys(faker.number().digits(10)).click().build().perform();
		couponRedeem.click();
		wait.until(ExpectedConditions.visibilityOf(couponError));
		errorMsg=couponError.getText();
		
		return errorMsg;
		
	}
	
	public OrdersPage PlaceOrder()
	{
		wait.until(ExpectedConditions.visibilityOf(card));
		card.click();
		scrollTo(next);
		next.click();
		return new OrdersPage(driver);
	}
}
