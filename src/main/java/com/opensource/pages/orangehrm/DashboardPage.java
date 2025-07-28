package com.opensource.pages.orangehrm;

import com.opensource.core.BasePage;
import com.opensource.core.driver.DriverManager;
import com.opensource.pages.orangehrm.constants.DashboardPageConstants;
import com.opensource.utils.ConfigReader;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class DashboardPage extends BasePage {
    public DashboardPage(WebDriver driver) {
        super(driver);
    }
    @Step("Profil fotoğrafı kontrol edilir.")
    public void controlProfilPhoto() {
        Assert.assertTrue(isElementVisible(DashboardPageConstants.viewPhoto,3));
    }

}
