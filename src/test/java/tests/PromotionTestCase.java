package tests;

import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.Baseclass;
import page.LoginpageObjects;
import page.PromotionpageObjects;

public class PromotionTestCase extends Baseclass {

    @Test
    public void verifyDiscountsList() {
        // One Wait object to rule them all
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        
        // 1. Login Logic
        LoginpageObjects lp = new LoginpageObjects(driver);
        lp.enterUserName(prop.getProperty("admin_email"));
        lp.enterPassword(prop.getProperty("admin_password"));
        lp.clickSubmit();
        
        // 2. Synchronization: Wait for Dashboard
        wait.until(ExpectedConditions.urlContains("admin/"));
        wait.until(ExpectedConditions.titleContains("Dashboard"));
        System.out.println("Login validated. Navigating to Promotions...");
        
        // 3. Navigation
        PromotionpageObjects promo = new PromotionpageObjects(driver);
        promo.navigateToDiscounts();
        
        // 4. Verification
        wait.until(ExpectedConditions.urlContains("Discount/List"));
        
        // Using the boolean method from Page Object
        boolean isLoaded = promo.isDiscountsPageLoaded();
        Assert.assertTrue(isLoaded, "Discounts page 'Add New' button not found! Page might not have loaded correctly.");
        
        System.out.println("Success: Promotions Discounts page verified.");
    }
}