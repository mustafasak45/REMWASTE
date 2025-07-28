package com.opensource.tests;

import com.github.javafaker.Faker;
import com.opensource.core.BaseTest;
import com.opensource.core.driver.DriverManager;
import com.opensource.pages.orangehrm.AdminPage;
import com.opensource.pages.orangehrm.DashboardPage;
import com.opensource.pages.orangehrm.LoginPage;
import com.opensource.pages.orangehrm.MenuPage;
import com.opensource.pages.orangehrm.enums.Status;
import com.opensource.pages.orangehrm.enums.UserRole;
import com.opensource.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AdminTests extends BaseTest {

    @BeforeMethod
    public void setup() {
        extentTest.info("Test Setup started - Login işlemleri");
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openHomePage();
        loginPage.checkLoginTitle();
        loginPage.checkHomePageURL();
        loginPage.enterUserName(ConfigReader.getProperty("validUserName"));
        loginPage.enterPassword(ConfigReader.getProperty("validPassword"));
        loginPage.clickLoginButton();
        extentTest.pass("Login başarılı");

        DashboardPage dashboardPage = new DashboardPage(DriverManager.getDriver());
        dashboardPage.controlProfilPhoto();
        extentTest.info("Profil fotoğrafı kontrol edildi");

        MenuPage menuPage = new MenuPage(DriverManager.getDriver());
        menuPage.clickMenu("Admin");
        extentTest.info("Admin menüsüne geçildi");
    }

    @Test
    public void addAndEditUser() {
        extentTest.info("addAndEditUser testi başladı");

        AdminPage adminPage = new AdminPage(DriverManager.getDriver());

        adminPage.clickAddButton();
        extentTest.info("Add butonuna tıklandı");

        adminPage.chooseUserRole(UserRole.ADMIN.text);
        extentTest.info("User Role seçildi: " + UserRole.ADMIN.text);

        adminPage.chooseStatus(Status.ENABLED.text);
        extentTest.info("Status seçildi: " + Status.ENABLED.text);

        Faker faker = new Faker();
        String password = faker.internet().password();
        String userName = faker.name().username();
        String employeeName = "Ranga  Akunuri";

        adminPage.enterPassword(password);
        adminPage.enterConfirmPassword(password);
        adminPage.enterEmployeeName(employeeName);
        adminPage.enterUserName(userName);
        extentTest.info("Yeni kullanıcı bilgileri girildi: " + userName);

        adminPage.clickSave();
        extentTest.pass("Yeni kullanıcı kaydedildi");

        adminPage.enterSearchUserName(userName);
        adminPage.clickSearch();
        extentTest.info("Kullanıcı arandı: " + userName);

        boolean recordFound = adminPage.controlRecord(userName);
        Assert.assertTrue(recordFound, "Kayıt bulunamadı: " + userName);
        extentTest.pass("Kullanıcı kaydı doğrulandı: " + userName);

        adminPage.clickEditButton();
        extentTest.info("Edit butonuna tıklandı");

        adminPage.checkPageName("Edit User");
        extentTest.info("Edit User sayfası kontrol edildi");

        adminPage.chooseUserRole(UserRole.ESS.text);
        extentTest.info("User Role güncellendi: " + UserRole.ESS.text);

        userName = faker.name().username();
        adminPage.enterUserName(userName);
        extentTest.info("Kullanıcı adı güncellendi: " + userName);

        adminPage.clickSave();
        extentTest.pass("Kullanıcı düzenlemesi kaydedildi");

        extentTest.info("addAndEditUser testi tamamlandı");
    }

}
