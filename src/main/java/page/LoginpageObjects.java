package page;

import java.time.Duration;
import java.util.Collections;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginpageObjects {

	public WebDriver driver;

	@FindBy(id = "Email")
	public WebElement Username;

	@FindBy(id = "Password")
	public WebElement Passcode;

	@FindBy(xpath = "//*[@id=\'main\']/div/section/div/div[2]/div[1]/div/form/div[3]/button")
	public WebElement submit;

	public LoginpageObjects(WebDriver driver) {
	    this.driver = driver;
	    PageFactory.initElements(driver, this);
	}

	public void enterUserName(String username) {

		Username.clear();
		Username.sendKeys(username);

	}

	public void enterPassword(String password) {

		Passcode.clear();
		Passcode.sendKeys(password);
	}

	public void clickSubmit() throws InterruptedException {
	
		Thread.sleep(3000);;
		org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", submit);
	}

}