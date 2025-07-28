package com.opensource.pages.orangehrm;

import com.opensource.core.BasePage;
import com.opensource.core.driver.DriverManager;
import com.opensource.pages.orangehrm.constants.LoginPageConstants;
import com.opensource.utils.ConfigReader;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Ana Sayfaya gidilir.")
    public void openHomePage() {
        DriverManager.getDriver().get(ConfigReader.getProperty("website.url"));
    }
    @Step("Login başlığı kontrol edilir.")
    public void checkLoginTitle() {
        Assert.assertEquals(getText(LoginPageConstants.loginTitle),"Login");
    }
    @Step("Sayfanın URL'i kontrol edilir.")
    public void checkHomePageURL() {
        Assert.assertEquals(DriverManager.getDriver().getCurrentUrl(),ConfigReader.getProperty("website.url"),"Sayfanın adresi doğru değil!");
    }
    @Step("Kullanıcı adı {userName} olarak girilir.")
    public void enterUserName(String userName) {
        sendKeysInput(LoginPageConstants.usernameTextBox,userName);
    }
    @Step("Şifre {password} olarak girilir.")
    public void enterPassword(String password) {
        sendKeysInput(LoginPageConstants.passwordTextBox,password);
    }
    @Step("Login buttonuna tıklanır.")
    public void clickLoginButton() {
        click(LoginPageConstants.loginButton);
    }
    @Step("Mesaj {message} olarak görülür.")
    public void checkMessage(String message) {
        isElementVisible(LoginPageConstants.invalidCredentials,3);
        Assert.assertEquals(getText(LoginPageConstants.invalidCredentials),message,"Mesajlar aynı değil!");
    }

}
