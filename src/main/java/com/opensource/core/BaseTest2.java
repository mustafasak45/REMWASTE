import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTest2 {
    protected WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentTest test;

    @BeforeSuite
    public void setUpSuite() {
        // Rapor dosyasının konumunu ve adını ayarla
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport_" + timeStamp + ".html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

        // Rapor konfigürasyonları
        sparkReporter.config().setDocumentTitle("Selenium TestNG Raporu");
        sparkReporter.config().setReportName("Otomasyon Test Raporu");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setEncoding("UTF-8");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Sistem bilgilerini ekle
        extent.setSystemInfo("Uygulama", "Demo Web Sitesi");
        extent.setSystemInfo("Test Türü", "Regresyon");
        extent.setSystemInfo("Test Çevresi", "QA");
        extent.setSystemInfo("Selenium Sürümü", "4.1.2");
        extent.setSystemInfo("TestNG Sürümü", "7.4.0");
    }

    @BeforeMethod
    public void setUp(Method method) {
        // Test ismini ExtentReports'a kaydet
        test = extent.createTest(method.getName(), method.getAnnotation(Test.class).description());

        // WebDriver'ı başlat
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        test.log(Status.INFO, "Tarayıcı başlatıldı: Chrome");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Test sonucunu rapora yaz
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - TEST BAŞARISIZ", ExtentColor.RED));
            test.fail(result.getThrowable());

            // Ekran görüntüsü al (örnek olarak, implementasyonu aşağıda)
            String screenshotPath = takeScreenshot(result.getName());
            test.addScreenCaptureFromPath(screenshotPath);

        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - TEST ATLANDI", ExtentColor.ORANGE));
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - TEST BAŞARILI", ExtentColor.GREEN));
        }

        // WebDriver'ı kapat
        if (driver != null) {
            driver.quit();
            test.log(Status.INFO, "Tarayıcı kapatıldı");
        }
    }

    @AfterSuite
    public void tearDownSuite() {
        // Raporu kaydet
        extent.flush();
    }

    // Ekran görüntüsü alma metodu (örnek)
    private String takeScreenshot(String testName) {
        // Bu metodun implementasyonu projenize göre değişebilir
        // Örnek bir implementasyon:
        String screenshotDir = System.getProperty("user.dir") + "/test-output/screenshots/";
        new File(screenshotDir).mkdirs();
        String screenshotPath = screenshotDir + testName + "_" + System.currentTimeMillis() + ".png";

        // Selenium ile ekran görüntüsü alma kodu buraya gelecek
        // ((TakesScreenshot)driver).getScreenshotAs(...);

        return screenshotPath;
    }
}