package com.opensource.pages.orangehrm;

import com.opensource.core.BasePage;
import org.openqa.selenium.WebDriver;

public class MenuPage extends BasePage {
    public MenuPage(WebDriver driver) {
        super(driver);
    }
    public void clickMenu(String selected) {
        clickMenuItemByText(selected);
    }
}
