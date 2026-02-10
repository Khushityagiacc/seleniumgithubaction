package Fedex.Testngtestcase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.MediaEntityBuilder;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;

public class Seletest {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeTest
    public void setup() {
        // Initialize ExtentReports
        extent = Extenrreport.getInstance();
        test = extent.createTest("GitHub Actions Selenium Test");

        // Setup ChromeDriver
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");       // headless for GitHub Actions
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);

        System.out.println("Chrome started successfully!");
    }

    @Test
    public void openGoogleAndTakeScreenshot() {
        try {
            driver.get("https://www.google.com");
            System.out.println("Opened Google: " + driver.getTitle());

            // Take screenshot
            String screenshotPath = Screenshot.takeScreenshot(driver, "google_home");
            System.out.println("Screenshot saved: " + screenshotPath);

            // Add to report
            test.pass(
                "Opened Google successfully",
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
            );

            // Debug: List test-output folder contents
            File outputDir = new File("test-output/screenshots");
            if (outputDir.exists()) {
                System.out.println("Screenshots folder exists: " + outputDir.getAbsolutePath());
                for (File f : outputDir.listFiles()) {
                    System.out.println(" - " + f.getName());
                }
            } else {
                System.out.println("Screenshots folder not found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            test.fail("Test failed: " + e.getMessage());
        }
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) driver.quit();
        extent.flush();
        System.out.println("Extent report flushed and browser closed.");
    }
}
