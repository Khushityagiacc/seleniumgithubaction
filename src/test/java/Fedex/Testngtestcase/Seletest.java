package Fedex.Testngtestcase;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import com.aventstack.extentreports.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Seletest {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeTest
    public void launchBrowser() {

        // ✅ Force create test-output folder (VERY IMPORTANT for CI)
        new File("test-output").mkdirs();

        extent = Extenrreport.getInstance();
        test = extent.createTest("Sample Testcase");

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void dd() throws IOException {

        driver.get("https://www.thewellnesscorner.com/");

        new WebDriverWait(driver, Duration.ofSeconds(20)).until(d ->
                ((JavascriptExecutor) d)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );

        // Screenshot homepage
        String homeScreenshot = Screenshot.takeScreenshot(driver, "homepage");

        test.info("Opened homepage",
                MediaEntityBuilder.createScreenCaptureFromPath(homeScreenshot).build());

        WebElement health = driver.findElement(
                By.cssSelector(".lg\\:w-\\[14\\%\\] .cursor-pointer")
        );

        Screenshot.highlightElement(driver, health);

        String healthScreenshot = Screenshot.takeScreenshot(driver, "health_field");

        test.pass("Health element captured",
                MediaEntityBuilder.createScreenCaptureFromPath(healthScreenshot).build());
    }

    @AfterTest
    public void tearDown() {

        if (extent != null) {
            extent.flush(); // generates report
        }

        if (driver != null) {
            driver.quit();
        }
    }
}
