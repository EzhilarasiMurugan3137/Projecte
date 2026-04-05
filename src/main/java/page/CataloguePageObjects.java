package page;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CataloguePageObjects {
	public WebDriver driver;

	@FindBy(xpath = "//p[normalize-space()='Catalog']")
	public WebElement Catalog;

	@FindBy(xpath = "//p[normalize-space()='Products']")
	public WebElement Product;

	@FindBy(id = "SearchProductName")
	public WebElement ProductName;

	@FindBy(id = "search-products")
	public WebElement SearchBtn;

	// FIX: This should point to the table result, NOT the catalog menu again
	@FindBy(xpath = "//table[@id='products-grid']//td[contains(text(),'Build your own computer')]")
	public WebElement SearchResult;

	public CataloguePageObjects(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void selectCatalog() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// Wait for Parent Catalog menu and click
		wait.until(ExpectedConditions.elementToBeClickable(Catalog)).click();
	}

	public void selectProduct() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// Wait for the Child Product menu to become visible after animation
		wait.until(ExpectedConditions.visibilityOf(Product)).click();
	}

	public void enterProductName(String prodName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // 1. Wait for the field to be visible and clear it properly
        wait.until(ExpectedConditions.visibilityOf(ProductName));
        ProductName.clear();
        
        // 2. Type the name
        ProductName.sendKeys(prodName);
        System.out.println("Searching for product: " + prodName);
    }

    public void clickSearch() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;

        // 1. Ensure the button is present in the DOM
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("search-products"))); 

        // 2. PROFESSIONAL TIP: Scroll the button into view before clicking
        // This prevents 'ElementClickIntercepted' errors on Windows 11
        js.executeScript("arguments[0].scrollIntoView(true);", SearchBtn);
        
        // 3. Perform the JavaScript Click
        js.executeScript("arguments[0].click();", SearchBtn);
        
        // 4. Wait for the loading "spinner" to disappear (if applicable)
        // Or wait for the table to refresh
        try { Thread.sleep(2000); } catch (InterruptedException e) {} 
    }

	public boolean isProductVisible(String prodName) {
		try {
			return SearchResult.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}