package RSA.pageobjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import RSA.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent{
	
	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver)
	{
		super(driver);
		// initialization
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".mb-3")
	List<WebElement> products;
	
	@FindBy(css=".ng-animating")
	WebElement Spinner;
	
	By productsBy = By.cssSelector(".mb-3");
	
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	
	By ToastMessage = By.cssSelector("#toast-container");

	public List<WebElement> getProductList()
	{
		waitForElementToAppear(productsBy);
		return products;
	}	
	
	public WebElement getProductByName(String productName)
	{
		WebElement ProductMatch = getProductList().stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		
		return ProductMatch;
	}
	
	public void addProductToCart(String productName)
	{
		WebElement ProductMatch = getProductByName(productName);
		ProductMatch.findElement(addToCart).click();
		waitForElementToAppear(ToastMessage);
		waitForElementToDisappear(Spinner);
	}
}








