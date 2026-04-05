package page;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SalespageObjects {
    public WebDriver driver;

    // More robust XPath: targeting the 'p' inside the sidebar nav-item
    @FindBy(xpath = "//li[contains(@class,'nav-item')]//p[normalize-space()='Sales']")
    public WebElement Sales;

    @FindBy(xpath = "//a[@href='/Admin/Order/List']")
    public WebElement Sales_subOrders;

    public SalespageObjects(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void Selectsales() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        // Ensure the parent menu is visible before clicking
        wait.until(ExpectedConditions.elementToBeClickable(Sales)).click();
    }

    public void SelectOrders() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        // CRITICAL: Wait for the sub-menu animation to finish
        wait.until(ExpectedConditions.visibilityOf(Sales_subOrders));
        
        // Using JavaScript click here is a "Professional Secret" to bypass 
        // overlapping elements during sidebar animations.
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", Sales_subOrders);
    }
}