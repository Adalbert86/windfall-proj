package com.padoli.windfall.vojtech.utils;

import com.padoli.windfall.vojtech.models.ArithmeticOperator;
import com.padoli.windfall.vojtech.models.LBracketToken;
import com.padoli.windfall.vojtech.models.NumericToken;
import com.padoli.windfall.vojtech.models.Operator;
import com.padoli.windfall.vojtech.models.OperatorToken;
import com.padoli.windfall.vojtech.models.RBracketToken;
import com.padoli.windfall.vojtech.models.VariableToken;

/**
 * 
 * @author vojtech
 *
 */
public class TokenBuilder {

	public static NumericToken numeric(float f) {

		return new NumericToken(f);
	}

	public static OperatorToken operator(Operator o) {

		if (o == Operator.LBRACKET)
			return lbracket();
		else if (o == Operator.RBRACKET)
			return rbracket();
		else
			return new ArithmeticOperator(o);
	}

	public static VariableToken variable(String str) {

		return new VariableToken(str);
	}

	public static LBracketToken lbracket() {

		return new LBracketToken();
	}

	public static RBracketToken rbracket() {

		return new RBracketToken();
	}

}
