package com.padoli.windfall.vojtech.models;

/**
 * 
 * @author vojtech
 *
 */
public class NumericToken extends Token {

	public NumericToken(float f) {

		super();
		this.value = String.valueOf(f);
	}

	public Float getFloatValue() {

		return Float.valueOf(getStringValue());
	}

}
