package com.opensource.pages.orangehrm;

import com.opensource.core.BasePage;
import com.opensource.pages.orangehrm.constants.AdminPageConstants;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class AdminPage extends BasePage {
    public AdminPage(WebDriver driver) {
        super(driver);
    }
    @Step("Add buttonuna tıklanır.")
    public void clickAddButton() {
        clickElementJs(AdminPageConstants.addButton);
    }
    @Step("User Role {userRole} olarak seçilir.")
    public void chooseUserRole(String userRole) {
        selectDropdownOption(AdminPageConstants.userRoleDropdown, userRole);
    }
    @Step("Status {status} olarak seçilir.")
    public void chooseStatus(String status) {
        selectDropdownOption(AdminPageConstants.statusDropdown, status);
    }
    @Step("Password {password} olarak girilir.")
    public void enterPassword(String password) {
        sendKeys(AdminPageConstants.passwordTextbox,password);
    }
    @Step("Confirm Password {password} olarak girilir.")
    public void enterConfirmPassword(String password) {
        sendKeys(AdminPageConstants.confirmPasswordTextbox,password);
    }
    @Step("Employee Name {employeeName} olarak girilir.")
    public void enterEmployeeName(String employeeName) {
        selectEmployeeName(AdminPageConstants.employeeNameTextbox,employeeName);
    }
    @Step("UserName {UserName} olarak girilir.")
    public void enterUserName(String UserName) {
        sendKeys(AdminPageConstants.userNameTextbox,UserName);
    }
    @Step("UserName {UserName} olarak girilir.")
    public void enterSearchUserName(String UserName) {
        Assert.assertTrue(isElementVisible(AdminPageConstants.systemUserTitle,5));
        sendKeys(AdminPageConstants.searchUserNameTextbox,UserName);
    }
    @Step("Save buttonuna tıklanır.")
    public void clickSave() {
        click(AdminPageConstants.saveButton);
    }
    @Step("Search buttonuna tıklanır.")
    public void clickSearch() {
        click(AdminPageConstants.searchButton);
        waitBySeconds(5);
    }
    @Step("Kayıt yapıldığı kontrol edilir.")
    public void controlRecord(String userName) {
        List<WebElement> recordList = findElements(AdminPageConstants.tableRows);
        Assert.assertTrue(recordList.get(0).getText().contains(userName));
    }
    @Step("Düzenle buttonuna tıklanır.")
    public void clickEditButton() {
        click(AdminPageConstants.editButton);
    }
    @Step("{pageName} sayfası açıldığı kontrol edilir.")
    public void checkPageName(String pageName) {
        By page = By.xpath("//h6[contains(normalize-space(.), '"+pageName+"')]");
        isElementVisible(page,5);
        Assert.assertEquals(getText(page),pageName);
    }
}
