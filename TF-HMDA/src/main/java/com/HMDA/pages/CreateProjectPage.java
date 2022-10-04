package com.HMDA.pages;

import org.bouncycastle.jcajce.provider.symmetric.ARC4.Base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.HMDA.baseclass.BasePage;
import com.HMDA.helper.SeleniumUtils;
import com.HMDA.reports.Logs;

public class CreateProjectPage extends BasePage {
	
	
	public CreateProjectPage (WebDriver paramDriver) { driver = paramDriver;
	PageFactory.initElements( driver, this ); }
	
	
	//HMDA HOME page
		@FindBy(xpath = "//div/h4[contains(text(),'create project')]")
		public WebElement CreateProject;
		
		@FindBy(xpath = "//input[@placeholder='Project Name']")
		public WebElement ProjectNameField;
		
		@FindBy(xpath ="//div/textarea[@class='form-control _base-textarea_selectbg_Ln2S-']")
		public WebElement DescriptionField;
		
		@FindBy (xpath ="//div/button[@type='submit']")
		public WebElement CreateButton;
		
		
		
		//Create Project
		
		public void clickonCreateProject()
		{
			waitForElementVisibilityWithPollingTime(CreateProject);
			Actions action = new Actions( driver );
			Logs.info("Mouse hovering on the CreateProject buttton");
			action.moveToElement(CreateProject ).perform();		
		}
		public void EnterProjectDetails(String ProjectName, String Description)
		{
			
			SeleniumUtils.setText(ProjectNameField, ProjectName);
			SeleniumUtils.setText(DescriptionField, Description);
		}
		
		public void clickCreatProjectButton()
		{ 
			
		Actions  actionBuilder = new Actions(driver);
		actionBuilder.click(CreateButton).build().perform();
		waitforpagetoload(); 
		}

}
