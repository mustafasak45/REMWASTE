package com.opensource.pages.orangehrm;

import com.opensource.core.BasePage;
import com.opensource.pages.orangehrm.constants.DashboardPageConstants;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class DashboardPage extends BasePage {
    public DashboardPage(WebDriver driver) {
        super(driver);
    }
    public void controlProfilPhoto() {
        Assert.assertTrue(isElementVisible(DashboardPageConstants.viewPhoto,3));
    }

}
