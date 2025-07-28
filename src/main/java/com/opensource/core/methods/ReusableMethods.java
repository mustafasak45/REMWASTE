package com.opensource.core.methods;
import com.opensource.core.driver.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

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


}
