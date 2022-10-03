package com.HMDA.pages;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.HMDA.baseclass.BasePage;
import com.HMDA.helper.SeleniumUtils;
import com.HMDA.reports.Logs;

import groovy.transform.AutoFinal;

public class CreateXactusUserPage extends BasePage {
	
	public String CustomerName;
	
	@FindBy(xpath="//div[contains(text(),'xactus Users')]")
	public WebElement XactusUserspageTitle;
	
		
	@FindBy(xpath="//div[@name='customer']/div/div[@class='multiselect__tags']")
	public WebElement Customerdropdown;
	
	@FindBy(xpath="//div[@name='customer']/div/div/span['100000-xactus']")
	public WebElement SelectCustomer;
	
	@FindBy(id="tooltip-target-id")
	public WebElement CreateNewUserButton;
	
	@FindBy(id="firstName")
	public WebElement FirstnameField;
	
	@FindBy(id="lastName")
	public WebElement LastnameField;
	
	@FindBy (id="phoneNumber")
	public WebElement phonenumberfield;
	
	@FindBy (id="text-email")
	public WebElement emailIdField;
	
	@FindBy (xpath="//div[@name='roles']")
	public WebElement rolesDropdown;	
	
	@FindBy(xpath="//button[contains(text(),'Save')] ")
	public WebElement ClickonSaveButton;
	
	@FindBy(xpath="")
	public WebElement xactusUserId;
	
	

	//  Billing Address
	public CreateXactusUserPage(WebDriver paramDriver)
	{
		driver = paramDriver;
		PageFactory.initElements( driver, this );

	}




	public String verifyPageTitle(WebElement element ,String pageName) 
	{
		waitForElementVisibilityWithPollingTime( element );
		String cusTitletext=element.getText();

		try {
			Assert.assertEquals(cusTitletext, pageName);
			Logs.info("Naviagated to "+pageName+" page ");
		} catch (Exception e) {
			Logs.info("System Unable to Naviagated to "+pageName+" page ");
			e.printStackTrace();
		}
		return cusTitletext;
	}

//
//	public String getCPUserID()
//	{
//		String text=SeleniumUtils.getText();
//		return text;
//
//	}
	@FindBy(xpath = "//div[@class='b-toaster-slot vue-portal-target']")
	WebElement successmessage;

	
	public void modalPopup(String messageExp) {
		waitForElementVisibilityWithPollingTime( successmessage );
		String message=SeleniumUtils.getText_v(successmessage);
		try {
			Assert.assertEquals(message, messageExp);
		} catch (Exception e) {
			Assert.fail("Not created USer and got failed");
		}
		
	}

	public String handleAlert() {
		waitforpagetoload();
		String alertText=handleAlertTextANDClickOK();
		try {
			Assert.assertNotSame(alertText, "User created successfully.");
		} catch (Exception e) {

		}
		//String [] num=alertText.split(" ");
		Logs.info("Creating Credit Plus User Successfully"+alertText);
		return alertText.trim();
	}

	public String handleAlertforEdit() 
	{
		waitforpagetoload();
		String alertText=handleAlertTextANDClickOK();
		try {
			Assert.assertNotSame(alertText, "User has been saved successfully..");
		} catch (Exception e) {

		}
		//String [] num=alertText.split(" ");
		Logs.info("Updating Credit Plus User Successfully"+alertText);
		return alertText.trim();
	}


	public void updatDataInPropertiesValue (String cAccNum)
	{

		updatDataInPropertiesFile("CustomerAccountNumber",cAccNum);
	}

	public String verifyxactusPageHeaderText() {

		waitforpagetoload();	    	
		String text=XactusUserspageTitle.getText();

		try {
			Assert.assertEquals(text, "xactus Users");
			Logs.info("Naviagated to xactus Users page ");
		} catch (Exception e) {
			Logs.info("System Unable to Navigate to xactus Users page ");
			e.printStackTrace();
		}
		return text;
	}

	/*
	 * public void clickCreateCPUserIcon() { waitforpagetoload();
	 * SeleniumUtils.clickElement(creditPlusAddUserIcon); waitforpagetoload(); }
	 */

	//added new script for click on new cpadduser icon
	
	public void clickxactusUserSaveButton() {
		scrollIntoView(ClickonSaveButton);
		SeleniumUtils.clickElement(ClickonSaveButton);
	}


	public void clickCreatexactusIcon()
	{ 
		waitforpagetoload(); 
		waitForElementVisibilityWithPollingTime(CreateNewUserButton);
	Actions  actionBuilder = new Actions(driver);
	actionBuilder.click(CreateNewUserButton).build().perform();
	waitforpagetoload(); 
	}

	public void enterxactusUserDetails(String xactusUserFname, String xactusUserLname,String xactusUserPhone,String xactusUserEmail ,String xactusUserCustomer,String xacrusUserRole)
	{

		SeleniumUtils.setText(FirstnameField, xactusUserFname);
		SeleniumUtils.setText(LastnameField, xactusUserLname);
		SeleniumUtils.setText(emailIdField, xactusUserEmail); 
		SeleniumUtils.setText(phonenumberfield, xactusUserPhone); 


		try {
			//selectProductFromDropDown(cpSearchCustomerDropDown, cpSelectCustomerDropDown,cpUserCustomer);
			//selectCPCustomerFromCPCustomerDropDown(cpSearchCustomerDropDown, cpSelectCustomerDropDown, cpUserCustomer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		try {

			//selectCPCustomerFromCPCustomerDropDown(cpRoleSearchDropDown,cpRoleDropDown,cpUserRole);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();


		}
		
	}
		
		
	
	public String verifyxactusUserPageTitle() {
		waitforpagetoload();
		waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq"));
		String xactusUserTitletext=verifyPageTitle(CreateNewUserButton, "Create xactus User");
		return xactusUserTitletext;
	}

	public String verifyCPUserData(String Fname)
	{
		waitforpagetoload();
		String text=SeleniumUtils.getText(FirstnameField);
		Assert.assertEquals(Fname, text);
		return text;
	}

	public String verifyPhoneNumber(String phoneNumber)
	{
		waitforpagetoload();
		String text=SeleniumUtils.getText(phonenumberfield);
		Assert.assertEquals(phoneNumber, text);
		return text;
	}

//	public void clickxactusUserSaveButton() {
//		scrollIntoView(ClickonSaveButton);
//		SeleniumUtils.clickElement(ClickonSaveButton);
//	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*
 * public void clickOnCancelButton() { waitforpagetoload();
 * scrollIntoView(clickOnCancel); SeleniumUtils.clickElement(clickOnCancel);
 * waitforpagetoload(); }
 */

/*
 * public void clickSearchButton() { waitforpagetoload();
 * SeleniumUtils.clickElement(searchButton); waitforpagetoload(); }
 */
/*
 * public void clickUpdateButton() { waitforpagetoload();
 * scrollIntoView(clickOnUpdate); SeleniumUtils.clickElement(clickOnUpdate);
 * waitforpagetoload(); }
 */

/*
 * public void clickEditIcon() { waitforpagetoload();
 * SeleniumUtils.clickElement(editIcon); waitforpagetoload(); }
 */



	/*
	 * public void enterDataForEdit(String textval) {
	 * 
	 * SeleniumUtils.setText(cpSearchFieldforEdit,textval);
	 * 
	 * }
	 */

	/*
	 * public String updatePhoneNumber() { String phoneNumber="(333) 333-3333";
	 * SeleniumUtils.setText(cpUserPhoneNumber, phoneNumber); return phoneNumber;
	 * 
	 * }
	 */


	/*
	 * public void clickoncpCustomerField(String cust) throws Exception {
	 * waitforpagetoload(); //waitForInvisibilityOfElement(By.className(
	 * "_base-spinner_spnrPosition_2XcEq"));
	 * selectCPCustomerFromCPCustomerDropDown(cpCustomerSearchField,
	 * cpSelectCustomerDropDown, cust);
	 * 
	 * waitforpagetoload(); }
	 */


	/*
	 * public void clickoncpSearchIcon() {
	 * waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq")
	 * ); //Actions act=new Actions(driver);
	 * //act.moveToElement(cpSearchUserICon).click().build().perform();
	 * SeleniumUtils.clickElement(cpSearchUserICon); waitforpagetoload(); }
	 */

	
//
//
//	public void selectSearchCriteria()
//	{
//		DropDownUtils.selectDropDownOptionByVisibleText(cpSearchbyDropDown,"First Name");
//	}
//
//
//	public void selectCPCustomerFromCPCustomerDropDown(WebElement clickcustomerDropDown, WebElement searchcustomerDropDown,
//			String value) throws Exception {
//
//		waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq"));
//		Logs.info("Clicking on the Customer type-ahead dropdown");
//		SeleniumUtils.clickElement(searchcustomerDropDown);
//		waitForElementVisibilityWithPollingTime(clickcustomerDropDown);
//		Logs.info("Clicked on the Customer type-ahead dropdown field");
//		Logs.info("Entering Customer name in Customer type-ahead dropdown field" + value);
//		SeleniumUtils.sendText(clickcustomerDropDown, value);
//		//clickcustomerDropDown.sendKeys(value,Keys.ENTER);
//		Logs.info("Waiting for all Customers in Customer type-ahead dropdown field to be displayed");
//
//		/*
//		 * try { explicitWaitBy(By.cssSelector(".multiselect__option > span")); }catch
//		 * (Exception e) { Logs.info("Customers list are not loading"); throw new
//		 * AutomationException("Customers list are not loading", e); }
//		 */
//		Logs.info("Fetching all Customers in Customer type-ahead dropdown field for the customer text entered");
//		//List<WebElement> findElements = driver.findElement(By.xpath("//ul[@class='multiselect__content']"))
//		//.findElements(By.xpath("//li"));
//		String ele="//ul[@class='multiselect__content']//li/span/span[contains(text(),'"+value.trim()+"')]";
//		Thread.sleep(2000);
//		List<WebElement> findElements=driver.findElements(By.xpath(ele));
//		Logs.info("Total no. of matching customers displayed are: " + findElements.size());		for (WebElement webElement : findElements) {
//
//			String text=webElement.getText();
//			if (webElement.getText().trim().equals(value)) {
//				Actions act=new Actions(driver);
//				act.moveToElement(webElement).click().perform();
//
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					//.printStackTrace();
//				}
//				//webElement.click();
//				//driver.findElement(By.xpath("//div/div/div/div")).click()	;
//				Logs.info("Clicked on the Expected Customer");
//				break;
//			}
//		}
//
//
//
//
//
//
//	}
//
//
//	public void selectCPCustomerFromCPCustomerDropDown( WebElement searchcustomerDropDown,
//			String value) throws Exception {
//
//		waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq"));
//		Logs.info("Clicking on the Customer type-ahead dropdown");
//		SeleniumUtils.clickElement(searchcustomerDropDown);
//		waitForElementVisibilityWithPollingTime(searchcustomerDropDown);
//		Logs.info("Clicked on the Customer type-ahead dropdown field");
//		Logs.info("Entering Customer name in Customer type-ahead dropdown field" + value);
//		SeleniumUtils.sendText(searchcustomerDropDown, value);
//		//clickcustomerDropDown.sendKeys(value,Keys.ENTER);
//		Logs.info("Waiting for all Customers in Customer type-ahead dropdown field to be displayed");
//
//		/*
//		 * try { explicitWaitBy(By.cssSelector(".multiselect__option > span")); }catch
//		 * (Exception e) { Logs.info("Customers list are not loading"); throw new
//		 * AutomationException("Customers list are not loading", e); }
//		 */
//		Logs.info("Fetching all Customers in Customer type-ahead dropdown field for the customer text entered");
//		List<WebElement> findElements = driver.findElement(By.xpath("//ul[@class='multiselect__content']"))
//				.findElements(By.xpath("//li"));
//
//
//		Logs.info("Total no. of matching customers displayed are: " + findElements.size());		
//		for (WebElement webElement : findElements) 
//		{
//
//			String text=webElement.getText();
//			if (webElement.getText().trim().equals(value)) {
//				Actions act=new Actions(driver);
//				act.moveToElement(webElement).click().perform();
//
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					//.printStackTrace();
//				}
//				webElement.click();
//				//		driver.findElement(By.xpath("//div[contains(@class,'create-user_status')]")).click()	;
//				Logs.info("Clicked on the Expected Customer");
//				break;
//			}
//	
//	
//	
//	
//	
//	
//
//}
