package pages;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.TestBase;

public class HomePage extends TestBase {

	WebDriver driver;

	@FindBy(id = "navbarAccount")
	WebElement accountLink;

	@FindBy(id = "navbarLoginButton")
	WebElement loginLink;

	@FindBy(id = "navbarLogoutButton")
	WebElement logoutLink;
	
	@FindBy(xpath = "//span[contains(text(),'Dismiss')]")
	WebElement dismiss;

	@FindBy(xpath = "//a[contains(text(),'Me want it!')]")
	WebElement wantIt;

	@FindBy(css = ".item-name")
	List<WebElement> items;

	@FindBy(xpath = "//button[@aria-label='Next page']")
	WebElement next;
	
	@FindBy(xpath="//span[contains(text(),'Your Basket')]")
	WebElement basketLink;

	Actions actions;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		actions=new Actions(driver);
	}

	public HomePage NavigateToJuiceSite() {
		String url = GetConfigValue("url");
		driver.get(url);

		wait.until(ExpectedConditions.visibilityOf(dismiss));
		dismiss.click();

		wait.until(ExpectedConditions.visibilityOf(wantIt));
		wantIt.click();

		return new HomePage(driver);
	}

	public LoginPage Login() {
		accountLink.click();
		wait.until(ExpectedConditions.visibilityOf(loginLink));
		loginLink.click();
		return new LoginPage(driver);
	}

	public void Logout() {
		//actions.moveToElement(accountLink).click().build().perform();
		accountLink.click();
		wait.until(ExpectedConditions.visibilityOf(logoutLink));
		logoutLink.click();
	}
	
	public HomePage SelectProducts(Map<String, String> productsPerPage) {
		
		
		// 1st page
		AddProductsPerPage(productsPerPage.get("1"));
		Next();
		
		// 2nd page
		AddProductsPerPage(productsPerPage.get("2"));
		Next();
		
		// 3rd page
		AddProductsPerPage(productsPerPage.get("3"));

		// while (next.isEnabled()) { }
		return new HomePage(driver);

	}
	
	public BasketPage GoToBasketPage()
	{
		scrollTo(basketLink);
		basketLink.click();
		return new BasketPage(driver);
		
	}
	
	private void AddProductsPerPage(String products)
	{
		String[] productsArray=products.split(",");
		
		for(String product:productsArray)
		{
			AddProductToBasket(product.trim());
		}
	}
	private void AddProductToBasket(String productName)
	{
		String productXpath, productXpathstart, productXpathend;
		WebElement product;

		productXpathstart = "//div[contains(text(),'";
		productXpathend = "')]/ancestor::div[contains(@aria-label,'Click')]/following-sibling::div//span[text()='Add to Basket']";
		
		productXpath = productXpathstart + productName + productXpathend;
		
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(productXpath)));
		product = driver.findElement(By.xpath(productXpath));
		scrollTo(product);
		product.click();
	}
	
	private void Next()
	{
		scrollTo(next);
		next.click();
	}

	
	
	
}
