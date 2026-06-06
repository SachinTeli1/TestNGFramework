package script.test;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class StandAloneTest {

	public static void main(String[] args) throws InterruptedException {

		String productName = "IPHONE 13 PRO";

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		driver.get("https://rahulshettyacademy.com/client");

		// Login
		driver.findElement(By.id("userEmail")).sendKeys("Sac@tester.com");
		driver.findElement(By.id("userPassword")).sendKeys("Test@123");
		driver.findElement(By.id("login")).click();

		// Wait for products
		List<WebElement> products = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".mb-3")));

		// Add product to cart
		for (int i = 0; i < products.size(); i++) 
		{	
			WebElement product = products.get(i);
			String prod = product.getText();

			if (prod.contains("IPHONE 13 PRO")) {
				WebElement addToCart = product.findElement(By.cssSelector(".card-body button:last-of-type"));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCart);
				break; 
			}
		}

		// Wait for toast message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));

		// Wait for loader to disappear
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));

		// Click cart
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
//		WebElement cartButton = wait.until(
//		        ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[routerlink*='cart']")));
//		((JavascriptExecutor) driver)
//		        .executeScript("arguments[0].click();", cartButton);

		// Verify product in cart
		List<WebElement> cartProducts = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".cartSection h3")));

		Boolean match = cartProducts.stream()
				.anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);

		// Checkout
		WebElement checkoutButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".totalRow button")));
		checkoutButton.click();

		// Select country
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();

		// Submit order
		WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".action__submit")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);

		// Verify confirmation
		String confirmMessage = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".hero-primary"))).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

		driver.quit();
	}
}