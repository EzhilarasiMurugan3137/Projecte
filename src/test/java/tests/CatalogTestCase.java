package tests;

import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.Baseclass;
import page.CataloguePageObjects;
import page.LoginpageObjects;

public class CatalogTestCase extends Baseclass {

    @Test
    public void testProductSearch() {
        // 1. Setup a single Wait instance for the test
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        // 2. Perform Login
        LoginpageObjects lp = new LoginpageObjects(driver);
        lp.enterUserName(prop.getProperty("admin_email"));
        lp.enterPassword(prop.getProperty("admin_password"));
        lp.clickSubmit();
        
        // 3. Wait for Dashboard load
        wait.until(ExpectedConditions.urlContains("admin/"));
        wait.until(ExpectedConditions.titleContains("Dashboard"));
        
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, "Dashboard / nopCommerce administration", "Login failed or redirected to wrong page");
        System.out.println("Login validated, proceeding to Catalog search...");

        // 4. Interaction with Catalog
        CataloguePageObjects catalogPage = new CataloguePageObjects(driver);
        
        // Navigation: Catalog -> Products
        catalogPage.selectCatalog();
        catalogPage.selectProduct();
        
        // 5. Search Action
        String productName = "Build your own computer";
        
        // Optional: Wait for the product name input to be visible before typing
        catalogPage.enterProductName(productName);
        catalogPage.clickSearch(); 
        
        // 6. Verification
        // It's better to wait for the table to refresh after clicking search
        boolean isFound = catalogPage.isProductVisible(productName);
        Assert.assertTrue(isFound, "Product '" + productName + "' was not found in the search results table!");
        
        System.out.println("Test Passed: Product found successfully.");
    }
}