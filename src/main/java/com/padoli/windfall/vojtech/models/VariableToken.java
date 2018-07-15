package com.padoli.windfall.vojtech.models;

import com.padoli.windfall.vojtech.exceptions.ExpressionManagerException;
import com.padoli.windfall.vojtech.utils.ExpressionHelper;

/**
 * 
 * @author vojtech
 *
 */
public class VariableToken extends Token {

	public VariableToken(String var) {

		if (var == null || var.isEmpty())
			throw new ExpressionManagerException("Cannot intialize empty variable token");

		if (!ExpressionHelper.isAlpha(var.charAt(0)))
			throw new ExpressionManagerException("Variable token must contain alpha character at the beginning");

		this.value = var;
	}

}
