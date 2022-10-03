package com.HMDA.reports;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.HMDA.constants.ConfigConstants;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class GetScreenShot {
	public static File destination ;
	public static String capture(WebDriver driver, String screenShotName) throws IOException {
		
		String timestamp = new SimpleDateFormat("MM_dd_yyyy_hh_mm_ss").format(new Date());
		Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000)).takeScreenshot(driver);
//		Screenshot fpScreenshot = new AShot().takeScreenshot(driver);
		BufferedImage image = fpScreenshot.getImage();
		String dest = com.HMDA.constants.ConfigConstants.outputDir + "Screenshots" + File.separator + screenShotName+ ".png";
		//File destination = new File("D:\\CPProject\\ThoughtFocus_CreditServices_NewUITest\\test-output\\" + "Screenshots" + File.separator +screenShotName + timestamp + ".png");
		 destination = new File(dest);
		ImageIO.write((RenderedImage) image, "PNG", destination);

		try {
			Alert alert = driver.switchTo().alert();
			Reporter.log("Unexpected Alert Encountered: " + alert.getText());
			alert.accept();
		}

		catch (Exception e) {
			Reporter.log("No Unwanted Alert Encountered");
		}
		
		return destination.getAbsolutePath();
	}
	
	public static String shootPage(WebDriver driver, String screenShotName) throws IOException {		
		String timestamp = new SimpleDateFormat("MM_dd_yyyy_hh_mm_ss").format(new Date());			
		BufferedImage image = Shutterbug.shootPage(driver, Capture.FULL_SCROLL).getImage();
	    String dest = com.HMDA.constants.ConfigConstants.outputDir + "Screenshots" + File.separator + screenShotName + timestamp + ".png";
		File destination = new File(dest);
		ImageIO.write((RenderedImage) image, "PNG", destination);    
	        
		try {
			Alert alert = driver.switchTo().alert();
			Reporter.log("Unexpected Alert Encountered: " + alert.getText());		
			alert.accept();
		}

		catch (Exception e) {
			Reporter.log("No Unwanted Alert Encountered");
		}
		
		return destination.getAbsolutePath();
	}
	
	

}
