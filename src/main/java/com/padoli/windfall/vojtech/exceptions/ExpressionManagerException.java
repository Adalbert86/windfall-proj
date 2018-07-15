package com.padoli.windfall.vojtech.exceptions;

/**
 * 
 * @author vojtech
 *
 */
public class ExpressionManagerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3226329952226551619L;

	public ExpressionManagerException(String message) {
		super(message);
	}

	public ExpressionManagerException(String message, Throwable t) {
		super(message, t);
	}
}
