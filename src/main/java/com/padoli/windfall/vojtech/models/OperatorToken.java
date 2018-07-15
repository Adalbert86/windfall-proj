package com.padoli.windfall.vojtech.models;

/**
 * 
 * @author vojtech
 *
 */
public abstract class OperatorToken extends Token {

	public OperatorToken(Operator op) {

		this.value = String.valueOf(op.getChar());
	}

	public OperatorToken(char chOp) {

		this.value = String.valueOf(chOp);
	}

	public Operator getOperator() {

		return Operator.getByChar(this.value.charAt(0));
	}

}
