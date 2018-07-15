package com.padoli.windfall.vojtech.models;

/**
 * 
 * @author vojtech
 *
 */
public enum Operator {

	PLUS('+'), MINUS('-'), DIVIDE('/'), MULTIPLY('*'), LBRACKET('('), RBRACKET(')');

	private char ch;

	private Operator(char ch) {

		this.ch = ch;
	}

	public char getChar() {
		return this.ch;
	}

	public static Operator getByChar(char ch) {

		switch (ch) {

		case '+':
			return PLUS;
		case '-':
			return MINUS;
		case '*':
			return MULTIPLY;
		case '/':
			return DIVIDE;
		case '(':
			return LBRACKET;
		case ')':
			return RBRACKET;

		default:
			throw new RuntimeException("Invalid operator");

		}
	}

}
