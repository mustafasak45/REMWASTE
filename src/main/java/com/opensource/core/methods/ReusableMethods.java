package com.opensource.core.methods;
import com.opensource.core.driver.DriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class ReusableMethods extends Methods {
    public ReusableMethods(WebDriver driver) {
        super(driver);
    }

    public void sendKeysInput(By by, String text) {
        WebElement inputElement = findElement(by);

        if (!text.isEmpty()) {
            inputElement.clear(); // Normal clear
        } else {
            clearValueWithBackSpace(inputElement); // Manuel silme
        }

        inputElement.sendKeys(text);
    }

    public void selectEmployeeName(By inputLocator, String employeeName) {
        WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Employee input alanına yaz
        WebElement inputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(inputLocator));
        inputElement.clear();
        inputElement.sendKeys(employeeName);

        // 2. Dropdown yüklendi mi kontrol et
        By dropdownOptionLocator = By.xpath("//div[@role='listbox']//span[text()='" + employeeName + "']");
        WebElement optionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownOptionLocator));

        // 3. Dropdown'daki değeri tıkla
        optionElement.click();
    }


    public void selectDropdownOption(By dropdownLocator, String visibleTextToSelect) {
        WebDriver driver = DriverManager.getDriver(); // veya kendi driver erişim methodun
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Dropdown'u tıkla
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
        dropdown.click();

        // Seçeneklerin açılmasını bekle ve ilgili seçeneği bul
        By optionLocator = By.xpath("//div[@role='option']//span[normalize-space(text())='" + visibleTextToSelect + "']");

        WebElement optionToSelect = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        optionToSelect.click();
    }

    public void clearValueWithBackSpace(WebElement element) {
        element.sendKeys(Keys.COMMAND + "a");
        element.sendKeys(Keys.BACK_SPACE);
    }

    public void clickMenuItemByText(String menuText) {
        // Tıklanabilir menü öğesini, li > a > span yapısına göre locate et
        String xpath = "//ul[contains(@class,'oxd-main-menu')]//li[a/span[text()='" + menuText + "']]";

        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
        WebElement menuElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

        click(menuElement);
    }

    public static String getScreenshot(String name) throws IOException {

        // naming the screenshot with the current date to avoid duplication
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        // TakesScreenshot is an interface of selenium that takes the screenshot
        TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);

        // full path to the screenshot location
        String target = System.getProperty("user.dir") + "/target/Screenshots/" + name + date + ".png";
        File finalDestination = new File(target);

        // save the screenshot to the path given
        FileUtils.copyFile(source, finalDestination);
        return target;
    }
}
