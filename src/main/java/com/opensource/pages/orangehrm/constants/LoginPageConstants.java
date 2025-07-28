package com.opensource.pages.orangehrm.constants;

import org.openqa.selenium.By;

public class LoginPageConstants {

    public static By loginTitle = By.cssSelector("[class='oxd-text oxd-text--h5 orangehrm-login-title']");
    public static By usernameTextBox = By.cssSelector("[name='username']");
    public static By passwordTextBox = By.cssSelector("[name='password']");
    public static By loginButton = By.cssSelector("[type='submit']");
    public static By invalidCredentials = By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']");

}
