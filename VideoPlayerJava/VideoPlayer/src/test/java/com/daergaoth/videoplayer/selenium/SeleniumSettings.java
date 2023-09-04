package com.daergaoth.videoplayer.selenium;

import com.daergaoth.videoplayer.selenium.enums.BrowserType;

public class SeleniumSettings {
    public static BrowserType actualBrowserType = BrowserType.EDGE;
    public static String actualWebdriverPath = "src/test/java/resources/EdgeWebdriver/msedgedriver.exe";
    public static boolean actualHeadlessMode = true;
    public static String actualSavePath = "src/test/java/resources/TestFailedScreenshots/";
    public static boolean updated = false;

    public static String seleniumSettingsXMLPath = "src/test/java/resources/seleniumSettings.xml";
}
