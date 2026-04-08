package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.*;

import utils.ExtentReportNG;

public class Baseclass {

    public WebDriver driver;
    public Properties prop;
    public static ExtentReports extent;
    public static ExtentTest test;

    // ===========================
    // REPORT SETUP
    // ===========================
    @BeforeSuite
    public void setupReport() {
        extent = ExtentReportNG.getReportInstance();
    }

    // ===========================
    // TEST SETUP
    // ===========================
    @BeforeMethod
    public void setup(java.lang.reflect.Method method) {

        test = extent.createTest(method.getName());

        // Load properties
        try {
            prop = new Properties();
            FileInputStream fis = new FileInputStream(
                    System.getProperty("user.dir") + "/src/main/resources/config.properties");
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ===========================
        // CHROME OPTIONS (FIXED)
        // ===========================
        ChromeOptions options = new ChromeOptions();

        // ✅ Use separate automation profile (NO conflict)
        options.addArguments("user-data-dir=C:/AutomationProfile");

        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");

        // ❌ DO NOT USE:
        // --incognito
        // Default Chrome profile

        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        // ===========================
        // OPEN APPLICATION
        // ===========================
        driver.get(prop.getProperty("url"));

        // ===========================
        // HANDLE CLOUDFLARE
        // ===========================
        handleCloudflare();

        test.log(Status.INFO, "Browser launched and page loaded");
    }

    // ===========================
    // CLOUDFLARE HANDLER
    // ===========================
    public void handleCloudflare() {

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            wait.until(d -> {
                String title = d.getTitle().toLowerCase();
                String page = d.getPageSource();

                // ✅ Login page detected
                if (title.contains("login") || page.contains("Email")) {
                    return true;
                }

                // ❌ Cloudflare page detected
                if (title.contains("just a moment") || page.contains("Verify you are human")) {
                    try {
                        Thread.sleep(5000); // wait and retry
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return false;
                }

                return false;
            });

        } catch (Exception e) {
            System.out.println("⚠ Cloudflare blocking → manually click checkbox once");
        }
    }

    // ===========================
    // TEARDOWN
    // ===========================
    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {

        if (result.getStatus() == ITestResult.FAILURE) {

            test.log(Status.FAIL, "Test FAILED: " + result.getName());
            test.log(Status.FAIL, "Error: " + result.getThrowable());

            if (driver != null) {
                String screenshotPath = utils.TestUtils.getScreenshot(driver, result.getName());
                test.addScreenCaptureFromPath(screenshotPath);
            }

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test PASSED: " + result.getName());
        }

        if (driver != null) {
            driver.quit();
        }
    }

    // ===========================
    // REPORT FLUSH
    // ===========================
    @AfterSuite
    public void generateReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}