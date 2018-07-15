package com.padoli.windfall.vojtech.models;

/**
 * 
 * @author vojtech
 *
 */
public class Cell {

	private String name;
	private String payload;

	public Cell(String name, String payload) {
		super();
		this.name = name;
		this.payload = payload;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

}
