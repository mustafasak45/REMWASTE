package com.opensource.core.driver;
import com.opensource.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class DriverManager {
    public DriverManager() {

    }
    static WebDriver driver;
    public static WebDriver getDriver(){

        if (driver==null){
            switch (ConfigReader.getProperty("browser")){

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver=new EdgeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver=new FirefoxDriver();
                    break;
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                default:
                    WebDriverManager.chromedriver().setup();
                    driver=new ChromeDriver();
            }
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        return driver;
    }
    public static void closeDriver(){
        if (driver!=null) {
            driver.close();
            driver=null;
        }
    }
    public static void quitDriver(){
        if (driver!=null){
            driver.quit();
            driver=null;
        }
    }
}
