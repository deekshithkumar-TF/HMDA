package com.HMDA.testcases;

import com.HMDA.baseclass.BaseTest;
import com.HMDA.pages.HomePage;
import com.HMDA.pages.LoginPage;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

public class LoginTestCases extends BaseTest {

	@Test(groups = { "Smoke" })

	public void loginInAsHMDAAdmin() {
		reportLogger("Launch the application and log in as HMDA Admin"); 
		HomePage homePage =	loginPage.loginAsUser( "HMDAAdmin");

		reportLogger( "Verify that user has logged in successfully!" );
		assertTrue( homePage.checkWelcomeUserid("HMDA Admin") ); }

	@Test(groups = { "Smoke" }) 
	public void loginInAsLenderHMDAAdmin() {
		reportLogger( "Launch the application and log in as Lender HMDA Admin");
		HomePage homePage = loginPage.loginAsUser( "LenderHMDAAdmin");

		reportLogger( "Verify that user has logged in successfully" ); 
		assertTrue(homePage.checkWelcomeUserid("LenderHMDA Admin") ); 
		
	}
	@Test(groups = { "Smoke" })

	public void loginInAsLenderHMDAProcessor() {
		reportLogger("Launch the application and log in as HMDA Admin"); 
		HomePage homePage =	loginPage.loginAsUser( "LenderHMDAProcessor");

		reportLogger( "Verify that user has logged in successfully!" );
		assertTrue( homePage.checkWelcomeUserid("Lender Processor") ); }

	@Test(groups = { "Smoke" }) 
	public void loginInAsLenderHMDAManager() {
		reportLogger( "Launch the application and log in as Lender HMDA Admin");
		HomePage homePage = loginPage.loginAsUser( "LenderHMDAManager");

		reportLogger( "Verify that user has logged in successfully" ); 
		assertTrue(homePage.checkWelcomeUserid("Lender Manager") ); 
	
	}
	@Test
	
	public void LoginasxactusHMDAManager()

	{
		reportLogger("Login as xactus HMDA Manager");
		HomePage homePage = loginPage.loginAsUser("xactusHMDAManager");
		
		reportLogger("verify that user has logged in successfully");		
		assertTrue(homePage.checkWelcomeUserid("HMDA Manager"));
		
	}
}
