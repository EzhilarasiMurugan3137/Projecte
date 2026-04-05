package tests;

import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.Baseclass;
import page.LoginpageObjects;
import page.SalespageObjects;

public class SalesTestCase extends Baseclass {

    @Test
    public void CheckordersList() {
        // Use ONE wait object for the entire test
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        
        // 1. Login Flow
        LoginpageObjects lp = new LoginpageObjects(driver);
        lp.enterUserName(prop.getProperty("admin_email"));
        lp.enterPassword(prop.getProperty("admin_password"));
        lp.clickSubmit();
        
        // 2. Synchronization
        wait.until(ExpectedConditions.urlContains("admin/"));
        wait.until(ExpectedConditions.titleContains("Dashboard"));
        System.out.println("Login validated. Navigating to Sales...");

        // 3. Navigation
        SalespageObjects sales = new SalespageObjects(driver);
        sales.Selectsales(); // Opens dropdown
        sales.SelectOrders(); // Clicks link
        
        // 4. Final Verification
        wait.until(ExpectedConditions.urlContains("Order/List"));
        
        String actualTitle = driver.getTitle();
        Assert.assertTrue(actualTitle.contains("Orders"), 
            "Expected title to contain 'Orders' but found: " + actualTitle);
            
        System.out.println("Success: Orders List is visible.");
    }
}