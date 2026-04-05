package tests;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.Baseclass;
import page.AddingCustomerpageObjects;
import page.CustomersPageObjects;
import page.LoginpageObjects;

public class AddingCustomerTestCase extends Baseclass {

    @Test
    public void testCreateNewCustomer() throws InterruptedException {
        // 1. Login Logic
        LoginpageObjects lp = new LoginpageObjects(driver);
        lp.enterUserName(prop.getProperty("admin_email"));
        lp.enterPassword(prop.getProperty("admin_password"));
        lp.clickSubmit();

        // Use a single Wait instance for the whole test to keep it clean
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        
        // Wait for Dashboard URL and Title to confirm login success
        wait.until(ExpectedConditions.urlContains("admin/"));  
        wait.until(ExpectedConditions.titleContains("Dashboard"));
        System.out.println("Login validated. Dashboard loaded.");
        
        // 2. Navigation to Customer List
        CustomersPageObjects cp = new CustomersPageObjects(driver);
        
        // Ensure the sidebar is clickable before interacting
        cp.SelectCustomers(); 
        cp.SelectSubCustomer(); 

        // 3. Add New Customer
        AddingCustomerpageObjects addCustomerPage = new AddingCustomerpageObjects(driver);
        
        // Wait for the 'Add New' button to appear on the list page
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.btn.btn-primary")));
        addCustomerPage.clickOnAddNew();
        
        // Confirm we are on the Create page
        wait.until(ExpectedConditions.urlContains("/Admin/Customer/Create"));
        
        // 4. Fill Information with Unique Data
        String uniqueEmail = "automation_" + System.currentTimeMillis() + "@test.com";
        System.out.println("Creating customer with email: " + uniqueEmail);
        
        // Pass specific data to the Page Object
        addCustomerPage.setCustomerInfo(uniqueEmail, "Admin@123", "Selenium", "Tester");
        addCustomerPage.clickSave();

        // 5. Verification of Success Message
     // 5. Verification of Success Message
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-success")));

        String successMsg = addCustomerPage.getSuccessMessage();

        // Adding a clear failure message for the Extent Report
        Assert.assertTrue(successMsg.contains("The new customer has been added successfully."), 
            "FAIL: Success message was not displayed or text mismatch. Actual: " + successMsg);

        System.out.println("Test Passed: Customer created successfully with email: " + uniqueEmail);
        
    }
    }