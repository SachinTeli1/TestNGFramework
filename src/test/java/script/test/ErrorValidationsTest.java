package script.test;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import script.testcomponents.Retry;

import script.pom.CartPage;
import script.pom.ProductCatalogue;
import script.testcomponents.BaseTest;

public class ErrorValidationsTest extends BaseTest {

	@Test(groups = {"ErrorHandling"}, retryAnalyzer = Retry.class)
	public void LoginErrorValidation() throws IOException
	{
		String productName = "IPHONE 13 PRO";
		landingPage.loginApplication("Sachin@tester.com", "Sachin@123");
		Assert.assertEquals("Incorrect email or password.?", landingPage.getErrorMessage());

	}
	
	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException{
		String productName = "IPHONE 13 PRO"; 
		ProductCatalogue productCatalogue = landingPage.loginApplication("Sac@tester.com", "Test@123");

		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.verifyProductDisplay("IPHONE 15 PRO");
		Assert.assertFalse(match);
		}
}

