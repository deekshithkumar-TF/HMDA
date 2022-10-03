package com.HMDA.helper;

import com.HMDA.constants.ConfigConstants;
import com.HMDA.reports.Logs;


import java.awt.datatransfer.StringSelection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UploadFiles {

	/*
	 * Funtion to set the value in clipboard
	 */
	public static void setClipboardData(String string) {
		// StringSelection is a class that can be used for copy and paste
		// operations.
		StringSelection stringSelection = new StringSelection(string);
		//Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}


	public static void uploadFile(String filePath) {

		try {
			Process p = Runtime.getRuntime().exec("\"" + ConfigConstants.resources + "FileUpload.exe" + "\" " + filePath);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line;
			while ((line = input.readLine()) != null) {
				Logs.info(line);
			}

			int exitVal = p.waitFor();
			Logs.info("Exited with error code " + exitVal);

		} catch (IOException e) {
			throw new AutomationException( "Unable to upload file " + e.getMessage() );
		} catch (InterruptedException e) {
			throw new AutomationException( e.getMessage() );
		}
		}
	}

