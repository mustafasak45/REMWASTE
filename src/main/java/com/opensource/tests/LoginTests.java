package com.opensource.tests;

import com.opensource.core.BaseTest;
import com.opensource.core.driver.DriverManager;
import com.opensource.pages.orangehrm.DashboardPage;
import com.opensource.pages.orangehrm.LoginPage;
import com.opensource.utils.ConfigReader;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {
    @BeforeMethod
    public void setup() {
        extentTest.info("Test Setup started - Login işlemleri");
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openHomePage();
        loginPage.checkLoginTitle();
        loginPage.checkHomePageURL();
    }

    @Test
    public void validLogin() {
        extentTest.info("validLogin testi başladı");
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.enterUserName(ConfigReader.getProperty("validUserName"));
        loginPage.enterPassword(ConfigReader.getProperty("validPassword"));
        loginPage.clickLoginButton();
        extentTest.pass("Login başarılı");
        DashboardPage dashboardPage= new DashboardPage(DriverManager.getDriver());
        dashboardPage.controlProfilPhoto();
        extentTest.info("Profil fotoğrafı kontrol edildi");

    }
    @Test
    public void inValidLogin() {
        extentTest.info("inValidLogin testi başladı");
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.enterUserName(ConfigReader.getProperty("inValidUserName"));
        loginPage.enterPassword(ConfigReader.getProperty("inValidPassword"));
        loginPage.clickLoginButton();
        loginPage.checkMessage("Invalid credentials");
        extentTest.pass("Login başarısız");
    }

    @AfterMethod
    public void teardown() {

    }

}
