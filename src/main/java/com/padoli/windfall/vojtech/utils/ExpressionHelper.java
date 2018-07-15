package com.padoli.windfall.vojtech.utils;

import java.util.ArrayList;
import java.util.List;

import com.padoli.windfall.vojtech.exceptions.ExpressionManagerException;
import com.padoli.windfall.vojtech.models.ArithmeticOperator;
import com.padoli.windfall.vojtech.models.LBracketToken;
import com.padoli.windfall.vojtech.models.NumericToken;
import com.padoli.windfall.vojtech.models.OperatorToken;
import com.padoli.windfall.vojtech.models.RBracketToken;
import com.padoli.windfall.vojtech.models.Token;
import com.padoli.windfall.vojtech.models.VariableToken;

/**
 * 
 * @author vojtech
 *
 */
public class ExpressionHelper {

	public static boolean isAlphaNumeric(char ch) {

		return isDigit(ch) || isAlpha(ch);
	}

	public static boolean isAlpha(char ch) {

		return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
	}

	public static boolean containsAlpha(String expr) {

		for (char ch : expr.toCharArray())
			if (isAlpha(ch))
				return true;

		return false;

	}

	public static boolean isDigit(char ch) {

		return ch >= '0' && ch <= '9';
	}

	public static boolean isOperator(char ch) {

		return ch == '+' || ch == '-' || ch == '*' || ch == '/';
	}

	public static boolean isOpeningBracket(char ch) {

		return ch == '(';
	}

	public static boolean isClosingBracket(char ch) {

		return ch == ')';
	}

	public static boolean isBracket(char ch) {

		return isOpeningBracket(ch) || isClosingBracket(ch);
	}

	public static List<Token> tokenize(String expression) {

		List<Token> tokens = new ArrayList<>();

		char[] chars = expression.toCharArray();

		int i = 0;
		while (i < chars.length) {

			if (isAlphaNumeric(chars[i])) {

				StringBuffer sb = new StringBuffer();

				while (i < chars.length && isAlphaNumeric(chars[i]))
					sb.append(chars[i++]);

				String alnumtoken = sb.toString();
				if (containsAlpha(alnumtoken)) {

					tokens.add(new VariableToken(alnumtoken));
				} else {

					tokens.add(new NumericToken(Float.valueOf(alnumtoken)));
				}

			} else if (isOperator(chars[i])) {

				OperatorToken ot = new ArithmeticOperator(chars[i++]);
				tokens.add(ot);

			} else if (isBracket(chars[i])) {

				Token t;

				if (isOpeningBracket(chars[i]))
					t = new LBracketToken();
				else if (isClosingBracket(chars[i]))
					t = new RBracketToken();
				else
					throw new ExpressionManagerException("Unexpected type of a bracket");

				tokens.add(t);
				i++;

			}

			else if (chars[i] == ' ') {

				i++;

			} else {

				throw new ExpressionManagerException("Invalid syntax!");
			}

		}

		return tokens;
	}

}
