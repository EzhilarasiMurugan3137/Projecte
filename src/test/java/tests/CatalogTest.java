package tests;

import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.Baseclass;
import page.CataloguePageObjects;
import page.LoginpageObjects;

public class CatalogTest extends Baseclass {

	@Test
	public void testProductSearch() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		LoginpageObjects lp = new LoginpageObjects(driver);
		lp.enterUserName(prop.getProperty("admin_email"));
		lp.enterPassword(prop.getProperty("admin_password"));
		lp.clickSubmit();

		wait.until(ExpectedConditions.urlContains("admin/"));
		wait.until(ExpectedConditions.titleContains("Dashboard"));

		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, "Dashboard / nopCommerce administration",
				"Login failed or redirected to wrong page");
		System.out.println("Login validated, proceeding to Catalog search...");

		CataloguePageObjects catalogPage = new CataloguePageObjects(driver);

		catalogPage.selectCatalog();
		catalogPage.selectProduct();

		String productName = "Build your own computer";
		catalogPage.enterProductName(productName);
		catalogPage.clickSearch();

		boolean isFound = catalogPage.isProductVisible(productName);
		Assert.assertTrue(isFound, "Product '" + productName + "' was not found in the search results table!");

		System.out.println("Test Passed: Product found successfully.");
	}
}