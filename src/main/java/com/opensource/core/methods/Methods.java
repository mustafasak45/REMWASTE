package com.opensource.core.methods;

import com.opensource.core.driver.DriverManager;
import com.opensource.utils.TimeUtils;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.fail;

public class Methods {

    Logger logger=LogManager.getLogger(this.getClass());
    WebDriver driver;
    FluentWait<WebDriver> wait;
    JsMethods jsMethods;
    ActionMethods actionMethods;
    long waitElementTimeout = 30;
    long pollingEveryValue = 250;

    public Methods(WebDriver driver){

        this.driver = driver;
        wait = setFluentWait(waitElementTimeout, pollingEveryValue);
        jsMethods = new JsMethods(driver);
        actionMethods = new ActionMethods(driver);
    }

    public FluentWait<WebDriver> setFluentWait(long timeout){

        return setFluentWait(timeout, pollingEveryValue);
    }

    public FluentWait<WebDriver> setFluentWait(long timeout, long pollingEveryValue){

        FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver);
        fluentWait.withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofMillis(pollingEveryValue))
                .ignoring(NoSuchElementException.class);
        return fluentWait;
    }

    public List<String> getByValueAndSelectorType(By by){

        List<String> list = new ArrayList<String>();
        String stringBy = by.toString();
        Matcher matcher = Pattern.compile("[By\\.A-Za-z]+: ").matcher(stringBy);
        matcher.find();
        String t = matcher.group();
        String type = t.replace(": ","").trim();
        list.add(stringBy.replaceFirst(t,"").trim());
        //list.add(methodsUtil.getSelectorTypeName(type.replace("By.","").trim()));
        return list;
    }

    public Object jsExecuteScript(String script, boolean isScriptAsync, Object... args){

        return isScriptAsync ? jsMethods.jsExecuteAsyncScript(script, args) : jsMethods.jsExecuteScript(script, args);
    }

    public JsMethods getJsMethods(){

        return jsMethods;
    }

    public ActionMethods getActionMethods(){

        return actionMethods;
    }

    public Boolean isElementEnabled(By by){

        return findElement(by).isEnabled();
    }

    private Boolean isElementCondition(By by, int count, boolean condition, String valueType){

        boolean value = false;
        for (int i = 0; i < count; i++) {

            switch (valueType){
                case "enabled":
                    value = findElement(by).isEnabled();
                    break;
                case "disabled":
                    value = jsMethods.isElementDisabled(findElementForJs(by,"1"));
                    break;
                case "expanded":
                    value = jsMethods.isElementExpanded(findElementForJs(by,"1"));
                    break;
                default:
            }
            if (condition && value) {
                return true;
            }
            if (!condition && !value) {
                return true;
            }
            if (count != 1) {
                waitByMilliSeconds(250,false);
            }
        }
        return false;
    }

    public Boolean isElementEnabled(By by, int count, boolean condition){

        return isElementCondition(by, count, condition,"enabled");
    }

    public Boolean isElementDisabledJs(By by, int count, boolean condition){

       return isElementCondition(by, count, condition,"disabled");
    }

    public Boolean isElementExpandedJs(By by, int count, boolean condition){

        return isElementCondition(by, count, condition,"expanded");
    }

    public void clickElementForStaleElement(By by){

        try {
            click(by);
        } catch (StaleElementReferenceException e) {
            waitByMilliSeconds(400);
            waitUntilWithoutStaleElement(by,30);
            click(by);
        }
    }
    
    public void waitUntilWithoutStaleElement(By by, long timeout){

        setFluentWait(timeout).until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(findElement(by))));
    }

    public WebElement findElement(By by){

        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement findElementWithoutWait(By by){

        return driver.findElement(by);
    }

    public List<WebElement> findElements(By by){

        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public List<WebElement> findElementsWithOutError(By by){

        List<WebElement> list = new ArrayList<>();
        try {
            list.addAll(wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by)));
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<WebElement> findElementsWithoutWait(By by){

        return driver.findElements(by);
    }

    public void click(By by){

        findElement(by).click();
        logger.info(by.toString() + " elementine tıklandı.");
    }

    public void waitElementAndClick(By by,long timeout){

        setFluentWait(timeout).until(ExpectedConditions.elementToBeClickable(by)).click();
        logger.info(by.toString() + " elementine tıklandı.");
    }

    public void click(WebElement element){

        waitElementAndClick(element,waitElementTimeout);
        logger.info(element.toString() + " elementine tıklandı.");
    }

    public void waitElementAndClick(WebElement element,long timeout){

        setFluentWait(timeout).until(ExpectedConditions.elementToBeClickable(element)).click();
        logger.info(element.toString() + " elementine tıklandı.");
    }

    public void submitElement(By by){

        findElement(by).submit();
        logger.info(by.toString() + " elementine tıklandı.");
    }

    public void clearElement(By by){

        findElement(by).clear();
        logger.info("Elementin text alanı temizlendi.");
    }

    public void clearElementWithBackSpace(By by, String value){

        int count;
        String attribute = "";
        if (value.startsWith("attribute_")){
            attribute = value.replaceFirst("attribute_","");
            value = "attribute";
        }
        switch (value) {
            case "valueJs":
                count = getValueJs(by,"3").toString().toCharArray().length;
                break;
            case "text":
                count = getText(by).toCharArray().length;
                break;
            case "attribute":
                count = getAttribute(by, attribute).toCharArray().length;
                break;
            default:
                clearElement(by);
                waitByMilliSeconds(100);
                sendKeys(by, value.substring(0, 1));
                waitByMilliSeconds(100);
                count = 1;
        }
        WebElement webElement = findElement(by);
        for (int i = 0; i < count; i++){
            webElement.sendKeys(Keys.valueOf("BACK_SPACE"));
            //methodsUtil.waitByMilliSeconds(100);
        }
    }

    public Dimension getSize(By by){

        return findElement(by).getSize();
    }

    public Point getLocation(By by){

        return findElement(by).getLocation();
    }

    public Rectangle getRect(By by){

        return findElement(by).getRect();
    }

    public void sendKeys(By by, String text){

        findElement(by).sendKeys(text);
        logger.info(" Elemente " + text + " texti yazıldı.");
    }

    public void sendKeysByAction(String text){

        actionMethods.sendKeys(text);
        logger.info("Alana " + text + " texti yazıldı.");
    }

    public void sendKeysWithKeysByAction(String keys){

        actionMethods.sendKeys(keys);
    }

    public void sendKeysWithKeys(By by, String text){

        findElement(by).sendKeys(Keys.valueOf(text));
    }

    public void sendKeysWithNumpad(By by, String text){

        WebElement webElement = findElement(by);
        char[] textArray = text.toCharArray();
        for(int i = 0; i < textArray.length; i++){

            webElement.sendKeys(Keys.valueOf("NUMPAD" + String.valueOf(textArray[i])));
        }
        logger.info(" Elemente " + text + " texti yazıldı.");
    }

    public String getText(By by){

        return findElement(by).getText();
    }

    public String getTextContentJs(By by, String type){

        return jsMethods.getText(findElementForJs(by,type),"textContent");
    }

    public String getInnerTextJs(By by, String type){

        return jsMethods.getText(findElementForJs(by,type),"innerText");
    }

    public String getOuterTextJs(By by, String type){

        return jsMethods.getText(findElementForJs(by,type),"outerText");
    }

    public void mouseOverJs(By by, String type){

        jsMethods.mouseOver(findElementForJs(by,type));
        logger.info("mouseover " + by);
    }

    public void mouseOutJs(By by, String type){

        jsMethods.mouseOut(findElementForJs(by,type));
        logger.info("mouseout " + by);
    }

    public String getAttribute(By by, String attribute){

        return findElement(by).getAttribute(attribute);
    }

    public Object getAttributeJs(By by, String attribute, String type){

        return jsMethods.getAttribute(findElementForJs(by,type), attribute);
    }

    public Object getValueJs(By by, String type){

        return jsMethods.getValue(findElementForJs(by,type));
    }

    public void setValueJs(By by, String type, String text, boolean isValueString){

        jsMethods.setValue(findElementForJs(by,type), text, isValueString);
    }

    public String getCssValue(By by, String attribute){

        return findElement(by).getCssValue(attribute);
    }

    public String getHexCssValue(By by, String attribute){

        return Color.fromString(getCssValue(by, attribute)).asHex();
    }

    public String getCssValueJs(By by, String attribute, String type){

        return jsMethods.getCssValue(findElementForJs(by,type), attribute).toString();
    }

    public String getHexCssValueJs(By by, String attribute, String type){

        return Color.fromString(getCssValueJs(by, attribute, type)).asHex();
    }

    public Object validationMessage(By by, String type){

        return jsMethods.validationMessage(findElementForJs(by,type));
    }

    public Object checkValidity(By by, String type){

        return jsMethods.checkValidity(findElementForJs(by,type));
    }

    public String getPageSource(){

        return driver.getPageSource();
    }

    public String getCurrentUrl(){

        return driver.getCurrentUrl();
    }

    public Point getDriverPosition(){

        return driver.manage().window().getPosition();
    }

    public Dimension getDriverSize(){

        return driver.manage().window().getSize();
    }

    public void openNewWindowOrTab(boolean newWindowOrTab){

        WindowType windowType = newWindowOrTab ? WindowType.WINDOW : WindowType.TAB;
        driver.switchTo().newWindow(windowType);
    }

    public void openNewTabJs(String url){

        jsMethods.openNewTab(url);
        logger.info("Yeni tab açılıyor..." + " Url: " + url);
    }

    public void acceptAlert(){

        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public List<String> listTabs(){

        return new ArrayList<String>(driver.getWindowHandles());
    }

    public void close(){

        driver.close();
    }

    public void switchTab(int tabNumber){

        driver.switchTo().window(listTabs().get(tabNumber));
    }

    public void switchFrame(int frameNumber){

        driver.switchTo().frame(frameNumber);
    }

    public void switchFrame(String frameName){

        driver.switchTo().frame(frameName);
    }

    public void switchFrameWithKey(By by){

        WebElement webElement = findElement(by);
        driver.switchTo().frame(webElement);
    }

    public void switchParentFrame(){

        driver.switchTo().parentFrame();
    }

    public void switchDefaultContent(){

        driver.switchTo().defaultContent();
    }
    public void clickElementJs(By by){

        jsMethods.clickByElement(findElementForJs(by,"1"));
        logger.info(by.toString() + " elementine tıklandı.");
    }

    public void clickElementJs(By by, boolean notClickByCoordinate){

        jsMethods.clickByElement(findElementForJs(by,"3"), notClickByCoordinate);
        logger.info(by.toString() + " elementine tıklandı.");
    }

    public void navigateToBack(){

        driver.navigate().back();
    }

    public void navigateToForward(){

        driver.navigate().forward();
    }

    public void navigateToRefresh(){

        driver.navigate().refresh();
    }

    public Select getSelect(By by){

        return new Select(findElement(by));
    }

    public void selectByValue(By by, String value){

        getSelect(by).selectByValue(value);
    }

    public void selectByVisibleText(By by, String text){

        getSelect(by).selectByVisibleText(text);
    }

    public void selectByIndex(By by, int index){

        getSelect(by).selectByIndex(index);
    }

    public void selectItemByIndex(By by, int index){

        WebElement element = findElement(by);
        actionMethods.selectItemByIndex(element, index);
    }

    public List<WebElement> getSelectOptions(By by){

        return getSelect(by).getOptions();
    }

    public WebElement getFirstSelectedOption(By by){

        return getSelect(by).getFirstSelectedOption();
    }

    public List<WebElement> getAllSelectedOptions(By by){

        return getSelect(by).getAllSelectedOptions();
    }

    public void selectByIndexJs(By by, int index, String type){

        jsMethods.selectWithIndex(findElementForJs(by,type), index);
    }

    public void selectByTextJs(By by, String text, String type){

        jsMethods.selectWithText(findElementForJs(by,type), text);
    }

    public void selectByValueJs(By by, String value, String type){

        jsMethods.selectWithValue(findElementForJs(by,type), value);
    }

    public int getSelectedOptionIndexJs(By by, String type){

        return jsMethods.getSelectedOptionIndex(findElementForJs(by,type));
    }

    public String getSelectedOptionTextJs(By by, String type){

        return jsMethods.getSelectedOptionText(findElementForJs(by,type));
    }

    public String getSelectedOptionValueJs(By by, String type){

        return jsMethods.getSelectedOptionValue(findElementForJs(by,type));
    }

    public void scrollElementJs(By by, String type){

        jsMethods.scrollElement(findElementForJs(by,type));
    }

    public void scrollIntoViewIfNeededJs(By by, String type){

        jsMethods.scrollIntoViewIfNeeded(findElementForJs(by,type));
    }

    public void scrollElementCenterJs(By by,String type){

        jsMethods.scrollElementCenter(findElementForJs(by,type));
    }

    public void focusElementJs(By by){

        WebElement webElement = findElementForJs(by,"1");
        jsMethods.scrollElementCenter(webElement);
        jsMethods.focusElement(webElement);
    }

    public void jsExecutorWithBy(String script, By by){

        jsMethods.jsExecuteScript(script, findElementForJs(by,"3"));
    }

    public boolean isElementClickable(By by, long timeout){

        try {
            setFluentWait(timeout).until(ExpectedConditions.elementToBeClickable(by));
            logger.info("true");
            return true;
        }
        catch (Exception e) {
            logger.warn("false" + " " + e.getMessage());
            return false;
        }
    }

    public boolean isElementClickable(WebElement element, long timeout){

        try {
            setFluentWait(timeout).until(ExpectedConditions.elementToBeClickable(element));
            logger.info("true");
            return true;
        }
        catch (Exception e) {
            logger.warn("false" + " " + e.getMessage());
            return false;
        }
    }

    public boolean isElementInVisible(By by, long timeout){

        try {
            setFluentWait(timeout).until(ExpectedConditions.invisibilityOfElementLocated(by));
            logger.info("true");
            return true;
        } catch (Exception e) {
            logger.warn("false" + " " + e.getMessage());
            return false;
        }
    }
    public boolean isElementLocated(By by, long timeout){

        try {
            setFluentWait(timeout).until(ExpectedConditions.presenceOfElementLocated(by));
            logger.info("true");
            return true;
        } catch (Exception e) {
            logger.warn("false" + " " + e.getMessage());
            return false;
        }
    }

    public boolean isElementVisible(By by, long timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeoutInSeconds));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));

            boolean isVisible = element.isDisplayed();
            logger.info("Element is visible: " + by.toString());
            return isVisible;
        } catch (Exception e) {
            logger.warn("Element is NOT visible: " + by.toString() + " | Hata: " + e.getMessage());
            return false;
        }
    }

    public void hoverElementAction(By by, boolean isScrollElement) {

        WebElement webElement = findElementForJs(by,"1");
        if(isScrollElement){
            jsMethods.scrollElementCenter(webElement);
        }
        actionMethods.hoverElement(webElement);
    }

    public void moveAndClickElement(By by, boolean isScrollElement) {

        WebElement webElement = findElementForJs(by,"1");
        if(isScrollElement){
            jsMethods.scrollElementCenter(webElement);
            waitByMilliSeconds(500);
        }
        actionMethods.moveAndClickElement(webElement);
    }

    public void moveAndDoubleClickElement(By by, boolean isScrollElement) {

        WebElement webElement = findElementForJs(by,"1");
        if(isScrollElement){
            jsMethods.scrollElementCenter(webElement);
        }
        actionMethods.moveAndDoubleClickElement(webElement);
    }

    public void clickElementWithAction(By by, boolean isScrollElement){

        WebElement webElement = findElementForJs(by,"1");
        if(isScrollElement){
            jsMethods.scrollElementCenter(webElement);
        }
        actionMethods.clickElement(webElement);
    }

    public void doubleClickElementWithAction(By by, boolean isScrollElement){

        WebElement webElement = findElementForJs(by,"1");
        if(isScrollElement){
            jsMethods.scrollElementCenter(webElement);
        }
        actionMethods.doubleClickElement(webElement);
    }

    public WebElement findElementForJs(By by, String type){

        WebElement webElement = null;
        switch (type){
            case "1":
                webElement = findElement(by);
                break;
            case "2":
                webElement = findElementWithoutWait(by);
                break;
            case "3":
                List<String> byValueList = getByValueAndSelectorType(by);
                webElement = jsMethods.findElement(byValueList.get(0),byValueList.get(1));
                break;
            default:
                fail("type hatalı");
        }
        return webElement;
    }

    public List<WebElement> findElementsForJs(By by, String type){

        List<WebElement> webElementList = null;
        switch (type){
            case "1":
                webElementList = findElements(by);
                break;
            case "2":
                webElementList = findElementsWithoutWait(by);
                break;
            case "3":
                List<String> byValueList = getByValueAndSelectorType(by);
                webElementList = jsMethods.findElements(byValueList.get(0),byValueList.get(1));
                break;
            default:
                fail("type hatalı");
        }
        return webElementList;
    }

    private String saveScreenshot(File srcFile, String fileNamePrefix){
        String slash = System.getProperty("file.separator");
        String userDir = System.getProperty("user.dir");

        String path = slash + "target" + slash + "screenshotFiles"
                + slash + fileNamePrefix + "-" + TimeUtils.getTime("dd_MM_yyyy-HH_mm_ss_SSS") + ".jpg";
        String fileLocation = userDir + path;
        File destFile = new File(fileLocation);
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return fileLocation;
    }

    public String takeScreenshot(){

        TakesScreenshot scrShot = ((TakesScreenshot)driver);
        File srcFile =  scrShot.getScreenshotAs(OutputType.FILE);
        return saveScreenshot(srcFile,"screenshot");
    }
    public String takeScreenshotForElement(By by){

        File srcFile = findElement(by).getScreenshotAs(OutputType.FILE);
        return saveScreenshot(srcFile,"screenshotElement");
    }

    public void waitByMilliSeconds(long milliSeconds, Boolean... condition){

        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (condition.length == 0) {
            logger.info(milliSeconds + " milisaniye beklendi");
        }
    }

    public void waitBySeconds(long seconds, Boolean... condition){

        waitByMilliSeconds(seconds*1000,true);
        if (condition.length == 0) {
            logger.info(seconds + " saniye beklendi");
        }
    }
}
