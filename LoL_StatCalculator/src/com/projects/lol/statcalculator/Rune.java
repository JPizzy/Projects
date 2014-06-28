package com.projects.lol.statcalculator;

public class Rune {

	private final String name;
	private final String type;
	private final String statType;
	private final double value;
	

	public Rune(String name, String type, String statType, double value) {
		this.name = name;
		this.type = type;
		this.statType = statType;
		this.value = value;
	}

	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public String getStatType() {
		return statType;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return this.name + " - " + this.value + this.statType;
	}
	
}
