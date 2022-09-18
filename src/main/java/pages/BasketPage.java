package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.TestBase;

public class BasketPage extends TestBase {
	WebDriver driver;
	Actions actions;

	@FindBy(xpath = "//mat-row[@role='row']")
	List<WebElement> products;

	@FindBy(id = "checkoutButton")
	WebElement checkOut;

	@FindBy(xpath="//div[contains(@class,'cdk-overlay-container') and contains(@class,'bluegrey-lightgreen-theme')]/div")
	WebElement msgBox;
	
	public BasketPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		actions = new Actions(driver);
	}

	public void IncreaseProductCnt(int cnt) throws InterruptedException {

		// increment 2 times
		for (int i = 0; i < cnt; i++) {
			for (int rowNum = 1; rowNum <= products.size(); rowNum++) {
				String strXpath = "//mat-row[@role='row'][" + rowNum
						+ "]//*[local-name()='svg' and @data-icon='plus-square']";
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(strXpath)));
				WebElement increaseCnt = driver.findElement(By.xpath(strXpath));
				actions.moveToElement(increaseCnt).click().build().perform();
			}
		}
		
	}

	public AddressPage checkout() {
		/*
		wait.until(ExpectedConditions.visibilityOf(msgBox));
		while(!msgBox.isDisplayed()) {
		wait.until(ExpectedConditions.invisibilityOf(msgBox));
		}*/
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wait.until(ExpectedConditions.elementToBeClickable(checkOut));
		scrollTo(checkOut);
		checkOut.click();
		return new AddressPage(driver);
	}
}
