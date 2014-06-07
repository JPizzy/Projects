package com.projects.lol.statcalculator;

/**
 * Rune Calculator for League of Legends<br><br>
 * Calculates the highest DPS achievable using flat Attack Damage(AD)
 * and Attack Speed(AS) Runes.
 * 
 * @author Jaspal
 */
public class RuneCalculator {

	static final int MARK_SLOTS = 9;
	static final double AD_MARK = 0.95;
	static final double AS_MARK = 0.017;
	
	static final int QUINT_SLOTS = 3;
	static final double AD_QUINT = 2.25;
	static final double AS_QUINT = 0.045;
	
	
	public static void main(String[] args) {
		// DPS = baseAS * (bonusAS + 1) * (baseAD + bonusAD) 
		double currentDPS;
		double maxDPS = 0;
		double baseAD = 49;
		double baseAS = 0.679;
		double bonusAD = 0;
		double bonusAS = 0;
		
		int bestMarks = 0;
		int bestQuints = 0;
		int numOfASMarks;
		int numOfASQuints;
		
		for(int numOfADMarks = 0; numOfADMarks < MARK_SLOTS; numOfADMarks++) {
			for(int numOfADQuints = 0; numOfADQuints < QUINT_SLOTS; numOfADQuints++) {
				numOfASMarks = MARK_SLOTS - numOfADMarks;
				numOfASQuints = QUINT_SLOTS - numOfADQuints;
				bonusAD = (numOfADMarks * AD_MARK) + (numOfADQuints * AD_QUINT);
				bonusAS = (numOfASMarks * AS_MARK) + (numOfASQuints * AS_QUINT);
				currentDPS = (baseAS * (1 + bonusAS)) * (baseAD + bonusAD);
				
				if(currentDPS > maxDPS) {
					maxDPS = currentDPS;
					bestMarks = MARK_SLOTS - numOfASMarks;
					bestQuints = QUINT_SLOTS - numOfASQuints;
				}
			}
		}
		
		System.out.println("The best combination of runes is \n");
		System.out.println("Flat AD Marks: " + bestMarks);
		System.out.println("Flat AS Marks: " + (MARK_SLOTS - bestMarks));
		System.out.println("\nFlat AD Quints: " + bestQuints);
		System.out.println("Flat AS Quints: " + (QUINT_SLOTS - bestQuints));
		System.out.println("DPS: " + maxDPS);
	}
}

