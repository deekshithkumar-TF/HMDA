package com.HMDA.testcases;

import java.util.Map;

import org.testng.annotations.Test;

import com.HMDA.baseclass.BaseTest;
import com.HMDA.pages.CreateProjectPage;
import com.HMDA.pages.HomePage;

public class CreateProject extends BaseTest {
	
	

	@Test(dataProvider = "CreateProject", alwaysRun = true, groups = { "Smoke" })
	public void createUser(Map<String, Object> testData)
	{
		HomePage homePage =	loginPage.loginAsUser( "LenderHMDAAdmin");
		
		reportLogger( "Verify that user has logged in successfully!" );
		
		CreateProject.clickonCreateProject();
		
		CreateProject.EnterProjectDetails((String)testData.get("ProjectName"),(String)testData.get("Description"));
		
		CreateProject.clickCreatProjectButton();
		
		
		
		
		
		
	}
		

}
