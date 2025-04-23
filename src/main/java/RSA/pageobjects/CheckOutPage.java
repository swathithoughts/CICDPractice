package RSA.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import RSA.AbstractComponents.AbstractComponent;

public class CheckOutPage extends AbstractComponent {

	WebDriver driver;
	
	public CheckOutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="[placeholder='Select Country']")
	private WebElement Country;
	
	@FindBy(xpath="(//button[contains(@class,'ta-item')])[2]")
	private WebElement SelectCountry;;
	
	private By results = By.cssSelector(".ta-results");
	
	@FindBy(css=".action__submit")
	private WebElement Submit;
	
	public void selectCountry(String CountryName) 
	{
		Actions a = new Actions(driver);
		
		a.sendKeys(Country,CountryName).build().perform();
		
		waitForElementToAppear(results);
		
		SelectCountry.click();
	}
	
	public ConfirmationPage submitOrder () 
	{
		Submit.click();
		return new ConfirmationPage(driver);
	}

	

}