package com.HMDA.helper;

import com.HMDA.reports.Logs;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;


import java.awt.datatransfer.StringSelection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeleniumUtils {

	/*
	 * Author : Rishi Pre-Condition : None Summary: This method will set the text
	 * for the weblement passed. Parameters : This method will take 1 parameter.
	 * User needs to pass the locator of the element while calling this method.
	 * Description : This method will set the text for the weblement passed.
	 */
	public static String setText(WebElement element, String textToSet) {
		element.clear();
		element.sendKeys(textToSet);
		return textToSet;
	}

	/*
	 * //public static void uploadImage() throws AWTException {
	 * Logs.info("Uploading image by the help of Robot class "); StringSelection ss
	 * = new StringSelection("C:\\Users\\105452\\Desktop\\download_jpeg");
	 * //Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null); //
	 * imitate mouse events like ENTER, CTRL+C, CTRL+V Robot robot1 = new Robot();
	 * robot1.keyPress(KeyEvent.VK_ENTER); robot1.keyRelease(KeyEvent.VK_ENTER);
	 * robot1.keyPress(KeyEvent.VK_CONTROL); robot1.keyPress(KeyEvent.VK_V);
	 * robot1.keyRelease(KeyEvent.VK_V); robot1.keyRelease(KeyEvent.VK_CONTROL);
	 * robot1.keyPress(KeyEvent.VK_ENTER); robot1.keyRelease(KeyEvent.VK_ENTER); }
	 */

	/*
	 * Author : Rishi Pre-Condition : None Summary: This method will fetch the text
	 * from the webelement passed as the parameter. Parameters : Webelement for
	 * which the text has to be fetched Description : This method will fetch the
	 * text from the webelement passed as the parameter.
	 */
	public static String getText(WebElement element) {
		String elementText = element.getAttribute("value").trim();
		Logs.info("Element Name fetched is: " + elementText);
		return elementText;
	}

	public static String getText_v(WebElement element) {
		String elementText = element.getText().trim();
		Logs.info("Element Name fetched is: " + elementText);
		return elementText;
	}

	public static void sendText(WebElement element, String textToSet) {
		element.sendKeys(textToSet);

	}

	public static void clickElement(WebElement element) {
		element.click();
	}

	/**
	 * This method will set any parameter string to the system's clipboard.
	 */
	public static void setClipboardData(String string) {
		// StringSelection is a class that can be used for copy and paste operations.
		StringSelection stringSelection = new StringSelection(string);
		//Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	/*
	 * Author : Rishi Pre-Condition : None Summary: This method will clear the text
	 * for the weblement passed. Parameters : This method will take 1 parameter.User
	 * needs to pass the locator of the element while calling this method
	 * Description : This method will clear the text for the weblement passed.
	 */
	public static void clearText(WebElement element) {
		element.click();
		element.clear();
	}

	/*
	 * Author : Rishi Pre-Condition : None Summary: This method will validate
	 * whether the element is not present. If the element is found to be present on
	 * that page, it return true else it will return false Parameters : This method
	 * will take 2 parameters. User needs to pass the locator of the element and the
	 * locator name while calling this method Description : This method will
	 * validate whether the element is not present. If the element is found to be
	 * present on that page, it return true else it will return false
	 */
	public static boolean isElementPresent(WebElement element, String elementName) {
		try {
			element.isDisplayed();
			Logs.info("Successfully validated the presence of the element: " + elementName);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Author : Rishi Pre-Condition : None Summary: This method will validate
	 * whether the element is present. If the element is not found , it will throw
	 * exception Parameters : This method will take 2 parameters. User needs to pass
	 * the locator of the element and the locator name while calling this method
	 * Description : This method will validate whether the element is present. If
	 * the element is not found , it will throw exception
	 */
	public static boolean isElementNotPresent(WebElement element, String elementName) {
		try {
			if (element.isDisplayed()) {
				Assert.fail("Validation failed for " + elementName
						+ " because it is displayed but was expected not to be displayed");
				return false;
			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
			Logs.info("Successfully Validated the unpresence of the element: " + elementName);
			return true;
		}
		return false;

	}

	/*
	 * Author : Rishi Pre-Condition : None Summary: This method will validate
	 * whether the element is enabled. If the element is found to be enabled , it
	 * will throw exception Parameters : This method will take 2 parameters. User
	 * needs to pass the locator of the element and the locator name while calling
	 * this method Description : This method will validate whether the element is
	 * enabled. If the element is found to be enabled , it will throw exception
	 */
	public static boolean isElementEnabled(WebElement element, String elementName) {
		if (element.isEnabled()) {
			Logs.info("Enabled status is found to be: " + true);
			Logs.info("Successfully validated that the element is enabled :" + elementName);
			return true;
		} else {
			Logs.info("Enabled status is found to be: " + false);
			Assert.fail("Validation failed for because it is enabled but was expected to be disabled");
			return false;
		}
	}

	/*
	 * Author : Rishi Pre-Condition : None Summary: This method will validate
	 * whether the element is disabled. If the element is found to be enabled , it
	 * will throw exception Parameters : This method will take 2 parameters. User
	 * needs to pass the locator of the element and the locator name while calling
	 * this method Description : This method will validate whether the element is
	 * disabled. If the element is found to be enabled , it will throw exception
	 */
	public static boolean isElementDisabled(WebElement element, String elementName) {
		boolean status;
		if (element.isEnabled()) {
			status = false;
			Logs.info("Disabled status is found to be: " + status);
			Assert.fail(
					"Validation failed for " + elementName + " because it is enabled but was expected to be disabled");
			return false;
		} else {
			status = true;
			Logs.info("Disabled status is found to be: " + status);
			Logs.info("Successfully validated that the element is disabled :" + elementName);
			return true;
		}
	}

	String Keyth;
	String valuetd;

	/**
	 * This method is getting table header and table data 
	 * @param tableElement
	 * @return
	 */
	public Map<String, String> tableDetailsTableHeader(WebElement tableElement) {
		Map<String, String> map = new HashMap<String, String>();

		List<WebElement> rows = tableElement.findElements(By.tagName("tr"));
		System.out.println("Total Table rows is =>" + (rows.size()));
		rows.get(2).click();
		for (int row = 0; row < rows.size(); row++) {
			List<WebElement> colstd = rows.get(row).findElements(By.tagName("td"));
			List<WebElement> colsth = rows.get(row).findElements(By.tagName("th"));
			valuetd = colstd.get(0).getText().trim();
			Keyth = colsth.get(0).getText();
			// System.out.println(valuetd);
			// System.out.println(Keyth);
			if (valuetd.length() > 0) {
				map.put(Keyth, valuetd);
			}		
			
		}

		return map;

	}

	/**
	 * This method for getting data from table
	 * @param tableElement
	 * @return
	 */
	public static Map<String, String> getTableData(WebElement tableElement) {
		String Keyth;
		String valuetd;
		Map<String, String> map = new HashMap<String, String>();

		List<WebElement> rows = tableElement.findElements(By.tagName("tr"));
		System.out.println("Total Table rows is =>" + (rows.size()));
		// rows.get(2).click();
		for (int row = 0; row < rows.size(); row++) {
			List<WebElement> colstd = rows.get(row).findElements(By.tagName("td"));
			Keyth = colstd.get(0).getText();
			valuetd = colstd.get(1).getText();
			try {
				if (!valuetd.equals("")) {
					map.put(Keyth, valuetd);
				}
			} catch (Exception e) {
				Assert.fail("Table Data Not available ");				
			}
		}

		return map;
	}
}
