package tests;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.Baseclass;
import page.CustomersPageObjects;
import page.LoginpageObjects;

public class CustomersTestCase extends Baseclass {

    @Test
    public void testCustomerSearch() throws InterruptedException 
    
    {
    	// Wait specifically for the Email field to appear after the security wall drops
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45)); 
        
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Email")));
        } catch (Exception e) {
            System.out.println("Cloudflare wall is still up. Please solve manually one time.");
            Assert.fail("Automation detected by security wall.");
        }

        LoginpageObjects lp = new LoginpageObjects(driver);
        lp.enterUserName(prop.getProperty("admin_email"));
        lp.enterPassword(prop.getProperty("admin_password"));
        lp.clickSubmit();
        
        // Proceed with Customer Search
        CustomersPageObjects customerPage = new CustomersPageObjects(driver);
        customerPage.SelectCustomers();
        customerPage.SelectSubCustomer();

        String searchEmail = "victoria_victoria@nopCommerce.com";
        customerPage.EnterEmailid(searchEmail);
        customerPage.SearchBtn();

        // Verification
        wait.until(ExpectedConditions.textToBePresentInElement(customerPage.CustomerSearchResult, searchEmail));
        Assert.assertEquals(customerPage.getCustomerDetail(), searchEmail);
    }
}