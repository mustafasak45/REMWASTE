package com.opensource.pages.orangehrm.constants;

import org.openqa.selenium.By;

public class AdminPageConstants {
    public static By addButton = By.cssSelector("[class='oxd-icon bi-plus oxd-button-icon']");
    public static By userRoleDropdown = By.xpath("(//div[@class='oxd-select-text oxd-select-text--active'])[1]");
    public static By statusDropdown = By.xpath("(//div[@class='oxd-select-text oxd-select-text--active'])[2]");
    public static By passwordTextbox = By.xpath("(//div/input[@type='password'])[1]");
    public static By employeeNameTextbox = By.xpath("//div/input[@placeholder='Type for hints...']");
    public static By userNameTextbox = By.xpath("(//div/input[@autocomplete='off'])[1]");
    public static By confirmPasswordTextbox = By.xpath("(//div/input[@type='password'])[2]");
    public static By saveButton = By.cssSelector("[type='submit']");
    public static By tableRows = By.xpath("//div[@class='oxd-table-card']");
    public static By searchUserNameTextbox = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");
    public static By searchButton = By.xpath("//button[@type='submit']");
    public static By systemUserTitle = By.xpath("//h5[contains(normalize-space(.), 'System Users')]");
    public static By editUserTitle = By.xpath("//h6[contains(normalize-space(.), 'Edit User')]");
    public static By editButton = By.xpath("//button[i[contains(@class, 'bi-pencil-fill')]]");


}
