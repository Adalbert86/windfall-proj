package com.padoli.windfall.vojtech.exceptions;

/**
 * 
 * @author vojtech
 *
 */
public class DivisionByZeroException extends ExpressionManagerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8386424344500784195L;

	
	public DivisionByZeroException() {
		super("Division by zero!");
	}
}
