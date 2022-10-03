package com.HMDA.testcases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.HMDA.baseclass.BaseTest;
import com.HMDA.constants.ApplicationConstants;
import com.HMDA.helper.DataBaseUtils;
import com.HMDA.reports.Logs;

public class InteractiveCreditReport extends BaseTest {

	@Test(dataProvider = "CompReportTestData", alwaysRun = true, groups = { "Smoke" })
	public void verifyCompareReport(Map<String, Object> testData) throws IOException {
		try {
			reportLogger("Launch the application and log in as ");
			homePage = loginPage.loginAsUser((String) testData.get("Customer"));
			reportLogger("Navigate to the Order Report Page and get Security Questions");
			homePage.getSecQuestions();
			reportLogger("Select the Security questions ");
			
			
			
		} catch (Exception e) {
			reportImage("verifyCCRReportGeneration");
			System.out.println(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}
}
