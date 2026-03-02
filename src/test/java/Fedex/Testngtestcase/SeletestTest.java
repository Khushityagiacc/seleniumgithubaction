package Fedex.Testngtestcase;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import org.testng.annotations.*;

import com.aventstack.extentreports.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SeletestTest {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeTest
    public void launchBrowser() {

        // ✅ Force create test-output folder (IMPORTANT for CI)
        new File("test-output").mkdirs();

        extent = Extenrreport.getInstance();
        test = extent.createTest("GgTest - Wellness Corner Navigation");

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

        // ---------------- Homepage Screenshot ----------------
        String homeScreenshot = Screenshot.takeScreenshot(driver, "01_homepage");
        test.info("Opened homepage",
                MediaEntityBuilder.createScreenCaptureFromPath(homeScreenshot).build());

        // ---------------- Step 1 ----------------
        WebElement firstSection = driver.findElement(
                By.cssSelector(".lg\\:w-\\[14\\%\\]:nth-child(1) .md\\:text-sm > span")
        );

        Screenshot.highlightElement(driver, firstSection);
        firstSection.click();

        String step1 = Screenshot.takeScreenshot(driver, "02_click_first_section");
        test.pass("Clicked first section",
                MediaEntityBuilder.createScreenCaptureFromPath(step1).build());


        // ---------------- Scroll Top ----------------
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0)");

        String scroll1 = Screenshot.takeScreenshot(driver, "03_scroll_top");
        test.info("Scrolled to top",
                MediaEntityBuilder.createScreenCaptureFromPath(scroll1).build());


        // ---------------- Click Home ----------------
        WebElement homeLink = driver.findElement(By.linkText("Home"));
        Screenshot.highlightElement(driver, homeLink);
        homeLink.click();

        String step2 = Screenshot.takeScreenshot(driver, "04_click_home");
        test.pass("Clicked Home",
                MediaEntityBuilder.createScreenCaptureFromPath(step2).build());


        // ---------------- Click md:text-sm ----------------
        WebElement mdText = driver.findElement(
                By.cssSelector(".md\\:text-sm > .whitespace-nowrap")
        );

        Screenshot.highlightElement(driver, mdText);
        mdText.click();

        String step3 = Screenshot.takeScreenshot(driver, "05_click_md_text");
        test.pass("Clicked md:text-sm element",
                MediaEntityBuilder.createScreenCaptureFromPath(step3).build());


        // ---------------- Click Dashboard ----------------
        WebElement dashboard = driver.findElement(By.linkText("Dashboard"));
        Screenshot.highlightElement(driver, dashboard);
        dashboard.click();

        String step4 = Screenshot.takeScreenshot(driver, "06_click_dashboard");
        test.pass("Clicked Dashboard",
                MediaEntityBuilder.createScreenCaptureFromPath(step4).build());


        test.pass("Test completed successfully");
    }

    @AfterTest
    public void tearDown() {

        if (extent != null) {
            extent.flush();
        }

        if (driver != null) {
            driver.quit();
        }
    }
}
