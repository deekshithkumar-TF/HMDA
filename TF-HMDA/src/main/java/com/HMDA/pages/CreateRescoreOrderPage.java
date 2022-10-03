package com.HMDA.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import com.HMDA.baseclass.BasePage;
import com.HMDA.helper.SeleniumUtils;
import com.HMDA.reports.Logs;

public class CreateRescoreOrderPage extends BasePage {

	Select select;
	//From here update rescore elements 
  
    
    @FindBy(xpath="//div[contains(@class,'_base-label_label' )] [contains(text(),'Rescore')]")
    public WebElement createReScoreHeaderText;
    
    @FindBy(xpath="//input[@name='customer']")
    public WebElement customerName;
    
    @FindBy(xpath="(//div[contains(@class,'justify-content-between')])[2]/span")
    public WebElement orderIdRescore;
    
    @FindBy(xpath="//table[@id='tradelineTable']/tbody/tr/td/div/th/div/div/input")
    public WebElement tradeLincheckbox;
    
    @FindBy(xpath="//input[@type='checkbox']/../following::label")
    public List<WebElement> listTradeLincheckboxs;
    @FindBy(xpath="//input[@type='checkbox']/../following::label/preceding-sibling::input")
    public List<WebElement> listTradeLinecheckboxs;
  
    @FindBy(xpath="(//div[contains(@class,'justify-content-between')])[2]/span")
    public WebElement unMergedcheckbox;
    
    @FindBy(xpath="//input[@type='checkbox']/parent::div")
    public WebElement tradelineAccuntNo;
  
    @FindBy(xpath="//*[@id='tradelineTable']/tbody/tr/td/div[1]/div/div[2]/div")
    public List<WebElement> listTradeLineAccountNo;
    @FindBy(xpath="//table[@class='tradeline-group-table']/tbody/tr/following::div/tr/td/div/div/input")
    public List<WebElement> listTradeLineRawLine;
  
    @FindBy(xpath="//textarea")    
    public WebElement rescoreComment;
    @FindBy(xpath="//div[contains(text(),'Reasons')]/following-sibling::div/div/input")    
    public WebElement reasons;
    
    @FindBy(xpath="//div[contains(text(),'Update Balance')]")    
    public WebElement UpdateBalance;
    @FindBy(xpath="//div[contains(text(),'Update Status')]")    
    public WebElement UpdateStatus;
    @FindBy(xpath="//div[contains(text(),'Remove Lates')]")    
    public WebElement  RemoveLates;
    @FindBy(xpath="//div[contains(text(),'Delete Accoun')]")    
    public WebElement  DeleteAccount;
    @FindBy(xpath="//div[contains(text(),'Remove Disputes')]")    
    public WebElement  RemoveDisputes;
    
    @FindBy(xpath="//button[@name='submit']")    
    public WebElement  submit;
    @FindBy(xpath="//div[@class='modal-body']")    
    public WebElement  modalWidowText;
    @FindBy(xpath="//button[text()='OK']")    
    public WebElement  modalWidowOkButton;
  
  
	public CreateRescoreOrderPage(WebDriver paramDriver) {
		driver = paramDriver;
		PageFactory.initElements(driver, this);
	}

	public String verifyHeaderTextCreateRescorePage() {
		waitforpagetoload();
		waitForElementVisibilityWithPollingTime(createReScoreHeaderText);
		String text=SeleniumUtils.getText_v(createReScoreHeaderText);
		try {
			Assert.assertEquals("Create Rescore Order", text.trim());
		}catch (Exception e) {
			Assert.fail("Header text Not matching");
		}
		return text;
	}
	
	public void validateOrderIdCreateRescorePage(String OrderId) {
		waitforpagetoload();
		String IdInRescore=SeleniumUtils.getText_v(orderIdRescore);
		try {
		Assert.assertEquals(OrderId, IdInRescore.trim());
		}catch (Exception e) {
			Assert.fail("Rescore Order ID Not matching");
		}
	}

	public int  validateTotalTradeLins() {
		int size= listTradeLincheckboxs.size();
		return size;
		
	}
	List<String> acc;
	public List<String> getAccountNumbers() {
		acc= new ArrayList<String>();
		//String accNo=SeleniumUtils.getText_v(tradelineAccuntNo);
		//String[] aNo=accNo.split("#");
		//Logs.info("Account number "+aNo[1]);
		
		for (int i = 0; i < listTradeLineAccountNo.size(); i++) {
			String acc_No=listTradeLineAccountNo.get(i).getText();
		//if(!acc_No.equals("Rush")){
			String[] a_No=acc_No.split("#");
			Logs.info("Account number "+a_No[1]);
			acc.add(a_No[1].trim());
			//}else{Logs.info("");}
			
			
			
			
			
		}
		Collections.sort(acc);
		Logs.info("Acccount Number From UI "+acc);
		return acc;
	}
	
	public void clickTradelineCheckbox() {
		SeleniumUtils.clickElement(tradeLincheckbox);
	}
	
	public void selectRescoreRawBureauLine(int listTradeLine,String comments) {
		for (int i = 0; i < listTradeLine; i++) {
			Actions act=new Actions(driver);
			act.moveToElement(listTradeLinecheckboxs.get(i)).click().build().perform();
			act.moveToElement(listTradeLineRawLine.get(0)).click().build().perform();
			//act.moveToElement(SupplementComment.get(i)).sendKeys(Comments).build().perform();
			SeleniumUtils.setText(rescoreComment, comments);
			Logs.info("selected Trade lines " );
		}
		
	}
	
	public void selectReasions(String reasons) {
		String []reason=reasons.split(",");
		for (String reasontext : reason) {
					
		if(reasontext.equalsIgnoreCase("Delete Account")) {
			DeleteAccount.click();
		}else if(reasontext.equalsIgnoreCase("RemoveDisputes")) {
			RemoveDisputes.click();
		}else if(reasontext.equalsIgnoreCase("UpdateBalance")) {
			UpdateBalance.click();
			
		}else if(reasontext.equalsIgnoreCase("UpdateStatus")) {
			UpdateStatus.click();
			
		}else  {
			RemoveLates.click();
		}
		}
	}
	
	public void clickSubmit() {
		Actions act=new Actions(driver);
		scrollIntoView(submit);
		act.moveToElement(submit).click().build().perform();
		//SeleniumUtils.clickElement(submit);
	}
	public void handleAlert() {
		String alertText=handleAlertTextANDAccept();
		String [] num=alertText.split(" ");
		Logs.info("Rescore Request Number Popup "+num[0]);
	}
	
	public String getRescoreRequestNumber() {
		//parentWindow(driver);
		//switchToChildWindow(driver);
		String altText =SeleniumUtils.getText_v(modalWidowText);
		
		String [] text=altText.split("#");
		String [] num=text[1].split(" ");
		Logs.info("Rescore Request Number Popup "+num[0].trim());
		//modalWidowText.getText()
		SeleniumUtils.clickElement(modalWidowOkButton);
		
		return num[0].trim();
	}
}
