package RSA.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import RSA.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;
	public LandingPage landingPage;
	
	public WebDriver initializeDriver() throws IOException {
		
		// Properties class
		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+"\\src\\main\\java\\RSA\\resources\\GlobalData.properties");
		
		properties.load(fis);
		
//		Java Ternary operation
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") :properties.getProperty("browser");
		
		if(browserName.contains("chrome"))
		{	
			WebDriverManager.chromedriver().setup();
			
			ChromeOptions options = new ChromeOptions();
			
			if(browserName.contains("headless"))
			{
				options.addArguments("headless");
			}
			
			Map<String, Object> prefs = new HashMap<String, Object>();

			prefs.put("credentials_enable_service", false);

			prefs.put("password_manager_enabled", false);

			Map<String, Object> profile = new HashMap<String, Object>();

			profile.put("password_manager_leak_detection", false);

			prefs.put("profile", profile);

			options.setExperimentalOption("prefs", prefs); //	options.addArguments("--incognito");

			driver = new ChromeDriver(options);


//			driver.manage().window().setSize(new Dimension(1440, 900));  optional - full screen
		}
		else if(browserName.equalsIgnoreCase("edge"))
		{
			driver = new EdgeDriver();
		}
		else if (browserName.equalsIgnoreCase("firefox")) 
		{
			driver = new FirefoxDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
	public void handleAlertIfPresent(WebDriver driver) {
	
		driver.switchTo().alert().accept();
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
	{
		//read json to string
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		
		//String to HashMap- Jackson Databind
		ObjectMapper mapper = new ObjectMapper();
		
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
		});
		
		return data;
	}
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir")+"//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir")+"//reports//" + testCaseName + ".png";
	}
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException 
	{	
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}	
	
	@AfterMethod(alwaysRun=true)
	public void tearDown()
	{
		driver.quit();
	}
}