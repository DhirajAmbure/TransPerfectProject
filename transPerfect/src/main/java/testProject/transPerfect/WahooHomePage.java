package testProject.transPerfect;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class WahooHomePage {
	WebDriver driver;
	
	@FindBy(how=How.XPATH, using="//a/span[text()='SHOP']") private WebElement shopEleLink;
	
	@FindBy(how=How.XPATH, using="(//a[text()='All Products'])[1]") private WebElement allProductsLink;
	
	public WahooHomePage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
		
	public void clickShopLink(){
		shopEleLink.click();
	}
	
	public void clickOnAllProducts(){
		allProductsLink.click();
	}

}
