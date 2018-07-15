package com.padoli.windfall.vojtech.exceptions;

/**
 * 
 * @author vojtech
 * 
 *         The idea would be to have multiple different (runtime) exception
 *         classes to keep the project well organized
 *
 */
public class SpreadSheetRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1892074180212518101L;

	public SpreadSheetRuntimeException(String message) {
		super(message);
	}

	public SpreadSheetRuntimeException(String message, Throwable t) {
		super(message, t);
	}
	
}
