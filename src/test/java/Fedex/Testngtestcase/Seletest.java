package Fedex.Testngtestcase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

import com.aventstack.extentreports.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Seletest {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeTest
    public void setup() {

        extent = Extenrreport.getInstance();
        test = extent.createTest("GitHub Actions Selenium Test");

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
    }

    @Test
    public void openGoogleAndTakeScreenshot() {

        driver.get("https://www.google.com");

        String screenshotPath = Screenshot.takeScreenshot(driver, "google_home");

        test.pass(
                "Opened Google successfully",
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
        );
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush();
    }
}
