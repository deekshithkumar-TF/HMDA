package com.HMDA.testcases;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.HMDA.baseclass.BaseTest;
import com.HMDA.helper.CommonUtils;
import com.HMDA.helper.DataBaseUtils;
import com.HMDA.pages.CreateXactusUserPage;
import com.HMDA.pages.HomePage;
import com.HMDA.reports.Logs;

public class CreateHMDAUser extends BaseTest {
	
	@Test(dataProvider = "CreateHMDAUserTestData", alwaysRun = true, groups = { "Smoke" })
	public void createUser(Map<String, Object> testData)
	{
		HomePage homePage =	loginPage.loginAsUser( "xactusAdmin");
		
		homePage.navigateToxactusUsers();
		
		
		
		CreateHMDAUser.clickCreatexactusIcon();
		
		CreateHMDAUser.enterxactusUserDetails((String)testData.get("FirstName"),(String)testData.get("Lastname"),(String)testData.get("Phone"),(String)testData.get("Customer"),
		(String)testData.get("EmailAddress"),(String)testData.get("Role"));
		
		CreateHMDAUser.clickxactusUserSaveButton();
		
		
		
		
	}
	

}
