package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.TestBase;

public class LoginPage extends TestBase {

	WebDriver driver;

	@FindBy(id = "navbarAccount")
	WebElement accountLink;

	@FindBy(xpath = "//span[contains(text(),'Login')]")
	WebElement loginLink;

	@FindBy(id = "email")
	WebElement email;

	@FindBy(id = "password")
	WebElement password;

	@FindBy(id = "loginButton")
	WebElement loginButton;
	
	@FindBy(id="newCustomerLink")
	WebElement newLogin;
	
	@FindBy(xpath="//div[text()='Use another account']")
	WebElement useAnotherAccount;

	@FindBy(id="identifierId")
	WebElement identifierId;
	
	@FindBy(id="identifierNext")
	WebElement identifierNext;
	
	@FindBy(name="password")
	WebElement idPasswd;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public HomePage EnterCredentials() {
		email.sendKeys(GetConfigValue("email"));
		password.sendKeys(GetConfigValue("password"));
		loginButton.click();
		return new HomePage(driver);

	}
	
	public HomePage LoginAsNewUser()
	{
		scrollTo(newLogin);
		newLogin.click();
		//wait.until(ExpectedConditions.visibilityOf(useAnotherAccount));
		//useAnotherAccount.click();
		wait.until(ExpectedConditions.visibilityOf(identifierId));
		identifierId.sendKeys(GetConfigValue("email"));
		wait.until(ExpectedConditions.visibilityOf(identifierNext));
		identifierNext.click();
		wait.until(ExpectedConditions.visibilityOf(idPasswd));
		idPasswd.sendKeys(GetConfigValue("password"));
		return new HomePage(driver);
	}
}
