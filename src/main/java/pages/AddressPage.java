package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.TestBase;

public class AddressPage extends TestBase {

	WebDriver driver;
		

	@FindBy(xpath = "//button[@aria-label='Add a new address']")
	WebElement addAddress;

	@FindBy(xpath = "//input[@placeholder='Please provide a country.']")
	WebElement country;
	@FindBy(xpath = "//input[@placeholder='Please provide a mobile number.']")
	WebElement mobile;
	@FindBy(xpath = "//input[@placeholder='Please provide a name.']")
	WebElement name;
	@FindBy(xpath = "//input[@placeholder='Please provide a ZIP code.']")
	WebElement zip;
	@FindBy(xpath = "//textarea[@placeholder='Please provide an address.']")
	WebElement address;
	@FindBy(xpath = "//input[@placeholder='Please provide a city.']")
	WebElement city;
	@FindBy(xpath = "//input[@placeholder='Please provide a state.']")
	WebElement state;
	@FindBy(id = "submitButton")
	WebElement submitButton;
	@FindBy(css  = ".mat-radio-button.mat-accent")
	WebElement addressRadioBtn;

	@FindBy (xpath="//span[text()='Continue']")
			WebElement continueBtn;
	
	public AddressPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public DeliveryPaymentsPage UpdateAddressDetails() {
		addAddress.click();
		country.sendKeys(faker.address().country());
		mobile.sendKeys(faker.number().digits(10));
		name.sendKeys(faker.name().name());
		zip.sendKeys(faker.number().digits(5));
		address.sendKeys(faker.address().country());
		city.sendKeys(faker.address().cityName());
		state.sendKeys(faker.address().state());
		submitButton.click();
		
		wait.until(ExpectedConditions.visibilityOf(addressRadioBtn));
		scrollTo(addressRadioBtn);
		addressRadioBtn.click();
		wait.until(ExpectedConditions.visibilityOf(continueBtn));
		continueBtn.click();
		return new DeliveryPaymentsPage(driver);
	}

	
	
	
}
