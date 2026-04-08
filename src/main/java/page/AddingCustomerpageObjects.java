package page;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddingCustomerpageObjects {
    
    public WebDriver driver;
    
    @FindBy(xpath = "//a[@href='/Admin/Customer/Create']")
    public WebElement btnAddNew;

    @FindBy(id = "Email")
    public WebElement txtEmail;

    @FindBy(id = "Password")
    public WebElement txtPassword;

    @FindBy(id = "FirstName")
    public WebElement txtFirstName;

    @FindBy(id = "LastName")
    public WebElement txtLastName;

    @FindBy(id = "Gender_Male") 
    public WebElement rdMaleGender;
    
    @FindBy(name = "save")
    public WebElement btnSave;

    @FindBy(xpath = "//div[contains(@class,'alert-success')]")
    public WebElement msgSuccess;

    public AddingCustomerpageObjects(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickOnAddNew() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(btnAddNew));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    
    public void setCustomerInfo(String email, String password, String firstName, String lastName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        wait.until(ExpectedConditions.visibilityOf(txtEmail)).sendKeys(email);
        txtPassword.sendKeys(password);
        txtFirstName.sendKeys(firstName);
        txtLastName.sendKeys(lastName);
        rdMaleGender.click();
    }

    public void clickSave() {
        btnSave.click();
    }

    public String getSuccessMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        // This waits for the success banner to appear in the HTML
        WebElement successAlert = wait.until(ExpectedConditions.visibilityOf(msgSuccess));
        return successAlert.getText();
    }
}