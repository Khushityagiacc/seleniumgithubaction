package Fedex.Testngtestcase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.nio.file.Files;

public class Seletest {

    @Test
    public void sampleTest() throws Exception {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // Go to example website
        driver.get("https://example.com");

        // Ensure test-output/screenshots exists
        File folder = new File("test-output/screenshots");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Take a screenshot so the folder is not empty
        File screenshot = ((org.openqa.selenium.TakesScreenshot) driver)
                .getScreenshotAs(org.openqa.selenium.OutputType.FILE);
        Files.copy(screenshot.toPath(), new File(folder, "example.png").toPath());

        driver.quit();
    }
}
