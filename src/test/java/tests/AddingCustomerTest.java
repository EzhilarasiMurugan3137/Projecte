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

public class AddingCustomerTest extends Baseclass {

    @Test
    public void testCreateNewCustomer() throws InterruptedException {
     
        LoginpageObjects lp = new LoginpageObjects(driver);
        lp.enterUserName(prop.getProperty("admin_email"));
        lp.enterPassword(prop.getProperty("admin_password"));
        lp.clickSubmit();

        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        
     
        wait.until(ExpectedConditions.urlContains("admin/"));  
        wait.until(ExpectedConditions.titleContains("Dashboard"));
        System.out.println("Login validated. Dashboard loaded.");
        
        CustomersPageObjects cp = new CustomersPageObjects(driver);
        
        
        cp.SelectCustomers(); 
        cp.SelectSubCustomer(); 

        AddingCustomerpageObjects addCustomerPage = new AddingCustomerpageObjects(driver);
      
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.btn.btn-primary")));
        addCustomerPage.clickOnAddNew();
        
        wait.until(ExpectedConditions.urlContains("/Admin/Customer/Create"));
        
        String uniqueEmail = "automation_" + System.currentTimeMillis() + "@test.com";
        System.out.println("Creating customer with email: " + uniqueEmail);
        
        addCustomerPage.setCustomerInfo(uniqueEmail, "Admin@123", "Selenium", "Tester");
        addCustomerPage.clickSave();

      
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-success")));

        String successMsg = addCustomerPage.getSuccessMessage();

        Assert.assertTrue(successMsg.contains("The new customer has been added successfully."), 
            "FAIL: Success message was not displayed or text mismatch. Actual: " + successMsg);

        System.out.println("Test Passed: Customer created successfully with email: " + uniqueEmail);
        
    }
    }