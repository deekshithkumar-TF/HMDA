package com.HMDA.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.HMDA.constants.ApplicationConstants;


/**
 * @author Shalyaj Rishi
 *
 */


public class CommonUtils {
	
	//This method will return the current year date in MM/dd/YYYY format
	public static String getCurrentYearDate(){
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/YYYY");
		Calendar cal = Calendar.getInstance();
		return format.format(cal.getTime());
	}

	//This method will return the previous year date in MM/dd/YYYY format
	public static String getPreviousYearDate(){
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/YYYY");
		Calendar cal = Calendar.getInstance();
		cal.add( Calendar.YEAR, -1);
		return format.format(cal.getTime());
	}

	//This method will return the future year date in MM/dd/YYYY format
	public static String getFutureYearDate(){
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/YYYY");
		Calendar cal = Calendar.getInstance();
		cal.add( Calendar.YEAR, +1);
		return format.format(cal.getTime());
	}

	//This method will generate 10 digit random no. starting with 9.This method can be used to enter mobile no.
	public static Long generateMobileNumber(){
		Random rnd = new Random();
		long number= rnd.nextInt(999999999);
		number = number+9000000000L;
		return number;
	}

	//This method will generate random string of length 8 characters.
	/*public static String generateRandomString(){
		StringBuilder randStr = new StringBuilder();
		Random rnd = new Random();
		while (randStr.length()<Constants.randomStringLength) {
			int index=(int)(rnd.nextFloat()*randStr.length());
			randStr.append(Constants.sCharList.charAt(index));
		}
		return randStr.toString();
	}*/

	//This method will generate random string of length 6 characters.
    public static String generateShortRandomString(){
        StringBuilder randStr = new StringBuilder();
        Random rnd = new Random();
        while (randStr.length()<ApplicationConstants.randomShortStringLength) {
            int index=(int)(rnd.nextFloat()*randStr.length());
            randStr.append(ApplicationConstants.scharlist.charAt(index));
        }
        return randStr.toString();
    }

	//This method will be used to generate the credit score within the range. 
	//User needs to pass the min and max range within which the credit score needs to be generated.
	public static int getRandomCreditScoreWithinRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	//This method will generate random string of length 12 characters.
	/*public static String generateLongRandomString(){
		StringBuilder randStr = new StringBuilder();
		Random rnd = new Random();
		while (randStr.length()<Constants.randomLongStringLength) {
			int index=(int)(rnd.nextFloat()*randStr.length());
			randStr.append(Constants.sCharList.charAt(index));
		}
		return randStr.toString();
	}*/

	//This method will return the current system date and time till seconds.
	//Basically this method can be used where some random data is required to be entered.
	public static String getCurrentSystemDateTime(){
		SimpleDateFormat format = new SimpleDateFormat("_YYYY-MM-dd-HH-mm-ss");
		Calendar cal = Calendar.getInstance();
		return format.format(cal.getTime());
	}

	//This method will return the current system date and time till seconds of the future year.
	//Basically this method can be used where some random data is required to be entered.
	public static String getCurrentSystemDateTimeFutureYear(){
		SimpleDateFormat format = new SimpleDateFormat("ddMMYYYYHHmmss");
		Calendar cal = Calendar.getInstance();
		cal.add( Calendar.YEAR, +1);
		return format.format(cal.getTime());
	}

	//This method will return the current system date and time till seconds of the previous year.
	//Basically this method can be used where some random data is required to be entered.
	public static String getCurrentSystemDateTimePreviousYear(){
		SimpleDateFormat format = new SimpleDateFormat("ddMMYYYYHHmmss");
		Calendar cal = Calendar.getInstance();
		cal.add( Calendar.YEAR, -1);
		return format.format(cal.getTime());
	}

	//This method will generate a random no. based on the runtime count and select any one option within that limit
	public static int setDropDownLimit(int limit){
		Random rnd = new Random();
		if(limit==0){
			limit++;
		}
		return rnd.nextInt(limit);
	}

   //This method will generate 2 digits random no.
	public static int generateTwoDigitNo(){
		Random rnd = new Random();
		return rnd.nextInt(99);
	}

	//This method will generate 11 digits random no.
	public static Long generateElevenDigitRandomOddNo(){
		Random rnd = new Random();
		long number = rnd.nextInt(999999999);
		long longNumber= number*100;
		longNumber++;
		return longNumber;
	}
	public static String getCurrentDate() {
		return getCurrentSystemDateTime().substring(0, 11);
	}

	//This method will generate current system date in specific format as passed by the user.
	public static String getSystemDateInFormat(String format){
		DateFormat dateFormater = new SimpleDateFormat(format);
		Date date = new Date();
		return dateFormater.format(date);
	}

	//This method will generate previous month system date in specific format as passed by the user.
	public static String getPreviousMonthDateInFormat(String format){
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.add( Calendar.DATE, -61);
		return dateFormat.format(cal.getTime());
	}

	// This method will convert Set to List. User needs to pass the Set object for
	// conversion to List.
	public static ArrayList<String> castToList(Set<String> sWindows) {
		ArrayList<String> arraylist = new ArrayList<>( sWindows );
		return arraylist;
	}


}
