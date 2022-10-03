package com.HMDA.helper;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.HMDA.constants.ConfigConstants;
import com.HMDA.reports.Logs;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class WebDriverUtils {

	WebDriver driver;
	public static long waitTime;

	public WebDriverUtils(WebDriver driver) {
		this.driver = driver;

	}

	/*
	 * Author : Rishi Pre-Condition : None Summary: This method will wait for 30 sec
	 * for the alert to be displayed. Parameters : WebDriver object and alert time
	 * needs to be passed by the user. Description : This method will wait for 30
	 * sec for the alert to be displayed.
	 */
	public void waitForElementVisibleWithPollingTime(WebDriver driver, long timeout) {
		long waitForAlert = System.currentTimeMillis() + timeout;
		boolean boolFound = false;
		do {
			try {
				Alert alert = driver.switchTo().alert();
				if (alert != null) {
					alert.accept();
					boolFound = true;
				}
			} catch (NoAlertPresentException ex) {
			}
		} while ((System.currentTimeMillis() < waitForAlert) && (!boolFound));
	}

	/*
	 * Author : Rishi Pre-Condition : User should copy the link which needs to be
	 * opened in new tab Summary: This method will open the copied link in new
	 * browser tab. Parameters : None. Description :This method will open the copied
	 * link in new browser tab.
	 */
	public void copyLinkAndOpenInNewTab() throws UnsupportedFlavorException, IOException {
		String tab = "window.open('about:blank','_blank');";
		((JavascriptExecutor) driver).executeScript(tab);
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
	//	String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		//driver.get(data);
	}

	/*
	 * Author : Rishi Pre-Condition : None Summary: This method will open new
	 * browser tab and navigate to the url passed as the parameter. Parameters :
	 * User needs to pass the url which needs to be opened. Description :This method
	 * will open new browser tab and navigate to the url passed as the parameter.
	 */
	public void openNewTabAndNavigateToUrl(String url) {
		String tab = "window.open('about:blank','_blank');";
		((JavascriptExecutor) driver).executeScript(tab);
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		// String data = (String)
		// Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		driver.get(url);
	}

	// This method will capture the screenshot
	public void captureScreenshot(String testName) {
		String timestamp = new SimpleDateFormat("MM_dd_yyyy_hh_mm_ss").format(new Date());
		File file = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(file, new File("Screenshots/" + testName + "_" + " " + timestamp + ".png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// This method will capture full page screenshot
	public String captureFullPageScreenshot(String testName) {
		String timestamp = new SimpleDateFormat("MM_dd_yyyy_hh_mm_ss").format(new Date());
		Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(this.driver);
//		ImageIO.write(fpScreenshot.getImage(), "PNG",
//				new File("Screenshots/" + testName + "_" + " " + timestamp + ".png"));
		return ConfigConstants.outputDir + "Screenshots" + testName + "_" + " " + timestamp + ".png";

	}

	/**
	 * This method will set any parameter string to the system's clipboard.
	 */
	public void setClipboardData(String string) {
		// StringSelection is a class that can be used for copy and paste operations.
		StringSelection stringSelection = new StringSelection(string);
		//Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	public void uploadFile() {
		try {
			driver.findElement(By.xpath("//label[@id='custom-link' and contains(text(),'Browse')]")).click();
			Runtime.getRuntime().exec("C:\\Users\\105452\\Desktop\\FileUpload.exe");
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	public void setImplicitWait() {
		driver.manage().timeouts().implicitlyWait(ConfigConstants.implicitWait, TimeUnit.SECONDS);
	}

	public void setExplicitWait(long waitTime) throws InterruptedException {
		Thread.sleep(waitTime);
	}

	public void reSetImplicitWait() {
		driver.manage().timeouts().implicitlyWait(ConfigConstants.implicitWait, TimeUnit.SECONDS);
	}

	/**
	 * This will help us to get WebDriverWait object
	 * 
	 * @return WebDriverWait
	 */
	private WebDriverWait getWait() {
		WebDriverWait wait = new WebDriverWait(driver, ConfigConstants.implicitWait);
		wait.pollingEvery(Duration.ofMillis(ConfigConstants.pollingWait));
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(ElementNotVisibleException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.ignoring(NoSuchFrameException.class);
		return wait;
	}

	/**
	 * This method will make sure invisibilityOf element
	 * 
	 * @param {WebElement} element
	 * @return
	 */
	public boolean waitForElementNotPresent(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, ConfigConstants.implicitWait);
		boolean status = wait.until(ExpectedConditions.invisibilityOf(element));
		Logs.info("element is invisibile now");
		return status;
	}

	/**
	 * This will wait for until the element becomes invisible
	 * 
	 * @param element
	 */
	public void waitForElementInvisibilityWithPollingTime(WebElement element) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
		Logs.info("element is not visible now");
	}

	public void waitForElementVisibilityWithPollingTime(WebElement element) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOf(element));
		Logs.info("element is visible now");
	}

	/**
	 * This method will wait for frameToBeAvailableAndSwitchToIt
	 * 
	 * @param element
	 */
	public void waitForframeToBeAvailableAndSwitchToIt(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, ConfigConstants.implicitWait);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
		Logs.info("frame is available and switched");
	}

	/**
	 * This method will give is fluentWait object
	 * 
	 * @return
	 */
	private Wait<WebDriver> getfluentWait() {
		return new FluentWait<>(driver).withTimeout(Duration.ofSeconds(ConfigConstants.implicitWait))
				.pollingEvery(Duration.ofMillis(ConfigConstants.implicitWait)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).ignoring(TimeoutException.class);
	}

	public void setScriptTimeout() {
		driver.manage().timeouts().setScriptTimeout(ConfigConstants.implicitWait, TimeUnit.SECONDS);
		Logs.info("page is loaded");
	}

	/**
	 * This will wait for till the page title is loaded/displayed
	 * 
	 * @param pageTitle
	 */
	public void waitForTitle(String pageTitle) {

		Logs.info("waiting for Page title:" + "'" + pageTitle + "'" + " to load");
		WebDriverWait wait = new WebDriverWait(driver, ConfigConstants.implicitWait);
		wait.until(ExpectedConditions.titleContains(pageTitle));
		Logs.info("Page title " + "'" + pageTitle + "'" + " loaded successfully");
	}

	public void waitForExactTitle(String pageTitle) {

		Logs.info("waiting for Page title:" + "'" + pageTitle + "'" + " to load");
		WebDriverWait wait = new WebDriverWait(driver, ConfigConstants.implicitWait);
		wait.until(ExpectedConditions.titleIs(pageTitle));
		Logs.info("Page title " + "'" + pageTitle + "'" + " loaded successfully");
	}

	public void waitTillTitleIsNot(String pageTitle) {
		Logs.info("waiting untill the page title doesn't contain:" + "'" + pageTitle + "'");
		WebDriverWait wait = new WebDriverWait(driver, ConfigConstants.implicitWait);
		wait.until(ExpectedConditions.not(ExpectedConditions.titleContains(pageTitle)));
		Logs.info("Landed to a page that dosn;t conatin title as - " + "'" + pageTitle + "'");
	}

	/**
	 * This will wait until page loads and document ready state is 'complete'
	 */
	public void waitForPageLoad() {
		ExpectedCondition<Boolean> pageLoadCondition = driver -> ((JavascriptExecutor) driver)
				.executeScript("return document.readyState").equals("complete");
		WebDriverWait wait = new WebDriverWait(driver, ConfigConstants.implicitWait);
		wait.until(pageLoadCondition);
	}

	/*
	 * This will wait for Bootstrap modal popUp window
	 */
	public void WaitForModalPopUpAppear(By locator) {
		reSetImplicitWait();
		WebDriverWait wait = new WebDriverWait(driver, ConfigConstants.implicitWait);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		setImplicitWait();
		// driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	/*
	 * This will wait for Bootstrap modal popUp to fade
	 */
	public void WaitForModalPopUpDisAppear(By locator) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.stalenessOf(driver.findElement(locator)));
		} catch (NoSuchElementException e) {
			throw new AutomationException(e.getLocalizedMessage());
		}
		setImplicitWait();

	}

	public void waitForPageComponentLoad(By locator) {
		reSetImplicitWait();
		WebDriverWait wait = new WebDriverWait(driver, ConfigConstants.implicitWait);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		setImplicitWait();
	}

	/*
	 * This will wait for Bootstrap modal popUp window
	 */
	public void WaitForFormStatus(By locator) {
		reSetImplicitWait();
		WebDriverWait wait = new WebDriverWait(driver, ConfigConstants.implicitWait);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		setImplicitWait();
		// driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

	}

	public void waitForStalenessOfElement(By locator) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.stalenessOf(driver.findElement(locator)));
		} catch (NoSuchElementException e) {

		}
		setImplicitWait();
	}

	// This method will delete the browser cookies.
	public void deleteCookies() {
		driver.manage().deleteAllCookies();
	}

	// This method will maximize the window
	public void maximizeWindow() {
		driver.manage().window().maximize();
	}

	/**
	 * This method will switch to parent window
	 */
	public void switchToParentWindow() {
		Logs.info("switching to parent window...");
		driver.switchTo().defaultContent();
	}

	/**
	 * This method will switch to child window based on index
	 * 
	 * @param index
	 */
	public void switchToWindow(int index) {
		Set<String> windows = driver.getWindowHandles();
		int i = 1;
		for (String window : windows) {
			if (i == index) {
				Logs.info("switched to : " + index + " window");
				driver.switchTo().window(window);
			} else {
				i++;
			}
		}
	}

	/**
	 * This method will close all tabbed window and switched to main window
	 */
	public void closeAllTabsAndSwitchToMainWindow() {
		Set<String> windows = driver.getWindowHandles();
		String mainwindow = driver.getWindowHandle();

		for (String window : windows) {
			if (!window.equalsIgnoreCase(mainwindow)) {
				driver.close();
			}
		}
		Logs.info("switched to main window");
		driver.switchTo().window(mainwindow);
	}

	/**
	 * This method will do browser back navigation
	 */
	public void navigateBack() {
		Logs.info("navigating back");
		driver.navigate().back();
	}

	/**
	 * This method will do browser forward navigation
	 */
	public void navigateForward() {
		Logs.info("navigating forward");
		driver.navigate().forward();
	}

	/**
	 * This method will refresh the browser.
	 */
	public void refreshBrowser() {
		Logs.info("refreshing the browser");
		driver.navigate().refresh();
	}

	// This method will accept the pop-up.
	public void acceptPopUpIfPresent() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, ConfigConstants.implicitWait);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			setImplicitWait();
			alert.accept();
			Logs.info("Successfully closed the pop-up");

		} catch (Exception e) {
			throw new AutomationException("No alert is displayed");
		}
	}

	// This method will return the message displayed in the pop-up.
	public String getPopUpMessage() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, ConfigConstants.implicitWait);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			String popUpMsg = alert.getText();
			Logs.info("Message displayed in the pop-up is: " + popUpMsg);
			return popUpMsg;
		} catch (Exception e) {
			Assert.fail("No alert is displayed");
			return null;
		}
	}

	// This method will dismiss the pop-up
	public boolean dismissPopUp() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, ConfigConstants.implicitWait);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
			Logs.info("Successfully closed the pop-up");
			return true;
		} catch (Exception e) {
			Logs.info("pop-up is not displayed");
			return false;
		}
	}

	// This method will return the page title
	public String getPageTitle() {
		Logs.info("Getting the title of the page");
		return driver.getTitle();
	}

	// This method will switch to the frame by the webelement passed by the user.
	public void switchToFrameByWebElement(WebElement element) {
		driver.switchTo().frame(element);
	}

	// This method will switch to the window based on the web element.
	public void switchToWindow(WebElement element, String elementName) {
		Set<String> windows = driver.getWindowHandles();
		ArrayList<String> alWindows = CommonUtils.castToList(windows);
		Logs.info("No. of windows found are: " + alWindows.size());
		String currentWindow = driver.getWindowHandle();
		boolean windowFound = false;
		for (int i = alWindows.size() - 1; i >= 0; i--) {
			String latestWindow = alWindows.get(i);
			if (!currentWindow.equalsIgnoreCase(latestWindow)) {
				try {
					Logs.info("Switching to the window");
					driver.switchTo().window(latestWindow);
					// waitForframeToBeAvailableAndSwitchToIt(this.frameName);
					if (element.isDisplayed()) {
						windowFound = true;
						Logs.info("Successfully switched to the window where: " + elementName + " is displayed");
						break;
					}
				} catch (Exception e) {
					Logs.info("Expected window not displayed. Continue with next Iteration.");
				}

			}

		}
		if (!windowFound) {
			throw new AutomationException("Specfified window not found");

		}

	}

	public void clickElementForIntercepted(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().build().perform();
	}

	public void switchToPreviousWindow() {
		String window1 = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			if (!window1.equalsIgnoreCase(window))
				driver.switchTo().window(window);
		}

	}

	public void waitUntilTextInsideElementDisplayed(WebElement element, String text) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}

	public void moveToElement(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();
	}

	// This method will return the message displayed in the pop-up.
	public String handleUnwantedAlert() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			String popUpMsg = alert.getText();
			reportLogger("Message displayed in the pop-up is: " + popUpMsg);
			return popUpMsg;
		} catch (Exception e) {
			reportLogger("No unwanted Alert displayed");
			return null;
		}
	}

	public void reportLogger(String message) {
		Logs.info(message);
		Reporter.log(message);
	}

	// This method can be used to switch directly to the tab in which pdf is opened
	public void switchToPdfWindow(String elementName) {
		Set<String> windows = driver.getWindowHandles();
		ArrayList<String> alWindows = CommonUtils.castToList(windows);
		Logs.info("No. of windows found are: " + alWindows.size());
		String currentWindow = driver.getWindowHandle();
		boolean windowFound = false;
		for (int i = alWindows.size() - 1; i >= 0; i--) {
			String latestWindow = alWindows.get(i);
			if (!currentWindow.equalsIgnoreCase(latestWindow)) {
				try {
					Logs.info("Switching to the window");
					driver.switchTo().window(latestWindow);
					windowFound = true;
					Logs.info("Successfully switched to the window where: " + elementName + " is displayed");
					break;
				} catch (Exception e) {
					Logs.info("Expected window not displayed. Continue with next Iteration.");
				}

			}

		}
		if (!windowFound) {
			throw new AutomationException("Specfified window not found");

		}

	}
}
