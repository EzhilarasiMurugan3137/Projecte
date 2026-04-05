package tests;

import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.Baseclass;
import page.LoginpageObjects;
import page.LogoutPageObjects;

public class LogoutTestCase extends Baseclass {

    @Test
    public void verifyLogout() {
        // Use a single wait object for the entire test
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // 1. Login Flow
        LoginpageObjects lp = new LoginpageObjects(driver);
        lp.enterUserName(prop.getProperty("admin_email"));
        lp.enterPassword(prop.getProperty("admin_password"));
        lp.clickSubmit();
        
        // Wait for Dashboard to ensure session is established
        wait.until(ExpectedConditions.urlContains("admin/"));
        wait.until(ExpectedConditions.titleContains("Dashboard"));
        System.out.println("Login validated. Proceeding to logout...");

        // 2. Perform Logout
        LogoutPageObjects logout = new LogoutPageObjects(driver);
        logout.clickLogout();
        
        // 3. Verification: Check for redirection back to Login
        System.out.println("Verifying redirection...");
        
        // We check for the Login page URL as it is more stable than the title
        wait.until(ExpectedConditions.urlContains("login"));
        
        String actualTitle = driver.getTitle();
        Assert.assertTrue(actualTitle.contains("Login"), 
            "Logout failed! Expected title to contain 'Login' but found: " + actualTitle);
            
        System.out.println("Logout successful and verified.");
    }
}