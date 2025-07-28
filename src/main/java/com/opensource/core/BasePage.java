package com.opensource.core;

import org.openqa.selenium.WebDriver;
import com.opensource.core.methods.ReusableMethods;

public class BasePage extends ReusableMethods {

    WebDriver driver;

    public BasePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }


}