package com.padoli.windfall.vojtech.models;

/**
 * 
 * @author vojtech
 *
 */
public abstract class Token {

	protected String value;

	public String getStringValue() {
		
		return this.value;
	}
	
	@Override
	public String toString() {

		return this.value;
	}

}
