package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportNG {
    
    private static ExtentReports extent;

    public static ExtentReports getReportInstance() {
        if (extent == null) {
            // Define the report path in your project folder
            String path = System.getProperty("user.dir") + "\\test-output\\ExtentReport.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(path);
            
            // Professional Configuration
            spark.config().setReportName("NopCommerce Regression Results");
            spark.config().setDocumentTitle("Automation Test Report");
            spark.config().setTheme(Theme.STANDARD);
            
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Tester", "Ezhilarasi");
            extent.setSystemInfo("Environment", "QA/Production");
            extent.setSystemInfo("OS", "Windows 11");
            extent.setSystemInfo("Browser", "Chrome 146");
        }
        return extent;
    }
}