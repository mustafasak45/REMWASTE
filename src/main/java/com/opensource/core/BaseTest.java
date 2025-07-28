package com.opensource.core;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

import com.opensource.core.driver.DriverManager;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class BaseTest {
    private static ThreadLocal<DriverManager> driverThreadLocal = new ThreadLocal<>();

    static DriverManager getDriverManager() {
        return driverThreadLocal.get();
    }

    public static WebDriver getDriver() {
        return getDriverManager().getDriver();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
            File source = ts.getScreenshotAs(OutputType.FILE);

            // Ekran görüntüsünü target/screenshots klasörüne kaydet
            String screenshotPath = "target/screenshots/" + result.getName() + "_" + System.currentTimeMillis() + ".png";
            File destination = new File(screenshotPath);

            // Klasör yoksa oluştur
            destination.getParentFile().mkdirs();

            try {
                FileUtils.copyFile(source, destination);
                System.out.println("Screenshot saved: " + screenshotPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        DriverManager.closeDriver();
    }

    @AfterClass
    public void teardown_class() {
        // Gerekirse buraya da kod ekleyebilirsin
    }
}
