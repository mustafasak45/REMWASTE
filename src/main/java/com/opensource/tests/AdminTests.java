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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AdminTests extends BaseTest {

    @BeforeMethod
    public void setup() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openHomePage();
        loginPage.checkLoginTitle();
        loginPage.checkHomePageURL();
        loginPage.enterUserName(ConfigReader.getProperty("validUserName"));
        loginPage.enterPassword(ConfigReader.getProperty("validPassword"));
        loginPage.clickLoginButton();
        DashboardPage dashboardPage= new DashboardPage(DriverManager.getDriver());
        dashboardPage.controlProfilPhoto();
        MenuPage menuPage=new MenuPage(DriverManager.getDriver());
        menuPage.clickMenu("Admin");
    }

    @Test
    public void addAndEditUser() {

        AdminPage adminPage=new AdminPage(DriverManager.getDriver());
        adminPage.clickAddButton();
        adminPage.chooseUserRole(UserRole.ADMIN.text);
        adminPage.chooseStatus(Status.ENABLED.text);
        Faker faker = new Faker();
        String password = faker.internet().password();
        String userName = faker.name().username();
        String employeeName = "test  qa";

        adminPage.enterPassword(password);
        adminPage.enterConfirmPassword(password);
        adminPage.enterEmployeeName(employeeName);
        adminPage.enterUserName(userName);
        adminPage.clickSave();
        adminPage.enterSearchUserName(userName);
        adminPage.clickSearch();
        adminPage.controlRecord(userName);
        adminPage.clickEditButton();
        adminPage.checkPageName("Edit User");
        adminPage.chooseUserRole(UserRole.ESS.text);
        userName=faker.name().username();
        adminPage.enterUserName(userName);
        adminPage.clickSave();

    }

}
