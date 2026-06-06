package script.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import script.pom.CartPage;
import script.pom.CheckOutPage;
import script.pom.ConfirmationPage;
import script.pom.OrderPage;
import script.pom.ProductCatalogue;
import script.testcomponents.BaseTest;

public class SubmitOrderTest extends BaseTest 
{
	String productName = "IPHONE 13 PRO";

	@Test(dataProvider = "getData", groups = {"Purchase"})
	public void submitOrder(HashMap<String, String> input) throws Throwable
	{
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));

		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);

		CheckOutPage CheckOutPage = cartPage.goTocheckOut();
		CheckOutPage.selectCountry("india");

		ConfirmationPage confirmationPage =  CheckOutPage.submitOrder();

		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));
	}
	
	@Test(dependsOnMethods = {"submitOrder"})
	public void orderHistoryTest() {
		ProductCatalogue productCatalogue = landingPage.loginApplication("Sac@tester.com", "Test@123");
		OrderPage orderPage = productCatalogue.goToOrdertPage();
		Assert.assertTrue(orderPage.verifyOrderDisplay(productName));
	} 
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"/src/test/java/script/data/purchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}
	
//	@DataProvider
//	public Object[][] getData() {
//		return new Object[][] {{"Sac@tester.com", "Test@123", "IPHONE 13 PRO"},{"Tester@sac.com", "Sachin@123", "ADIDAS ORIGINAL"}};
//	}
	
	//Using HashMap:
//	@DataProvider
//	public Object[][] getData(){
//		HashMap<String,String> map = new HashMap<String,String>();
//		map.put("email", "Sac@tester.com");
//		map.put("password", "Test@123");
//		map.put("product", "IPHONE 13 PRO");
//		
//		HashMap<String,String> map1 = new HashMap<String,String>();
//		map1.put("email", "Tester@sac.com");
//		map1.put("password", "Sachin@123");
//		map1.put("product", "ADIDAS ORIGINAL");
//		
//		return new Object[][] {{map},{map1}};
//	}
	
}
