package com.HMDA.helper;

import com.HMDA.baseclass.BaseTest;
import com.HMDA.constants.PdfFieldConstants;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.WebDriver;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.asprise.ocr.Ocr;


public class PdfUtils extends BaseTest {

	WebDriver driver;
	public static String url = "https://stcpssdataqa001.blob.core.windows.net/ccr/2019/Dec/24/332_ConsumerCreditReport.pdf";
	public static StringBuilder textFromPdf;

	public static Map<String, String> orderDetails = new LinkedHashMap<>();
	public static Map<String, String> orderSubjectProperty = new LinkedHashMap<>();
	public static Map<String, String> orderBorrower = new LinkedHashMap<>();
	public static Map<String, String> orderBorrowerAddress = new LinkedHashMap<>();
	public static List<Map<String, String>> inquiry = new ArrayList<>();
	public static List<Map<String, String>> sourceOfInfo = new ArrayList<>();

	public static Map<String, String> ficoScore = new LinkedHashMap<>();

	public void getPdfUrl() {
		driver.getCurrentUrl();
	}

	public static String pdfReader(String pdfUrl) throws IOException {
		URL url = new URL(pdfUrl);
		InputStream is = url.openStream();
		BufferedInputStream fileParse = new BufferedInputStream(is);
		PDDocument document = PDDocument.load(fileParse);
		return new PDFTextStripper().getText(document);
	}

	public static void parsePdfContent(String url) throws IOException {

		textFromPdf = new StringBuilder( pdfReader( url ) );
		System.out.println("URL = " + url);
//		System.out.println("============Start of Report===========");
//		Logs.info( "PDF Content = " + textFromPdf );
//		System.out.println("============End of Report===========");
		// Parsing begins
		orderDetails.put( "OrderId",  parseSingularContent(PdfFieldConstants.orderId));
		orderDetails.put( "CompletedDateTime", parseSingularContent(PdfFieldConstants.dateCompleted) );
		orderDetails.put( "ByUserId", parseSingularContent(PdfFieldConstants.reqBy) );
		orderDetails.put( "OrderDateTime", parseSingularContent(PdfFieldConstants.dateOrdered) );
		orderDetails.put( "LoanType", parseSingularContent(PdfFieldConstants.loanType) );
		orderDetails.put( "CustomerReferenceNumber", parseSingularContent(PdfFieldConstants.refNum) );

		System.out.println("==========Order Details=================");
		for (Map.Entry<String, String> entry: orderDetails.entrySet()) {
			System.out.println(entry.getKey() + "\t|\t" + entry.getValue());
		}

		String[] propertyAddress = parseAddress( PdfFieldConstants.propertyAdd );
		orderSubjectProperty.put( "AddressLine1", propertyAddress[0] );
		orderSubjectProperty.put( "City", propertyAddress[1] );
		orderSubjectProperty.put( "State", propertyAddress[2].split( " " )[0] );
		orderSubjectProperty.put( "Zip5Zip4", propertyAddress[2].split( " " )[1] );

		System.out.println("==========Subject Property Details=================");
		for (Map.Entry<String, String> entry: orderSubjectProperty.entrySet()) {
			System.out.println(entry.getKey() + "\t|\t" + entry.getValue());
		}

		String[] applicantName = parseApplicantInfo();
		orderBorrower.put( "LastName", applicantName[0]);
		orderBorrower.put( "FirstName", applicantName[1].split( " " )[0] );

		orderBorrower.put( "SSN", parseSingularContent(PdfFieldConstants.ssn) );
		orderBorrower.put( "DateofBirth", parseSingularContent(PdfFieldConstants.dob) );
		orderBorrower.put( "MaritalStatus", parseSingularContent(PdfFieldConstants.marStatus) );
		orderBorrower.put( "NumberOfDependents", parseSingularContent(PdfFieldConstants.dependents) );

		System.out.println("==========Order Borrower Details=================");
		for (Map.Entry<String, String> entry: orderBorrower.entrySet()) {
			System.out.println(entry.getKey() + "\t|\t" + entry.getValue());
		}

		String[] currentAddress = parseAddress( PdfFieldConstants.currentAdd );
		orderBorrowerAddress.put( "AddressLine1", currentAddress[0] );
		orderBorrowerAddress.put( "City", currentAddress[1] );
		orderBorrowerAddress.put( "State", currentAddress[2].split( " " )[0] );
		orderBorrowerAddress.put( "Zip5Zip4", currentAddress[2].split( " " )[1] );

		orderBorrowerAddress.put( "LengthOfStayInYears",  parseSingularContent(PdfFieldConstants.lengthOfStay));

		System.out.println("==========Current Address Details=================");
		for (Map.Entry<String, String> entry: orderBorrowerAddress.entrySet()) {
			System.out.println(entry.getKey() + "\t|\t" + entry.getValue());
		}

		String[] scoreModels = parseCompositeContent(PdfFieldConstants.scoreModels, PdfFieldConstants.tradeLine);
		ficoScore.put( "FicoScore", scoreModels[1].split( " " )[1] );
		ficoScore.put( "Reason1Code", scoreModels[2].split( " " )[0] );
		ficoScore.put( "Reason2Code", scoreModels[3].split( " " )[0] );
		ficoScore.put( "Reason3Code", scoreModels[4].split( " " )[0] );

		System.out.println("==========Score Model Details=================");
		for (Map.Entry<String, String> entry: ficoScore.entrySet()) {
			System.out.println(entry.getKey() + "\t|\t" + entry.getValue());
		}

//		inquiry
		String[] inquiries = parseCompositeContent(PdfFieldConstants.inquiries, PdfFieldConstants.publicRecords);
		for (String inq : inquiries) {
			Map<String, String> inquiryRow = new LinkedHashMap<>();
			inquiryRow.put("BureauCode", inq.split( " " )[0]);
			inquiryRow.put("ECOA", inq.split( " " )[1]);
			inquiryRow.put("DateOfInquiry", inq.split( " " )[2]);
			inquiryRow.put("CustomerName", inq.split( " " )[3]);
			inquiry.add( inquiryRow );
		}


		System.out.println("==========Inquiries Details=================");
		for (Map<String, String> map : inquiry) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				System.out.println(entry.getKey() + "\t|\t" + entry.getValue());
			}
			System.out.println("========================");
		}

//		source of info
		String[] soi = parseCompositeContent(PdfFieldConstants.sourceOfInfo, PdfFieldConstants.creditors);
		Map<String, String> soiDictionary = new LinkedHashMap<>();
		soiDictionary.put( "Name", parseSoi( soi, "NAME: " ));
		soiDictionary.put( "SSN", parseSoi( soi,"SSN: " ) );

		int count = 0;
		for (String s : soi) {
			if (s.contains( "ADDRESS : " )) {
				soiDictionary.put( "Address" + count++, s.substring(
						PdfFieldConstants.soiAddress.length(), s.indexOf( PdfFieldConstants.soiReported ) ) );
			}
		}
		soiDictionary.put( "Employer", parseSoi( soi,"EMPLOYER: " ) );
		sourceOfInfo.add( soiDictionary );

		System.out.println("==========SOI Details=================");
		for (Map<String, String> map : sourceOfInfo) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				System.out.println(entry.getKey() + "\t|\t" + entry.getValue());
			}
			System.out.println("========================");
		}
	}

	private static String parseSoi(String[] textToParse, String header) {

		for (String row : textToParse) {
			if (row.contains( header )) {
				return row.substring( header.length());
			}
		}
		throw new AutomationException( header + " field is not present" );
	}

	private static String[] parseCompositeContent(String pdfField, String nextField) {

		int orderPosition = textFromPdf.indexOf( pdfField );

		if (orderPosition != -1) {

			textFromPdf.delete( 0, orderPosition +  pdfField.length() );
			return textFromPdf.substring( 0, textFromPdf.indexOf(nextField) ).split( "\r\n" );
		}
		throw new AutomationException( "PDF field not present " + pdfField );
	}

	private static String[] parseApplicantInfo() {

		int orderPosition = textFromPdf.indexOf( PdfFieldConstants.applicant );

		if (orderPosition != -1) {

			textFromPdf.delete( 0, orderPosition +  PdfFieldConstants.applicant.length() );
			return textFromPdf.toString().split( ", ", 2 );
		}
			throw new AutomationException("PDF Field not present " + PdfFieldConstants.applicant );
	}

	private static String[] parseAddress(String addressField) {

		int orderPosition = textFromPdf.indexOf( addressField );

		if (orderPosition != -1) {
			textFromPdf.delete( 0, orderPosition +  addressField.length() );
			return textFromPdf.toString().split( "\r\n", 4 );
		}
			throw new AutomationException("PDF Field not present " + addressField);
	}

	private static String parseSingularContent(String pdfField) {

		int orderPosition = textFromPdf.indexOf( pdfField );
		if (orderPosition != -1) {
			textFromPdf.delete( 0, orderPosition +  pdfField.length() );
			return textFromPdf.substring( 0, Math.min( textFromPdf.indexOf( "\n" ), textFromPdf.indexOf( " " ) )).replace( "\r", "" );
		}
		throw new AutomationException( "Error while parsing " + pdfField);
	}
	
	public static String pdfOCRReader(String pdfImageUrl) throws IOException {
		Ocr.setUp(); // one time setup
		Ocr ocr = new Ocr(); // create a new OCR engine
		ocr.startEngine("eng", Ocr.SPEED_FASTEST); // English
		String s = ocr.recognize(new File[] { new File(pdfImageUrl) }, Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT);	
		ocr.stopEngine();
		return s;
	}
}