package com.padoli.windfall.vojtech.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.padoli.windfall.vojtech.exceptions.DivisionByZeroException;
import com.padoli.windfall.vojtech.exceptions.ExprUnresolvedVariablesException;
import com.padoli.windfall.vojtech.exceptions.ExpressionManagerException;
import com.padoli.windfall.vojtech.exceptions.MismatchedBracketsException;
import com.padoli.windfall.vojtech.models.ArithmeticOperator;
import com.padoli.windfall.vojtech.models.BracketToken;
import com.padoli.windfall.vojtech.models.LBracketToken;
import com.padoli.windfall.vojtech.models.NumericToken;
import com.padoli.windfall.vojtech.models.Operator;
import com.padoli.windfall.vojtech.models.OperatorToken;
import com.padoli.windfall.vojtech.models.RBracketToken;
import com.padoli.windfall.vojtech.models.Token;
import com.padoli.windfall.vojtech.models.VariableToken;
import com.padoli.windfall.vojtech.utils.ExpressionHelper;
import com.padoli.windfall.vojtech.utils.TokenBuilder;

/**
 * 
 * @author vojtech
 *
 */
public class ExpressionManager {

	private static ExpressionManager instance;

	private ExpressionManager() {
	}

	private float applyOp(char op, float b, float a) {
		switch (op) {
		case '+':
			return a + b;
		case '-':
			return a - b;
		case '*':
			return a * b;
		case '/':
			if (b == 0)
				throw new DivisionByZeroException();
			return a / b;
		}
		return 0;
	}

	private boolean tokenListContainsUnresolvedVars(List<Token> tokenList) {

		for (Token t : tokenList) {

			if (t instanceof VariableToken)
				return true;

		}

		return false;
	}

	private boolean isTokenBracket(Token token) {

		return isTokenOpeningBracket(token) || isTokenClosingBracket(token);
	}

	private boolean isTokenOpeningBracket(Token token) {

		return (token instanceof LBracketToken);
	}

	private boolean isTokenClosingBracket(Token token) {

		return (token instanceof RBracketToken);
	}

	private boolean isTokenNumeric(Token token) {

		return token instanceof NumericToken;
	}

	private boolean isArithmeticOperator(Token token) {

		return (token instanceof ArithmeticOperator);
	}

	private int getPrecedence(Operator op) {

		return getPrecedence(op.getChar());
	}

	private int getPrecedence(char op) {

		switch (op) {

		case '^':
			return 4;
		case '*':
		case '/':
			return 3;
		case '+':
		case '-':
			return 2;
		default:
			throw new ExpressionManagerException("Cannot get precedence value for " + op);

		}
	}

	private List<Token> preparePostfixExpression(List<Token> tokens) {

		List<Token> postfixRes = new ArrayList<>();
		Stack<OperatorToken> operators = new Stack<OperatorToken>();

		for (Token token : tokens) {

			if (isTokenNumeric(token)) {

				postfixRes.add(token);

			} else if (isArithmeticOperator(token)) {

				OperatorToken operator = (OperatorToken) token;
				while (!operators.isEmpty() && operators.peek() instanceof OperatorToken
						&& !(operators.peek() instanceof BracketToken)
						&& getPrecedence(operators.peek().getOperator()) >= getPrecedence(operator.getOperator())) {

					postfixRes.add(operators.pop());
				}

				operators.push(operator);

			} else if (isTokenOpeningBracket(token)) {

				// operators.push((OperatorToken)token);
				operators.push(TokenBuilder.lbracket());

			} else if (isTokenClosingBracket(token)) {

				while (!operators.isEmpty() && !isTokenOpeningBracket(operators.peek())) {

					postfixRes.add(operators.pop());
				}

				// pop the left bracket from the stack
				if (operators.empty()) {
					throw new MismatchedBracketsException();
				} else {
					postfixRes.add(operators.pop());
				}

			}
		}

		while (!operators.isEmpty()) {

			if (isTokenBracket(operators.peek())) {
				throw new MismatchedBracketsException();
			}

			postfixRes.add(operators.pop());
		}

		return postfixRes;
	}

	public Float evaluate(List<Token> tokenList) {

		if (tokenList == null || tokenList.isEmpty())
			return 0f;

		if (tokenListContainsUnresolvedVars(tokenList))
			throw new ExprUnresolvedVariablesException();

		List<Token> postfixOrderedTokens = preparePostfixExpression(tokenList);

		Stack<Float> stack = new Stack<Float>();
		float operand1, operand2;

		for (Token el : postfixOrderedTokens) {

			if (el instanceof NumericToken) {

				NumericToken nt = (NumericToken) el;
				stack.push(nt.getFloatValue());

			} else if (el instanceof ArithmeticOperator) {

				operand1 = stack.pop();
				operand2 = stack.pop();
				Operator op = ((OperatorToken) el).getOperator();

				stack.push(applyOp(op.getChar(), operand1, operand2));
			}
		}

		return stack.isEmpty() ? 0f : stack.pop();
	}

	public Float evaluate(String expression) {

		if (expression.isEmpty())
			return 0f;

		List<Token> tokens = ExpressionHelper.tokenize(expression);

		return evaluate(tokens);
	}

	public static ExpressionManager getInstance() {

		if (instance == null)
			instance = new ExpressionManager();

		return instance;
	}

}
