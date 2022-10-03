package com.HMDA.testcases;


import java.util.Map;

import org.testng.annotations.Test;

import com.HMDA.baseclass.BaseTest;
import com.HMDA.pages.HomePage;
import com.HMDA.pages.HMDACustomerUser;

public class CreateCustomerUser extends BaseTest{
		
		@Test(dataProvider = "CreateCustomerUserTestData", alwaysRun = true, groups = { "Smoke" })
		public void createUser(Map<String, Object> testData)
		{
			HomePage homePage =	loginPage.loginAsUser( "HMDAAdmin");
			
			homePage.navigateToCustomerUsers();
			
			CustomerHMDAUser.SelectCustomer();
			
			homePage.clickCreateCustomerUser();
			
			CustomerHMDAUser.enterCustomerUserDetails((String)testData.get("FirstName"),(String)testData.get("Lastname"),(String)testData.get("Phone"),(String)testData.get("Customer"),
			(String)testData.get("EmailAddress"),(String)testData.get("Role"));
			
			CustomerHMDAUser.clickCustomerUserSaveButton();
						
		}
}
