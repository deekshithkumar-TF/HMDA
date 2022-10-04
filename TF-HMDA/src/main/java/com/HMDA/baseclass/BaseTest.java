package com.HMDA.baseclass;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.HMDA.constants.ConfigConstants;
import com.HMDA.helper.ExcelUtils;
import com.HMDA.helper.WebDriverUtils;
import com.HMDA.pages.CreateProjectPage;

import com.HMDA.pages.CreateXactusUserPage;
import com.HMDA.pages.CustomersPage;
import com.HMDA.pages.HMDACustomerUser;
import com.HMDA.pages.HomePage;
import com.HMDA.pages.LoginPage;

import com.HMDA.reports.GetScreenShot;
import com.HMDA.reports.Logs;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class BaseTest {

    private ThreadLocal<RemoteWebDriver> tlDriver;
    public WebDriver driver;
    protected String appURL;
    public static String dbUrl;
//public ExtentTest logger;
//   public ExtentReports report;
    public static Properties prop;
    public String customer;
    public String customerUser;
    public String baseURL;
    public LoginPage loginPage;
    public HomePage homePage;
    public WebDriverUtils webDriverUtils;
    public String environment;
    public CustomersPage customersPage;  
    public CreateXactusUserPage CreateHMDAUser;
    public HMDACustomerUser CustomerHMDAUser;
    public CreateProjectPage CreateProject;
    
   
       
   
    @BeforeMethod(alwaysRun = true)
    @Parameters({"baseURL", "environment", "browser"})
    public void setUp(@Optional("https://qaxactusphere.xactus.com/") String baseURL,
                      @Optional("QA") String environment, @Optional("CH") String browser) {
    	try {
    		if (prop == null) {
    			prop = new Properties();
    		}
			FileInputStream fis = new FileInputStream(ConfigConstants.configPath);
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	this.baseURL=customerUser=prop.getProperty("baseURL");
    	customer=prop.getProperty("customer");
    	customerUser=prop.getProperty("user");
    	dbUrl=prop.getProperty("dbURL");
    	
        Logs.info( "Launching the browser" );
        String timestamp = new SimpleDateFormat( "MM_dd_yyyy_hh_mm_ss" ).format( new Date() );
       // extent= new ExtentReports( ConfigConstants.outputDir + "CPSS-Automation-Report"+timestamp+".html" );
        //logger = report.startTest( "CPSS Automation Test Report" );
       this. environment=environment;
       // browser Driver setting 
        setDriver( browser );
        driver = getTLDriver();

        loginPage = new LoginPage(driver , this.baseURL);
        homePage = new HomePage(driver);       
        //createRescoreOrderPage=new CreateRescoreOrderPage(driver);
        customersPage =new CustomersPage(driver);   
        CreateHMDAUser =new CreateXactusUserPage(driver);
        CreateProject =new CreateProjectPage(driver);
        

        appURL = baseURL;
        webDriverUtils = new WebDriverUtils(driver);
       
        
    }

    private synchronized WebDriver getTLDriver() {
        return tlDriver.get();
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void tearDown(ITestResult iResult) throws IOException {
       try {
    	if (iResult.getStatus() == ITestResult.FAILURE) {
            String testName = iResult.getName().trim();
            Logs.info( "The method which caused the failure is: " + testName );
            String timestamp = new SimpleDateFormat( "MM_dd_yyyy_hh_mm_ss" ).format( new Date() );

            //logger.log( LogStatus.FAIL, iResult.getThrowable() );
            GetScreenShot.capture( driver, testName + "_" + " " + timestamp );

            Logs.info( "Closing the browser as the execution is completed !!!" );
        }

        if (driver != null) {
            driver.quit();
            
        } else if (tlDriver != null) {
            getTLDriver().quit();
            
        }}
       catch(Exception e) { 
    	       	   if (driver != null) {
            driver.quit();
           
        } else if (tlDriver != null) {
            getTLDriver().quit();
           
            }}
    }
    
    public void reportImage(String message)  {  
        String destination;
		try {
			String timestamp = new SimpleDateFormat( "MM_dd_yyyy_hh_mm_ss" ).format( new Date() );
			//destination =  GetScreenShot.capture( driver, message + "_" + " " + timestamp );
			destination =  GetScreenShot.capture( driver, message + "_" + " " + timestamp );
			Reporter.log("<a href='"+ destination + "'> <img src='"+ destination + "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
    /*protected void failStep(Throwable message, String testName) throws IOException {
        String screenShotPath = captureFullPageScreenshot(testName);
        System.out.println("Screenshot path = " + screenShotPath);
        logger.log( LogStatus.FAIL, message);
    }

    public String captureFullPageScreenshot(String testName) throws IOException {
        String timestamp = new SimpleDateFormat("MM_dd_yyyy_hh_mm_ss").format(new Date());
        return GetScreenShot.capture(driver, testName + "_" + " " + timestamp);
    }*/

    private synchronized void setDriver(String browser) {
        switch (browser) {
            case "CH": {
                System.setProperty( ConfigConstants.chromeProperty, ConfigConstants.chromeExePath );
                Map<String, Object> prefs = new HashMap<>();
//            prefs.put(CPConstants.ChromeDownloadDirPref, Constants.downloadPath);
                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption( "prefs", prefs );
                options.setCapability( ConfigConstants.chromeSetting, false );
                options.setCapability( CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE );
//			options.setHeadless( true );
                tlDriver = ThreadLocal.withInitial( () -> new ChromeDriver( options ) );
                break;
            }
            case "FF":
                System.setProperty( ConfigConstants.fireFoxProperty, ConfigConstants.fireFoxExePath );
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setCapability( "marionette", true );
                tlDriver = ThreadLocal.withInitial( () -> new FirefoxDriver( firefoxOptions ) );
                break;
            case "IE": {
                System.setProperty( ConfigConstants.iEProperty, ConfigConstants.ieExePath );
                InternetExplorerOptions options = new InternetExplorerOptions();
                options.setCapability( InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true );
                options.setCapability( CapabilityType.SUPPORTS_ALERTS, false );
                options.setCapability( CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE );
                options.setCapability( InternetExplorerDriver.IGNORE_ZOOM_SETTING, true );
                options.setCapability( InternetExplorerDriver.NATIVE_EVENTS, true );
                options.setCapability( InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true );
                options.setCapability( InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true );
                options.setCapability( InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true );
                options.setCapability( "ignoreProtectedModeSettings", true );
                options.setCapability( "javascriptEnabled", true );
                options.setCapability( "acceptSslCerts", true );
                options.setCapability( "allowBlockedContent", true );
                tlDriver = ThreadLocal.withInitial( () -> new InternetExplorerDriver( options ) );
                break;
            }
        }
    }

    public void reportLogger(String message) {
        Logs.info(message);
        Reporter.log(message);
    }
  

    private static Object[][] asTwoDimensionalArray(List<Map<String, String>> interimResults) {
        Object[][] results = new Object[interimResults.size()][1];
        int index = 0;
        for (Map<String, String> interimResult : interimResults) {
            results[index++] = new Object[] {interimResult};
        }
        return results;
    }
    
    
    @DataProvider(name = "CompReportTestData")
    public Object[][] getBPRTestData() throws Exception{

        List<Map<String, String>> keyValuePairs = ExcelUtils.fetchData( ConfigConstants.HMDA_LoginUsers_testdata, "LoginUser" );
        return asTwoDimensionalArray(keyValuePairs);
    }
    
    @DataProvider(name = "CreateHMDAUserTestData")
    public Object[][] getHMDAUserTestData() throws Exception{

        List<Map<String, String>> keyValuePairs = ExcelUtils.fetchData( ConfigConstants.Create_HMDAUser_TestData, "Details" );
        return asTwoDimensionalArray(keyValuePairs);
    }
    
    @DataProvider(name = "CreateCustomerUserTestData")
    public Object[][] getCustomerUserTestData() throws Exception{

        List<Map<String, String>> keyValuePairs = ExcelUtils.fetchData( ConfigConstants.Create_CustomerUser_TestData, "Details" );
        return asTwoDimensionalArray(keyValuePairs);
    }
    
    @DataProvider(name = "CreateProject")
    public Object[][] getprojectDetails() throws Exception{

        List<Map<String, String>> keyValuePairs = ExcelUtils.fetchData( ConfigConstants.Create_Project_TestData, "Details" );
        return asTwoDimensionalArray(keyValuePairs);
    }
    
    
    
}
