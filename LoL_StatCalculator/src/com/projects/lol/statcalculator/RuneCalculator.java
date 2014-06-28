package com.projects.lol.statcalculator;

import java.util.HashMap;

/**
 * Rune Calculator for League of Legends<br><br>
 * Calculates the highest DPS achievable using flat Attack Damage(AD)
 * and Attack Speed(AS) Runes.
 * 
 * @author Jaspal
 */
public class RuneCalculator {

	// Values as of June 6, 2014
//	private static final int MARK_SLOTS = 9;
//	private static final double AD_MARK = 0.95;
//	private static final double AS_MARK = 0.017;
//	
//	private static final int QUINT_SLOTS = 3;
//	private static final double AD_QUINT = 2.25;
//	private static final double AS_QUINT = 0.045;
	
	public RuneCalculator() {
		//Create runes
//		Rune markAD = new Rune("AD Mark", "Mark", "AD", AD_MARK);
//		Rune markAS = new Rune("AS Mark", "Mark", "AS", AS_MARK);
//		Rune quintAD = new Rune("AD Quint", "Quint", "AD", AD_QUINT);
//		Rune quintAS = new Rune("AS Quint", "Quint", "AS", AS_QUINT);
		//move runes initialization to RunePage
		//create page
		//calculate total dps
		//find page with highest dps
	}
	
	public RunePage calculateDPSPage(Champion champ) {
		//DPS = baseAS * (bonusAS + 1) * (baseAD + bonusAD) 
		//double currentDPS;
		//double maxDPS = 0;
		//RunePage runePage = new RunePage();
		//runePage.generatePage();
		//runePage.getBestDPSPage();
		//currentDPS = calculateDPS(champ, runePage);
		RunePageGenerator generator = new RunePageGenerator(champ);
		return generator.getDPSPage();
	}
	
//	private double calculateDPS(Champion champ, RunePage page) {
//		HashMap<String, Double> runePageStats = page.getStats();
//		double bonusAtkDmg = 0;
//		double bonusAtkSpd = 0;
//		if(runePageStats.containsKey("AS") ) {
//			bonusAtkSpd = runePageStats.get("AS");			
//		}
//		if(runePageStats.containsKey("AD")) {			
//			bonusAtkDmg = runePageStats.get("AD");
//		}
//		return (champ.getBaseAS()) * (bonusAtkSpd + 1) * (champ.getBaseAD() + bonusAtkDmg);
//	}
	
}

