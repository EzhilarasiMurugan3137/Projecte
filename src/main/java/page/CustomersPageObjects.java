package page;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CustomersPageObjects {
	public WebDriver driver;

	// The Parent menu (the one with the 'Users' icon)
	@FindBy(xpath = "//li[contains(@class,'nav-item')]//p[normalize-space()='Customers']")
	public WebElement customer;

	// The Child menu (the actual link to the customers list)
	@FindBy(xpath = "//li[contains(@class,'nav-item')]//ul[contains(@class,'nav-treeview')]//p[normalize-space()='Customers']")
	public WebElement subCustomer;

	@FindBy(id = "SearchEmail")
	public WebElement SearchEmailBx;

	@FindBy(id = "search-customers")
	public WebElement searchCustomer;

	@FindBy(xpath = "//table[@id='customers-grid']//tbody/tr[1]/td[2]")
	public WebElement CustomerSearchResult;

	public CustomersPageObjects(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void SelectCustomers() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// 1. Wait for Parent Menu and click it to expand
		wait.until(ExpectedConditions.elementToBeClickable(customer));
		customer.click();

		// 2. Wait for the Child Menu to be visible and clickable
		// Using JS click here is safer for sidebar menus
		wait.until(ExpectedConditions.elementToBeClickable(subCustomer));
		js.executeScript("arguments[0].click();", subCustomer);
	}

	public void SelectSubCustomer() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement subItem = wait.until(ExpectedConditions.elementToBeClickable(subCustomer));

		try {
			subItem.click();
		} catch (Exception e) {
			org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", subItem);
		}
	}

	public void EnterEmailid(String email) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(SearchEmailBx));
		SearchEmailBx.clear();
		SearchEmailBx.sendKeys(email);
	}

	public void SearchBtn() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(searchCustomer)).click();
	}

	public String getCustomerDetail() {
		return CustomerSearchResult.getText();
	}
}