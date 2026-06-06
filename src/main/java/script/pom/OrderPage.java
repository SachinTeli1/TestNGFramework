package script.pom;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import script.reusablecomponents.AbstractComponent;

public class OrderPage extends AbstractComponent {
	WebDriver driver;

	public OrderPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//tr//td[2]")
	private List<WebElement> ProductNames;
	
	@FindBy(css = ".totalRow button")
	WebElement checkOutEle;
	
	public Boolean verifyOrderDisplay(String productName) {
		Boolean match = ProductNames.stream().anyMatch(product->product.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	
}
