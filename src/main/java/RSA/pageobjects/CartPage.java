package RSA.pageobjects;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import RSA.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent{
	
	WebDriver driver;
	
	@FindBy(css=".cartSection h3")
	List<WebElement> CartProducts;
	
	@FindBy(css = ".totalRow button") //(xpath="//button[text()='Checkout']")
	WebElement CheckoutElement;
	
	public CartPage(WebDriver driver)
	{	
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public Boolean VerifyProductDisplay(String productName) 
	{	
		Boolean Match = CartProducts.stream().anyMatch(CartProduct->CartProduct.getText().equalsIgnoreCase(productName));
		return Match;
	}
	
	public CheckOutPage goToCheckout() 
	{
		CheckoutElement.click();
		return new CheckOutPage(driver);
	}
}