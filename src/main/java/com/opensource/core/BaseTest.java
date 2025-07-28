package com.opensource.core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.opensource.utils.ConfigReader;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.opensource.core.driver.DriverManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {

    protected static ExtentReports extentReports;
    protected static ExtentTest extentTest;
    protected static ExtentSparkReporter extentSparkReporter;

    @BeforeSuite(alwaysRun = true)
    public void setupExtentReports() {
        extentReports = new ExtentReports();

        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String filePath = System.getProperty("user.dir") + "/target/Rapor/rapor_" + date + ".html";

        extentSparkReporter = new ExtentSparkReporter(filePath);
        extentReports.attachReporter(extentSparkReporter);

        extentReports.setSystemInfo("Environment", "QA");
        extentReports.setSystemInfo("Browser", ConfigReader.getProperty("browser"));
        extentReports.setSystemInfo("Automation Engineer", "Erol");

        extentSparkReporter.config().setDocumentTitle("TestNG Extent Report");
        extentSparkReporter.config().setReportName("TestNG Automation Reports");
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        extentTest = extentReports.createTest(method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        WebDriver driver = DriverManager.getDriver();

        if (result.getStatus() == ITestResult.FAILURE) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            String screenshotPath = "target/screenshots/" + result.getName() + "_" + System.currentTimeMillis() + ".png";
            File destination = new File(screenshotPath);
            destination.getParentFile().mkdirs();

            try {
                FileUtils.copyFile(source, destination);
                extentTest.fail("Test Failed. Screenshot attached.")
                        .addScreenCaptureFromPath(screenshotPath);
            } catch (IOException e) {
                e.printStackTrace();
                extentTest.fail("Test Failed, screenshot could not be saved: " + e.getMessage());
            }
            extentTest.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.skip("Test Skipped: " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.pass("Test Passed");
        }

        DriverManager.closeDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownExtentReports() {
        extentReports.flush();
    }
    @AfterClass(alwaysRun = true)
    public void afterClass() {
        if (extentReports != null) {
            extentReports.flush();
            System.out.println("ExtentReports flushed in @AfterClass.");
        }
    }

}
