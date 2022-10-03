package com.HMDA.pages;

import com.HMDA.constants.ConfigConstants;
import com.HMDA.helper.DropDownUtils;
import com.HMDA.helper.SeleniumUtils;
import com.HMDA.reports.Logs;
import com.HMDA.baseclass.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class CustomersPage extends BasePage {
    
    @FindBy(id = "menu")
    public WebElement menu;
    
    @FindBy(xpath  = "//div[contains(text(),'Customers')]")
    public WebElement customerHeaderElement;
    
    @FindBy(xpath = "//*[local-name()='svg' and @data-icon='plus']/*[local-name()='path']")
    //@FindBy(xpath  = "//a[contains(@title,'Create Customer')]")
    public WebElement createCircleICON;
    
    @FindBy(xpath  = "//div[contains(@class,'create-customer_text')]")
    public WebElement createCustTitleElement;
    
    @FindBy(xpath  = "//button[contains (text(),'Search')]")
    public WebElement clickSearchIcon;
    
    @FindBy(xpath  = "//input[contains (@class,'form-control')]") 
    public WebElement customerSearchField;
  
    @FindBy(xpath  = " //button[contains(text(),'Search') and contains(@class,'primaryBtn')]") 
    public WebElement searchButton;
  
    @FindBy(xpath  = " //table/tbody/tr/td[2]") 
    public WebElement customerAccountField;
 
    @FindBy(xpath  = "//select [contains(@class,'selectbg')]") 
    public WebElement searchByCustomerAccount;
 
    @FindBy(xpath  = " //td[7]/div[3]/a") 
    public WebElement editCustomerIcon;
    
    @FindBy(id ="customer-account-number") 
    public WebElement customerAccountNumEdit;
    
    @FindBy(id ="fax") 
    public WebElement editCustomerFaxNumber;
    
    @FindBy(xpath = "//button[contains(text(),'Update')]") 
    public WebElement updateEditCustomer;
    
    @FindBy(xpath  = "//div/div[2][contains(@class,'multiselect__tags')]") 
    public WebElement customerDropDown;
    
   
   
    
 
    
   // id="customerName" name="accountType" id="addressLine1  id=\"cityName  id=\"cityName
  //  Billing Address//div[contains(text(),'Is Billing Address same as Physical Address')]/following-sibling::div/label/div[contains(@class,'v-switch-core')]
    public CustomersPage(WebDriver paramDriver) {
        driver = paramDriver;
        PageFactory.initElements( driver, this );
    }

    public boolean checkMenuPresent() {
        waitForElementVisibilityWithPollingTime( menu );
        return SeleniumUtils.isElementPresent( menu , ConfigConstants.home );
    }
    
    public String verifyCustomerPageHeaderText() {
    	
    	waitForElementVisibilityWithPollingTime( customerHeaderElement );
    	String text=customerHeaderElement.getText();
    	
    	try {
			Assert.assertEquals(text, "Customers");
			Logs.info("Naviagated to Customer page ");
		} catch (Exception e) {
			Logs.info("System Unable to Naviagated to Customer page ");
			e.printStackTrace();
		}
		return text;
    }
    
    public void clickCreateCustomerIcon() {
    	waitforpagetoload();
    	SeleniumUtils.clickElement(createCircleICON);
    	waitforpagetoload();
    }
    
    public void searchCustomerIcon()
    {
    	waitforpagetoload();		
    	SeleniumUtils.clickElement(clickSearchIcon);
    	waitforpagetoload();
        }
    
    public void selectCustomerAccountfromDropDown()
    {
    	DropDownUtils.selectDropDownOptionByVisibleText(searchByCustomerAccount, "Customer Account Number");
    	waitforpagetoload();

    }
    
    public void enterCustomerAccountNumber()
    {
    	waitforpagetoload();
    	SeleniumUtils.setText(customerSearchField,prop.getProperty("CustomerAccountNumber"));
    	waitforpagetoload();
    }
    public void enterCustomerManualAccountNumber()
    {
    	waitforpagetoload();
    	SeleniumUtils.setText(customerSearchField,"106924");
    	waitforpagetoload();
    }
    public void clickSearchCustomerButton()
    {
    	SeleniumUtils.clickElement(searchButton);
    }
    
    public void clickEditCustomerIcon()
    
    {
    	waitforpagetoload();
    	SeleniumUtils.clickElement(editCustomerIcon);
    }
    
    public String verifyCreateCustomerPageTitle() {
    	waitForElementVisibilityWithPollingTime( createCustTitleElement );
    	String cusTitletext=createCustTitleElement.getText();
    	
    	
    	
    	try {
			Assert.assertEquals(cusTitletext, "Create Customer");
			Logs.info("Naviagated to Create Customer page ");
		} catch (Exception e) {
			Logs.info("System Unable to Naviagated to Create Customer page ");
			e.printStackTrace();
		}
		return cusTitletext;
    }
 
}
