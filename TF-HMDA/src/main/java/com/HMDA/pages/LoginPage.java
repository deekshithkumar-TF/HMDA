package com.HMDA.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Parameters;

import com.HMDA.baseclass.BasePage;
import com.HMDA.baseclass.BaseTest;
import com.HMDA.constants.ConfigConstants;
import com.HMDA.helper.SeleniumUtils;
import com.HMDA.reports.Logs;

public class LoginPage extends BasePage {



	@FindBy(css = "input[id='username']") private WebElement userName;

	@FindBy(name = "password") private WebElement password;

	@FindBy(xpath = "//button[@type='submit']") private WebElement loginButton;

	@Parameters({"baseURL"}) public LoginPage(WebDriver paramDriver , String
			baseURL) { driver = paramDriver; PageFactory.initElements(driver, this);
			driver.manage().window().maximize(); driver.get(baseURL);
			paramDriver.manage().timeouts().pageLoadTimeout(ConfigConstants.implicitWait,
					TimeUnit.SECONDS); driver.manage().deleteAllCookies(); }

	public HomePage  loginAsUser(String userType) {

		retrieveConfigParameteres();

		Logs.info("Entering the username"); SeleniumUtils.setText(userName,
				prop.getProperty(userType));

		Logs.info("Entering the password"); SeleniumUtils.setText(password,
				prop.getProperty("UserPassword"));

		Logs.info("Clicking on the Submit button"); loginButton.click();
		return new HomePage (driver) ;
		 

	}

}
