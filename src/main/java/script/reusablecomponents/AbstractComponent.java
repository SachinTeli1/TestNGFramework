package script.reusablecomponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import script.pom.CartPage;
import script.pom.OrderPage;

public class AbstractComponent
{
	WebDriver driver;

	public AbstractComponent(WebDriver driver){
		this.driver = driver;	
	}
	@FindBy(xpath ="(//button[@class='btn btn-custom'])[3]")
	WebElement cartHeader;

	@FindBy(css = "[routerlink*='myorders']")
	WebElement orderHeader;
	
	//locate
	public void waitForProductsToAppear(By locate) 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locate));
	}

	public void waitForProductsEleToAppear(WebElement locate) 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(locate));
	}

	public void waitForProductsToDisappear(WebElement prod) throws InterruptedException 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.invisibilityOf(prod));
	}
	
	public void waitForLoadToDisappear(By ele) 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(ele));
	}
	
	public WebElement waitForElementToClickable(WebElement sub) 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		return wait.until(ExpectedConditions.elementToBeClickable(sub));
	}

	public CartPage goToCartPage() {
		cartHeader.click();
		return new CartPage(driver);                //Object creation in one line

//		CartPage cartPage = new CartPage(driver);   //Object creation in two line
//		return cartPage;
	}

	public OrderPage goToOrdertPage() {
		orderHeader.click();
		OrderPage orderPage =  new OrderPage(driver); 
		return orderPage;
	}
}

