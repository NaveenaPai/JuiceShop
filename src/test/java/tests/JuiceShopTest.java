package tests;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import base.TestBase;
import pages.AddressPage;
import pages.BasketPage;
import pages.DeliveryPaymentsPage;
import pages.HomePage;
import pages.LoginPage;
import pages.OrderHistoryPage;
import pages.OrdersPage;

public class JuiceShopTest extends TestBase {

	public WebDriver driver;
	HomePage homePage;
	LoginPage loginPage;
	BasketPage basketPage;
	AddressPage addressPage;
	DeliveryPaymentsPage deliveryPaymentsPage;
	OrdersPage ordersPage;
	OrderHistoryPage orderHistorPage;

	/*Login to the website*/
	@BeforeTest
	public void setup() {

		driver = Initialise_Driver();
		logger.info("Driver successfully initialized");
		homePage = new HomePage(driver);
		homePage = homePage.NavigateToJuiceSite();
		logger.info("NavigateToJuiceSite(): Successfully navigated to " + GetConfigValue("url"));
		loginPage = homePage.Login();
		homePage = loginPage.EnterCredentials();
		logger.info("Login(): Successfully logged in for " + GetConfigValue("email"));
	}

	/*
	 * Add one or two items to basket (from each page) – scroll, navigate and select
	 * product
	 */
	@Test
	public void SelectProducts() {
		logger.info("Selection of products - Start ");

		Map<String, String> productsPerPage = new HashMap<>();
		// Products per page
		productsPerPage.put("1", "Apple Juice,Lemon Juice");
		productsPerPage.put("2", "Girlie-Shirt,Sticker Single");
		productsPerPage.put("3", "Raspberry Juice");

		// Scroll, navigate and select product
		homePage = homePage.SelectProducts(productsPerPage);
		logger.info("Orders successfully placed for -> " + productsPerPage.get("1") + "," + productsPerPage.get("2")
				+ "," + productsPerPage.get("3"));
		logger.info("SelectProducts(): Selection of products - End ");
	}

	/* Go to your basket and increase the quantity of all items by 2 */
	@Test(dependsOnMethods = "SelectProducts")
	public void GoToBasketPageIncreaseProductCount() throws InterruptedException {
		// Go to the basket
		basketPage = homePage.GoToBasketPage();

		// Increase the quantity of all items by 2
		basketPage.IncreaseProductCnt(2);
		logger.info("GoToBasketPageIncreaseProductCount(): Successfully increased the quantity of all items by 2");
	}

	/*
	 * Press checkout and add a new address (for all inputs generate random values
	 * using code and don’t hard code)
	 */
	@Test(dependsOnMethods = "GoToBasketPageIncreaseProductCount")
	public void CheckoutUpdateAddress() {
		//Press checkout 
		addressPage = basketPage.checkout();
		
		//Add a new address (for all inputs generate random values using code and don’t hard code)
		deliveryPaymentsPage = addressPage.UpdateAddressDetails();
		logger.info("CheckoutUpdateAddress(): Successfully updated Address Details");
	}

	/*Select the address and any delivery speed*/
	@Test(dependsOnMethods = "CheckoutUpdateAddress")
	public void ChooseDeliveryMethod() {
		String deliveryMthod = "Standard Delivery";
		deliveryPaymentsPage.ChooseDeliveryMethod(deliveryMthod);
		logger.info("ChooseDeliveryMethod(): Delivery method chosen: " + deliveryMthod);
	}

	/*Add a new card*/
	@Test(dependsOnMethods = "ChooseDeliveryMethod")
	public void AddNewCard() {
		deliveryPaymentsPage.AddNewCard();
		logger.info("AddNewCard(): Successfully added new card details");
	}

	//@Test(dependsOnMethods = "ChooseDeliveryMethod")
	public void VerifyCoupon() {
		String expectedErrorMsg = "Invalid coupon.";
		String actualErrorMsg = deliveryPaymentsPage.VerifyCoupon();
		Assert.assertEquals(actualErrorMsg, expectedErrorMsg);
		logger.info("VerifyCoupon(): Verified invalid coupon error message to be" + actualErrorMsg);
	}

	/*Select card, review and place the order.*/
	@Test(dependsOnMethods = "AddNewCard")
	public void PlaceOrderCheckOut() {
		String expectedHeaderTitle = "Thank you for your purchase!";
		ordersPage = deliveryPaymentsPage.PlaceOrder();
		logger.info("PlaceOrder(): Successfully placed order");
		String actualHeaderTitle = ordersPage.Checkout();
		Assert.assertEquals(actualHeaderTitle, expectedHeaderTitle);
		logger.info("Checkout(): Successfully checked out & verified title of order summary");
	}

	@Test(dependsOnMethods = "PlaceOrderCheckOut")
	public void ViewOrderHistory() {

		orderHistorPage = ordersPage.ViewOrderSummary();
		orderHistorPage.PrintOrder();
		logger.info(" Successfully placed order and save the order as pdf" );
		orderHistorPage.TrackOrder();
	}

	@Test(dependsOnMethods = "ViewOrderHistory")
	public void Logout()
	{
		homePage.Logout();
		logger.info("Successfully logged out");
	}
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
