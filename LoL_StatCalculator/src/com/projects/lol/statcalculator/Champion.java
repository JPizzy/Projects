package com.projects.lol.statcalculator;

public class Champion {

	private String name;
	private double baseAD;
	private double baseAS;
	
	public Champion(String name, double baseAD, double baseAS) {
		this.name = name;
		this.baseAD = baseAD;
		this.baseAS = baseAS;
	}

	public String getName() {
		return name;
	}

	public double getBaseAD() {
		return baseAD;
	}

	public double getBaseAS() {
		return baseAS;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Champion other = (Champion) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
