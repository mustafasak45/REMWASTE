package com.opensource.pages.orangehrm;

import com.opensource.core.BasePage;
import com.opensource.pages.orangehrm.constants.AdminPageConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class AdminPage extends BasePage {
    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public void clickAddButton() {
        clickElementJs(AdminPageConstants.addButton);
    }

    public void chooseUserRole(String userRole) {
        selectDropdownOption(AdminPageConstants.userRoleDropdown, userRole);
    }

    public void chooseStatus(String status) {
        selectDropdownOption(AdminPageConstants.statusDropdown, status);
    }

    public void enterPassword(String password) {
        sendKeys(AdminPageConstants.passwordTextbox, password);
    }

    public void enterConfirmPassword(String password) {
        sendKeys(AdminPageConstants.confirmPasswordTextbox, password);
    }

    public void enterEmployeeName(String employeeName) {
        selectEmployeeName(AdminPageConstants.employeeNameTextbox, employeeName);
    }

    public void enterUserName(String UserName) {
        sendKeys(AdminPageConstants.userNameTextbox, UserName);
    }

    public void enterSearchUserName(String UserName) {
        Assert.assertTrue(isElementVisible(AdminPageConstants.systemUserTitle, 5));
        sendKeys(AdminPageConstants.searchUserNameTextbox, UserName);
    }

    public void clickSave() {
        click(AdminPageConstants.saveButton);
    }

    public void clickSearch() {
        click(AdminPageConstants.searchButton);
        waitBySeconds(5);
    }

    public boolean controlRecord(String userName) {
        List<WebElement> recordList = findElements(AdminPageConstants.tableRows);
        return recordList.get(0).getText().contains(userName);
    }

    public void clickEditButton() {
        click(AdminPageConstants.editButton);
    }

    public void checkPageName(String pageName) {
        By page = By.xpath("//h6[contains(normalize-space(.), '" + pageName + "')]");
        isElementVisible(page, 5);
        Assert.assertEquals(getText(page), pageName);
    }
}
