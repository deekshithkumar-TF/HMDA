package com.HMDA.baseclass;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.HMDA.constants.ConfigConstants;
import com.HMDA.helper.AutomationException;
import com.HMDA.helper.SeleniumUtils;
import com.HMDA.reports.Logs;



public class BasePage {

	public static Properties prop;
	public WebDriver driver;

	public void waitForElementVisibilityWithPollingTime(WebElement element) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOf(element));
		Logs.info("element is visible now");
	}

	public void waitForInvisibilityOfElement(By by) {
		WebDriverWait wait = new WebDriverWait(driver, ConfigConstants.pageLoadTime);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
		Logs.info("element is Invisible now");
	}

	 public void reportLogger(String message) {
	        Logs.info(message);
	        Reporter.log(message);
	    }
	
	/**
	 * This method get alert text message 
	 * @return
	 */
	public String handleAlertTextANDClickOK()
	{//waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq"));
	    waitforpagetoload();
		Alert alt=driver.switchTo().alert();
	    String altText=alt.getText();
	              alt.accept();
	    return altText;
	   
	   
	}
	@FindBy(xpath = "//div[@class='modal-content']/div/div/div/div")
	WebElement popsucessText;
	@FindBy(xpath = "//div[@class='modal-content']/div/div/div/div")
	WebElement popDeleteButton;
	public void handleModelpopup() {
		waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq"));
				try {
					assertEquals(popsucessText.getText(), "Are you sure to delete the item?");
					SeleniumUtils.clickElement(popDeleteButton);
				}catch(Exception e) {
					Logs.info("Unable to Delete Tax due to not cliked");
				}
				
				
	}

	public void explicitWaitBy(By by) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		Logs.info("element is visible now");
	}

	public void waitForNestedElementPresent(WebElement element, By by) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(element, by));
		Logs.info("element is visible now");
	}

	private WebDriverWait getWait() {
		WebDriverWait wait = new WebDriverWait(driver, 160);
		wait.pollingEvery(Duration.ofMillis(4000));
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(ElementNotVisibleException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.ignoring(NoSuchFrameException.class);
		return wait;
	}

	
	public void scrollIntoView(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	
	
	
	public static boolean waitTillSpinnerDisable(WebDriver driver, By by)
	{
	  FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver);
	  fWait.withTimeout(20, TimeUnit.SECONDS);
	  fWait.pollingEvery(250, TimeUnit.MILLISECONDS);
	  fWait.ignoring(NoSuchElementException.class);

	  Function<WebDriver, Boolean> func = new Function<WebDriver, Boolean>() 
	   {
	     @Override
	     public Boolean apply(WebDriver driver) {
	    WebElement element = driver.findElement(by);
	    System.out.println(element.getCssValue("display"));         
	    if(element.getCssValue("display").equalsIgnoreCase("none")){
	    return true;
	       }
	        return false;
	     }
	   };

	   return fWait.until(func);
	}

	protected void retrieveConfigParameteres() {
		if (prop == null) {
			prop = new Properties();
		}
		try {
			FileInputStream fis = new FileInputStream(ConfigConstants.configPath);
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is for those drop-downs options of which appear delayed
	 * 
	 * @param element {WebElement} The select element
	 * @param value   {String} Value to be selected, which also is passed as nested
	 *                element for which it waits till the nested element loads
	 */
	public void selectDropDownByValue(WebElement element, String value) {

		Select select = new Select(element);
		Logs.info("Selecting drop-down options using value which is: " + value);

		By by = By.xpath("//option[.='" + value + "']");
		waitForNestedElementPresent(element, by);
		if (isDropDownPopulated(element)) {				
			//select.selectByVisibleText(value.replaceAll(" +", " "));
			select.selectByVisibleText(value);
			if (!isDropDownSelected(element)) {
				Logs.info("Could not select the drop down value: " + value);			
			}else {
				Logs.info("Selected drop down value is: " + select.getAllSelectedOptions());
			}
		}
	}
	public void selectDropDownByIndex(WebElement element, int value) {

		Select select = new Select(element);
		Logs.info("Selecting drop-down options using value which is: " + value);

		By by = By.xpath("//option[@value='" + value + "']");
		waitForNestedElementPresent(element, by);
		if (isDropDownPopulated(element)) {				
			//select.selectByVisibleText(value.replaceAll(" +", " "));
			select.selectByValue("23");
			if (!isDropDownSelected(element)) {
				Logs.info("Could not select the drop down value: " + value);			
			}else {
				Logs.info("Selected drop down value is: " + select.getAllSelectedOptions());
			}
		}
	}

	/**
	 * Description: This method will enter the specified customer name and selects
	 * the expected customer Customer type-ahead dropdown field Parameters to be
	 * passed:Locator of Customer type-ahead dropdown field, Locator of Customer
	 * text entry field and name of the customer to be selected
	 * @throws Exception 
	 */
	
	public void selectCustomerFromCustomerDropDown(WebElement clickcustomerDropDown, WebElement searchcustomerDropDown,
			String value) throws Exception {

		waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq"));
		Logs.info("Clicking on the Customer type-ahead dropdown");
		SeleniumUtils.clickElement(clickcustomerDropDown);
		waitForElementVisibilityWithPollingTime(searchcustomerDropDown);
		Logs.info("Clicked on the Customer type-ahead dropdown field");
		Logs.info("Entering Customer name in Customer type-ahead dropdown field" + value);
		SeleniumUtils.sendText(searchcustomerDropDown, value);
		Logs.info("Waiting for all Customers in Customer type-ahead dropdown field to be displayed");
		
		try {
			explicitWaitBy(By.cssSelector(".multiselect__option > span"));
		}catch (Exception e) {
			Logs.info("Customers list are not loading");
			throw new AutomationException("Customers list are not loading", e); 
		}

		Logs.info("Fetching all Customers in Customer type-ahead dropdown field for the customer text entered");
		List<WebElement> findElements = driver.findElement(By.xpath("//ul[@class='multiselect__content']"))
				.findElements(By.tagName("li"));

		Logs.info("Total no. of matching customers displayed are: " + findElements.size());
		for (WebElement webElement : findElements) {
			if (webElement.getText().equals(value)) {
				webElement.click();
				Logs.info("Clicked on the Expected Customer");
				break;
			}
		}

	}
	
	
	/**
	 * Description: This method will enter the specified Customer User and selects
	 * the expected Customer User type-ahead dropdown field Parameters to be
	 * passed:Locator of Customer User type-ahead dropdown field, Locator of Customer User 
	 * text entry field and name of the Customer User to be selected
	 * @throws Exception 
	 */
	
	public void selectCustomerUserFromDropDown(WebElement clickcustomerUserDropDown, WebElement searchcustomerUserDropDown,
			String value) throws Exception {
		
		waitForInvisibilityOfElement(By.xpath("//span[contains(@class, 'base-spinner_spinner')]"));
		Logs.info("Clicking on the Customer User multiselect dropdown");
		SeleniumUtils.clickElement(clickcustomerUserDropDown);
		waitForElementVisibilityWithPollingTime(clickcustomerUserDropDown);
		Logs.info("Clicked on the CustomerUser multiselect dropdown field");
		Logs.info("Entering Customer User name in Customer User multiselect dropdown field: " + value);
		SeleniumUtils.sendText(searchcustomerUserDropDown, value);
		Logs.info("Waiting for all Customers in Customer User multiselect dropdown field to be displayed");
		
		try {
			explicitWaitBy(By.xpath("//div[@name='customerUser']/descendant::span[contains(@class, 'multiselect__option')]"));
		}catch (Exception e) {
			Logs.info("Customer Users list are not loading");
			throw new AutomationException("Customers User list are not loading", e); 
		}

		Logs.info("Fetching all Customer Users in Customer User multiselect dropdown field for the customer text entered");
		List<WebElement> findElements = driver.findElement(By.xpath("//div[@name='customerUser']/descendant::ul"))
				.findElements(By.tagName("li"));

		Logs.info("Total no. of matching Customer Users displayed are: " + findElements.size());
		for (WebElement webElement : findElements) {
			value = value.trim().replaceAll(" +", " ");
			if (webElement.getText().equals(value)) {
				webElement.click();
				Logs.info("Clicked on the Expected Customer User: " + value);
				break;
			}
		}

	}
	
	
	/**
	 * Description: This method will enter the specified Product and selects
	 * the expected Product type-ahead dropdown field Parameters to be
	 * passed:Locator of Product type-ahead dropdown field, Locator of Product 
	 * text entry field and name of the Product to be selected
	 * @throws Exception 
	 */
	
	public void selectProductFromDropDown(WebElement clickProductDropDown, WebElement searchProductDropDown,
			String value) throws Exception {
		
		waitForInvisibilityOfElement(By.xpath("//span[contains(@class, 'base-spinner_spinner')]"));
		Logs.info("Clicking on the Product multiselect dropdown");
		SeleniumUtils.clickElement(clickProductDropDown);
		waitForElementVisibilityWithPollingTime(clickProductDropDown);
		Logs.info("Clicked on the Product multiselect dropdown field");
		Logs.info("Entering Product name in Product multiselect dropdown field: " + value);
		SeleniumUtils.sendText(searchProductDropDown, value);//dropdown enter textbox not available
		Logs.info("Waiting for all Product in Product multiselect dropdown field to be displayed");
		
		try {
			explicitWaitBy(By.xpath("//div[@name='product']/descendant::span[contains(@class, 'multiselect__option')]"));
		}catch (Exception e) {
			Logs.info("Product list are not loading");
			throw new AutomationException("Product list are not loading", e); 
		}

		Logs.info("Fetching all Product in Product multiselect dropdown field for the Product text entered");
		List<WebElement> findElements = driver.findElement(By.xpath("//div[@name='product']/descendant::ul"))
				.findElements(By.xpath("//li/span"));

		Logs.info("Total no. of matching Products displayed are: " + findElements.size());
		for (WebElement webElement : findElements) {
			value = value.trim().replaceAll(" +", " ");
			if (webElement.getText().equals(value)) {
				webElement.click();
				Logs.info("Clicked on the Expected Product: " + value);
				break;
			}
		}

	}
	

	// This method will fetch the message displayed on the popup
	// If the popup is not displayed the this method will directly fail the testcase
	public String getPopUpMessage() {
		try {
			WebDriverWait wait = getWait();
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
		
	public boolean waitForChildWindow(int noOfWindows) {
		try {
			WebDriverWait wait = getWait();
			wait.until(ExpectedConditions.numberOfWindowsToBe(noOfWindows));
			Logs.info("Number of child windows: " + driver.getWindowHandles().size());
			return true;
		} catch (Exception e) {
			Assert.fail("Could not find the child window, Number of child windows: " + driver.getWindowHandles().size());
			return false;
		}
	}
	
	public boolean isDropDownSelected(WebElement dropDown) {
		try {
			Select select = new Select(dropDown); 
			String selectedValue = select.getFirstSelectedOption().getText();
			if (selectedValue.contains("Select")) {
				Logs.info("Could not select the required drop down value, selected value is: " + selectedValue);
				return false;
			}else {
				return true;
			}
		} catch (Exception e) {
			Assert.fail("Could not get the text of selected values in the drop down");
			return false;
		}
	}
	
	public boolean isDropDownPopulated(WebElement dropDown) {
		try {
			Select select = new Select(dropDown); 
			int listCount = select.getOptions().size();
			if (listCount == 0) {
				Logs.info("Could not select the required drop down value, list is empty");
				return false;
			}else {
				return true;
			}
		} catch (Exception e) {
			Assert.fail("Could not get the count of options in the drop down");
			return false;
		}
	}
	public  void highlightMyElement(WebElement element, int waitSeconds) {
		try {
			for (int i = 0; i < waitSeconds; i++) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
						"color: red; border: 5px solid red;");
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
			}
		} catch (Exception ex) {
			System.out.println("Error highlighting the Element!!");
		
}
		
		
	}
	public static void waitFor(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void waitFor(long milliseconds, String message) {
		try {
			Thread.sleep(milliseconds);
			System.out.println(message);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void waitforpagetoload() {
		try {
			for (int i = 0; i <= 60; i++) {
				waitFor(4000);
				boolean pagestatus = ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
				if (pagestatus == true) {
					break;
				}
			}
		} catch (Exception ex) {
			System.out.println("Error capturing Page Status!");
			waitFor(6000, "Additional 3s Wait; Total 5s!");
		}
	}
	
	public String handleAlertTextANDAccept() {
		Alert alt=driver.switchTo().alert();
		String altText=alt.getText();
		String [] text=altText.split("#");
		
		alt.accept();
		return text[1];
		
		
	}
	
	public void windowHandle() {
		// It will return the parent window name as a String
		String parent=driver.getWindowHandle();

		Set<String>s=driver.getWindowHandles();
		Logs.info("Widows size"+s.size());
		List<String> ls=new ArrayList<String>(s);
		if(!parent.equals(ls.get(0)))
		{
		driver.switchTo().window(ls.get(0 ));

		//System.out.println(driver.switchTo().window(ls.get(1)).getTitle());

		//driver.close();
		}
	}
	String parent;
	/**
	 * This method for Geting the Parent window
	 * @param wd
	 * @return
	 */
	public String  parentWindow(WebDriver wd){
		 parent=wd.getWindowHandle();
		return parent;
	}
	/**
	 * This method for switch to Window 
	 * @param wd
	 */
	public void switchToChildWindow(WebDriver wd){
			String parentwindow=parentWindow(wd);
			Set<String> windows=wd.getWindowHandles();
		for (String child : windows) {
			if(!parentwindow.equals(child))
			wd.switchTo().window(child);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void updatDataInPropertiesFile(String key, String value)
	
	{
        Properties properties = new Properties();
        try {      
                String propertiesFilePath = ConfigConstants.configPath;
                FileInputStream fis = new FileInputStream(propertiesFilePath);
                properties.load(fis);
                properties.setProperty(key,value);
                FileOutputStream fos = new FileOutputStream(propertiesFilePath);
                properties.store(fos,null);
                System.out.println("SUCCESS");
               // reportLogger("SUCCESSFULLY UPDATE IN CONFIG FILE");
                Logs.info("SUCCESSFULLY UPDATE IN CONFIG FILE");
        }
        catch(Exception e) {
                System.out.println("FAILED");
        }
        }
	public String handleAlertTextANDAcceptIS()
	{
	    Alert alt=driver.switchTo().alert();
	    String altText=alt.getText();
	    String [] text=altText.split("is");
	   
	    alt.accept();
	    return text[1];
	   
	   
	}
	
	
	
	public String handleAlertTextAccept()
	{
	    Alert alt=driver.switchTo().alert();
	    String altText=alt.getText();
	    alt.accept();
	    return altText;
	   
	   
	}
	
	
}
