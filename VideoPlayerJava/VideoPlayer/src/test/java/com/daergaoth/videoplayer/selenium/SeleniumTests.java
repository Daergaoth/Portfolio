package com.daergaoth.videoplayer.selenium;

import com.daergaoth.videoplayer.selenium.enums.BrowserType;
import com.daergaoth.videoplayer.selenium.enums.ExceptionType;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class SeleniumTests {

    protected WebDriver driver;
    protected Map<String, Object> vars;
    protected JavascriptExecutor js;
    protected int screenshotCounter = 0;
    protected String screenshotSavePath;

    /**
     * <p>This method setup the Selenium Webdriver object to run with Edge browser with headless mode.</br>
     * Also setup the folder to save screenshots.
     * </p>
     *
     * @see <a href="https://www.selenium.dev/documentation/webdriver/getting_started/first_script/">Selenium Webdriver Documentation</a>
     * @since 1.0
     */
    @BeforeClass
    public void setup() {
        if (!SeleniumSettings.updated) {
            createSeleniumOptions();
        } else {
            waiting(5);
        }
        switch (SeleniumSettings.actualBrowserType) {
            case EDGE -> {
                EdgeOptions options = new EdgeOptions();
                if (SeleniumSettings.actualHeadlessMode) {
                    options.addArguments("--headless=new");
                }
                System.setProperty("webdriver.edge.driver", SeleniumSettings.actualWebdriverPath);
                driver = new EdgeDriver(options);
            }
            case CHROME, FIREFOX, OPERA, SAFARI -> throw new RuntimeException("Browser not supported.");
        }

        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String savePath = SeleniumSettings.actualSavePath + "SeleniumTest - " + timeStamp;
        File saveFolder = new File(savePath);
        if (saveFolder.exists()) {
            screenshotSavePath = saveFolder.getAbsolutePath();
        } else {
            if (saveFolder.mkdirs()) {
                screenshotSavePath = saveFolder.getAbsolutePath();
            } else {
                throw new RuntimeException("Save folder for screenshot creation failed.");
            }
        }
    }

    /**
     * Method read the src/main/resources/seleniumSettings.xml file and change the default values of the Selenium settings.
     *
     * @implNote BrowserType - String: EDGE/CHROME/FIREFOX/OPERA/SAFARI
     * WebdriverPatch - String: pointing to the webdriver.exe file
     * HeadlessMode - Boolean: Use the browser with headless mode if supported
     * SavePath - String: path where the screenshots will be saved in case of failing tests. If path not exists it will be created.
     */
    protected synchronized void createSeleniumOptions() {
        if (!SeleniumSettings.updated) {
            SeleniumSettings.updated = true;
            try {
                String str = Files.readString(Path.of(SeleniumSettings.seleniumSettingsXMLPath));
                for (String line : str.split("\n")) {
                    if (line.contains("<BrowserType>")) {
                        line = line.replace("<BrowserType>", "").replace("</BrowserType>", "").trim();
                        switch (line) {
                            case "EDGE", "Edge", "edge" -> SeleniumSettings.actualBrowserType = BrowserType.EDGE;
                            case "CHROME", "Chrome", "chrome" ->
                                    SeleniumSettings.actualBrowserType = BrowserType.CHROME;
                            case "FIREFOX", "Firefox", "firefox" ->
                                    SeleniumSettings.actualBrowserType = BrowserType.FIREFOX;
                            case "OPERA", "Opera", "opera" -> SeleniumSettings.actualBrowserType = BrowserType.OPERA;
                            case "SAFARI", "Safari", "safari" ->
                                    SeleniumSettings.actualBrowserType = BrowserType.SAFARI;
                        }
                    } else if (line.contains("<WebdriverPatch>")) {
                        line = line.replace("<WebdriverPath>", "").replace("</WebdriverPath>", "").trim();
                        if (line.length() > 0) {
                            SeleniumSettings.actualWebdriverPath = line;
                        }
                    } else if (line.contains("<HeadlessMode>")) {
                        line = line.replace("<HeadlessMode>", "").replace("</HeadlessMode>", "").trim();
                        if (line.length() > 0) {
                            switch (line) {
                                case "TRUE", "True", "true" -> SeleniumSettings.actualHeadlessMode = true;
                                case "FALSE", "False", "false" -> SeleniumSettings.actualHeadlessMode = false;
                            }
                        }
                    } else if (line.contains("<SavePath>")) {
                        line = line.replace("<SavePath>", "").replace("</SavePath>", "").trim();
                        if (line.length() > 0) {
                            SeleniumSettings.actualSavePath = line;
                        }
                    }
                }
            } catch (Exception e) {
                SeleniumSettings.updated = false;
            }
        }
    }

    /**
     * This method is used to create a screenshot and save it to the folder defined in the "setup()" method.
     * Then throw the AssertionError exception again.
     *
     * @param error
     * @return -
     * @see <a href="https://www.selenium.dev/documentation/webdriver/getting_started/first_script/">Selenium Webdriver Documentation</a>
     * @since 1.0
     */
    protected void handleAssertionError(AssertionError error) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        scrFile.renameTo(new File(generateFileName(ExceptionType.AssertError)));
        throw new AssertionError(error.getMessage());
    }

    /**
     * Generates the file name based on the error type.
     *
     * @return
     * @see <a href="https://www.selenium.dev/documentation/webdriver/getting_started/first_script/">Selenium Webdriver Documentation</a>
     * @since 1.0
     */
    protected String generateFileName(ExceptionType exceptionType) {
        StringBuilder result = new StringBuilder(screenshotSavePath);
        result.append("/");
        screenshotCounter++;
        switch (exceptionType) {
            case AssertError:
                result.append("AssertError_");
                break;
        }
        result.append(screenshotCounter);
        result.append(".png");
        return result.toString();
    }

    /**
     * Quit the driver at the end of execution of the whole class.
     *
     * @see <a href="https://www.selenium.dev/documentation/webdriver/getting_started/first_script/">Selenium Webdriver Documentation</a>
     * @since 1.0
     */
    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    /**
     * Make the execution wait until the defined time elapsed in milliseconds.
     *
     * @param seconds
     * @see <a href="https://www.selenium.dev/documentation/webdriver/getting_started/first_script/">Selenium Webdriver Documentation</a>
     * @since 1.0
     */
    protected void waiting(long seconds) {
        /*WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        try {
            wait.wait(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/

        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
