package com.padoli.windfall.vojtech.exceptions;

/**
 * 
 * @author vojtech
 *
 */
public class ExprUnresolvedVariablesException extends ExpressionManagerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8445023006241759128L;

	public ExprUnresolvedVariablesException() {
		super("Expression contains unresolved variables!");
	}

}
