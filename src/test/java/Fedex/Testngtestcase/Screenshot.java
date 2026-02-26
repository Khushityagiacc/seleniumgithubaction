package Fedex.Testngtestcase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.*;

public class Screenshot {

    public static String takeScreenshot(WebDriver driver, String name) {

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String screenshotDir = "test-output/screenshots";
        String fileName = name + "_" + timestamp + ".png";
        String fullPath = screenshotDir + "/" + fileName;

        try {
            Files.createDirectories(new File(screenshotDir).toPath());
            Files.copy(src.toPath(), new File(fullPath).toPath());
        } catch (IOException e) {
            System.out.println("Screenshot failed: " + e.getMessage());
        }

        // ✅ Important: return relative path from test-output
        return "screenshots/" + fileName;
    }

    // ✅ ADD THIS METHOD (this is missing in your repo)
    public static void highlightElement(WebDriver driver, WebElement element) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid red'", element);
    }
}
