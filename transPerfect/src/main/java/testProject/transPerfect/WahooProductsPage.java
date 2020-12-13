package testProject.transPerfect;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class WahooProductsPage {
	WebDriver driver;
	WebElement elementToSelect;
	String nameOfSelectedProduct;
	
	@FindBy(how=How.XPATH, using="//ul[@class='products-grid']//li/div[2]/p/a")
	private List<WebElement> allProductsList;
	
	@FindBy(how = How.XPATH, using="//div[@class='add-to-cart']//button[@title='Add to Cart']")
	private WebElement addToCart;
	
	@FindBy(how=How.XPATH, using= "//div[@data-block='minicart']")
	private WebElement miniCartBlock;
	
	@FindBy(how=How.XPATH, using="//a[@id='btn-minicart-close']")
	private WebElement closeMiniCart;
	
	@FindBy(how=How.XPATH, using="(//a[@title='Remove item'])[2]")
	private WebElement removeSecondItem;
	
	@FindBy(how=How.XPATH, using="//div[@class='modal-content']//div[contains(text(),'remove this item')]")
	private WebElement removeItemPopUp;
	
	@FindBy(how=How.XPATH, using="//button[@class='action-primary action-accept']")
	private WebElement popUpOKBtn;
	
	@FindBy(how=How.XPATH, using="//button[@id='top-cart-btn-checkout']")
	private WebElement checkOutBtn;
	
	@FindBy(how=How.XPATH, using="//p[text()='This product is out of stock']")
	private WebElement outOfTextDesc;
	
	@FindBy(how=How.XPATH, using="//select[@class='super-attribute-select']")
	private WebElement selectColorSizeDropDown;
	
	public WahooProductsPage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String openRandomProduct(String selectedProduct){
		int elementIndex = generateRandomIndex();
		elementToSelect = allProductsList.get(elementIndex);
		nameOfSelectedProduct = elementToSelect.getText();
		if(selectedProduct.isEmpty() || !selectedProduct.equals(nameOfSelectedProduct))
			elementToSelect.click();
		else{
			elementIndex += 1;
			elementToSelect = allProductsList.get(elementIndex);
			nameOfSelectedProduct = elementToSelect.getText();
			elementToSelect.click();
		}		
		return nameOfSelectedProduct;
	}
	
	public void addToCart() throws InterruptedException{
		if(checkOutOfStock()){
			backToProductsList();
			openRandomProduct(nameOfSelectedProduct);
		}
		selectColorSize();
		addToCart.click();
		Thread.sleep(3000);
	}
	
	public boolean checkMiniCart() throws InterruptedException{
		boolean miniCartDisplayed = false;
		Thread.sleep(5000);
		String displayAttribute = miniCartBlock.getAttribute("class");
		if(displayAttribute.contains("active"))
			miniCartDisplayed = true;
		return miniCartDisplayed;
	}
	
	public int generateRandomIndex(){
		int retIndex = 0;
		int upperBound = allProductsList.size()- 2;
		Random randInt = new Random();
		retIndex = randInt.nextInt(upperBound);
		return retIndex;
	}
	
	public void closeMiniCart() throws InterruptedException{
		closeMiniCart.click();
		Thread.sleep(3000);
	}
	
	public void backToProductsList() throws InterruptedException{
		driver.navigate().back();
		Thread.sleep(3000);
	}
	
	public void removeProduct() throws InterruptedException{
		Thread.sleep(3000);
		removeSecondItem.click();
		if(removeItemPopUp.isDisplayed()){
			Thread.sleep(3000);
			popUpOKBtn.click();
		}
	}
	
	public void clickOnCheckOut() throws InterruptedException{
		checkOutBtn.click();
		Thread.sleep(2000);
	}
	
	public boolean checkOutOfStock(){
		boolean isOutOfStock = false;
		try{
		if(outOfTextDesc.isDisplayed())
			isOutOfStock = true;		
		}catch(NoSuchElementException exp){
			isOutOfStock = false;
		}
		
		return isOutOfStock;
	}
	
	public void selectColorSize(){
		try{
			if(selectColorSizeDropDown.isDisplayed()){
				Select selColSize = new Select(selectColorSizeDropDown);
				selColSize.selectByIndex(1);
			}
		}catch(NoSuchElementException exp){
			System.out.println("No Option to Select Size or Color");
		}
	}

}
