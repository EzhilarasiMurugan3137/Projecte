package page;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PromotionpageObjects {
	public WebDriver driver;

	@FindBy(xpath = "//p[contains(text(),'Promotions')]/parent::a")
	public WebElement Promotions;

	@FindBy(xpath = "//a[@href='/Admin/Discount/List']")
	public WebElement Discounts;

	@FindBy(xpath = "//a[@class='btn btn-primary' and contains(@href, 'Create')]")
	public WebElement btnAddNewDiscount;

	public PromotionpageObjects(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void navigateToDiscounts() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		wait.until(ExpectedConditions.elementToBeClickable(Promotions)).click();
		wait.until(ExpectedConditions.elementToBeClickable(Discounts)).click();
	}

	public boolean isDiscountsPageLoaded() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		return wait.until(ExpectedConditions.visibilityOf(btnAddNewDiscount)).isDisplayed();
	}
}