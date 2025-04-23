package RSA.tests;

import org.testng.annotations.Test;

import java.io.IOException;
import org.testng.Assert;

import RSA.TestComponents.BaseTest;
import RSA.pageobjects.CartPage;
import RSA.pageobjects.ProductCatalogue;
import RSA.TestComponents.Retry;

public class ErrorValidationsTest extends BaseTest {
		
		@Test(groups= {"ErrorHandling"}, retryAnalyzer=Retry.class)
		public void LoginErrorValidation() throws IOException
		{
			landingPage.loginApplication("anshika@gmail.com", "Iamki000");
			Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
		}

		@Test
		public void ProductErrorValidation() throws IOException, InterruptedException
		{
			String productName = "ZARA COAT 3";
			ProductCatalogue productCatalogue = landingPage.loginApplication("shetty@gmail.com", "Iamking@000");
			productCatalogue.getProductList();
			productCatalogue.addProductToCart(productName);
			CartPage cartPage = productCatalogue.goToCartPage();
			Boolean Match = cartPage.VerifyProductDisplay("ZARA COAT 33");
			Assert.assertFalse(Match);
		}
}
