package script.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import script.reusablecomponents.AbstractComponent;

public class CartPage extends AbstractComponent {
	WebDriver driver;

	public CartPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = ".cartSection h3")
	private List<WebElement> productTitles;
	
	@FindBy(css = ".totalRow button")
	WebElement checkOutEle;
	
	public Boolean verifyProductDisplay(String productName) {
		Boolean match = productTitles.stream().anyMatch(product->product.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	public CheckOutPage goTocheckOut() {
		waitForLoadToDisappear(By.cssSelector("#toast-container"));
		waitForLoadToDisappear(By.cssSelector(".ng-animating"));
		checkOutEle.click();
		return new CheckOutPage(driver);
	}
}
