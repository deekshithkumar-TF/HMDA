package com.HMDA.helper;

import com.HMDA.reports.Logs;

public class AutomationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2416943227558284110L;
	
	public AutomationException() {
		super();
		Logs.error("Automation Exception !!!!");
	}
	
	public AutomationException(String message, Throwable cause) {
		super(message, cause);
		Logs.error("Automation exception with message: " + message + "and cause: " + cause.toString());
	}

	public AutomationException(String message) {
		super(message);
		Logs.error("Automation exception with message: " + message);
	}

}
