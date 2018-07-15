package com.padoli.windfall.vojtech.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.padoli.windfall.vojtech.models.Operator;
import com.padoli.windfall.vojtech.models.Token;
import com.padoli.windfall.vojtech.utils.ExpressionHelper;
import com.padoli.windfall.vojtech.utils.TokenBuilder;

/**
 * 
 * @author vojtech
 *
 */
public class ExpressionHelperTest {

	private boolean isTokensMatch(List<Token> actual, Token[] expected) {

		if (actual == null || actual.size() != expected.length)
			return false;

		for (int i = 0; i < expected.length; i++) {

			String a = expected[i].toString();
			String b = actual.get(i).toString();

			if (!a.equals(b))
				return false;

		}

		return true;
	}

	@Test
	public void testTokenizer() {

		String expr;
		Token[] expectedTokens;
		List<Token> outTokens;

		expr = "2+3-5*100";
		expectedTokens = new Token[] { TokenBuilder.numeric(2), TokenBuilder.operator(Operator.PLUS),
				TokenBuilder.numeric(3), TokenBuilder.operator(Operator.MINUS), TokenBuilder.numeric(5),
				TokenBuilder.operator(Operator.MULTIPLY), TokenBuilder.numeric(100) };
		outTokens = ExpressionHelper.tokenize(expr);
		assertTrue(isTokensMatch(outTokens, expectedTokens));

		expr = "B2+2";
		expectedTokens = new Token[] { TokenBuilder.variable("B2"), TokenBuilder.operator(Operator.PLUS),
				TokenBuilder.numeric(2) };
		outTokens = ExpressionHelper.tokenize(expr);
		assertTrue(isTokensMatch(outTokens, expectedTokens));

	}

}
