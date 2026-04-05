package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.Baseclass;
import page.LoginpageObjects;

public class LoginTestCase extends Baseclass {
	@Test
	public void testvalidLogin() throws InterruptedException {
	    LoginpageObjects lp = new LoginpageObjects(driver);
	    
	    // Give Cloudflare a moment to 'check' the browser before typing
	    Thread.sleep(3000); 

	    lp.enterUserName(prop.getProperty("admin_email"));
	    lp.enterPassword(prop.getProperty("admin_password"));
	    
	    // Use Javascript to click - this often avoids bot-detection listeners
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("arguments[0].click();", lp.submit);
	}
}
