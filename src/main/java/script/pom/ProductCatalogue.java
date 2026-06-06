package script.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import script.reusablecomponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {
	WebDriver driver;

	public ProductCatalogue(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	//List<WebElement> products = driver.findElements(By.cssSelector(".col-lg-4"));
	@FindBy(css=".col-lg-4")
	List<WebElement> products;

	@FindBy(css=".ng-animating")
	WebElement spinner;

	By productBy = By.cssSelector(".col-lg-4");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");

	public List<WebElement> getProductList() 
	{
		waitForProductsToAppear(productBy);
		return products;
	}

	public WebElement getProductByName(String productName) 
	{
		WebElement prod = getProductList().stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		return prod;
	}

	public void addProductToCart(String productName) throws InterruptedException 
	{
		WebElement prod = getProductByName(productName);
		waitForLoadToDisappear(toastMessage);		// wait for previous toast/spinner to disappear
		prod.findElement(addToCart).click();
		waitForProductsToAppear(toastMessage);
		waitForProductsToDisappear(spinner);
	}
}