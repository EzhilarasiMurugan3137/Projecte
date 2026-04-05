package page;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogoutPageObjects {
    public WebDriver driver;

    // Using a more specific selector to ensure we hit the admin logout
    @FindBy(xpath = "//a[contains(@href, 'logout')]")
    public WebElement lnkLogout;

    public LogoutPageObjects(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickLogout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        // Wait until clickable
        wait.until(ExpectedConditions.elementToBeClickable(lnkLogout));
        
        // Professional Tip: JS click is safer for header/footer links
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", lnkLogout);
    }
}