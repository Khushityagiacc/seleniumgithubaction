package Fedex.Testngtestcase;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Extenrreport {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {

        if (extent == null) {

            // ✅ MUST be inside test-output for artifact upload
            ExtentSparkReporter spark =
                    new ExtentSparkReporter("test-output/ExtentReport.html");

            extent = new ExtentReports();
            extent.attachReporter(spark);
        }

        return extent;
    }
}
