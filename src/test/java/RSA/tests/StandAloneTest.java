package RSA.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) {
//		 new comments added for webhook trail
		String productName = "ZARA COAT 3";

		WebDriverManager.chromedriver().setup();

		ChromeOptions options = new ChromeOptions();
		
		options.addArguments("--incognito");

		WebDriver driver = new ChromeDriver(options);
		
		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get("https://rahulshettyacademy.com/client");

		driver.findElement(By.id("userEmail")).sendKeys("anshika@gmail.com");

		driver.findElement(By.id("userPassword")).sendKeys("Iamking@000");

		driver.findElement(By.id("login")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

		List<WebElement> Products = driver.findElements(By.cssSelector(".mb-3"));

		WebElement ProductMatch = Products.stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst()
				.orElse(null);

		ProductMatch.findElement(By.cssSelector(".card-body button:last-of-type")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));

		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

		driver.findElement(By.cssSelector("[routerlink='/dashboard/cart']")).click();

		List<WebElement> CartProducts = driver.findElements(By.cssSelector(".cartSection h3"));

		Boolean Match = CartProducts.stream()
				.anyMatch(CartProduct -> CartProduct.getText().equalsIgnoreCase(productName));

		Assert.assertTrue(Match);

		driver.findElement(By.xpath("//button[text()='Checkout']")).click();

		Actions a = new Actions(driver);

		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));

		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();

		driver.findElement(By.cssSelector(".action__submit")).click();

		String ConfirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();

		Assert.assertTrue(ConfirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

		driver.quit();

	}

}
