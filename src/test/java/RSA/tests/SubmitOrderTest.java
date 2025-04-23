package RSA.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import RSA.pageobjects.CartPage;
import RSA.pageobjects.CheckOutPage;
import RSA.pageobjects.ConfirmationPage;
import RSA.pageobjects.OrderPage;
import RSA.pageobjects.ProductCatalogue;
import RSA.TestComponents.BaseTest;

public class SubmitOrderTest extends BaseTest {
		
	String productName = "ZARA COAT 3";
	
	@Test(dataProvider="getData", groups= {"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws IOException
	{
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"),input.get("password"));

		productCatalogue.getProductList(); // List<WebElement> products

		productCatalogue.addProductToCart(input.get("product"));

		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean Match = cartPage.VerifyProductDisplay(input.get("product"));

		Assert.assertTrue(Match);

		CheckOutPage checkoutPage = cartPage.goToCheckout();

		checkoutPage.selectCountry("india");

		ConfirmationPage confirmationPage = checkoutPage.submitOrder();

		String ConfirmMessage = confirmationPage.getConfirmationMessage();

		Assert.assertTrue(ConfirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void OrderHistoryTest()
	{
		// ZARA COAT 3
		ProductCatalogue productCatalogue = landingPage.loginApplication("anshika@gmail.com", "Iamking@000");
		OrderPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
	}
	
	
	@DataProvider
	public Object[][] getData() throws IOException //rahulshettyacademy.data and BaseTest.java
	{
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//RSA//data//PurchaseOrder.json");
		return new Object[][]  {{data.get(0)}, {data.get(1) } };
	}
	
	
//	@DataProvider // -- how to drive the data using data provider 
//	public Object[][] getData()
//	{
//		return new Object[][]  {{"anshika@gmail.com","Iamking@000","ZARA COAT 3"}, {"shetty@gmail.com","Iamking@000","ADIDAS ORIGINAL" } };
//	}	
	
//	@DataProvider // data provider also allowed to return hash map
//	public Object[][] getData() // UsingHashMap
//	{
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("email", "anshika@gmail.com");
//		map.put("password", "Iamking@000");
//		map.put("product", "ZARA COAT 3");
//		
//		HashMap<String,String> map1 = new HashMap<String,String>();
//		map1.put("email", "shetty@gmail.com");
//		map1.put("password", "Iamking@000");
//		map1.put("product", "ADIDAS ORIGINAL");
//		
//	    return new Object[][]  {{map}, {map1} };
//	}	
}








