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

	@FindBy(xpath = "//table[@id='products-grid']//td[contains(text(),'Build your own computer')]")
	public WebElement SearchResult;

	public CataloguePageObjects(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void selectCatalog() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(Catalog)).click();
	}

	public void selectProduct() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(Product)).click();
	}

	public void enterProductName(String prodName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.visibilityOf(ProductName));
		ProductName.clear();

		ProductName.sendKeys(prodName);
		System.out.println("Searching for product: " + prodName);
	}

	public void clickSearch() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("search-products")));
		js.executeScript("arguments[0].scrollIntoView(true);", SearchBtn);

		js.executeScript("arguments[0].click();", SearchBtn);
		try {
			Thread.sleep(2000);

		} catch (InterruptedException e) {

		}
	}

	public boolean isProductVisible(String prodName) {
		try {
			return SearchResult.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}