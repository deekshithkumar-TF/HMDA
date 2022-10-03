package com.HMDA.pages;

import com.HMDA.baseclass.BasePage;
import com.HMDA.baseclass.BaseTest;
import com.HMDA.constants.ConfigConstants;
import com.HMDA.helper.AutomationException;
import com.HMDA.helper.SeleniumUtils;
import com.HMDA.reports.Logs;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;

import org.apache.commons.collections4.functors.IfClosure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class HomePage extends BasePage {

	@FindBy(xpath = "(//a/div/p)[1]") // Welcome xpath=User Name in home page
	public WebElement welcomeHeader;
	@FindBy(id = "sc1")
	public WebElement sq1Label;
	@FindBy(name = "answer")
	public WebElement sqanswer1;
	@FindBy(id = "sc2")
	public WebElement sq2Label;
	@FindBy(name = "answer2")
	public WebElement sqanswer2;

	@FindBy(xpath = "//input[@value='Login']")
	public WebElement loginButton;

	@FindBy(xpath = "//input[@type='submit']")
	public WebElement SubmitsecurityQButton;

	List<String> sq = new ArrayList<>();

	public void getSecQuestions() {
		waitForElementVisibilityWithPollingTime(welcomeHeader);
		String question1 = sq1Label.getText();
		sq.add(question1);
		String question2 = sq2Label.getText();
		sq.add(question2);
	}

	String squestion;

	

	public boolean checkWelcomeUserid(String usertype) {
		waitForElementVisibilityWithPollingTime(welcomeHeader);
		boolean flag = false;
		String val = welcomeHeader.getText().trim();
		if (val.equals(usertype)) {
			flag = true;

		} else {
			flag = false;
		}
		return flag;
	}


	@FindBy(id = "menu") 
	public WebElement menu;

	@FindBy(xpath ="//a[@class='dropdown-toggle' and contains(text(), 'Services')]")
	public WebElement servicesTab;

	@FindBy(xpath ="//a[@target = '_self' and contains(text(), 'Business Credit Reports')]")
	public WebElement bcrMenu;

	@FindBy(xpath ="//a[@target = '_self' and contains(text(), 'Consumer Credit Reports')]")
	private WebElement ccrMenu;

	@FindBy(linkText = "Order Report")
	public WebElement orderReport;

	@FindBy(xpath = "//span[.='Company Search Results']")
	public WebElement bcirMenu;

	@FindBy(xpath ="//a[@class='dropdown-toggle' and contains(text(), 'Reports')]") 
	public	WebElement reportsTab;

	@FindBy(linkText = "Usage Report") 
	public WebElement usageReport;

	@FindBy(xpath ="//li[contains(@class, 'breadcrumb-item')]/span[text()='Usage Report']")
	private WebElement usageReportBreadCrumb;

	@FindBy(xpath = "//a[contains(text(),'Administration')]") 
	public WebElement	adminitrationTab;

	@FindBy(xpath = "//a[contains(text(),'Customers')]") 
	public WebElement customersTab;

	@FindBy(xpath ="//a[contains(text(),'Details') and contains(@href,'customer')]")
	public	WebElement customerDetailsTab;

	//Admin>>xactus
	@FindBy(xpath = "//a[contains(text(),'xactus')]")
	public WebElement	xactus;
	
	@FindBy(xpath = "//a[@href='/xactususers']")
    public WebElement xactususersTab;

//	@FindBy(xpath = "//a[contains(text(),'Users')]")
//	public WebElement	xactusUsers;
	
	@FindBy(xpath = "//a[@href='/xactusdetails']")
    public WebElement xactusdetailsTab;

	//ServiceProvider
	@FindBy(xpath = "//a[contains(@href,'serviceProviders')]")
	public WebElement	serviceProviderTab;

	//RateSheet
	@FindBy(xpath = "//a[contains(@href,'rateSheet')]")
	public WebElement	rateSheetTab;

	//BIllingReport
	@FindBy(xpath = "//a[contains(@href,'billingreport')]")
	public WebElement	billingReportTab;

	//Volume report
	@FindBy(xpath = "//a[contains(@href,'volume')]")
	public WebElement	volumeReportTab;

	//Global Setup
	@FindBy(xpath="//a[contains(text(),'Global Setup')]")
	public WebElement	globalSetUpSubMenu;

	//Mgt Report Tab
	@FindBy(xpath ="//a[ contains(text(), 'Management')]")//(//a[contains(text(),'Management')])[1]
	public WebElement managementTab;

	@FindBy(xpath = "//a[ contains(text(), 'Management Reports')]")
	public	WebElement managementReportsTab;


	@FindBy(xpath = "//p[contains(text(),'Credit Plus')]") 
	public WebElement	creditPlusLinkRight;

	
	//About
	@FindBy(xpath = "//a[contains(text(),'About')]")
	public WebElement aboutLink;

	
	//Myprofile
	@FindBy(xpath = " //a[contains(text(),'My Profile')]")
	public WebElement	myProfileLink;
	
	//Admins Navigation Web elements
	@FindBy (xpath="//a[@class='dropdown-toggle']")
	public WebElement NavigatetoAdministration;

	@FindBy (xpath="//a[contains(text(),'Customers')]")
	public WebElement NavigateAdministrationCustomer;

	@FindBy (xpath="//a[contains(text(),'Users')]")
	public WebElement ClickCustomerUsers ;
	
	@FindBy (xpath="//div/button[@id='tooltip-target-id']")
	public WebElement ClickonCreateUserButtton;
	

	//Navigating to xactus Users Page
	
	
	public void navigateToxactusUsers() {

		waitForElementVisibilityWithPollingTime(adminitrationTab);

		waitForInvisibilityOfElement(By.className("dropdown-menu py-0 dropright RootMenu_child_1Dm0R RootMenu_subMenuParent_2tHqw")
				); waitforpagetoload(); Actions action = new Actions( driver );
				Logs.info("Mouse hovering on the Administration Tab");
				action.moveToElement(adminitrationTab ).perform();
				action.moveToElement(xactus).perform();
				action.moveToElement(xactususersTab).click().perform();
	}
	
	//Navigation to Customer Users
	public void navigateToCustomerUsers() {

		waitForElementVisibilityWithPollingTime(NavigatetoAdministration);

		waitForInvisibilityOfElement(By.className("dropdown-menu py-0 dropright RootMenu_child_1Dm0R RootMenu_subMenuParent_2tHqw")
				); waitforpagetoload(); Actions action = new Actions( driver );
				Logs.info("Mouse hovering on the Administration Tab");
				action.moveToElement(NavigatetoAdministration ).perform();
				action.moveToElement(NavigateAdministrationCustomer).perform();
				
				action.moveToElement(ClickCustomerUsers).click().perform();
	}

	public void clickCreateCustomerUser()
	{ 
		waitforpagetoload(); 
		waitForElementVisibilityWithPollingTime(ClickonCreateUserButtton);
	Actions  actionBuilder = new Actions(driver);
	actionBuilder.click(ClickonCreateUserButtton).build().perform();
	waitforpagetoload(); 
	}



	public HomePage(WebDriver paramDriver) { driver = paramDriver;
	PageFactory.initElements( driver, this ); }

	public boolean checkMenuPresent() { waitForElementVisibilityWithPollingTime(
			menu ); return SeleniumUtils.isElementPresent( menu , ConfigConstants.home );
	}

	WebElement managementReportElement; String managementReportEleString;


	//For AMS

	/* @FindBy(xpath = "//a[contains(text(),'Account Monitoring')]") public
	WebElement Account_Monitoring_Tab;

	@FindBy(xpath =
			"//a[contains(text(),'Account Monitoring') and contains(@href,'account')]")
	public WebElement Account_Monitoring_ServiceTab;

	//For Monitor service

	@FindBy(xpath =
			"//a[contains(text(),'Monitoring Services') and contains(@href,'/monitoringservices')]"
			) public WebElement monitoringservicesTab;

	 */
	/* public Object navigateToReport(String reportType) {
	 * waitForElementVisibilityWithPollingTime( servicesTab );
	 * 
	 * Actions action = new Actions( driver );
	 * 
	 * Logs.info("Mouse hovering on the Services Tab"); action.moveToElement(
	 * servicesTab ).perform();
	 * 
	 * Logs.info("Mouse hovering on " + reportType + "  from Services Tab"); switch
	 * (reportType) {
	 * 
	 * case "BCR": waitForElementVisibilityWithPollingTime( bcrMenu );
	 * action.moveToElement( bcrMenu ).perform();
	 * 
	 * Logs.info("Clicking on the Order Report sub menu"); action.moveToElement(
	 * orderReport ).click().perform(); // return new BCROrderPage(driver);
	 * 
	 * case "CCR": waitForElementVisibilityWithPollingTime( ccrMenu );
	 * action.moveToElement( ccrMenu ).perform();
	 * 
	 * Logs.info("Clicking on the Order Report sub menu"); action.moveToElement(
	 * orderReport ).click().perform(); //return new CCROrderPage( driver );
	 * 
	 * 
	 * 
	 * default: throw new AutomationException( "Order type is invalid" ); } }
	 *//**
	 * Method for navigates to Orders reports tab from serviceTab
	 */
	/*
	 * 
	 * public void navigateToOrderPage() { waitForElementVisibilityWithPollingTime(
	 * servicesTab );
	 * waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq")
	 * ); waitforpagetoload(); Actions action = new Actions( driver );
	 * Logs.info("Mouse hovering on the Services Tab"); action.moveToElement(
	 * servicesTab ).perform(); Logs.info("Clicking on the Order Report option");
	 * waitForElementVisibilityWithPollingTime( orderReport );
	 * this.orderReport.click(); }
	 * 
	 *//**
	 * Method for navigates to Customer details from Administration
	 */
	/*
	 * 
	 * public void navigateToCustomerDetails() {
	 * waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq")
	 * ); waitforpagetoload();
	 * waitForElementVisibilityWithPollingTime(adminitrationTab);
	 * waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq")
	 * ); Actions action = new Actions( driver );
	 * Logs.info("Mouse hovering on the Administration Tab");
	 * action.moveToElement(adminitrationTab ).perform();
	 * action.moveToElement(customersTab).perform();
	 * action.moveToElement(customerDetailsTab).click().perform();
	 * 
	 * 
	 * }
	 * 
	 * 
	 *//**
	 * Method for navigates to Usage reports from Reports tab
	 */
	/*
	 * 
	 * public void navigateToReports(String reportType) {
	 * waitForElementVisibilityWithPollingTime(reportsTab);
	 * waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq")
	 * ); waitforpagetoload(); Actions action = new Actions( driver );
	 * Logs.info("Mouse hovering on the Reports Tab");
	 * action.moveToElement(reportsTab).perform();
	 * 
	 * switch (reportType) {
	 * 
	 * case "Usage Report": Logs.info("Clicking on the Usage Report option");
	 * waitForElementVisibilityWithPollingTime(usageReport); usageReport.click();
	 * Logs.info("Clicked on the Usage Report option"); break; case
	 * "Billing Report": Logs.info("Clicking on the Billing Reportt option");
	 * waitForElementVisibilityWithPollingTime(billingReportTab);
	 * billingReportTab.click(); Logs.info("Clicked on the Billing Report option");
	 * break; case "Volume Report":
	 * Logs.info("Clicking on the Volume Report option");
	 * waitForElementVisibilityWithPollingTime(volumeReportTab);
	 * volumeReportTab.click(); Logs.info("Clicked on theVolume Reportoption"); } }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 * public void navigateToCreditPlusLink() {
	 * 
	 * waitForElementVisibilityWithPollingTime(adminitrationTab);
	 * 
	 * waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq")
	 * ); waitforpagetoload(); Actions action = new Actions( driver );
	 * Logs.info("Mouse hovering on the CreditPlus Link");
	 * action.moveToElement(creditPlusLinkRight ).perform();
	 * action.moveToElement(creditPlusLinkRight).click().perform();
	 * action.moveToElement(aboutLink).click().perform();
	 * 
	 * }
	 * 
	 * public void navigateToMyProfile() {
	 * waitForElementVisibilityWithPollingTime(adminitrationTab);
	 * 
	 * waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq")
	 * ); waitforpagetoload(); Actions action = new Actions( driver );
	 * Logs.info("Mouse hovering on the CreditPlus Link");
	 * action.moveToElement(creditPlusLinkRight ).perform();
	 * action.moveToElement(creditPlusLinkRight).click().perform();
	 * action.moveToElement(myProfileLink).click().perform(); }
	 * 
	 * public void navigateToGlobalSetUp_TaxSetUpTab() {
	 * waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq")
	 * ); waitForElementVisibilityWithPollingTime( adminitrationTab );
	 * waitforpagetoload(); Actions action = new Actions( driver );
	 * Logs.info("Mouse hovering on the Administration Tab"); waitforpagetoload();
	 * action.moveToElement( adminitrationTab ).perform();
	 * Logs.info("Clicking on the GlobalSetup sub menu"); waitforpagetoload();
	 * waitForElementVisibilityWithPollingTime( globalSetUpSubMenu );
	 * waitforpagetoload(); this.globalSetUpSubMenu.click(); }
	 *//**
	 * MEthod Navigates to Service provider by clicking service provider from
	 * Administration tab
	 */
	/*
	 * 
	 * public void navigateToServiceProviderTab() {
	 * waitForElementVisibilityWithPollingTime(adminitrationTab);
	 * waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq")
	 * ); waitforpagetoload(); Actions action = new Actions( driver );
	 * Logs.info("Mouse hovering on the Administration Tab");
	 * action.moveToElement(adminitrationTab ).perform();
	 * action.moveToElement(serviceProviderTab).click().perform();
	 * waitforpagetoload(); }
	 *//**
	 * Method for navigates to Rate sheet details from Administration
	 */
	/*
	 * 
	 * public void navigateToRateSheet() {
	 * waitForElementVisibilityWithPollingTime(adminitrationTab);
	 * waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq")
	 * ); waitforpagetoload(); Actions action = new Actions( driver );
	 * Logs.info("Mouse hovering on the Administration Tab");
	 * action.moveToElement(adminitrationTab ).perform();
	 * action.moveToElement(rateSheetTab).click().perform();
	 * 
	 * }
	 * 
	 *//**
	 * Method for navigates to Usage reports from Reports tab
	 */
	/*
	 * 
	 * public void navigateToManagementReports(String reportType) {
	 * waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq")
	 * ); waitforpagetoload(); Actions action = new Actions( driver );
	 * Logs.info("Mouse hovering on the Management Reports Tab");
	 * waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq")
	 * ); action.moveToElement(managementTab).click().perform();
	 * action.moveToElement(managementReportsTab).click().perform();
	 * waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq")
	 * ); //waitforpagetoload();
	 * 
	 * if(reportType.equals("Customers")) {
	 * managementReportElement=driver.findElement(By.xpath(
	 * "(//li/a[contains(text(),'"+reportType+"')])[2]")); }else
	 * if(reportType.equals("Users")){
	 * managementReportElement=driver.findElement(By.xpath(
	 * "(//li/a[contains(text(),'"+reportType+"')])[3]")); }else {
	 * managementReportElement=driver.findElement(By.xpath(
	 * "//li/a[contains(text(),'"+reportType+"')]")); } switch (reportType)
	 * {//li/a[contains(text(),'Activity Report')]
	 * 
	 * case "Activity Report": Logs.info("Clicking on the Activity Report option");
	 * waitForElementVisibilityWithPollingTime(managementReportElement);
	 * managementReportElement.click();
	 * Logs.info("Clicked on the Activity Report option"); break; case
	 * "Score Report": Logs.info("Clicking on the Score Report option");
	 * waitForElementVisibilityWithPollingTime(managementReportElement);
	 * managementReportElement.click();
	 * Logs.info("Clicked on the Score Report option"); break; case
	 * "Transaction Report": Logs.info("Clicking on the Transaction Report option");
	 * waitForElementVisibilityWithPollingTime(managementReportElement);
	 * managementReportElement.click();
	 * Logs.info("Clicked on the Transaction Report option"); break; case
	 * "Customers": Logs.info("Clicking on the Customers option");
	 * waitForElementVisibilityWithPollingTime(managementReportElement);
	 * managementReportElement.click();
	 * Logs.info("Clicked on the Customers option"); break; case "Users":
	 * Logs.info("Clicking on the Users option");
	 * waitForElementVisibilityWithPollingTime(managementReportElement);
	 * managementReportElement.click(); Logs.info("Clicked on the Users option");
	 * break;
	 * 
	 * } }
	 *//**
	 * Method for navigates to Account monitoring service from serviceTab
	 */
	/* public void navigateToAMS() { waitForElementVisibilityWithPollingTime(
	 * servicesTab );
	 * waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq")
	 * ); waitforpagetoload(); Actions action = new Actions( driver );
	 * Logs.info("Mouse hovering on the Services Tab"); action.moveToElement(
	 * servicesTab ).perform(); Logs.info("Clicking on the Order Report option");
	 * waitForElementVisibilityWithPollingTime( Account_Monitoring_Tab );
	 * this.Account_Monitoring_Tab.click(); action.moveToElement(
	 * Account_Monitoring_ServiceTab ).click().perform(); }
	 * 
	 * public void navigateToMonitoringServices() {
	 * 
	 * waitForElementVisibilityWithPollingTime(adminitrationTab);
	 * 
	 * waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq")
	 * ); waitforpagetoload(); Actions action = new Actions( driver );
	 * Logs.info("Mouse hovering on the Administration Tab");
	 * action.moveToElement(adminitrationTab ).perform();
	 * action.moveToElement(monitoringservicesTab).click().perform();
	 * 
	 * 
	 * 
	 * }
	 * 
	 * // Customer user
	 * 
	 * @FindBy(xpath =
	 * "//a[contains(text(),'Users') and contains(@href,'manageusers')]") public
	 * WebElement customerUsersTab;
	 * 
	 * public void navigateToCustomerUser() {
	 * waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq")
	 * ); waitforpagetoload();
	 * waitForElementVisibilityWithPollingTime(adminitrationTab);
	 * waitForInvisibilityOfElement(By.className("_base-spinner_spnrPosition_2XcEq")
	 * ); Actions action = new Actions(driver);
	 * Logs.info("Mouse hovering on the Administration Tab");
	 * action.moveToElement(adminitrationTab).perform();
	 * action.moveToElement(customersTab).perform();
	 * action.moveToElement(customerUsersTab).click().perform();
	 * Logs.info("Clicked on USers tab"); }
	 */

}
