package com.HMDA.helper;

import com.HMDA.reports.Logs;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.LinkedList;
import java.util.List;

/**
 * @author S Rishi
 *
 */
public class DropDownUtils {

	 WebDriver driver;

	public DropDownUtils(WebDriver driver) {
		super();
		this.driver = driver;
	}
	
	

	//This method will select a drop-down option based on the value passed by the user.
	public void selectDropDownOptionByValue(WebElement element, String value) {
		Select select = new Select(element);
		Logs.info("Selecting drop-down options using value which is: "+ value);
		select.selectByValue(value);
	}

	//This method will select a drop-down option based on the index passed by the user.
	public static void selectDropDownOptionByIndex(WebElement element, int index) {
		Select select = new Select(element);
		Logs.info("Selecting drop-down options using index which is: "+ index);
		select.selectByIndex(index);
	}

	//This method will select a drop-down option based on the visible text passed by the user.
	public static void selectDropDownOptionByVisibleText(WebElement element, String visibleText) {
		Select select = new Select(element);
		Logs.info("Selecting drop-down options using visible text which is: "+ visibleText);
		select.selectByVisibleText(visibleText);
	}

	//This method will de-select a drop-down option based on the value passed by the user.
	public void deSelectDropDownOptionByValue(WebElement element, String value) {
		Select select = new Select(element);
		Logs.info("DeSelecting drop-down options using value which is: "+ value);
		select.deselectByValue(value);
	}

	//This method will de-select a drop-down option based on the index passed by the user.
	public void deSelectDropDownOptionByIndex(WebElement element, int index) {
		Select select = new Select(element);
		Logs.info("DeSelecting drop-down options using index which is: "+ index);
		select.deselectByIndex(index);
	}

	//This method will de-select a drop-down option based on the visible text passed by the user.
	public void deSelectDropDownOptionByVisibleText(WebElement element, String visibleText) {
		Select select = new Select(element);
		Logs.info("DeSelecting drop-down options using visible text which is: "+ visibleText);
		select.deselectByVisibleText(visibleText);
	}

	//This method will return all the options of displayed in the drop-down
	public static List<String> getAllDropDownOptions(WebElement element) {
		Select value = new Select(element);
		Logs.info("Getting all the drop-down options");
		List<WebElement> elementList = value.getOptions();
		List<String> valueList = new LinkedList<>();
		for (WebElement ele : elementList) {
			Logs.info(ele.getText());
			valueList.add(ele.getText());
		}
		Logs.info("Options displayed in the drop-down are: "+valueList);
		return valueList;
	}

	//This method will select a drop-down option randomly amongst the list of available options.
	public static void selectDropDownOptionRandomly(WebElement element) {
		Select select = new Select(element);
		Logs.info("Getting all the options from the drop-down");
		List<WebElement> allOptions = select.getOptions();
		List<String> valueList = new LinkedList<>();
			for (WebElement string : allOptions) {
			String option = string.getText();
			valueList.add(option);
		}
		int optionsCount = valueList.size();
		Logs.info("Options displayed in the drop-down are: "+valueList);
		Logs.info("Total no. of Options displayed in the drop-down are: "+optionsCount);
		selectDropDownOptionByIndex(element, CommonUtils.setDropDownLimit(optionsCount));
	}

	//This method will enable the check-box
	public static void checkingCheckBox(WebElement element){
		boolean checkStatus;
		checkStatus = element.isSelected();
		if (!checkStatus) {
			element.click();
			Logs.info("Checkbox is checked successfully");
		}
		else {
			Logs.info("Checkbox is already checked");
		}
	}

	//This method will disable the check-box
	public static void unCheckingCheckBox(WebElement element){
		boolean checkStatus;
		checkStatus = element.isSelected();
		if (checkStatus) {
			element.click();
			Logs.info("Checkbox is Unchecked successfully");
		}
		else {
			Logs.info("Checkbox is already Unchecked");
		}
	}

	//This method will validate that no drop-down option is selected
	public void validateNoDropDownOptionSelected(WebElement element) {
		Select select = new Select(element);
		Logs.info("Getting all the drop-down options");
		List<WebElement> options = select.getOptions();
		if (options.size()!=0) {
			for (WebElement option : options) {
				option.click();
				//JavaScriptHelper.clickElement(removebutton);
			}
		}
		else {
			Logs.info("No options are added");
		}
	}

	public static WebElement getPreviouslySelectedOption(WebElement element)
	{
		Select select = new Select(element);
		Logs.info("Getting the already selected option in the drop down");
		return select.getFirstSelectedOption();
	}

	public static void waitUntilDropDownPopulated(Select select , long timeout) {
		long waitForDropDownLoad = System.currentTimeMillis() + timeout;
		boolean boolFound = false;
		do {
			try {
				if (select.getOptions().size() >1) {
					boolFound = true;
				}
			} catch (Exception ignored) {
			}
		} while ((System.currentTimeMillis() < waitForDropDownLoad) && (!boolFound));
	}

}
