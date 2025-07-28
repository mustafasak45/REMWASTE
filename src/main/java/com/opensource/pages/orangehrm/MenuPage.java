package com.opensource.pages.orangehrm;

import com.opensource.core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class MenuPage extends BasePage {
    public MenuPage(WebDriver driver) {
        super(driver);
    }
    @Step("Menüden {selected} e tıklanır.")
    public void clickMenu(String selected) {
        clickMenuItemByText(selected);
    }
}
