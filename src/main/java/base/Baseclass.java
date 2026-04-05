package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utils.ExtentReportNG; // Matches your package and class name

public class Baseclass {
    public WebDriver driver;
    public Properties prop;
    
    // Extent Report variables
    public static ExtentReports extent;
    public static ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        // Initialize the report using your static method
        extent = ExtentReportNG.getReportInstance();
    }

    @BeforeMethod
    public void setup(java.lang.reflect.Method method) {
        // 1. Create a test entry in the report using the method name
        test = extent.createTest(method.getName());
        
        // 2. Load Properties
        try {
            prop = new Properties();
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties");
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3. Browser Setup (Windows 11 / Chrome 146)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("user-data-dir=" + System.getProperty("user.dir") + "\\ChromeProfile");
        
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.get(prop.getProperty("url"));
        
        test.log(Status.INFO, "Browser started and navigated to: " + prop.getProperty("url"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            // 1. Capture the screenshot using your TestUtils
            String screenshotPath = utils.TestUtils.getScreenshot(driver, result.getName());
            
            // 2. Log the failure and ATTACH the screenshot to the report
            test.log(Status.FAIL, "Test Case FAILED: " + result.getName());
            test.log(Status.FAIL, "Error: " + result.getThrowable());
            test.addScreenCaptureFromPath(screenshotPath); // This puts the image in the report!
            
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test Case PASSED: " + result.getName());
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void generateReport() {
        // 5. Finalize the report
        if (extent != null) {
            extent.flush();
        }
    }
}