package com.HMDA.constants;

import java.io.File;

public class ConfigConstants {

    public static final String resources = System.getProperty( "user.dir" ) + File.separator
            + "src" + File.separator + "main" + File.separator + "resources" + File.separator;
    public static final String configPath = resources + "config.properties";
    public static final String outputDir = System.getProperty( "user.dir" ) + File.separator+"test-output" + File.separator;
    public static final String chromeProperty = "webdriver.chrome.driver";
    public static final String chromeExePath = "lib/chromedriver.exe";
    public static final String fireFoxProperty = "webdriver.gecko.driver";
    public static final String fireFoxExePath = "lib/geckodriver.exe";
    public static final String iEProperty = "webdriver.ie.driver";
    public static final String ieExePath = "lib/iedriver.exe";
    public static final String chromeSetting= "chrome.verbrose";
    public static final String rootDir = System.getProperty( "user.dir" );
    public static final int implicitWait= 30;
    public static final int pageLoadTime= 120;
    public static final long alertTime = 3000;
    public static final int pollingWait= 1000;
    public static final String home = "Home";
    public static final String servicesTab = "Services Tab";
    public static String HMDA_LoginUsers_testdata = resources + "HMDA_LoginUsers_testdata.xlsx";
    public static String Create_HMDAUser_TestData = resources + "xactusUser_test_data.xlsx";
    public static String Create_CustomerUser_TestData = resources + "customeruser_test_data.xlsx";
    
}
