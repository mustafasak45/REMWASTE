package com.opensource.pages.orangehrm;

import com.opensource.core.BasePage;
import com.opensource.core.driver.DriverManager;
import com.opensource.pages.orangehrm.constants.LoginPageConstants;
import com.opensource.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void openHomePage() {
        DriverManager.getDriver().get(ConfigReader.getProperty("website.url"));
    }

    public void checkLoginTitle() {
        Assert.assertEquals(getText(LoginPageConstants.loginTitle), "Login");
    }

    public void checkHomePageURL() {
        Assert.assertEquals(
                DriverManager.getDriver().getCurrentUrl(),
                ConfigReader.getProperty("website.url"),
                "Sayfanın adresi doğru değil!"
        );
    }

    public void enterUserName(String userName) {
        sendKeysInput(LoginPageConstants.usernameTextBox, userName);
    }

    public void enterPassword(String password) {
        sendKeysInput(LoginPageConstants.passwordTextBox, password);
    }

    public void clickLoginButton() {
        click(LoginPageConstants.loginButton);
    }

    public void checkMessage(String message) {
        isElementVisible(LoginPageConstants.invalidCredentials, 3);
        Assert.assertEquals(
                getText(LoginPageConstants.invalidCredentials),
                message,
                "Mesajlar aynı değil!"
        );
    }
}
