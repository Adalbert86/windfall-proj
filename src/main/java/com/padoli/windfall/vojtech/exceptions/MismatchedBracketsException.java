package com.padoli.windfall.vojtech.exceptions;

/**
 * 
 * @author vojtech
 *
 */
public class MismatchedBracketsException extends ExpressionManagerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2875535301440371242L;

	public MismatchedBracketsException() {
		super("Mismatched brackets");
	}
}
