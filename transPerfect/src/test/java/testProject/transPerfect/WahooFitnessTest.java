package testProject.transPerfect;

import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;

public class WahooFitnessTest {
	private WebDriver driver;
	public WahooHomePage wHomePage;
	public WahooProductsPage wProductsPage;
	public WahooCheckOutPage wCheckoutPage;
	String selectedProd;
	boolean isOutOfStock;
	boolean isMiniCartDisplayed;
	
  @Test
  public void testWahooFitness() {
	  try {
		  wHomePage = new WahooHomePage(driver);
		  wHomePage.clickShopLink();
		  wProductsPage = new WahooProductsPage(driver);
		  selectedProd = wProductsPage.openRandomProduct("");
		  wProductsPage.addToCart();
		  isMiniCartDisplayed = wProductsPage.checkMiniCart();
		  if(isMiniCartDisplayed){
			  wProductsPage.closeMiniCart();
			  wProductsPage.backToProductsList();
			  selectedProd = wProductsPage.openRandomProduct(selectedProd);
			  wProductsPage.addToCart();
			  wProductsPage.removeProduct();
			  wProductsPage.clickOnCheckOut();
			  wCheckoutPage = new WahooCheckOutPage(driver);
			  wCheckoutPage.selectExpressShipping();
			  wCheckoutPage.updateProductQty();
			  wCheckoutPage.enterCustomerDetails();
			  wCheckoutPage.enterPaymentDetails();
			  wCheckoutPage.placeOrder();
		  }		  
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
  }
  @BeforeClass
  public void beforeClass() {
	  WebDriverManager.chromedriver().setup();
	  ChromeOptions options = new ChromeOptions();
	  options.addArguments("start-maximized"); 
	  options.addArguments("enable-automation"); 
	  driver = new ChromeDriver(options);
	  driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
	  driver.get("https://eu.wahoofitness.com/");
  }

  @AfterClass
  public void afterClass() {
	  driver.quit();
  }

}
