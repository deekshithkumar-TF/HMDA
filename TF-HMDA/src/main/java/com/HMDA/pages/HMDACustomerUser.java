package com.HMDA.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.HMDA.baseclass.BasePage;
import com.HMDA.baseclass.BaseTest;
import com.HMDA.helper.SeleniumUtils;
import com.HMDA.reports.Logs;

public class HMDACustomerUser extends BasePage {

	@FindBy (xpath="//a[@class='dropdown-toggle']")
	public WebElement NavigatetoAdministration;

	@FindBy (xpath="//a[contains(text(),'Customers')]")
	public WebElement NavigateAdministrationCustomer;

	@FindBy (xpath="//a[contains(text(),'Users')]")
	public WebElement ClickCustomerUsers ;
	
	@FindBy (xpath="//div[contains(text(),'Users')]")
	public	WebElement CustomerUsersPage;

	@FindBy (id="tooltip-target-id")
	public WebElement ClickonCreateUserButtton;

	@FindBy (xpath="//div[@class='multiselect__select']")
	public WebElement ClickonCustoemrDropdown;

	@FindBy (xpath="//div[@name='customer']/div/div/span['107746-HmdaTestCustomer']")
	public WebElement SelectCustomerfromdropdown;

	@FindBy (id="firstName")
	public WebElement FirstNameField;

	@FindBy (id="lastName")
	public WebElement LastNameField;

	@FindBy (id="phoneNumber")
	public WebElement PhonenumField;

	@FindBy (id="text-email")
	public WebElement EmailField;

	@FindBy (xpath="//input[@name='roles']")
	public WebElement Rolesdropdown;

	@FindBy (xpath="//input[@id='__BVID__557']")
	public WebElement HMDACHeckbox;

	@FindBy (xpath="//button[contains(text(),'Save')]")
	public WebElement ClickonSave;




	//NavigatetoAdministrationCustomerUsers

	public void navigateToCustomerUsers() {

		waitForElementVisibilityWithPollingTime(NavigatetoAdministration);

		waitForInvisibilityOfElement(By.className("dropdown-menu py-0 dropright RootMenu_child_1Dm0R RootMenu_subMenuParent_2tHqw")
				); waitforpagetoload(); Actions action = new Actions( driver );
				Logs.info("Mouse hovering on the Administration Tab");
				action.moveToElement(NavigatetoAdministration ).perform();
				action.moveToElement(NavigateAdministrationCustomer).perform();
				action.moveToElement(ClickCustomerUsers).click().perform();
	}
	
	
	public String verifyCustomerUserPageHeaderText() {
    	
	    waitforpagetoload();	    	
		String text=CustomerUsersPage.getText();
	    	
	    	try {
				Assert.assertEquals(text, "Users");
				Logs.info("Naviagated to Customer Users page ");
			} catch (Exception e) {
				Logs.info("System Unable to Navigate to Credit Plus Users page ");
				e.printStackTrace();
			}
			return text;
	    }
	
	public void clickCreateCustomerUser()
	{ 
		waitforpagetoload(); 
		waitForElementVisibilityWithPollingTime(ClickonCreateUserButtton);
	Actions  actionBuilder = new Actions(driver);
	actionBuilder.click(ClickonCreateUserButtton).build().perform();
	waitforpagetoload(); 
	}
	
	public void SelectCustomer() {
		Actions  actionBuilder = new Actions(driver);
		actionBuilder.click(SelectCustomerfromdropdown).build().perform();
		waitforpagetoload();
	}
	
	

	public void enterCustomerUserDetails(String CustomerUserFname, String CustomerUserLname,String CustomerUserPhone,String CustomerUserEmail ,String Customer,String CustomerUserRole)
	{

		SeleniumUtils.setText(FirstNameField, CustomerUserFname);
		SeleniumUtils.setText(LastNameField, CustomerUserLname);
		SeleniumUtils.setText(EmailField, CustomerUserEmail); 
		SeleniumUtils.setText(PhonenumField, CustomerUserPhone); 
	}
	
	public void clickCustomerUserSaveButton() {
		scrollIntoView(ClickonSave);
		SeleniumUtils.clickElement(ClickonSave);
	}

}




